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
package io.enoa.rpc.http;

import io.enoa.http.protocol.HttpResponse;
import io.enoa.http.protocol.HttpResponseBody;
import io.enoa.log.EnoaLog;
import io.enoa.rpc.Rpc;
import io.enoa.rpc.parser.IRpcParser;
import io.enoa.rpc.parser.ResponseType;
import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.toolkit.text.TextKit;
import io.enoa.toolkit.thr.EoException;

import java.lang.reflect.Type;

class HttpRpcHandler {

  private static EnoaLog log = Rpc.config().factory().log("rpc_handler");

  private static <T> IRpcParser<T> handler(ResponseType type) {
    IRpcParser<T> handler = Rpc.config().handler().handler(type);
    if (handler == null)
      throw new EoException(EnoaTipKit.message("eo.tip.rpc.handler_null", type.name()));
    return handler;
  }

  static <T> T handle(HttpResponse response, Type type) {
    String contentType = response.header("content-type");
    if (TextKit.isBlank(contentType))
      return null;
    if (contentType.contains("application/json")) {
      HttpResponseBody body = response.body();
      try {
        IRpcParser<T> handler = handler(ResponseType.JSON);
        return handler.parse(body, type);
      } catch (Exception e) {
        if (e instanceof EoException)
          throw e;
        String message = EnoaTipKit.message("eo.tip.rpc.handler_parse_fail", type.getTypeName(), body.string());
        throw new EoException(message, e);
      }
    }
    if (contentType.contains("text/xml") || contentType.contains("application/xml")) {
      IRpcParser<T> handler = handler(ResponseType.XML);
      return handler.parse(response.body(), type);
    }
    if (contentType.contains("application/octet-stream") ||
      contentType.contains("image")) {
      IRpcParser<T> handler = handler(ResponseType.BINARY);
      return handler.parse(response.body(), type);
    }

    int fix = contentType.indexOf(";");
    if (fix != -1)
      contentType = contentType.substring(0, fix);
    log.warn(EnoaTipKit.message("eo.tip.rpc.content_type_not_support", contentType));
    log.debug("RESPONSE CODE => {} | RESPONSE BODY => {}", response.code(), response.body());
    return null;
  }

}
