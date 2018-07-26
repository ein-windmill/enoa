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
package io.enoa.repeater.provider.tio.server;

import io.enoa.repeater.EoxAccessor;
import io.enoa.repeater.EoxConfig;
import io.enoa.repeater.factory.error.EoxErrorRenderFactory;
import io.enoa.repeater.factory.name.EoxNameRuleFactory;
import io.enoa.repeater.http.HttpStatus;
import io.enoa.repeater.http.Request;
import io.enoa.repeater.http.Response;
import io.enoa.repeater.provider.tio.server.plus._RepeaterTioRequest;
import io.enoa.repeater.provider.tio.ts.TioTranslateProvider;
import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.toolkit.http.UriKit;
import org.tio.http.common.HttpConfig;
import org.tio.http.common.HttpRequest;
import org.tio.http.common.HttpResponse;
import org.tio.http.common.RequestLine;
import org.tio.http.common.handler.HttpRequestHandler;

class _TioHandler implements HttpRequestHandler {
  private EoxAccessor accessor;
  private TioTranslateProvider ts;
  private EoxNameRuleFactory rule;
  private EoxConfig config;
  private HttpConfig tiocfg;
  private EoxErrorRenderFactory errorRender;

  _TioHandler(EoxAccessor accessor, TioTranslateProvider ts, EoxNameRuleFactory rule, EoxErrorRenderFactory errorRender,
              EoxConfig config, HttpConfig tiocfg) {
    this.accessor = accessor;
    this.ts = ts;
    this.rule = rule;
    this.config = config;
    this.tiocfg = tiocfg;
    this.errorRender = errorRender;
  }

  @Override
  public HttpResponse handler(HttpRequest packet) throws Exception {
    String headerString = packet.getHeaderString();
    String[] headerLine = headerString.split("\n");
    String reqinfo = headerLine[0];
    String[] infos = reqinfo.split(" ");
    String url = infos[1];

    HttpResponse response = new HttpResponse(packet, this.tiocfg);
    if (!"/".equals(this.config.context())) {
      String uri = url.split("\\?")[0];
      uri = UriKit.correct(uri).concat("/");
      String context = this.config.context().concat("/");
      if (!uri.startsWith(context)) {
        return this.ts.packet(packet)
          .response(this.config, response, this.errorRender.renderError(HttpStatus.NOT_FOUND,
            this.config.debug() ? EnoaTipKit.message("eo.tip.repeater.context_no_match", this.config.context()) : null));
      }
    }

    try {
      _RepeaterTioRequest tioreq = new _RepeaterTioRequest(packet) {
        @Override
        public String context() {
          return config.context();
        }
      };
      Request req = this.ts.request(this.config, this.rule, tioreq);
      Response resp = this.accessor.access(req);
      return this.ts.packet(packet)
        .response(this.config, response, resp);
    } catch (Exception e) {
      return this.ts.packet(packet)
        .response(this.config, response, this.errorRender.renderError(HttpStatus.INTERNAL_ERROR, e));
    }
  }

  @Override
  public HttpResponse resp404(HttpRequest request, RequestLine requestLine) {
    HttpResponse response = new HttpResponse(request, this.tiocfg);
    return this.ts.packet(request)
      .response(this.config, response, this.errorRender.renderError(HttpStatus.NOT_FOUND));
  }

  @Override
  public HttpResponse resp500(HttpRequest request, RequestLine requestLine, Throwable throwable) {
    HttpResponse response = new HttpResponse(request, this.tiocfg);
    return this.ts.packet(request)
      .response(this.config, response, this.errorRender.renderError(HttpStatus.INTERNAL_ERROR, throwable));
  }

  @Override
  public void clearStaticResCache(HttpRequest request) {

  }
}
