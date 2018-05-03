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
package io.enoa.repeater.provider.nanohttpd;

import fi.iki.elonen.NanoHTTPD;

import java.io.IOException;
import java.util.Map;

public class NaonohttpdTest extends NanoHTTPD {

  public NaonohttpdTest(int port) throws IOException {
    super(port);
    start(SOCKET_READ_TIMEOUT, false);
    System.out.println("\nRunning! Point your browsers to http://localhost:8080/ \n");
  }

  public static void main(String[] args) {
    try {
      new NaonohttpdTest(8000);
    } catch (IOException ioe) {
      System.err.println("Couldn't start server:\n" + ioe);
    }
  }

  @Override
  public Response serve(IHTTPSession session) {
    String msg = "<html><body><h1>Hello server</h1>\n";
    Map<String, String> parms = session.getParms();
    if (parms.get("username") == null) {
      msg += "<form action='?' method='get'>\n  <p>Your name: <input type='text' name='username'></p>\n" + "</form>\n";
    } else {
      msg += "<p>Hello, " + parms.get("username") + "!</p>";
    }
    return newFixedLengthResponse(msg + "</body></html>\n");
  }
}
