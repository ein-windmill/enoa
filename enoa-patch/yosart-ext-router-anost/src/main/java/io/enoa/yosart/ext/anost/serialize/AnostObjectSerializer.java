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
package io.enoa.yosart.ext.anost.serialize;

import io.enoa.repeater.http.Response;
import io.enoa.yosart.ext.anost.AnostSerializer;
import io.enoa.yosart.kernel.http.YoRequest;
import io.enoa.yosart.resp.Renderer;

public class AnostObjectSerializer implements AnostSerializer {
  @Override
  public Response serialize(YoRequest request, Object data) {
    if (data == null)
      return null;
    return Renderer.with(request).renderHtml(data.toString()).end();
  }
}
