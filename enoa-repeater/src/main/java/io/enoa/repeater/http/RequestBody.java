/*
 * Copyright (c) 2018, enoa (ein.windmill@outlook.com)
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

import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.stream.StreamKit;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public abstract class RequestBody {

//  private byte[] binary;

  private RequestBody() {
  }

//  private RequestBody(String bodyString) {
//    this.bodyString = bodyString;
//  }

//  private RequestBody(byte[] bytes) {
//    this.bytes = bytes;
//  }

  public abstract Charset charset();

  public abstract ByteBuffer binary();

//  public byte[] binary() throws IOException {
//    if (this.binary != null)
//      return this.binary;
//    return StreamKit.bytes(this.byteStream());
//  }

  public String string() {
    return StreamKit.string(this.binary(), this.charset());
  }

  public String string(Charset charset) {
//    if (this.bodyString != null)
//      return this.bodyString;
//    if (this.byteStream() == null)
//      return null;
//    return StreamKit.string(this.byteStream(), charset);
    return StreamKit.string(this.binary(), charset);
  }

  public byte[] bytes() {
    return this.binary().array();
  }

//  public static RequestBody create(InputStream byteStream, Charset charset) {
//    if (byteStream == null)
//      return null;
//    return new RequestBody() {
//      @Override
//      public Charset charset() {
//        return charset;
//      }
//
//      @Override
//      public InputStream byteStream() {
//        return byteStream;
//      }
//    };
//  }

  public static RequestBody create(byte[] body, Charset charset) {
    if (CollectionKit.isEmpty(body))
      return null;
    return new RequestBody() {
      @Override
      public Charset charset() {
        return charset;
      }

      @Override
      public ByteBuffer binary() {
        return ByteBuffer.wrap(body);
      }
    };
  }

  public static RequestBody create(String body, Charset charset) {
    if (body == null)
      return null;
    return create(body.getBytes(charset), charset);
  }

}
