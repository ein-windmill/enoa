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
package io.enoa.repeater.provider.tomcat.server;

import io.enoa.repeater.EoxAccessor;
import io.enoa.repeater.EoxConfig;
import io.enoa.repeater.factory.error.EoxErrorRenderFactory;
import io.enoa.repeater.factory.name.EoxNameRuleFactory;
import io.enoa.repeater.factory.ts.RepeaterTranslateFactory;
import io.enoa.repeater.http.HttpStatus;
import io.enoa.repeater.http.Request;
import io.enoa.repeater.http.Response;
import io.enoa.repeater.provider.tomcat.server.plus._RepeaterTomcatRequest;
import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.toolkit.http.UriKit;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

class _TomcatApp extends HttpServlet {

  private RepeaterTranslateFactory<HttpServletRequest, HttpServletResponse> ts;
  private EoxNameRuleFactory rule;
  private EoxAccessor accessor;
  private EoxConfig config;
  private EoxErrorRenderFactory errorRender;

  _TomcatApp(EoxAccessor accessor, RepeaterTranslateFactory<HttpServletRequest, HttpServletResponse> ts,
             EoxNameRuleFactory rule, EoxErrorRenderFactory errorRender, EoxConfig config) {
    this.ts = ts;
    this.accessor = accessor;
    this.config = config;
    this.rule = rule;
    this.errorRender = errorRender;
  }

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    if (!"/".equals(this.config.context())) {
      String uri = req.getRequestURI();
      uri = UriKit.correct(uri).concat("/");
      String context = this.config.context().concat("/");

      if (!uri.startsWith(context)) {
        this.ts.response(this.config, resp, this.errorRender.renderError(HttpStatus.NOT_FOUND,
          this.config.debug() ? EnoaTipKit.message("eo.tip.repeater.context_no_match", this.config.context()) : null));
        return;
      }
    }
    try {
      _RepeaterTomcatRequest rtq = new _RepeaterTomcatRequest(req) {
        @Override
        public String getContextPath() {
          return config.context();
        }
      };
      Request request = this.ts.request(this.config, this.rule, rtq);
      Response response = this.accessor.access(request);
      this.ts.response(this.config, resp, response);
    } catch (Exception e) {
      this.ts.response(this.config, resp, this.errorRender.renderError(HttpStatus.INTERNAL_ERROR, e));
    }
  }

}
