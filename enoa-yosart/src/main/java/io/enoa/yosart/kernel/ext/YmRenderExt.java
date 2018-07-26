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
package io.enoa.yosart.kernel.ext;

import io.enoa.repeater.http.Request;
import io.enoa.toolkit.map.Kv;
import io.enoa.yosart.YoExt;
import io.enoa.yosart.kernel.render.YoRender;

public interface YmRenderExt extends YoExt {

  @Override
  default Type type() {
    return Type.RENDER;
  }


  String renderType();

  /**
   * render provider
   *
   * @return YoRender
   */
  YoRender renderer(Request req, Kv attr, Object... args) throws Exception;

}
