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

import io.enoa.stove.template.ast.tree.Ast;
import io.enoa.stove.template.thr.StoveException;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.number.NumberKit;
import io.enoa.toolkit.text.TextKit;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

class VarValueObject {

  private static class Holder {
    private static final VarValueObject INSTANCE = new VarValueObject();
  }

  static VarValueObject instance() {
    return Holder.INSTANCE;
  }


  Object parseObjectValue(Ast ast, String var, Object source, Map<String, ?> attr) {
    if (var.contains("(") && var.contains(")")) {
      return this.parseObjectValueFunc(ast, var, source, attr);
    }
    return this.parseObjectValueAttr(ast, var, source);
  }

  private Object parseObjectValueFunc(Ast ast, String var, Object source, Map<String, ?> attr) {

    String funcName = var.substring(0, var.indexOf("("));
    String vags = var.substring(var.indexOf("(") + 1, var.lastIndexOf(")"));


    try {
      Class<?> clazz = source.getClass();
      Method[] methods = clazz.getMethods();
      Method invm = null;
      for (Method method : methods) {
        if (!method.getName().equals(funcName))
          continue;
        invm = method;
        break;
      }
      if (invm != null) {
        return this.callMethod(invm, source, vags, attr);
      }

      funcName = TextKit.union("get", TextKit.upperFirst(funcName));
      for (Method method : methods) {
        if (!method.getName().equals(funcName))
          continue;
        invm = method;
        break;
      }
      if (invm != null) {
        return this.callMethod(invm, source, vags, attr);
      }

      return null;
    } catch (Exception e) {
      throw new StoveException(e.getMessage(), e);
    }
  }

  private Object callMethod(Method method, Object source, String senArg, Map<String, ?> attr) throws Exception {
    Class<?>[] ptypes = method.getParameterTypes();
    List<Object> args = new ArrayList<>();
    if (CollectionKit.notEmpty(ptypes)) {
      if (TextKit.blanky(senArg))
        throw new IllegalArgumentException("调用方法参数错误 => " + Arrays.toString(ptypes));
      String[] targs = senArg.split(",");
      if (ptypes.length != targs.length)
        throw new IllegalArgumentException("调用方法参数不匹配 => " + senArg);

      for (int i = 0; i < targs.length; i++) {
        String targ = targs[i];
        Class ptype = ptypes[i];
        targ = TextKit.nospace(targ);
        char c0 = targ.charAt(0),
          c1 = targ.charAt(targ.length() - 1);
        if ((c0 == '\'' && c1 == '\'') || (c0 == '"' && c1 == '"')) {
          if (ptype != String.class && ptype != Object.class)
            throw new IllegalArgumentException("参数类型错误 => " + ptype);
          targ = targ.substring(1);
          targ = targ.substring(0, targ.length() - 1);
          args.add(targ);
          continue;
        }
        if (NumberKit.isNumber(targ)) {
          if (!ptype.isAssignableFrom(Number.class))
            throw new IllegalArgumentException("参数类型错误 => " + ptype);
          args.add(NumberKit.to(targ, ptype));
          continue;
        }
        args.add(attr.get(targ));
      }

    }
    return method.invoke(source, args.toArray());
  }

  private Object parseObjectValueAttr(Ast ast, String var, Object source) {
    try {
      if (source instanceof Map) {
        return ((Map) source).get(var);
      }
      Class<?> clazz = source.getClass();
      Field field = clazz.getField(var);
      if (field != null) {
        return field.get(source);
      }
      return null;
    } catch (Exception e) {
      throw new StoveException(e.getMessage(), e);
    }
  }

}
