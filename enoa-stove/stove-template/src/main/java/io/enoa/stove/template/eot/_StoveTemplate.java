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
package io.enoa.stove.template.eot;

import io.enoa.stove.template.SPM;
import io.enoa.stove.template.StoveConfig;
import io.enoa.stove.template.Tpl;
import io.enoa.stove.template.ast.VotsAst;
import io.enoa.stove.template.ast.tree.Ast;
import io.enoa.stove.template.renderer.VotsRenderer;
import io.enoa.toolkit.binary.EnoaBinary;
import io.enoa.toolkit.file.FileKit;
import io.enoa.toolkit.text.TextKit;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class _StoveTemplate implements Tpl {

  private static Map<String, Tpl> CACHE = new HashMap<>();

  private StoveConfig config;
  private String tpl;
  private SPM spm;

  private _StoveTemplate(SPM spm, StoveConfig config, String tpl) {
    this.config = config;
    this.tpl = tpl;
    this.spm = spm;
  }

  public static Tpl create(SPM spm, StoveConfig config, String tpl) {
    if (CACHE.containsKey(tpl))
      return CACHE.get(tpl);
    Tpl _tpl = new _StoveTemplate(spm, config, tpl);
    CACHE.put(tpl, _tpl);
    return _tpl;
  }

  private String fileName(String name) {
    if (this.config.suffix() == null)
      return name;
    if (!name.contains("."))
      return TextKit.union(name, ".", this.config.suffix());
    if (!name.endsWith(this.config.suffix()))
      return TextKit.union(name, ".", this.config.suffix());
    name = name.substring(0, name.lastIndexOf("."));
    return TextKit.union(name, ".", this.config.suffix());
  }

  @Override
  public String render(Map<String, ?> attr) {
    Path path = this.config.path().resolve(this.fileName(this.tpl));
    EnoaBinary binary = FileKit.read(path);
    String string = binary.string();
    List<Ast> asts = VotsAst.analysis(this.spm, this.config, path, string);
    return VotsRenderer.render(this.spm, this.config, asts, attr);
  }


}
