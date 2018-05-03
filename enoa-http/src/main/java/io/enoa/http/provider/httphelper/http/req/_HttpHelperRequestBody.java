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

import java.nio.charset.Charset;

public abstract class _HttpHelperRequestBody {

  private static final Charset DEF_CHARSET = Charset.forName("UTF-8");

  public abstract int contentLength();

  public abstract Charset charset();

  public abstract byte[] content();


  public static _HttpHelperRequestBody create(final byte[] content, final Charset charset) {
    return create(content, charset, content.length);
  }

  public static _HttpHelperRequestBody create(final String content, final Charset charset) {
    byte[] bytes = content.getBytes(charset == null ? DEF_CHARSET : charset);
    return create(bytes, charset, bytes.length);
  }

  public static _HttpHelperRequestBody create(final byte[] content, final Charset charset, final int contentLength) {
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
      public byte[] content() {
        return content;
      }
    };
  }

  public String string() {
    return new String(this.content(), this.charset() == null ? DEF_CHARSET : this.charset());
  }

  @Override
  public String toString() {
    String ret = this.string();
    return ret.length() > 4000 ? ret.substring(0, 4000).concat("\n...") : ret;
  }
}
