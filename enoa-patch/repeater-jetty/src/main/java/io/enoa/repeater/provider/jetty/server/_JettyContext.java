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
package io.enoa.repeater.provider.jetty.server;

import io.enoa.repeater.EoxAccessor;
import io.enoa.repeater.EoxConfig;
import io.enoa.repeater.factory.error.EoxErrorRenderFactory;
import io.enoa.repeater.factory.name.EoxNameRuleFactory;
import io.enoa.repeater.factory.ts.RepeaterTranslateFactory;
import io.enoa.repeater.http.HttpStatus;
import io.enoa.repeater.http.Response;
import io.enoa.repeater.provider.jetty.server.plus._RepeaterJettyRequest;
import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.toolkit.http.UriKit;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

class _JettyContext extends AbstractHandler {

  private static MultipartConfigElement multipartConfigElement;
  private RepeaterTranslateFactory<HttpServletRequest, HttpServletResponse> ts;
  private EoxNameRuleFactory rule;
  private EoxAccessor accessor;
  private EoxConfig config;
  private EoxErrorRenderFactory errorRender;

  _JettyContext(EoxAccessor accessor, RepeaterTranslateFactory<HttpServletRequest, HttpServletResponse> ts,
                EoxNameRuleFactory rule, EoxErrorRenderFactory errorRender, EoxConfig config) {
    this.ts = ts;
    this.accessor = accessor;
    this.config = config;
    this.rule = rule;
    this.errorRender = errorRender;
  }

  @Override
  public void handle(String s, Request request, HttpServletRequest httpServletRequest,
                     HttpServletResponse httpServletResponse) {

    try {
      if (!"/".equals(this.config.context())) {
        String uri = httpServletRequest.getRequestURI();
        uri = UriKit.correct(uri).concat("/");
        String context = this.config.context().concat("/");

        if (!uri.startsWith(context)) {
          this.ts.response(this.config, httpServletResponse, this.errorRender.renderError(HttpStatus.NOT_FOUND,
            this.config.debug() ? EnoaTipKit.message("eo.tip.repeater.context_no_match", this.config.context()) : null));
          return;
        }
      }
    } catch (Exception e) {
      this.ts.response(this.config, httpServletResponse, this.errorRender.renderError(HttpStatus.INTERNAL_ERROR, e));
    }


    String vendor = this.config.other().string("provider.jetty.upload.vendor", "default");
    if ("default".equals(vendor)) {
      if (multipartConfigElement == null)
        multipartConfigElement = new MultipartConfigElement(this.config.tmp().toString());
      request.setAttribute(Request.MULTIPART_CONFIG_ELEMENT, multipartConfigElement);
    }

    try {
      _RepeaterJettyRequest rjq = new _RepeaterJettyRequest(httpServletRequest) {
        @Override
        public String getContextPath() {
          return config.context();
        }
      };
      io.enoa.repeater.http.Request req = this.ts.request(this.config, this.rule, rjq);
      Response resp = this.accessor.access(req);
      request.setHandled(true);
      this.ts.response(this.config, httpServletResponse, resp);
    } catch (Exception e) {
      this.ts.response(this.config, httpServletResponse, this.errorRender.renderError(HttpStatus.INTERNAL_ERROR, e));
    }
  }
}
