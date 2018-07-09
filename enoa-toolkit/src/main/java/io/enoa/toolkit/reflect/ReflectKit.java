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
package io.enoa.toolkit.reflect;

import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.text.TextKit;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 反射工具类
 */
public class ReflectKit {

  public static NewClassStringInstanceBuilder newInstance(String clazz) {
    return new NewClassStringInstanceBuilder(clazz);
  }

  public static <T> NewClassInstanceBuilder<T> newInstance(Class<T> clazz) {
    return new NewClassInstanceBuilder<>(clazz);
  }

  public static NewClassStringInstanceBuilder newInstance(Type type) {
    String czn = type.getTypeName();
    int ix = czn.indexOf("<");
    return ix == -1 ?
      newInstance(czn) :
      newInstance(czn.substring(0, ix));
  }

//  public static <T> T newInstance(Class<T> clazz) {
//    try {
//      return clazz.getConstructor().newInstance();
//    } catch (Exception e) {
//      throw new EoReflectException(e.getMessage(), e);
//    }
//  }

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

  /**
   * 获取当前类以及父类的所有 public 字段
   *
   * @param clazz class
   * @return List
   */
  public static List<Field> fields(Class<?> clazz) {
    return Stream.of(clazz.getFields()).collect(Collectors.toList());
  }

  /**
   * 获取当前类定义的所有字段以及父类的字段
   *
   * @param clazz class
   * @return Set
   */
  public static Set<String> allfieldNames(Class<?> clazz) {
    Set<String> fileds = new HashSet<>();
    while (true) {
      fileds.addAll(Stream.of(clazz.getDeclaredFields()).map(Field::getName).collect(Collectors.toSet()));
      Class _super = clazz.getSuperclass();
      if (_super == null || _super.getName().equalsIgnoreCase("java.lang.Object"))
        break;
      clazz = clazz.getSuperclass();
    }
    return fileds;
  }

}






