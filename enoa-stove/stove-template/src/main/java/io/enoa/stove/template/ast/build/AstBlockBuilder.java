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

import io.enoa.stove.template.SPM;
import io.enoa.stove.template.StoveConfig;
import io.enoa.stove.template.ast.tree.Ast;
import io.enoa.stove.template.ast.tree.BlockAst;
import io.enoa.stove.template.ast.tree.BlockSemAst;
import io.enoa.stove.template.ast.tree.TextAst;
import io.enoa.stove.template.command.StoveCommand;
import io.enoa.stove.template.thr.CommandNotFoundException;
import io.enoa.stove.template.thr.SyntaxException;
import io.enoa.toolkit.text.TextKit;

import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

class AstBlockBuilder {

  int build(SPM spm, Path path, StoveConfig config, List<List<Ast>> historys, List<Ast> asts, AstStamar asr, String[] lines, int ix) {
    int nextIx = ix + 1;

    int fromIx = 0;
    String first = lines[ix];
    int blockIx0 = first.indexOf(config.tokenBlock(), fromIx);

    if (blockIx0 > fromIx) {
      Ast ast = new TextAst(first.substring(fromIx, blockIx0), nextIx, nextIx);
      asts.add(ast);
    }

    String blockStart = first.substring(blockIx0);
    // 指令名称
    String cmdname = TextKit.nospace(blockStart.substring(1));
    cmdname = cmdname.split(" ")[0];

    // 当前指令与需要的指令结束符相同
    if (cmdname.equals(asr.need())) {
      Ast ast = new BlockSemAst(TextKit.union(blockStart, "\n"), nextIx, nextIx);
      asr.rem();
      asts.add(ast);
      historys.remove(historys.size() - 1);
      return nextIx;
    }

    // 新的指令

    if (!spm.exists(cmdname))
      throw new CommandNotFoundException("Command not found -> {0}", cmdname);

    StoveCommand cmd = spm.command(cmdname);
    // 如果是多行命令, 维持需要结束的指令
    if (cmd.multi()) {
      asr.need(cmd.end());
      return this.resolveBlock(spm, path, config, historys, asts, asr, lines, nextIx, blockStart);
    }


    // 单行指令直接添加
    Ast ast = new BlockSemAst(TextKit.union(blockStart, "\n"), nextIx, nextIx);
    asts.add(ast);
    return nextIx;
  }

  private int resolveBlock(SPM spm, Path path, StoveConfig config, List<List<Ast>> historys, List<Ast> asts, AstStamar asr, String[] lines, int ix, String blockStart) {
    List<Ast> subasts = new LinkedList<>();
    subasts.add(new BlockSemAst(TextKit.union(blockStart, "\n"), ix, ix));
    Ast ast = new BlockAst(subasts, ix, ix);
    asts.add(ast);
    historys.add(subasts);
    // 仍需解決的區塊數量
    int size = asr.size();
    while (!asr.resolve()) {
      if (ix + 1 > lines.length)
        throw new SyntaxException("Syntax not close -> {0}", asr.need());
      // 每解決一個會從待解決列表中刪除一個, 因此判斷總數量大於當前數量時表明走出當前區塊, 走出則使用上層 asts
      if (size > asr.size()) {
        ix = _AstBuilder.instance().build(spm, path, config, historys, historys.get(asr.size()), asr, lines, ix);
      } else {
        ix = _AstBuilder.instance().build(spm, path, config, historys, subasts, asr, lines, ix);
      }
    }
    return ix;
  }

}
