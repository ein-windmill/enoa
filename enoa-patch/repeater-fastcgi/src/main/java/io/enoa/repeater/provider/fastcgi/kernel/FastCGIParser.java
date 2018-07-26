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
package io.enoa.repeater.provider.fastcgi.kernel;

import io.enoa.log.Log;
import io.enoa.repeater.http.Cookie;
import io.enoa.repeater.http.Header;
import io.enoa.repeater.http.Response;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.text.TextKit;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;


class FastCGIParser {


  FastCGIRet parseRequest(InputStream inputStream, OutputStream outputStream) throws IOException {

    int requestId = 0;
    boolean closeConnection = true;
    byte[] data = null;
    Map<String, String> properties = null;

    FastCGIMessage message;
    do {
      message = new FastCGIMessage(inputStream);
      switch (message.type) {
        case FastCGIServer.BEGIN_REQUEST:
          if (requestId != 0) {
            Log.debug("reject extra request with id {}", message.requestId);
            //server tries to send multiplexed connection, but we process it only one by one, reject request:
            new FastCGIMessage(FastCGIServer.END_REQUEST, message.requestId, FastCGIServer.NO_MULTIPLEX_CONNECTION).write(outputStream);
          } else {
            requestId = message.requestId;
            closeConnection = (message.content[2] & FastCGIServer.FCGI_KEEP_CONN) == 0;
            int requestRole = ((message.content[0] & 0xff) << 8) | (message.content[1] & 0xff);
            if (requestRole != FastCGIServer.FCGI_RESPONDER) {
              throw new IOException("Only responder role is supported");
            }
            Log.debug("accept request id {}", requestId);
          }
          break;

        case FastCGIServer.STDIN:
          Log.debug("STDIN {}", message.contentLength);
          if (message.contentLength > 0) {
            if (data == null) {
              data = message.content;
            } else {
              byte[] concatenated = new byte[data.length + message.contentLength];
              System.arraycopy(data, 0, concatenated, 0, data.length);
              System.arraycopy(message.content, 0, concatenated, data.length, message.contentLength);
              data = concatenated;
            }
          }
          break;

        case FastCGIServer.PARAMETERS:
          if (message.contentLength > 0) {
            int[] length = new int[2];
            int offset = 0;
            properties = new HashMap<>();
            while (offset < message.contentLength) {
              for (int i = 0; i < 2; i++) {
                length[i] = message.content[offset++];
                if ((length[i] & 0x80) != 0) {
                  length[i] = ((length[i] & 0x7f) << 24) |
                    ((message.content[offset++] & 0xff) << 16) |
                    ((message.content[offset++] & 0xff) << 8) |
                    (message.content[offset++] & 0xff);
                }
              }
              String name = new String(message.content, offset, length[0]);
              String value = new String(message.content, offset + length[0], length[1]);
//                System.out.println("PARAM " + name + " = " + value);
              properties.put(name, value);
              offset += length[0] + length[1];
            }
          }
          break;
      }
    } while (message.type != FastCGIServer.STDIN || message.contentLength != 0);

    FastCGIRequest request = new FastCGIRequest.Builder()
      .prop(properties)
      .data(data)
      .build();
    return new FastCGIRet.Builder()
      .closeConn(closeConnection)
      .requestId(requestId)
      .request(request)
      .build();

  }


  byte[] parseResponse(Response response) {
    StringBuilder headerStr = new StringBuilder();
    for (Header header : response.headers()) {
//      headerStr.append(String.format("%s: %s\n", header.name(), header.value()));
      headerStr.append(TextKit.union(header.name(), ": ", header.value(), "\n"));
    }
    for (Cookie cookie : response.cookies()) {
//      headerStr.append(String.format("Set-Cookie: %s\n", cookie.toString()));
      headerStr.append(TextKit.union("Set-Cookie: ", cookie.toString(), "\n"));
    }
    if (response.headers().stream().noneMatch(h -> h.name().equalsIgnoreCase("content-type")))
      headerStr.append("Content-Type: ").append(response.contentType()).append("\n");
    headerStr.append("\r\n\r\n");
    byte[] headerBytes = headerStr.toString().getBytes(response.charset());

    byte[] bodyBytes = response.body().bytes();
    return CollectionKit.merge(headerBytes, bodyBytes);
  }

}
