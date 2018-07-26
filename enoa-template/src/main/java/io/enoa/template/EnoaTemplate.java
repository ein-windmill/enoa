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
package io.enoa.template;

import io.enoa.template.compressor.EoCompressorFactory;
import io.enoa.toolkit.text.TextKit;

/**
 * vtom - io.enoa.template
 */
public abstract class EnoaTemplate implements _Template {

  private String patchView(String view) {
    if (view.startsWith("/"))
      return view;
//    return String.format("/%s", view);
    return TextKit.union("/", view);
  }

  protected String compress(EoCompressorFactory compressor, String text) {
    if (compressor == null)
      return text;
    return compressor.compressor().compress(text);
  }

  protected String fillView(String view, String suffix) {
    if (TextKit.isBlank(suffix))
      return this.patchView(view);
//    if (view.endsWith(String.format(".%s", suffix)))
    if (view.endsWith(TextKit.union(".", suffix)))
      return this.patchView(view);
//    view = String.format("%s.%s", view, suffix);
    view = TextKit.union(view, ".", suffix);
    return this.patchView(view);
  }

  /**
   * 設定視圖名
   *
   * @param viewName 視圖名
   */
  protected abstract void viewName(String viewName);

}
