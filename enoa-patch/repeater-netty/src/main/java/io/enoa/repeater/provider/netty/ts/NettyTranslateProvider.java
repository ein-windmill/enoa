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
package io.enoa.repeater.provider.netty.ts;

import io.enoa.repeater.EoxConfig;
import io.enoa.repeater.Repeater;
import io.enoa.repeater.factory.name.EoxNameRuleFactory;
import io.enoa.repeater.factory.ts.RepeaterTranslateFactory;
import io.enoa.repeater.http.Cookie;
import io.enoa.repeater.http.Request;
import io.enoa.repeater.http.Response;
import io.enoa.repeater.provider.netty.server.plus._RepeaterNettyRequest;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.thr.EoException;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;

public class NettyTranslateProvider implements RepeaterTranslateFactory<_RepeaterNettyRequest, FullHttpResponse> {

  @Override
  public Request request(EoxConfig config, EoxNameRuleFactory rule, _RepeaterNettyRequest oreq) {
    String vendor = config.other().string("provider.netty.upload.vendor", "default");
    switch (vendor) {
      case "cos":
        return new NettyCosRequestWrapper(oreq, Repeater.config(), rule);
      case "common-upload":
        throw new EoException("Can not support now.");
      default:
        return new NettyRequestWrapper(oreq, Repeater.config(), rule);
    }
  }

  @Override
  public FullHttpResponse response(EoxConfig config, FullHttpResponse oresp, Response resp) {


    oresp.headers().set(HttpHeaderNames.CONTENT_LENGTH, oresp.content().readableBytes());

    resp.headers().stream()
      .filter(h -> !h.name().equalsIgnoreCase("content-length"))
      .forEach(h -> oresp.headers().set(h.name(), h.value()));

    if (resp.headers().stream().noneMatch(h -> h.name().equalsIgnoreCase("content-type")))
      oresp.headers().set(HttpHeaderNames.CONTENT_TYPE, resp.contentType());


    if (CollectionKit.isEmpty(resp.cookies()))
      return oresp;

    for (Cookie cookie : resp.cookies()) {
      oresp.headers().add(HttpHeaderNames.SET_COOKIE, cookie.toString());
    }
    return oresp;
  }
}
