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
package io.enoa.http.protocol;

import java.nio.charset.Charset;

public abstract class HttpResponseBody {

  public abstract Charset charset();

  public abstract byte[] bytes();


  public String string() {
    return this.string(this.charset());
  }

  public String string(Charset charset) {
    return new String(this.bytes(), charset);
  }

  public static HttpResponseBody create(byte[] bytes, Charset charset) {
    return new HttpResponseBody() {
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

  public static HttpResponseBody create(String body, Charset charset) {
    return new HttpResponseBody() {
      @Override
      public Charset charset() {
        return charset;
      }

      @Override
      public byte[] bytes() {
        return body.getBytes(charset);
      }
    };
  }

  @Override
  public String toString() {
    String show;
    try {
      show = this.string();
    } catch (Exception e) {
      show = String.format("null (ERROR : %s)", e.getMessage());
    }
    return show.length() > 3000 ? show.substring(0, 3000).concat("\n...") : show;
  }
}
