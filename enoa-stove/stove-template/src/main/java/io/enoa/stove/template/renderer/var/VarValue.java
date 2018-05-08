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
package io.enoa.stove.template.renderer.var;

import io.enoa.stove.template.ast.tree.VarAst;

import java.util.Map;

class VarValue {

  private static class Holder {
    private static final VarValue INSTANCE = new VarValue();
  }

  static String parse(VarAst ast, String var, Map<String, ?> attr, String def) {
    return Holder.INSTANCE.parseVarValue(ast, var, attr, def);
  }

  /**
   * 提取变量值
   *
   * @param var  变量名
   * @param attr 传递参数属性
   * @param def  无值时的默认值
   * @return String
   */
  private String parseVarValue(VarAst ast, String var, Map<String, ?> attr, String def) {
    String[] zones = var.split("\\.");

    Object _val = null;
    for (String zone : zones) {
      boolean isArr = VarValueArray.instance().isArrVar(ast, zone);
      if (isArr) {
        _val = VarValueArray.instance().parseArrayValue(ast, zone, attr);
      } else {
        _val = attr.get(zone);
      }
    }
    return _val == null ? def : String.valueOf(_val);
  }


}
