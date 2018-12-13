/*
 * Copyright (c) 2018, enoa (fewensa@enoa.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.enoa.repeater.http;

import io.enoa.toolkit.EoConst;
import io.enoa.toolkit.binary.EnoaBinary;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.toolkit.file.FileKit;
import io.enoa.toolkit.stream.StreamKit;
import io.enoa.toolkit.thr.EoException;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Path;

public abstract class ResponseBody {

  public abstract long length();

  public abstract byte[] bytes();

  public ByteBuffer buffer() {
    return ByteBuffer.wrap(this.bytes());
  }

  public EnoaBinary binary() {
    return EnoaBinary.create(this.bytes());
  }

  public static ResponseBody create(String content) {
    return create(content, EoConst.CHARSET);
  }

  public static ResponseBody create(String content, Charset charset) {
    if (content == null)
      throw new EoException(EnoaTipKit.message("eo.tip.repeater.resp_body_cant_null"));
    byte[] bytes = content.getBytes(charset);
    return create(bytes);
  }

  public static ResponseBody create(Path path) {
    if (path == null)
      throw new EoException(EnoaTipKit.message("eo.tip.repeater.resp_body_cant_null"));
    EnoaBinary ebinary = FileKit.read(path);
    byte[] bytes = ebinary.bytes();
    return create(bytes);
  }

  public static ResponseBody create(byte[] bytes) {
//    return create(ByteBuffer.wrap(bytes));
    if (bytes == null)
      throw new EoException(EnoaTipKit.message("eo.tip.repeater.resp_body_cant_null"));
    return new ResponseBody() {
      @Override
      public long length() {
        return bytes.length;
      }

      @Override
      public byte[] bytes() {
        return bytes;
      }
    };

  }

  @Override
  public String toString() {
    try {
      byte[] bytes = this.bytes();
      if (CollectionKit.isEmpty(bytes))
        return null;
      String body = StreamKit.string(bytes, EoConst.CHARSET);
      if (body == null)
        return "";
      return body.substring(0, body.length() < 2048 ? body.length() - 1 : 2047);
    } catch (Exception e) {
      return e.getMessage();
    }
  }
}
