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
package io.enoa.toolkit.sys;

import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.text.TextKit;
import io.enoa.toolkit.thr.EoReflectException;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * 反射工具类
 */
public class ReflectKit {

  public static <T> T newInstance(Class<T> clazz) {
    try {
      return clazz.newInstance();
    } catch (Exception e) {
      throw new EoReflectException(e.getMessage(), e);
    }
  }

  public static boolean hasClazz(String className) {
    try {
      Class.forName(className);
      return true;
    } catch (ClassNotFoundException e) {
      return false;
    }
  }


  public static String methodString(Class clazz, Method method) {
    StringBuilder sb = new StringBuilder();
    if (clazz != null) {
      sb.append(clazz.getName()).append("#");
    }
    sb.append(method.getName());
    sb.append("(");
    Parameter[] paras = method.getParameters();
    if (CollectionKit.notEmpty(paras)) {
      int i = 0;
      for (Parameter para : paras) {
        sb.append(para.getName()).append("::").append(para.getType().getName());
        if (i + 1 < paras.length)
          sb.append(", ");
        i += 1;
      }
    }
    sb.append(")");
    return sb.toString();
  }

  /**
   * 直观类型优化显示
   *
   * @param type type
   * @return String
   */
  public static String intuitiveType(String type) {
    if (type == null)
      return null;
    if (!type.startsWith("["))
      return type;
    switch (type) {
      case "[I":
        return "int[]";
      case "[S":
        return "short[]";
      case "[J":
        return "long[]";
      case "[B":
        return "byte[]";
      case "[C":
        return "char[]";
      case "[D":
        return "double[]";
      case "[F":
        return "float[]";
      case "[Z":
        return "boolean[]";
      default: {
        if (type.startsWith("[L"))
          return TextKit.union(type.substring(2), "[]");
        return type;
      }
    }
  }
}






