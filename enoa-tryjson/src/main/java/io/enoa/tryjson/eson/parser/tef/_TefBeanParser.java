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
package io.enoa.tryjson.eson.parser.tef;

import io.enoa.tryjson.Tsonfig;
import io.enoa.tryjson.eson.parser.BsonParser;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class _TefBeanParser implements BsonParser {

  private static class Holder {
    private static final _TefBeanParser INSTANCE = new _TefBeanParser();
  }

  public static _TefBeanParser instance() {
    return Holder.INSTANCE;
  }

  @Override
  public <T> T object(String json, Type type, Tsonfig config) {
//    Toa toa = EsonParser.def().parse(json, config);
//    return this.reduction(toa, type, config);
    return null;
  }

  @Override
  public <T> List<T> array(String json, Type type, Tsonfig config) {
//    Toa toa = EsonParser.def().parse(json, config);
//    List<T> ret = this.reduction(toa, type, config);
//    return ret == null ? Collections.emptyList() : ret;
    return Collections.emptyList();
  }

//  /**
//   * bean 还原, object 解析方式也支持 array 解析, 因此统一在此处处理
//   *
//   * @param toa    toa
//   * @param type   type
//   * @param config config
//   * @param <T>    Result
//   * @return T
//   */
//  private <T> T reduction(Toa toa, Type type, Tsonfig config) {
//    RefType reftype = TypeKit.reftype(type);
//    if (toa instanceof Jo) {
//      return this.reductionObject((Jo) toa, reftype, config);
//    }
//    if (toa instanceof Ja) {
//      return this.reductionArray((Ja) toa, reftype, config);
//    }
//    return null;
//  }
//
//  @FunctionalInterface
//  private interface Filling {
//    void fill(Object obj);
//  }
//
//  @SuppressWarnings({"rawtypes", "unchecked"})
//  private <T> T reductionObject(Jo jo, RefType reftype, Tsonfig config) {
//    Class<T> clazz;
//    if (reftype == null) {
//      clazz = (Class<T>) Jo.class;
//    } else {
//      clazz = ReflectKit.newInstance(reftype.type()).clazz();
//    }
//    T target = ReflectKit.newInstance(clazz).build();
//    if (target instanceof Map) {
//      Map m0 = (Map) target;
//      for (String _k0 : jo.keySet()) {
//        Object _v0 = jo.get(_k0);
//        // 填充数据到 map
//        this.fillObject(_v0, reftype, config, null, obj ->
//          m0.put(_k0, obj)
//        );
//      }
//      return target;
//    }
//
//    // 获取当前类以及父类的所有 public 字段
//    List<Field> fields = ReflectKit.fields(clazz);
//    // 获取当前类定义的所有字段以及父类的字段
//    Set<String> allfields = ReflectKit.declaredFields(clazz).stream().map(Field::getName).collect(Collectors.toSet());
//
//    // public 字段名称
////    Set<String> pubfields = fields.stream().map(Field::getName).collect(Collectors.toSet());
//    // private 私有字段, 私有字段只能通过 setter 或者同名的方法方可写入到对象中
//    Set<String> privfields = allfields.stream().filter(field -> fields.stream().noneMatch(pf -> pf.getName().equals(field))).collect(Collectors.toSet());
//
//    // 所有方法
//    Method[] methods = clazz.getMethods();
//    Map<String, Method> allowMethods = new HashMap<>();
//    for (Method method : methods) {
//      String mname = method.getName();
//      // 允许 setter
//      int gix = mname.indexOf("set");
//      if (gix == 0 && mname.length() > 3) {
//        // 合法 setter 方法校验是否只有一个参数
//        Class<?>[] ptypes = method.getParameterTypes();
//        if (CollectionKit.isEmpty(ptypes))
//          continue;
//        if (ptypes.length != 1)
//          continue;
//        // 校验完毕放入到允许的 methods 中, 供后续填充数据使用, 同时同对 method name 进行格式转换
//        allowMethods.put(config.namecase().convert(mname.substring(3)), method);
//        continue;
//      }
//      // 允许于 public 字段同名的方法
//      if (fields.stream().anyMatch(pf -> pf.getName().equals(mname))) {
//        Class<?>[] ptypes = method.getParameterTypes();
//        if (CollectionKit.isEmpty(ptypes))
//          continue;
//        if (ptypes.length != 1)
//          continue;
//        allowMethods.put(config.namecase().convert(mname), method);
//        continue;
//      }
//      // 允许于 private 字段同名的方法
//      if (privfields.stream().anyMatch(pf -> pf.equals(mname))) {
//        Class<?>[] ptypes = method.getParameterTypes();
//        if (CollectionKit.isEmpty(ptypes))
//          continue;
//        if (ptypes.length != 1)
//          continue;
//        allowMethods.put(config.namecase().convert(mname), method);
//        continue;
//      }
//    }
//
//    for (String _k1 : jo.keySet()) {
//      Object _v1 = jo.get(_k1);
//      String _k2 = config.namecase().convert(_k1);
//
//      Class<?> argType = null;
//
//      Method method = allowMethods.get(_k2);
//      // 无论是否找到方法, 首先写入 public 字段, 避免后写入, 刷掉了 方法写入有部分逻辑存在
//      Optional<Field> first = fields.stream().filter(pf -> config.namecase().convert(pf.getName()).equals(_k2)).findFirst();
//      if (first.isPresent()) {
//        argType = this.realType(1, first.get(), clazz);
//      }
//      if (method != null) {
//        argType = this.realType(2, method, clazz);
//      }
//      // 不能从 field 以及 method 找到返回类型, 表明该字段不存在于当前对象中, 直接抛弃
//      if (argType == null)
//        continue;
//      // 填充数据到 class 中
//      this.fillObject(_v1, reftype, config, argType, obj -> {
//        if (first.isPresent()) {
//          Field field = first.get();
//          try {
//            field.set(target, obj);
//          } catch (IllegalAccessException e) {
//            throw new EoReflectException(e.getMessage(), e);
//          }
//          return;
//        }
//        // 如果根据当前名称找到同名的方法, 进行方法调用, 并写入
//        try {
//          method.invoke(target, obj);
//        } catch (Exception e) {
//          throw new EoReflectException(e.getMessage(), e);
//        }
//      });
//    }
//
//    return target;
//  }
//
//  /**
//   * 获取真实的字段类型
//   *
//   * @param type   type 1 field 2 method
//   * @param origin origin 来源 Field Method
//   * @param clazz  所属 class
//   * @return Class
//   */
//  private Class<?> realType(int type, Object origin, Class clazz) {
//    Class<?> argType;
//    String fieldName = null;
//    switch (type) {
//      case 1:
//        Field field = (Field) origin;
////        argType.add(field.getType());
//        argType = field.getType();
//        fieldName = field.getName();
//        break;
//      case 2:
//        Method method = (Method) origin;
//        argType = method.getParameterTypes()[0];
//        String mname = method.getName();
//        fieldName = mname.startsWith("set") ? TextKit.firstToLower(method.getName().substring(3)) : mname;
//        break;
//      default:
//        throw new TryjsonException(""); // todo unknown operate exception
//    }
//
//    // list 直接取第一个泛型形参即可, map, 在 json 中, 键必须是字符串, 因此取第二个形参
//    if (Iterable.class.isAssignableFrom(argType) || Map.class.isAssignableFrom(argType)) {
//      try {
//        Type genericType = clazz.getDeclaredField(fieldName).getGenericType();
//        Type[] atas = ((ParameterizedType) genericType).getActualTypeArguments();
//        if (CollectionKit.isEmpty(atas))
//          return null;
//        argType = ReflectKit.newInstance(atas[Map.class.isAssignableFrom(argType) ? 1 : 0]).clazz();
//      } catch (Exception e) {
//        throw new EoReflectException(e.getMessage(), e);
//      }
//    }
//    return argType;
//  }
//
//  /**
//   * 填充 object 值, 反序列化 json
//   *
//   * @param obj     object 原始值
//   * @param filling 数据填充
//   */
//  private void fillObject(Object obj, RefType reftype, Tsonfig config, Class<?> type, Filling filling) {
//    if (obj instanceof Jo) {
//      if (type != null && !Object.class.getName().equals(type.getName())) {
//        if (Iterable.class.isAssignableFrom(type))
//          throw new EoReflectException(""); // todo type fail.
//        Object _val = this.reductionObject((Jo) obj, RefType.with(type.getName()), config);
//        filling.fill(_val);
//        return;
//      }
//      if (reftype != null) {
////        TypeKit.reftype(reftype.type())
//        System.out.println(1);
//      }
//      filling.fill(obj);
//      return;
//    }
//    if (obj instanceof Ja) {
////      Object oty = type.get
//      Object _val = this.reductionArray((Ja) obj, RefType.with(type.getName()), config);
//      filling.fill(_val);
//      return;
//    }
//    Object _val = ConvertKit.to(ConvertKit.string(obj), type);
//    filling.fill(_val);
//  }
//
//  @SuppressWarnings({"rawtypes", "unchecked"})
//  private <T> T reductionArray(Ja ja, RefType reftype, Tsonfig config) {
//
//    boolean refisarr = true;
//    Class<T> sup = ReflectKit.newInstance(reftype.type()).clazz();
//    if (!Iterable.class.isAssignableFrom(sup)) {
//      sup = (Class<T>) ArrayList.class;
//      refisarr = false;
//    }
//
//    Collection arr = (Collection) ReflectKit.newInstance(sup).build();
//    int len = ja.size();
//    for (int i = 0; i < len; i++) {
//      Object a = ja.get(i);
//      if (a instanceof Ja) {
//        RefType _joref = refisarr ? reftype.safeGeneric(0) : reftype;
//        arr.add(this.reductionArray((Ja) a, _joref, config));
//        continue;
//      }
//      if (a instanceof Jo) {
//        RefType _joref = refisarr ? reftype.safeGeneric(0) : reftype;
//        arr.add(this.reductionObject((Jo) a, _joref, config));
//        continue;
//      }
//      RefType[] generics = reftype.generics();
//      if (CollectionKit.notEmpty(generics)) {
//        RefType generic = generics[0];
//        boolean assign = ReflectKit.newInstance(generic.type()).clazz()
//          .isAssignableFrom(a.getClass());
//        if (!assign)
//          throw new TryjsonException("Generic fail.");
//      }
//      arr.add(a);
//    }
//    return (T) arr;
//  }

}
