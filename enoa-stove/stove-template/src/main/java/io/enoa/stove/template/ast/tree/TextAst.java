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
package io.enoa.stove.template.ast.tree;

import io.enoa.toolkit.text.PadKit;
import io.enoa.toolkit.text.TextKit;

public class TextAst implements Ast {


  private String code;
  private int start;
  private int end;

  public TextAst(String code, int start, int end) {
    this.code = code;
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
    return AstType.TEXT;
  }

  @Override
  public String code() {
    return this.code;
  }

  public TextAst code(String code) {
    this.code = code;
    return this;
  }

  public TextAst end(int end) {
    this.end = end;
    return this;
  }

  private String elegantValue() {
    if (this.code == null)
      return null;
    return this.code
      .replace("\\", "\\\\")
      .replace("\r", "\\r")
      .replace("\n", "\\n")
      .replace("\t", "\\t")
      .replace(" ", "∙");
  }

  @Override
  public String toString() {
    return TextKit.union("\nAST -> ",
      "start => ", PadKit.lalign(String.valueOf(this.start), "0", 4),
      " end => ", PadKit.lalign(String.valueOf(this.end), "0", 4),
      " type => ", PadKit.ralign(this.type().name(), "∙", 5), " <= ",
      "code => ", this.elegantValue(),
      " <=");
  }

}
