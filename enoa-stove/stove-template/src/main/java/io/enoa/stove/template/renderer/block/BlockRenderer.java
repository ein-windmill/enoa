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
package io.enoa.stove.template.renderer.block;

import io.enoa.stove.template.SPM;
import io.enoa.stove.template.StoveConfig;
import io.enoa.stove.template.ast.tree.Ast;
import io.enoa.stove.template.ast.tree.BlockAst;
import io.enoa.stove.template.ast.tree.BlockSemAst;
import io.enoa.stove.template.command.StoveCommand;
import io.enoa.stove.template.command.StoveResult;
import io.enoa.stove.template.thr.CommandNotFoundException;
import io.enoa.toolkit.value.EnoaValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlockRenderer {

  private static class Holder {
    private static final BlockRenderer INSTANCE = new BlockRenderer();
  }

  public static String render(SPM spm, StoveConfig config, BlockAst ast, Map<String, ?> attr) {
    return Holder.INSTANCE.renderBlock(spm, config, ast, attr);
  }

  private String renderBlock(SPM spm, StoveConfig config, BlockAst ast, Map<String, ?> attr) {
    List<Ast> blocks = ast.blocks();
    Ast first = blocks.get(0);
    if (!(first instanceof BlockSemAst)) {
      return null;
    }
    BlockSemAst bsa = (BlockSemAst) first;
    String code = bsa.code();
    String command;
    command = code.substring(config.tokenBlock().length());
    command = command.substring(0, command.indexOf(" "));
    StoveCommand cmd = spm.command(command);
    if (cmd == null)
      throw new CommandNotFoundException("未发现该命令 => {0}", command);
    StoveResult result = cmd.execute(spm, config, EnoaValue.NULL, ast, new HashMap<>(attr));
    if (result == null)
      return null;

    Object value = result.value();
    return value == null ? null : String.valueOf(value);
  }

}
