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
package io.enoa.stove.template.ast.tree;

import io.enoa.toolkit.text.PadKit;

import java.util.List;

public class BlockAst implements Ast {

  private List<Ast> blocks;
  private int start;
  private int end;

  public BlockAst(List<Ast> blocks, int start, int end) {
    this.blocks = blocks;
    this.start = start;
    this.end = end;
  }

  @Override
  public int start() {
    return this.start;
  }

  @Override
  public int end() {
    return this.end;
  }

  @Override
  public AstType type() {
    return AstType.BLOCK;
  }

  @Override
  public String code() {
    StringBuilder sb = new StringBuilder();
    for (Ast block : blocks) {
      sb.append(block.code());
    }
    return sb.toString();
  }


  public List<Ast> blocks() {
    return this.blocks;
  }

  private String buildToString(List<Ast> blocks, int ix) {
    StringBuilder sb = new StringBuilder();
    for (Ast _ast : blocks) {
      if (_ast instanceof BlockAst) {
        List<Ast> subasts = ((BlockAst) _ast).blocks();
        sb.append(this.buildToString(subasts, ix + 1));
        continue;
      }
      sb.append(PadKit.rpad("\n>", ">", ix)).append(" ");
      String supstr = _ast.toString();
      sb.append(supstr.length() > 0 ? supstr.substring(1) : supstr);
    }
    return sb.toString();
  }

  @Override
  public String toString() {
    return this.buildToString(this.blocks, 0);
  }
}
