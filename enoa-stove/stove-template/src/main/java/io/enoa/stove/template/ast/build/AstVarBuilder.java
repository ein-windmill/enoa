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

import io.enoa.stove.template.StoveConfig;
import io.enoa.stove.template.ast.tree.Ast;
import io.enoa.stove.template.ast.tree.VarAst;
import io.enoa.toolkit.text.TextKit;

import java.nio.file.Path;
import java.util.List;

class AstVarBuilder {


  int build(Path path, StoveConfig config, List<Ast> asts, String line, int ix) {
    int nextIx = ix + 1;
    int fromIx = 0;
    while (true) {
      int varIx0 = line.indexOf(config.tokenVarStart(), fromIx);
      int varIx1 = line.indexOf(config.tokenVarEnd(), fromIx) + config.tokenVarEnd().length();

      if (varIx0 == -1)
        varIx1 = fromIx;

      /*
      變量轉義處理
       */

      /*
       若變量開始字符不在第一個, 將之前的內容標記爲 text
       以及變量轉義處理
       */
      if (varIx0 > fromIx) {
        char beforeChar = line.charAt(varIx0 - 1);
        boolean isEscape = beforeChar == '\\';

        // 變量/轉義 前的文字字符
        String pretext = line.substring(fromIx, isEscape ? varIx0 - 1 : varIx0);
        if (TextKit.notBlank(pretext)) {
          _AstBuilder.instance().textBuilder().build(path, asts, pretext, ix, false);
        }

        // 變量轉義處理
        if (isEscape) {
          fromIx = line.indexOf(config.tokenVarEnd(), fromIx) + config.tokenVarEnd().length();
          String escapeText = line.substring(varIx0, fromIx);
          _AstBuilder.instance().textBuilder().build(path, asts, escapeText, ix, false);
          continue;
        }

      }

      /*
      截取變量信息加入到語法樹
       */
      if (varIx0 > 0) {
        String var = line.substring(varIx0, varIx1);
        Ast ast = new VarAst(var, nextIx, nextIx);
        asts.add(ast);
      }

      /*
      判斷變量結束後是否還有內容, 如果有修改 fromIx 再次進行分析
       */
      int varIx2 = line.indexOf(config.tokenVarStart(), varIx1);
      if (varIx2 > 0) {
        fromIx = varIx1;
        continue;
      }

      /*
      若沒有內容直接跳出
       */
      if (varIx1 >= line.length()) {
        _AstBuilder.instance().textBuilder().build(path, asts, "\n", ix, false);
        break;
      }

      /*
      有內容已 TEXT 填充到語法樹
       */
      _AstBuilder.instance().textBuilder().build(path, asts, TextKit.union(line.substring(varIx1), "\n"), ix, false);
      break;
    }
    return nextIx;
  }

}
