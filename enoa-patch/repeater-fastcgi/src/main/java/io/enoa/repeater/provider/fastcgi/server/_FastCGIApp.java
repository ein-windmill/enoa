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
package io.enoa.repeater.provider.fastcgi.server;

import io.enoa.repeater.EoxAccessor;
import io.enoa.repeater.EoxConfig;
import io.enoa.repeater.factory.error.EoxErrorRenderFactory;
import io.enoa.repeater.factory.name.EoxNameRuleFactory;
import io.enoa.repeater.factory.provider.EoxProviderFactory;
import io.enoa.repeater.factory.ts.RepeaterTranslateFactory;
import io.enoa.repeater.http.HttpStatus;
import io.enoa.repeater.http.Request;
import io.enoa.repeater.http.Response;
import io.enoa.repeater.provider.fastcgi.kernel.FastCGIHandler;
import io.enoa.repeater.provider.fastcgi.kernel.FastCGIRequest;
import io.enoa.repeater.provider.fastcgi.server.plus._RepeaterFastCGIRequest;
import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.toolkit.http.UriKit;
import io.enoa.toolkit.thr.EoException;

class _FastCGIApp implements FastCGIHandler {


  private EoxAccessor accessor;
  private RepeaterTranslateFactory<_RepeaterFastCGIRequest, Response> ts;
  private EoxNameRuleFactory rule;
  private EoxErrorRenderFactory errorRender;
  private EoxConfig config;

  _FastCGIApp(EoxAccessor accessor, EoxConfig config, EoxProviderFactory factory) {
    this.accessor = accessor;
    this.ts = factory.ts();
    this.rule = factory.rule();
    this.errorRender = factory.errorRender();
    this.config = config;
  }

  @Override
  public Response handle(FastCGIRequest request) {
    try {

      if (request == null) {
        return this.ts.response(this.config, null, this.errorRender.renderError(HttpStatus.INTERNAL_ERROR));
      }

      if (!"/".equals(this.config.context())) {
        String uri = request.prop().get("SCRIPT_NAME");
        uri = UriKit.correct(uri).concat("/");

        String context = this.config.context().concat("/");

        if (!uri.startsWith(context)) {
          return this.ts.response(this.config, null, this.errorRender.renderError(HttpStatus.NOT_FOUND,
            this.config.debug() ? EnoaTipKit.message("eo.tip.repeater.context_no_match", this.config.context()) : null));
        }
      }

      _RepeaterFastCGIRequest oreq = new _RepeaterFastCGIRequest(request) {
        @Override
        public String context() {
          return config.context();
        }
      };

      Request req = this.ts.request(this.config, this.rule, oreq);
      Response resp = this.accessor.access(req);
      if (resp == null)
        throw new EoException("Response can not be null.");
      return resp;
    } catch (Exception e) {
      return this.ts.response(this.config, null, this.errorRender.renderError(HttpStatus.INTERNAL_ERROR, e));
    }
  }
}
