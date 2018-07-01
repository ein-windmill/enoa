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
package io.enoa.tryjson.converter;

import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.text.TextKit;
import io.enoa.tryjson.Tryjson;
import io.enoa.tryjson.thr.JsonException;

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
  public String json(Object object, int depth, ConvConf conf) {
    if (object == null)
      return null;


    if (object.getClass().isArray()) {
      int len = Array.getLength(object);
      List<Object> _list = new ArrayList<>(len);
      for (int i = 0; i < len; i++) {
        _list.add(Array.get(object, i));
      }
      String _json;
      _json = Tryjson.json(_list, depth, conf);
      CollectionKit.clear(_list);
      return _json;
    }

    if (object instanceof Enumeration) {
      List<?> list = Collections.list((Enumeration<?>) object);
      return Tryjson.json(list, depth, conf);
    }

    if (object instanceof Enum)
      return Tryjson.json(((Enum) object).name(), depth);

    return this.bean(object, depth, conf);
  }

  private String bean(Object object, int depth, ConvConf conf) {
    Map _map = new HashMap();

    Class<?> clazz = object.getClass();
//    List<Field> fields = Stream.of(clazz.getDeclaredFields()).collect(Collectors.toList());
//    List<String> fields = Stream.of(clazz.getDeclaredFields())
//      .map(Field::getName)
//      .collect(Collectors.toList());


//    fields.forEach(field -> {
//      field.isAccessible()
//    });

    // 获取当前类以及父类的所有 public 字段
    List<Field> fields = Stream.of(clazz.getFields()).collect(Collectors.toList());

    // 获取当前类定义的所有字段以及父类的字段
    List<String> allfields = new ArrayList<>();
    allfields.addAll(Stream.of(clazz.getDeclaredFields()).map(Field::getName).collect(Collectors.toList()));
    allfields.addAll(fields.stream().map(Field::getName).collect(Collectors.toList()));


    // public 字段 直接加入到序列化中
    fields.forEach(field -> {
      try {
        _map.put(field.getName(), field.get(object));
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
        String _attr = mname.substring(3);
        if (_attr.equalsIgnoreCase("class"))
          continue;

        this.filleMap(object, _attr, method, _map);
        continue;
      }

      // bool 字段
      gix = mname.indexOf("is");
      if (gix == 0 && mname.length() > 2) {
        String _attr = mname.substring(2);
        this.filleMap(object, _attr, method, _map);
        continue;
      }

      // 检查当前方式是否有与字段名相同, 相同则加入到序列化中
      if (allfields.stream().anyMatch(mname::equals)) {
        this.filleMap(object, mname, method, _map);
        continue;
      }
    }
    String json = Tryjson.json(_map, depth, conf);
    CollectionKit.clear(_map);
    return json;
  }

  private void filleMap(Object object, String attr, Method method, Map map) {
    try {
      Object _val = method.invoke(object);
      map.put(TextKit.firstToLower(attr), _val);
    } catch (Exception e) {
      throw new JsonException(e.getMessage(), e);
    }
  }

}
