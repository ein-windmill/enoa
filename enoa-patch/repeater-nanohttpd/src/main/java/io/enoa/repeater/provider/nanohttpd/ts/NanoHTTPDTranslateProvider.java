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
package io.enoa.repeater.provider.nanohttpd.ts;

import fi.iki.elonen.NanoHTTPD;
import io.enoa.repeater.EoxConfig;
import io.enoa.repeater.factory.name.EoxNameRuleFactory;
import io.enoa.repeater.factory.ts.RepeaterTranslateFactory;
import io.enoa.repeater.http.Request;
import io.enoa.repeater.http.Response;
import io.enoa.repeater.provider.nanohttpd.server.plus._RepeaterNanoHTTPDSession;
import io.enoa.toolkit.thr.EoException;

import java.io.IOException;


public class NanoHTTPDTranslateProvider implements RepeaterTranslateFactory<_RepeaterNanoHTTPDSession, NanoHTTPD.Response> {

  @Override
  public Request request(EoxConfig config, EoxNameRuleFactory rule, _RepeaterNanoHTTPDSession oreq) {
    try {
      return new NanoRequestWrapper(oreq, config, rule);
    } catch (IOException | NanoHTTPD.ResponseException e) {
      throw new EoException(e);
    }
  }

  @Override
  public NanoHTTPD.Response response(EoxConfig config, NanoHTTPD.Response oresp, Response resp) {
    return new RepeaterNanoHTTPDResponse(resp);
  }
}
