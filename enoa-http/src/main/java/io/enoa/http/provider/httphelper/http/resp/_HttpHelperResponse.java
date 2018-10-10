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
package io.enoa.http.provider.httphelper.http.resp;

import io.enoa.http.protocol.HttpResponseBody;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.nio.charset.Charset;

public class _HttpHelperResponse extends AbstractResponse {

  public _HttpHelperResponse(HttpURLConnection conn, InputStream inputStream, Charset charset) {
    super(conn, inputStream, charset);
    super.extrabody(this.extra());
  }

//  @Override
//  protected HttpResponseBody extrabody(InputStream inputstream) throws IOException {
//
//  }


  private _IBodyExtra extra() {
    return stream -> {
      try (ByteArrayOutputStream swap = new ByteArrayOutputStream()) {
        byte[] buff = new byte[8129];
        int rc = 0;
        while ((rc = stream.read(buff, 0, 1000)) > 0) {
          swap.write(buff, 0, rc);
        }

        byte[] bytes = swap.toByteArray();
        return HttpResponseBody.create(bytes, super.charset());
      }
    };
  }

}
