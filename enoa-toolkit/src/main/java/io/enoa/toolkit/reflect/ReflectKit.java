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

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

  public static boolean hasClazz(String className) {
    try {
      Class.forName(className);
      return true;
    } catch (ClassNotFoundException e) {
      return false;
    }
  }

  public static boolean hasClazz(String className, boolean initialize, ClassLoader loader) {
    try {
      Class.forName(className, initialize, loader);
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
  public static List<Field> declaredFields(Class<?> clazz) {
    List<Field> fileds = new ArrayList<>();
    while (true) {
      fileds.addAll(Stream.of(clazz.getDeclaredFields()).collect(Collectors.toSet()));
      Class _super = clazz.getSuperclass();
      if (_super == null || _super.getName().equalsIgnoreCase("java.lang.Object"))
        break;
      clazz = clazz.getSuperclass();
    }
    return fileds;
  }

  /**
   * 获取类的所有泛型参数名称
   *
   * @param clazz class
   * @return String[]
   */
  public static String[] genericTypes(Class<?> clazz) {
    // 当前类的所有泛型名称
    TypeVariable<? extends Class<?>>[] czParas = clazz.getTypeParameters();
    String[] czgnes = new String[czParas.length];
    for (int i = 0; i < czParas.length; i++) {
      czgnes[i] = czParas[i].getName();
    }
    return czgnes;
  }

  /**
   * 获取某个泛型在该当前类中的定义位置
   *
   * @param clazz       类
   * @param genericType 泛型定义名称 T
   * @return int
   */
  public static int indexOfGenericType(Class<?> clazz, String genericType) {
    String[] generics = genericTypes(clazz);
    for (int i = 0; i < generics.length; i++) {
      if (genericType.contains(TextKit.union("<", generics[i], ">")))
        return i;
    }
    return -1;
  }

  /**
   * 将 Type 映射到类中的具体字段
   *
   * @param clazz class
   * @param type  type
   * @return Map
   */
  public static Map<String, RefType> mapFieldGeneric(Class<?> clazz, Type type) {

    RefType reftype = TypeKit.reftype(type);
    List<Field> fields = ReflectKit.declaredFields(clazz);
    Map<String, RefType> fieldGeneric = new HashMap<>();

//    int ix = 0;
//    for (Field field : fields) {
//      Type _generic = field.getGenericType();
//      String gtype = _generic.getTypeName();
//
//      if (!gtype.contains("<")) {
//        if (ReflectKit.hasClazz(gtype)) {
//          continue;
//        }
//      }
//
//      String fname = field.getName();
////      RefType[] generics = reftype.generics();
//      // 只有一个泛型或没有其他类嵌套泛型
//      if (!gtype.contains("<")) {
//        int tix = ReflectKit.indexOfGenericType(clazz, gtype);
//        if (tix == -1)
//          continue;
//        fieldGeneric.put(fname, reftype.safeGeneric(ix));
//        ix += 1;
//        continue;
//      }
//      /*
//      解析出当前字段有多少个泛型, 不一定都是可用的泛型
//      e.g. List<Map<String, T>>  => [Map<String, T>, String, T]
//       */
////      List<String> fgts = parseGenericTypes(gtype);
////      for (String gt : fgts) {
////        int tix = ReflectKit.indexOfGenericType(clazz, gt);
////        if (tix == -1)
////          continue;
////        fieldGeneric.put(fname, reftype.safeGeneric(ix));
////        ix += 1;
////      }
////      CollectionKit.clear(fgts);
//
//      int tix = ReflectKit.indexOfGenericType(clazz, gtype);
//      if (tix != -1) {
//        fieldGeneric.put(fname, reftype.safeGeneric(ix));
//        ix += 1;
//      }
//    }
//    return fieldGeneric;

    // todo fix it
    return null;
  }


//  /**
//   * 解析类的所有存在泛型
//   *
//   * @param gtype 泛型字符串
//   * @return List<String>
//   */
//  private static List<String> parseGenericTypes(String gtype) {
//    // 截取第一个 < 开始后面的内容
//    String _gtype0 = gtype.substring(gtype.indexOf('<') + 1);
//    // 删除最后一个 >
//    _gtype0 = _gtype0.substring(0, _gtype0.length() - 1);
//
//    List<String> rets = new ArrayList<>();
//    StringBuilder _gtp = new StringBuilder();
//    int len = _gtype0.length();
//    int ix = 0;
//    while (true) {
//      if (ix == len) {
//        if (_gtp.length() == 0)
//          break;
//        rets.add(_gtp.toString());
//        _gtp.delete(0, _gtp.length());
//        break;
//      }
//
//      char ch = _gtype0.charAt(ix);
//
//      ix += 1;
//
//      if (ch == ' ') {
//        continue;
//      }
//
//      if (ch == ',') {
//        rets.add(_gtp.toString());
//        _gtp.delete(0, _gtp.length());
//        continue;
//      }
//      _gtp.append(ch);
//    }
//
//    List<List<String>> others = new ArrayList<>();
//    for (String item : rets) {
//      if (item.contains("<"))
//        others.add(parseGenericTypes(item));
//    }
//    others.forEach(rets::addAll);
//    CollectionKit.clear(others);
//
//    return rets;
//  }


}






