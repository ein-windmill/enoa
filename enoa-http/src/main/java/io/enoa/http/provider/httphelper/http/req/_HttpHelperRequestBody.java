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
package io.enoa.http.provider.httphelper.http.req;

import io.enoa.http.protocol.enoa.HttpRequestBody;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

public abstract class _HttpHelperRequestBody implements HttpRequestBody {

  private static final Charset DEF_CHARSET = Charset.forName("UTF-8");

  public abstract int contentLength();

  @Override
  public abstract Charset charset();

  @Override
  public abstract byte[] bytes();


  public static _HttpHelperRequestBody create(final byte[] bytes, final Charset charset) {
    return create(bytes, charset, bytes.length);
  }

  public static _HttpHelperRequestBody create(final String body, final Charset charset) {
    byte[] bytes = body.getBytes(charset == null ? DEF_CHARSET : charset);
    return create(bytes, charset, bytes.length);
  }

  public static _HttpHelperRequestBody create(final byte[] bytes, final Charset charset, final int contentLength) {
    return new _HttpHelperRequestBody() {
      @Override
      public int contentLength() {
        return contentLength;
      }

      @Override
      public Charset charset() {
        return charset;
      }

      @Override
      public byte[] bytes() {
        return bytes;
      }
    };
  }

  public String string() {
//    return new String(this.content(), this.charset() == null ? DEF_CHARSET : this.charset());
    CharsetDecoder decoder = (this.charset() == null ? DEF_CHARSET : this.charset()).newDecoder();
    ByteBuffer byteBuffer = ByteBuffer.wrap(this.bytes());
    CharBuffer charBuffer = CharBuffer.allocate(byteBuffer.limit());
    decoder.decode(byteBuffer, charBuffer, true);
    charBuffer.flip();
    String ret = charBuffer.toString();
    charBuffer.clear();
    byteBuffer.clear();
    return ret;
  }

  @Override
  public String toString() {
    String ret = this.string();
    return ret.length() > 4000 ? ret.substring(0, 4000).concat("\n...") : ret;
  }
}
