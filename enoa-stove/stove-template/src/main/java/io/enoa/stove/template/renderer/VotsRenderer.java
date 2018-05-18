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
package io.enoa.stove.template.renderer;

import io.enoa.stove.template.SPM;
import io.enoa.stove.template.StoveConfig;
import io.enoa.stove.template.ast.tree.Ast;
import io.enoa.stove.template.ast.tree.BlockAst;
import io.enoa.stove.template.ast.tree.TextAst;
import io.enoa.stove.template.ast.tree.VarAst;
import io.enoa.stove.template.renderer.block.BlockRenderer;
import io.enoa.stove.template.renderer.var.VarRenderer;

import java.util.List;
import java.util.Map;

public class VotsRenderer {

  public static String render(SPM spm, StoveConfig config, List<Ast> asts, Map<String, ?> attr) {
    StringBuilder result = new StringBuilder();
    for (Ast ast : asts) {
      switch (ast.type()) {
        case TEXT:
          result.append(((TextAst) ast).code());
          break;
        case VAR:
          String _ret0 = VarRenderer.render(spm, config, (VarAst) ast, attr);
          result.append(_ret0 == null ? "" : _ret0);
          break;
        case BLOCK:
          String _ret1 = BlockRenderer.render(spm, config, (BlockAst) ast, attr);
          result.append(_ret1 == null ? "" : _ret1);
          break;
      }
    }
    return result.toString();
  }

}
