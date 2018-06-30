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
package io.enoa.stove.template.command.def;

import io.enoa.stove.template.SPM;
import io.enoa.stove.template.StoveConfig;
import io.enoa.stove.template.ast.tree.*;
import io.enoa.stove.template.command.StoveCommand;
import io.enoa.stove.template.command.StoveResult;
import io.enoa.stove.template.renderer.block.BlockRenderer;
import io.enoa.stove.template.renderer.var.VarRenderer;
import io.enoa.stove.template.renderer.var.VarValue;
import io.enoa.stove.template.thr.StoveException;
import io.enoa.stove.template.thr.SyntaxException;
import io.enoa.toolkit.convert.ConvertKit;
import io.enoa.toolkit.text.TextKit;
import io.enoa.toolkit.value.EnoaValue;

import java.util.List;
import java.util.Map;

public class _IfCmd implements StoveCommand {
  @Override
  public String name() {
    return "if";
  }

  @Override
  public boolean multi() {
    return true;
  }

  @Override
  public StoveResult execute(SPM spm, StoveConfig config, EnoaValue pipeval, Ast ast, Map<String, ?> attr) throws SyntaxException {
    if (!(ast instanceof BlockAst))
      return StoveResult.NULL;
    BlockAst bast = (BlockAst) ast;

    StringBuilder ret = new StringBuilder();

    boolean sem = false;
    // 是否跳过
    boolean skip = false;

    List<Ast> blocks = bast.blocks();
    for (int i = 0; i < blocks.size(); i++) {
      Ast block = blocks.get(i);
      if (skip && (!(block instanceof BlockSemAst)))
        continue;

      if (block instanceof BlockAst) {
        ret.append(BlockRenderer.render(spm, config, (BlockAst) block, attr));
        continue;
      }
      if (block instanceof VarAst) {
        ret.append(VarRenderer.render(spm, config, (VarAst) block, attr));
        continue;
      }
      if (block instanceof BlockSemAst) {
        BlockSemAst bsa = (BlockSemAst) block;
        // 进入区块
        if (!sem) {
          String code = bsa.code();
          String cmd = code.substring(config.tokenBlock().length());
          skip = this.skipIf(spm, config, bsa, cmd, attr);
          sem = true;
          ret.append("\n");
          continue;
        }

        // 匹配结束
        String code = bsa.code();
        String endCmd = code.substring(config.tokenBlock().length());
        endCmd = TextKit.nospace(endCmd);
        int six = endCmd.indexOf(" ");
        if (six > 0)
          endCmd = endCmd.substring(0, six);
        if (!endCmd.equals(this.end()))
          throw new SyntaxException("if 未正常结束");
        sem = false;
        skip = false;
        ret.append("\n");
        continue;
      }
      if (block instanceof TextAst) {
        ret.append(block.code());
        continue;
      }
    }
    if (sem)
      throw new SyntaxException("区块错误");
    return ret::toString;
  }

  private boolean skipIf(SPM spm, StoveConfig config, Ast ast, String cmd, Map<String, ?> attr) {
    cmd = TextKit.nospace(cmd);
    cmd = cmd.substring(this.name().length());
    if (cmd.endsWith(";"))
      cmd = cmd.substring(0, cmd.length() - 1);
    String[] cmds = cmd.split(" ");
    if (cmds[cmds.length - 1].equals("then"))
      cmd = cmd.substring(0, cmd.length() - 4);
    String _val = VarValue.parse(spm, config, ast, cmd, attr);
//    if (_val == null)
//      throw new StoveException("错误的 bool 表达式 => {0}", cmd);
    try {
//      return ConvertKit.bool(_val);
      return _val == null ? true : ConvertKit.bool(_val);
    } catch (Exception e) {
      throw new StoveException(e.getMessage(), e);
    }
  }

}
