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

import java.util.ArrayList;
import java.util.List;

public class RGatewayAccessor implements EoxAccessor {

//  private List<Header> DEF_CROS_HEADERS;
  private GatewayHandler handler;
  private GData gateway;

  public RGatewayAccessor(GatewayHandler handler, GData gateway) {
    this.handler = handler;
    this.gateway = gateway;
//    this.DEF_CROS_HEADERS = new ArrayList<Header>() {{
//      add(new Header("Access-Control-Allow-Origin", "*"));
//      add(new Header("Access-Control-Allow-Credentials", "true"));
//      add(new Header("Access-Control-Allow-Method", "GET,POST,PUT,PATCH,DELETE"));
//      add(new Header("Access-Control-Allow-Headers", String.join(",", new String[]{
//        "Content-Type",
//        "X-HTTP-Method-Override",
//        "Access-Control-Request-Headers",
//        "Access-Control-Request-Method"
//      })));
//    }};
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
      this.crosHeaders().forEach(builder::header);
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
