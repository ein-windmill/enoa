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
package io.enoa.gateway;

import io.enoa.repeater.Repeater;
import io.enoa.repeater.factory.error.EoxErrorRenderFactory;
import io.enoa.repeater.http.HttpStatus;
import io.enoa.repeater.http.Response;
import io.enoa.repeater.http.ResponseBody;

public interface GErrorRenderFactory extends EoxErrorRenderFactory {

  @Override
  default Response renderError(HttpStatus stat) {
    return new Response.Builder()
      .status(stat)
      .contentType("text/html")
      .body(ResponseBody.create(String.format("error %d", stat.code()), Repeater.config().charset()))
      .build();
  }

  @Override
  Response renderError(HttpStatus stat, Throwable e);

  @Override
  default Response renderError(HttpStatus stat, String message) {
    return this.renderError(stat);
  }

  @Override
  default Response renderError(HttpStatus stat, String message, Throwable e) {
    return this.renderError(stat, e);
  }
}
