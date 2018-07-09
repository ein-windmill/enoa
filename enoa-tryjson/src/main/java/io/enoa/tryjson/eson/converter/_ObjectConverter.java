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
package io.enoa.tryjson.eson.converter;

import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.reflect.ReflectKit;
import io.enoa.toolkit.text.TextKit;
import io.enoa.tryjson.Tsonfig;
import io.enoa.tryjson.eson.Eson;
import io.enoa.tryjson.thr.TryjsonException;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class _ObjectConverter implements EsonConverter<Object> {
  private static class Holder {
    private static final _ObjectConverter INSTANCE = new _ObjectConverter();
  }

  static _ObjectConverter instance() {
    return Holder.INSTANCE;
  }

  @Override
  public String json(Object object, int depth, Tsonfig conf) {
    if (object == null)
      return null;


    if (object.getClass().isArray()) {
      int len = Array.getLength(object);
      List<Object> _list = new ArrayList<>(len);
      for (int i = 0; i < len; i++) {
        _list.add(Array.get(object, i));
      }
      String _json;
      _json = Eson.json(_list, depth, conf);
      CollectionKit.clear(_list);
      return _json;
    }

    if (object instanceof Enumeration) {
      List<?> list = Collections.list((Enumeration<?>) object);
      return Eson.json(list, depth, conf);
    }

    return this.bean(object, depth, conf);
  }

  private String bean(Object object, int depth, Tsonfig conf) {
    Map _map = new HashMap();

    Class<?> clazz = object.getClass();

    // 获取当前类以及父类的所有 public 字段
    List<Field> fields = ReflectKit.fields(clazz);

    // 获取当前类定义的所有字段以及父类的字段
    Set<String> allfields = ReflectKit.allfieldNames(clazz);


    // public 字段 直接加入到序列化中
    fields.forEach(field -> {
      try {
        Object _val = field.get(object);
        if (conf.skipNull() && _val == null)
          return;

        _map.put(field.getName(), _val);
      } catch (IllegalAccessException e) {
        // skip
      }
    });


    // 字段 getter 方式
    Method[] methods = clazz.getMethods();
    for (Method method : methods) {

      Class<?>[] _types = method.getParameterTypes();
      if (_types.length != 0)
        continue;

      String mname = method.getName();
      int gix = mname.indexOf("get");
      // 仅允许 getter 方式
      if (gix == 0 && mname.length() > 3) {
        String _key = mname.substring(3);
        if (_key.equalsIgnoreCase("class"))
          continue;

        this.filleMap(object, TextKit.firstToLower(_key), method, _map, conf);
        continue;
      }

      // bool 字段
      gix = mname.indexOf("is");
      if (gix == 0 && mname.length() > 2) {
        String _key = mname.substring(2);
        this.filleMap(object, TextKit.firstToLower(_key), method, _map, conf);
        continue;
      }

      /*
      检查当前方式是否有与字段名相同, 相同则加入到序列化中
      同名方法策略, 必須該方法是此隊中的某個字段名稱相同才考慮
       */
      if (allfields.stream().anyMatch(mname::equals)) {
        this.filleMap(object, TextKit.firstToLower(mname), method, _map, conf);
        continue;
      }
    }
    String json = Eson.json(_map, depth, conf);
    CollectionKit.clear(_map);
    CollectionKit.clear(fields, allfields);
    return json;
  }

  private void filleMap(Object object, String key, Method method, Map map, Tsonfig conf) {
    try {
      Object _val = method.invoke(object);
      if (conf.skipNull() && _val == null)
        return;

      key = conf.namecase().convert(key);
      map.put(key, _val);
    } catch (Exception e) {
      throw new TryjsonException(e.getMessage(), e);
    }
  }

//  /**
//   * 獲取當前類以及父類的所有字段
//   *
//   * @param clazz 類
//   * @return Set<String>
//   */
//  private Set<String> allFields(Class clazz) {
//    Set<String> fileds = new HashSet<>();
//    while (true) {
//      fileds.addAll(Stream.of(clazz.getDeclaredFields()).map(Field::getName).collect(Collectors.toSet()));
//      Class _super = clazz.getSuperclass();
//      if (_super == null || _super.getName().equalsIgnoreCase("java.lang.Object"))
//        break;
//      clazz = clazz.getSuperclass();
//    }
//    return fileds;
//  }

}
