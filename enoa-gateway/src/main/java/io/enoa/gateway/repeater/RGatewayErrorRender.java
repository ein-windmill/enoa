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
package io.enoa.gateway.repeater;

import io.enoa.gateway.GErrorRenderFactory;
import io.enoa.gateway.thr.GatewayAuthException;
import io.enoa.log.Log;
import io.enoa.repeater.Repeater;
import io.enoa.repeater.http.HttpStatus;
import io.enoa.repeater.http.Response;
import io.enoa.repeater.http.ResponseBody;
import io.enoa.toolkit.sys.ThrowableKit;

public class RGatewayErrorRender implements GErrorRenderFactory {


  @Override
  public Response renderError(HttpStatus stat, Throwable ex) {
    Response.Builder builder = new Response.Builder();
    builder.status(stat)
      .contentType("text/html");
    if (ex != null) {
      Throwable e = ThrowableKit.accurate(ex);
      builder.body(ResponseBody.create(String.format("error %d <br> <pre>%s</pre>", stat.code(), ThrowableKit.string(e)),
        Repeater.config().charset()));
      if (e instanceof GatewayAuthException) {
        Log.error(e.getMessage());
      } else {
        Log.error(e.getMessage(), e);
      }
    } else {
      builder.body(ResponseBody.create(String.format("error %d", stat.code()),
        Repeater.config().charset()));
    }
    return builder.build();
  }

}
