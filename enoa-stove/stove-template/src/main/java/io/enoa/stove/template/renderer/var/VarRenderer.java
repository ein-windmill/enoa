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
package io.enoa.stove.template.renderer.var;

import io.enoa.stove.template.SPM;
import io.enoa.stove.template.StoveConfig;
import io.enoa.stove.template.ast.tree.VarAst;
import io.enoa.stove.template.thr.StoveException;

import java.util.Map;

public class VarRenderer {

  private static class Holder {
    private static final VarRenderer INSTANCE = new VarRenderer();
  }

  public static String render(SPM spm, StoveConfig config, VarAst ast, Map<String, ?> attr) {
    return Holder.INSTANCE.renderVar(spm, config, ast, attr);
  }

  private String renderVar(SPM spm, StoveConfig config, VarAst ast, Map<String, ?> attr) {
    String full = ast.code();
    String code = full.substring(config.tokenVarStart().length());
    code = code.substring(0, code.lastIndexOf(config.tokenVarEnd()));

    try {
      String value = VarValue.parse(spm, config, ast, code, attr);
      return value == null ? "" : value;
    } catch (Throwable e) {
      if (e instanceof RuntimeException)
        throw e;
      throw new StoveException(e.getMessage(), e);
    }
  }

}
