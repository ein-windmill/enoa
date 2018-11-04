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

import io.enoa.http.EoHttpConfig;
import io.enoa.http.protocol.HttpResponseBody;
import io.enoa.http.protocol.chunk.Chunk;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Set;

public class _HttpHelperChunkedResponse extends AbstractResponse {


//  private static final byte R = (byte) '\r';
//  private static final byte N = (byte) '\n';

  public _HttpHelperChunkedResponse(EoHttpConfig config, HttpURLConnection conn, InputStream inputstream, Charset charset, Chunk chunk) {
    super(config, conn, inputstream, charset);
    super.extrabody(this.extra(chunk, charset));
  }

  private _IBodyExtra extra(Chunk chunk, Charset charset) {
    return stream -> {
      String transferencoding = super.header("Transfer-Encoding");
      boolean ischunked = "chunked".equalsIgnoreCase(transferencoding);

      // check other chunk type
      if (!ischunked) {
        Set<String> chunktypes = config().chunktype();
        for (String chunktype : chunktypes) {
          if (!chunktype.equalsIgnoreCase(super.header("Content-Type")))
            continue;
          ischunked = true;
          break;
        }
      }

      if (!ischunked || chunk == null) {
        return _HttpHelperResponse.extra(charset).extra(stream);
      }

      return this.chunkread(stream, chunk, charset);
    };
  }

  private HttpResponseBody chunkread(InputStream stream, Chunk chunk, Charset charset) throws IOException {
    ChunkCaller caller = new ChunkCaller(chunk, charset);
    try (ByteArrayOutputStream swap = new ByteArrayOutputStream()) {
      byte[] buff = new byte[2048];
      int rc;
      while ((rc = stream.read(buff)) != -1) {
        caller.call(Arrays.copyOf(buff, rc));
        swap.write(buff, 0, rc);
      }

      byte[] bytes = swap.toByteArray();
      caller.destroy();
      return HttpResponseBody.create(bytes, super.charset());
    }
  }


}
