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
package io.enoa.yosart.repeater;

import io.enoa.log.kit.LogKit;
import io.enoa.repeater.factory.error.EoxErrorRenderFactory;
import io.enoa.repeater.http.HttpStatus;
import io.enoa.repeater.http.Response;
import io.enoa.toolkit.sys.ThrowableKit;
import io.enoa.yosart.resp.Renderer;

public class OysartRepeaterErrorRender implements EoxErrorRenderFactory {

  @Override
  public Response renderError(HttpStatus stat, String message, Throwable ex) {
    Renderer renderer = Renderer.with(null);
    if (message != null)
      renderer.attr("msg", message);

    if (ex == null) {
      return renderer.renderError(stat).end();
    }

    Throwable e = ThrowableKit.accurate(ex);
    renderer.renderError(stat, e);
    LogKit.error(e.getMessage(), e);
    return renderer.end();
  }
}
