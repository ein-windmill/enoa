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
import io.enoa.http.protocol.chunk.Chunk;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.nio.charset.Charset;

public class _HttpHelperChunkedResponse extends AbstractResponse {


  private static final byte R = (byte) '\r';
  private static final byte N = (byte) '\n';


  public _HttpHelperChunkedResponse(HttpURLConnection conn, InputStream inputstream, Charset charset, Chunk chunk) {
    super(conn, inputstream, charset);
    super.extrabody(this.extra(chunk));
  }

  private _IBodyExtra extra(Chunk chunk) {
    return stream -> {
      String transferencoding = super.header("Transfer-Encoding");
      boolean ischunked = "chunked".equalsIgnoreCase(transferencoding);

      if (!ischunked || chunk == null) {
        try (ByteArrayOutputStream swap = new ByteArrayOutputStream()) {
          int rc = 0;
          byte[] buff = new byte[8129];
          while ((rc = stream.read(buff, 0, 1000)) > 0)
            swap.write(buff, 0, rc);

          byte[] bytes = swap.toByteArray();
          return HttpResponseBody.create(bytes, super.charset());
        }
      }

//      try (ByteArrayOutputStream swap = new ByteArrayOutputStream();
//           ByteArrayOutputStream temp = new ByteArrayOutputStream()) {
//        byte[] buff = new byte[2];
//        int rc = 0;
//        while ((rc = stream.read(buff, 0, 2)) > 0) {
//          if (chunk.stopper() != null && chunk.stopper().stop())
//            return HttpResponseBody.create(swap.toByteArray(), super.charset());
//
//          byte curr = buff[0];
//
//          if (curr == R || curr == N) {
//            temp.write(buff, 0, rc);
//            byte[] bytes = temp.toByteArray();
//            chunk.runner().run(bytes);
//            swap.write(bytes);
//            temp.reset();
//            continue;
//          }
//          temp.write(buff, 0, rc);
//        }
//        byte[] bytes = swap.toByteArray();
//        return HttpResponseBody.create(bytes, super.charset());
//      }

      try (ByteArrayOutputStream swap = new ByteArrayOutputStream();
           ByteArrayOutputStream temp = new ByteArrayOutputStream()) {
        byte[] buff = new byte[1];
        int rc = 0;
        boolean needn = false;
        while ((rc = stream.read(buff, 0, 1)) > 0) {
          if (chunk.stopper() != null && chunk.stopper().stop())
            return HttpResponseBody.create(swap.toByteArray(), super.charset());
          byte curr = buff[0];

          // \r
          if (needn) {
            if (curr != N) {
              byte[] bytes = temp.toByteArray();
              chunk.runner().run(bytes);
              swap.write(bytes, 0, bytes.length);
              temp.reset();
              temp.write(buff, 0, rc);
              continue;
            }
          }
          // \r || \r\n
          if (curr == R) {
            needn = true;
            temp.write(buff, 0, rc);
            continue;
          }
          // \n
          if (curr == N) {
            needn = false;
            temp.write(buff, 0, rc);
            byte[] bytes = temp.toByteArray();
            chunk.runner().run(bytes);
            swap.write(bytes, 0, bytes.length);
            temp.reset();
            continue;
          }
          temp.write(buff, 0, rc);
        }
        byte[] bytes = swap.toByteArray();
        return HttpResponseBody.create(bytes, super.charset());
      }
    };
  }

}
