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
package io.enoa.stove.template.ast.build;

import io.enoa.stove.template.SPM;
import io.enoa.stove.template.StoveConfig;
import io.enoa.stove.template.ast.tree.Ast;
import io.enoa.stove.template.ast.tree.AstType;
import io.enoa.stove.template.thr.SyntaxException;

import java.nio.file.Path;
import java.util.List;

public class _AstBuilder {

  private static class Holder {
    private static final _AstBuilder INSTANCE = new _AstBuilder();
    private static final AstTextBuilder BUILDER_TEXT = new AstTextBuilder();
    private static final AstVarBuilder BUILDER_VAR = new AstVarBuilder();
    private static final AstBlockBuilder BUILDER_BLOCK = new AstBlockBuilder();
  }

  public static _AstBuilder instance() {
    return Holder.INSTANCE;
  }

  public int build(SPM spm, Path path, StoveConfig config, List<List<Ast>> historys, List<Ast> asts, AstStamar asr, String[] lines, int ix) {
    String line = lines[ix];
    switch (this.typeCheck(config, line)) {
      case TEXT:
        return Holder.BUILDER_TEXT.build(path, asts, lines[ix], ix, true);
      case VAR:
        return Holder.BUILDER_VAR.build(path, config, asts, lines[ix], ix);
      case BLOCK:
        return Holder.BUILDER_BLOCK.build(spm, path, config, historys, asts, asr, lines, ix);
      default:
        throw new SyntaxException("Syntax error.");
    }
  }

  AstType typeCheck(StoveConfig config, String line) {
    if (line.contains(config.tokenBlock()))
      return AstType.BLOCK;
    if (line.contains(config.tokenVarStart()))
      return AstType.VAR;
    return AstType.TEXT;
  }

  AstTextBuilder textBuilder() {
    return Holder.BUILDER_TEXT;
  }

  AstVarBuilder varBuilder() {
    return Holder.BUILDER_VAR;
  }

  AstBlockBuilder blockBuilder() {
    return Holder.BUILDER_BLOCK;
  }

}
