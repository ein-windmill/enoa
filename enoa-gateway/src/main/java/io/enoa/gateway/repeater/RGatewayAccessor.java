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
package io.enoa.gateway.repeater;

import io.enoa.gateway.GatewayHandler;
import io.enoa.gateway.data.GData;
import io.enoa.repeater.EoxAccessor;
import io.enoa.repeater.http.Header;
import io.enoa.repeater.http.HttpStatus;
import io.enoa.repeater.http.Request;
import io.enoa.repeater.http.Response;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.text.TextKit;
import io.enoa.toolkit.value.EnoaValue;

import java.util.List;

public class RGatewayAccessor implements EoxAccessor {

  private GatewayHandler handler;
  private GData gateway;

  public RGatewayAccessor(GatewayHandler handler, GData gateway) {
    this.handler = handler;
    this.gateway = gateway;
  }

  @Override
  public Response access(Request request) {
    try {
      return this.handler.handle(request, this.gateway);
    } catch (Exception e) {
      Response resp = gateway.errorRenderFactory()
        .renderError(HttpStatus.INTERNAL_ERROR, e);
      if (!this.gateway.cros()) {
        return resp;
      }
      Response.Builder builder = resp.newBuilder();
      this.crosHeaders().forEach(header -> {
        if (header.name().equalsIgnoreCase("Access-Control-Allow-Origin")) {
          Header.Builder hbu = header.newBuilder();
          String origin = EnoaValue.with(request.header("origin")).string(request.header("x-origin"));
          hbu.value(TextKit.blanky(origin) ? "*" : origin);
          builder.header(hbu.build());
          return;
        }
        if (header.name().equalsIgnoreCase("Access-Control-Allow-Headers")) {
          String acrh = EnoaValue.with(request.header("Access-Control-Request-Headers"))
            .string(request.header("X-Access-Control-Request-Headers"));
          if (TextKit.blanky(acrh)) {
            builder.header(header);
            return;
          }
          Header.Builder hbu = header.newBuilder();
          hbu.value(TextKit.union(header.value(), ",", acrh));
          builder.header(hbu.build());
          return;
        }
        builder.header(header);
      });
      String origin = request.header("origin");
      if (TextKit.blankn(origin))
        builder.header(new Header("Access-Control-Allow-Origin", origin));
      return builder.build();
    }
  }


  private List<Header> crosHeaders() {
    if (CollectionKit.isEmpty(this.gateway.crosHeaders()))
      return this.gateway.crosHeaders();
    return this.gateway.defaultCrosHeaders();
  }

}
