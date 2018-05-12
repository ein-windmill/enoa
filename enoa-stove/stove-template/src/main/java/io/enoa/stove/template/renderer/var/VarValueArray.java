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

import io.enoa.stove.template.ast.tree.Ast;
import io.enoa.stove.template.thr.StoveException;
import io.enoa.stove.template.thr.SyntaxException;
import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.toolkit.number.NumberKit;
import io.enoa.toolkit.text.TextKit;

import java.lang.reflect.Array;
import java.util.*;

class VarValueArray {

  private static class Holder {
    private static final VarValueArray INSTANCE = new VarValueArray();
  }

  static VarValueArray instance() {
    return Holder.INSTANCE;
  }

  private char nextChar(char[] cas, int ix) {
    char ca = cas[ix];
    if (ca == ' ')
      return this.nextChar(cas, ix + 1);
    return ca;
  }

  /**
   * 鉴定是否数组取值
   *
   * @param var var
   * @return bolean
   */
  boolean isArrVar(Ast ast, String var) {

    // 判定数组取值语法是否正确, 如果有 [ 则必须要有 ] 结束

    // todo syntax exception message

    String nend = null;
    boolean slove = true;
    boolean isArr = false;
    char[] cas = var.toCharArray();
    int len = cas.length;
    for (int ix = 0; ix < len; ix++) {
      char c = cas[ix];
      if (nend == null && c == ']')
        throw new SyntaxException("语法错误 数组未闭合 => `{0}` line =>{1}", ast.code(), ast.start());

      if ("[".equals(nend)) {
        if (c == ' ')
          continue;

        if (c == '[') {
          if (this.nextChar(cas, ix + 1) == ']')
            throw new SyntaxException("语法错误 获取数组值错误 => `{0}` line =>{1}", ast.code(), ast.start());
          nend = "]";
          slove = false;
          continue;
        }
        throw new SyntaxException("语法错误 多维数组语法错误 => `{0}` line =>{1}", ast.code(), ast.start());
      }

      if (c == '[') {
        if (this.nextChar(cas, ix + 1) == ']')
          throw new SyntaxException("语法错误 获取数组值错误 => `{0}` line =>{1}", ast.code(), ast.start());
        nend = "]";
        slove = false;
        continue;
      }

      if (c == ']') {
        slove = true;
        isArr = true;
        nend = "[";
        continue;
      }
//      if (c == ' ') {
//        if (isArr)
//          continue;
//        if (nend != null)
//          continue;
//      // get('A 0')[2] // 检测错误
//        throw new SyntaxException("语法错误 获取数组值错误 => `{0}` line =>{1}", ast.code(), ast.start());
//      }
      if (!slove) {
        if (!NumberKit.isDigit(TextKit.nospace(String.valueOf(c))))
          throw new SyntaxException("语法错误 数组取值只可为数字 => `{0}` line =>{1}", ast.code(), ast.start());
      }
    }

    return isArr;
  }


  Object parseArrayValueFromAttr(Ast ast, String var, Map<String, ?> attr) {
    String _var0 = var.substring(0, var.indexOf("["));
    Object _val0 = attr.get(_var0);
    if (_val0 == null)
      throw new StoveException("不存在 {0} => line {1}", _var0, ast.start());
//    Class<?> oclazz = _val0.getClass();
    if (!this.isArr(_val0))
      throw new StoveException("访问值非数组 => {0}", var);

    List<Integer> ixs = this.ixs(ast, var);
    for (Integer ix : ixs) {
      _val0 = this.arrVal(ast, _val0, ix);
    }

    return _val0;
  }

  private List<Integer> ixs(Ast ast, String var) {
    String ixsr = var.substring(var.indexOf("["), var.lastIndexOf("]") + 1);
    List<Integer> ixs = new ArrayList<>();

    int ci = 0;
    StringBuilder sbix = new StringBuilder();
    while (true) {
      if (ci == ixsr.length())
        break;
      char c = ixsr.charAt(ci);
      if (c == '[') {
        ci += 1;
        continue;
      }
      if (c == ' ') {
        ci += 1;
        continue;
      }
      if (c == ']') {
        ixs.add(NumberKit.integer(sbix.toString()));
        sbix.delete(0, sbix.length());
        ci += 1;
        continue;
      }
      sbix.append(c);
      ci += 1;
    }
    return ixs;
  }

  private Object arrVal(Ast ast, Object obj, Integer ix) {
    if (obj.getClass().isArray()) {
      int len = Array.getLength(obj);
      if (ix + 1 > len)
        throw new ArrayIndexOutOfBoundsException(EnoaTipKit.message("获取数组长度超出数据长度 => {0} => line {1}", ast.code(), ast.start()));
      return Array.get(obj, ix);
    }
    if (obj instanceof Collection) {
      Collection cols = (Collection) obj;
      if (ix + 1 > cols.size())
        throw new ArrayIndexOutOfBoundsException(EnoaTipKit.message("获取数组长度超出数据长度 => {0} => line {1}", ast.code(), ast.start()));
      Iterator iterator = cols.iterator();
      int i = 0;
      while (iterator.hasNext()) {
        Object next = iterator.next();
        if (i == ix)
          return next;
        i += 1;
      }
    }
    throw new StoveException("错误的数组数据类型");
  }

  private boolean isArr(Object obj) {
    return obj.getClass().isArray() || obj instanceof Collection;
  }

  Object parseArrayValueFromObject(Ast ast, String var, Object source, Map<String, ?> attr) {
//    if (!this.isArr(source))
//      throw new StoveException("访问值非数组 => {0}", var);

    String _var0 = var.substring(0, var.indexOf("["));
    Object _val0 = VarValueObject.instance().parseObjectValue(ast, _var0, source, attr);

    if (_val0 == null)
      throw new StoveException("不存在 {0} => line {1}", _var0, ast.start());

    if (!this.isArr(_val0))
      throw new StoveException("访问值非数组 => {0}", var);

    List<Integer> ixs = this.ixs(ast, var);
    for (Integer ix : ixs) {
      _val0 = this.arrVal(ast, _val0, ix);
    }

    return _val0;
  }

}
