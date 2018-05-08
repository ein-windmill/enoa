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
package io.enoa.stove.template.ast;

import io.enoa.stove.template.SPM;
import io.enoa.stove.template.StoveConfig;
import io.enoa.stove.template.ast.build.AstStamar;
import io.enoa.stove.template.ast.build._AstBuilder;
import io.enoa.stove.template.ast.tree.Ast;
import io.enoa.stove.template.thr.SyntaxException;
import io.enoa.toolkit.collection.CollectionKit;

import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

public class VotsAst {

  public static List<Ast> analysis(SPM spm, StoveConfig config, Path path, String source) {
    long start = System.currentTimeMillis();
    String[] lines = source.split("\n");
    int ix = 0;
    List<Ast> asts = new LinkedList<>();
    AstStamar asr = new AstStamar();

    List<List<Ast>> historys = new LinkedList<>();
    historys.add(asts);

    while (ix < lines.length) {
      ix = _AstBuilder.instance().build(spm, path, config, historys, asts, asr, lines, ix);
    }
    if (!asr.resolve()) {
      throw new SyntaxException("Asr not resolve.");
    }
    asr.clear();
    CollectionKit.clear(historys);
    long end = System.currentTimeMillis();

//    asts.forEach(System.out::print);
//    System.out.println();

    System.out.println("Time-consuming: " + (end - start));
    return asts;
  }

}
