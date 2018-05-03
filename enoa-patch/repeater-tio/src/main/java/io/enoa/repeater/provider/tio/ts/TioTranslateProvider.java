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
package io.enoa.repeater.provider.tio.ts;

import io.enoa.repeater.EoxConfig;
import io.enoa.repeater.factory.name.EoxNameRuleFactory;
import io.enoa.repeater.factory.ts.RepeaterTranslateFactory;
import io.enoa.repeater.http.Cookie;
import io.enoa.repeater.http.Request;
import io.enoa.repeater.http.Response;
import io.enoa.repeater.provider.tio.server.plus._RepeaterTioRequest;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.thr.EoException;
import org.tio.http.common.HttpRequest;
import org.tio.http.common.HttpResponse;
import org.tio.http.common.HttpResponseStatus;

import java.io.IOException;

public class TioTranslateProvider implements RepeaterTranslateFactory<_RepeaterTioRequest, HttpResponse> {

  private HttpRequest packet;

  public TioTranslateProvider packet(HttpRequest packet) {
    this.packet = packet;
    return this;
  }

  @Override
  public Request request(EoxConfig config, EoxNameRuleFactory rule, _RepeaterTioRequest oreq) {
    try {
      return new TioRequestWrapper(oreq, config, rule);
    }catch (IOException e) {
      throw new EoException(e.getMessage(), e);
    }
  }

  @Override
  public HttpResponse response(EoxConfig config, HttpResponse oresp, Response resp) {
    oresp.setStatus(HttpResponseStatus.getHttpStatus(resp.status().code()));
    resp.headers().forEach(h -> oresp.addHeader(h.name(), h.value()));

    if (resp.headers().stream().noneMatch(h -> h.name().equalsIgnoreCase("content-type")))
      oresp.addHeader("Content-Type", resp.contentType());
//      if (resp.headers().stream().noneMatch(h -> h.name().equalsIgnoreCase("content-length")))
//        oresp.addHeader("Content-Length", String.valueOf(resp.contentLength()));

    oresp.setBody(resp.body().bytes(), this.packet);

    if (CollectionKit.notEmpty(resp.cookies()))
      for (Cookie cookie : resp.cookies())
        oresp.addHeader("Set-Cookie", cookie.toString());

    return oresp;
  }
}
