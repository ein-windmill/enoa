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

import java.io.Serializable;

class VarBlockSub implements Serializable {

  private StringBuilder code;

  public VarBlockSub append(String text) {
    this.code.append(text);
    return this;
  }

  public VarBlockSub append(char c) {
    this.code.append(c);
    return this;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) return true;
    if (object == null || getClass() != object.getClass()) return false;
    VarBlockSub that = (VarBlockSub) object;
    return code != null ? code.equals(that.code) : that.code == null;
  }

  @Override
  public int hashCode() {
    return code != null ? code.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "VarBlockSub{" +
      "code=" + code +
      '}';
  }
}
