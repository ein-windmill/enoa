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

import io.enoa.stove.template.StoveConfig;
import io.enoa.stove.template.ast.tree.BlockAst;
import io.enoa.stove.template.command.StoveCommand;
import io.enoa.stove.template.command.StoveResult;
import io.enoa.stove.template.thr.SyntaxException;
import io.enoa.toolkit.value.EnoaValue;

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
  public StoveResult execute(StoveConfig config, EnoaValue pipeval, BlockAst ast) throws SyntaxException {
    return null;
  }

}
