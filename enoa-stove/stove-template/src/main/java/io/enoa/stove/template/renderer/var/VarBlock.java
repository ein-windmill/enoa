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
package io.enoa.stove.template.renderer.var;

import io.enoa.toolkit.text.TextKit;

class VarBlock {


  public enum Type {
    NORMAL,
    COND_3,
    INC
  }

  //  private List<VarBlockSub> subs;
  private StringBuilder code;
  private Type type;

  public VarBlock(Type type) {
    this.type = type;
    this.code = new StringBuilder();
  }

  public String code() {
    return this.code.toString();
  }

  public Type type() {
    return this.type;
  }

  public VarBlock type(Type type) {
    this.type = type;
    return this;
  }

  public VarBlock append(String text) {
    this.code.append(text);
    return this;
  }

  public VarBlock append(char c) {
    this.code.append(c);
    return this;
  }


  @Override
  public String toString() {
//    return "VarBlock{" +
//      "code=" + code +
//      '}';
    return TextKit.union(this.type.name(), " => ", this.code);
  }
}
