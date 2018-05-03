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
package io.enoa.yosart.ext.render.error;

import io.enoa.repeater.http.Request;
import io.enoa.toolkit.map.Kv;
import io.enoa.yosart.kernel.ext.YmRenderExt;
import io.enoa.yosart.kernel.render.YoRender;

public class ErrorRenderExt implements YmRenderExt {

  private String defContentType;
  private String defBody;

  public ErrorRenderExt(String defContentType) {
    this(defContentType, null);
  }

  public ErrorRenderExt(String defContentType, String defBody) {
    this.defContentType = defContentType;
    this.defBody = defBody;
  }

  @Override
  public String renderType() {
    return YoRender.Type.ERROR.name();
  }

  @Override
  public YoRender renderer(Request req, Kv attr, Object... args) {
    return new ErrorRender(req, attr, args)
      .defContentType(this.defContentType)
      .defBody(this.defBody);
  }

  @Override
  public String name() {
    return "ErrorRender";
  }

  @Override
  public String version() {
    return "1";
  }

  @Override
  public double weight() {
    return 0;
  }
}
