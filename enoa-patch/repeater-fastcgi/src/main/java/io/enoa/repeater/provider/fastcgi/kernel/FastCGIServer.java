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
package io.enoa.repeater.provider.fastcgi.kernel;

import io.enoa.log.Log;
import io.enoa.repeater.http.Response;
import io.enoa.toolkit.text.TextKit;
import io.enoa.toolkit.stream.StreamKit;
import io.enoa.toolkit.thr.EoException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public final class FastCGIServer {

  static final byte BEGIN_REQUEST = 1;
  static final byte END_REQUEST = 3;
  static final int PARAMETERS = 4;

  private static final byte REQUEST_COMPLETE = 0;
  static final byte NO_MULTIPLEX_CONNECTION = 1;

  static final byte STDIN = 5;
  static final byte STDOUT = 6;

  static final int FCGI_KEEP_CONN = 1;
  static final int FCGI_RESPONDER = 1;

  private static final FastCGIParser PARSER = new FastCGIParser();

  private final FastCGIHandler handler;

  public FastCGIServer(FastCGIHandler handler) {
    if (handler == null)
      throw new EoException("Handler can not be null.");
    this.handler = handler;
  }

  public void listen(int port) throws IOException {
    this.listen(null, port);
  }

  public void listen(String hostname, int port) throws IOException {
    ServerSocket server = new ServerSocket();
    server.bind(TextKit.isBlank(hostname) ? new InetSocketAddress(port) : new InetSocketAddress(hostname, port));
    Log.debug("FastCGI server started on {}:{}", TextKit.isBlank(hostname) ? "localhost" : hostname, port);
    try {
      this.accept(server);
    } catch (Exception e) {
      Log.error(e.getMessage(), e);
    } finally {
      server.close();
    }
  }

  private void accept(ServerSocket server) throws IOException {
    Socket socket = null;
    InputStream inputStream = null;
    OutputStream outputStream = null;
    while (true) {
      if (socket == null) {
        socket = server.accept();
        inputStream = socket.getInputStream();
        outputStream = socket.getOutputStream();
        Log.debug("accepted socket {}", socket);
      }

      FastCGIRet ret = null;
      try {
        ret = PARSER.parseRequest(inputStream, outputStream);
      } catch (Exception e) {
        Log.error(e.getMessage(), e);
      }
      Response response = this.handler.handle(ret == null ? null : ret.request());

      int requestId = ret == null ? 0 : ret.requestId();
      boolean closeConnection = ret == null || ret.closeConn();
//      new FastCGIMessage(STDOUT, ret.requestId(), processRequest(ret.data(), ret.prop()).getBytes()).write(outputStream);
      new FastCGIMessage(STDOUT, requestId, PARSER.parseResponse(response)).write(outputStream);
      new FastCGIMessage(STDOUT, requestId).write(outputStream);
      new FastCGIMessage(END_REQUEST, requestId, REQUEST_COMPLETE).write(outputStream);

      if (closeConnection) {
        Log.debug("finished request id " + requestId);
        StreamKit.close(outputStream, inputStream, socket);
        socket = null;
      } else {
        Log.debug("finished request id " + requestId);
        outputStream.flush();
      }
    }
  }

}
