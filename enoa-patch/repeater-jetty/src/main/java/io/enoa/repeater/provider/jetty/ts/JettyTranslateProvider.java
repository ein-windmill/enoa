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
package io.enoa.repeater.provider.jetty.ts;

import io.enoa.repeater.EoxConfig;
import io.enoa.repeater.factory.name.EoxNameRuleFactory;
import io.enoa.repeater.factory.ts.RepeaterTranslateFactory;
import io.enoa.repeater.http.Cookie;
import io.enoa.repeater.http.Request;
import io.enoa.repeater.http.Response;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.thr.EoException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JettyTranslateProvider implements RepeaterTranslateFactory<HttpServletRequest, HttpServletResponse> {

  @Override
  public Request request(EoxConfig config, EoxNameRuleFactory rule, HttpServletRequest oreq) {
    try {
      String vendor = config.other().string("provider.jetty.upload.vendor", "default");
      switch (vendor) {
        case "cos":
          return new JettyCosRequestWrapper(oreq, config, rule);
        case "common-upload":
          throw new EoException("Can not support now.");
        default:
          return new JettyRequestWrapper(oreq, config, rule);
      }
    } catch (Exception e) {
      throw new EoException(e);
    }
  }

  @Override
  public HttpServletResponse response(EoxConfig config, HttpServletResponse oresp, Response resp) {
    try {
      this.tsResp(oresp, resp);
    } catch (IOException e) {
      throw new EoException(e.getMessage(), e);
    }
    return oresp;
  }

  private void tsResp(HttpServletResponse oresp, Response resp) throws IOException {
    oresp.setStatus(resp.status().code());
    resp.headers().forEach(header -> oresp.setHeader(header.name(), header.value()));

    if (resp.headers().stream().noneMatch(h -> h.name().equalsIgnoreCase("content-type")))
      oresp.setContentType(resp.contentType());
    if (resp.headers().stream().noneMatch(h -> h.name().equalsIgnoreCase("content-length")))
      oresp.setContentLengthLong(resp.contentLength());

    if (CollectionKit.notEmpty(resp.cookies())) {
      for (Cookie cookie : resp.cookies()) {
        oresp.addHeader("Set-Cookie", cookie.toString());
      }
    }
    ServletOutputStream outputStream = oresp.getOutputStream();
    outputStream.write(resp.body().bytes());
    outputStream.close();
  }
}
