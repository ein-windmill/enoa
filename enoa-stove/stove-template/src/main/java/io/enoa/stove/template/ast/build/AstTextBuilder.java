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
package io.enoa.stove.template.ast.build;

import io.enoa.stove.template.ast.tree.Ast;
import io.enoa.stove.template.ast.tree.AstType;
import io.enoa.stove.template.ast.tree.TextAst;
import io.enoa.toolkit.text.TextKit;

import java.nio.file.Path;
import java.util.List;

class AstTextBuilder {

  int build(Path path, List<Ast> asts, String line, int ix, boolean newline) {
    int nextIx = ix + 1;
    Ast ast = null;
    int size = asts.size();
    if (size > 0) {
      ast = asts.get(size - 1);
    }
    if (ast != null && ast.type() == AstType.TEXT) {
      TextAst tast = (TextAst) ast;
      tast.code(TextKit.union(tast.code(), line, newline ? "\n" : ""))
        .end(nextIx);
      return nextIx;
    }
    ast = new TextAst(TextKit.union(line, newline ? "\n" : ""), nextIx, nextIx);
    asts.add(ast);
    return nextIx;
  }

}
