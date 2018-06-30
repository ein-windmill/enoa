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
package io.enoa.stove.template.command;

import io.enoa.stove.template.SPM;
import io.enoa.stove.template.StoveConfig;
import io.enoa.stove.template.ast.tree.Ast;
import io.enoa.stove.template.command.helper.StoveHelper;
import io.enoa.stove.template.thr.SyntaxException;
import io.enoa.toolkit.text.TextKit;
import io.enoa.toolkit.value.EnoaValue;

import java.util.Map;

public interface StoveCommand {

  String name();

  default boolean multi() {
    return false;
  }

  default String end() {
    if (!this.multi())
      return null;
    return TextKit.reverse(this.name());
  }

  default StoveHelper help() {
    return null;
  }

  StoveResult execute(SPM spm, StoveConfig config, EnoaValue pipeval, Ast ast, Map<String, ?> attr) throws SyntaxException;
}
