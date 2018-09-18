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
package io.enoa.repeater.provider.nanohttpd.server;

import fi.iki.elonen.NanoHTTPD;
import io.enoa.repeater.EoxAccessor;
import io.enoa.repeater.EoxConfig;
import io.enoa.repeater.factory.error.EoxErrorRenderFactory;
import io.enoa.repeater.factory.name.EoxNameRuleFactory;
import io.enoa.repeater.factory.ts.RepeaterTranslateFactory;
import io.enoa.repeater.http.HttpStatus;
import io.enoa.repeater.http.Request;
import io.enoa.repeater.provider.nanohttpd.server.plus._RepeaterNanoHTTPDSession;
import io.enoa.repeater.provider.nanohttpd.server.plus._RepeaterNanoHTTPDSessionImpl;
import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.toolkit.http.UriKit;

class _NanoApp extends NanoHTTPD {

  private EoxAccessor accessor;
  private RepeaterTranslateFactory<_RepeaterNanoHTTPDSession, Response> ts;
  private EoxNameRuleFactory rule;
  private EoxErrorRenderFactory errorRender;
  private EoxConfig config;

  _NanoApp(String hostname, int port, EoxAccessor accessor,
           RepeaterTranslateFactory<_RepeaterNanoHTTPDSession, Response> ts,
           EoxNameRuleFactory rule, EoxErrorRenderFactory errorRender, EoxConfig config) {
    super(hostname, port);
    this.accessor = accessor;
    this.ts = ts;
    this.rule = rule;
    this.config = config;
    this.errorRender = errorRender;
  }

  @Override
  public Response serve(IHTTPSession session) {
    if (!"/".equals(this.config.context())) {
      String uri = session.getUri();

      uri = UriKit.correct(uri).concat("/");
      String context = this.config.context().concat("/");

      if (!uri.startsWith(context)) {
        return this.ts.response(this.config, null,
          this.errorRender.renderError(HttpStatus.NOT_FOUND,
            this.config.debug() ? EnoaTipKit.message("eo.tip.repeater.context_no_match", this.config.context()) : null));
      }
    }
    try {
      _RepeaterNanoHTTPDSession repeaterNanoSession = new _RepeaterNanoHTTPDSessionImpl(session) {
        @Override
        public String context() {
          return config.context();
        }
      };
      Request request = this.ts.request(this.config, this.rule, repeaterNanoSession);
      io.enoa.repeater.http.Response ret = this.accessor.access(request);
      return this.ts.response(this.config, null, ret);
    } catch (Exception e) {
      return this.ts.response(this.config, null, this.errorRender.renderError(HttpStatus.INTERNAL_ERROR, e));
    }
  }

}
