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
package io.enoa.toolkit.bean.reduction;

import io.enoa.toolkit.bean.Bonfig;
import io.enoa.toolkit.bean.bory.PriorityStrategy;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.namecase.INameCase;
import io.enoa.toolkit.reflect.RefType;
import io.enoa.toolkit.reflect.ReflectKit;
import io.enoa.toolkit.thr.EoReflectException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.*;

class BeanReduction {

  private static class Holder {
    private static final BeanReduction INSTANCE = new BeanReduction();
  }

  static BeanReduction instance() {
    return Holder.INSTANCE;
  }

  <T> T object(Map<String, Object> map, Type type, int depth, Bonfig config, Class<?> clazz, Map<String, RefType> generic) {
    INameCase namecase = config.namecase();


    Object bean = ReflectKit.newInstance(clazz).build();
    List<Field> pubfields = ReflectKit.fields(clazz);
    List<Field> declareds = ReflectKit.declaredFields(clazz);
    Method[] methods = clazz.getMethods();

    Set<String> alreadySet = new HashSet<>();

    Map<String, Method> mdmap = new HashMap<>();
    for (Method method : methods) {
      String mname = method.getName();
      // setter 方法
      int six = mname.indexOf("set");
      if (six > 0 && mname.length() > 3) {
        Class<?>[] pts = method.getParameterTypes();
        if (CollectionKit.isEmpty(pts))
          continue;
        if (pts.length != 1)
          continue;

        mname = mname.substring(3);
        mdmap.put(namecase.convert(mname), method);
        continue;
      }
      // 于字段同名方法
      if (declareds.stream().noneMatch(field -> field.getName().equals(method.getName()))) {
        continue;
      }
      mdmap.put(namecase.convert(mname), method);
    }

    map.forEach((_key, _val) -> {
      if (_key == null)
        _key = config.nullKey();
      if (_key == null)
        return;

      RefType genericType = generic.get(_key);
      String key = namecase.convert(_key);

      pubfields.stream()
        .filter(field -> namecase.convert(field.getName()).equals(key))
        .findFirst()
        .ifPresent(field -> {
          Class<?> expect = field.getType();
          Object _v0 = this.depthValue(expect, _val, map, type, depth, config, clazz, genericType);
          try {
            field.set(bean, _v0);
            alreadySet.add(key);
          } catch (IllegalAccessException e) {
            if (!config.skipError())
              throw new EoReflectException(e.getMessage(), e);
            e.printStackTrace();
          }
        });

      Method method = mdmap.get(key);
      if (method != null) {
        // 优先策略
        if (alreadySet.stream().anyMatch(as -> as.equals(key))) {
          if (config.priority() == PriorityStrategy.FIELD)
            return;
        }

        Class<?> expect = method.getParameterTypes()[0];
        Object _v1 = this.depthValue(expect, _val, map, type, depth, config, clazz, genericType);
        try {
          method.invoke(bean, _v1);
          alreadySet.add(key);
        } catch (Exception e) {
          if (!config.skipError())
            throw new EoReflectException(e.getMessage(), e);
          e.printStackTrace();
        }
      }

    });
    return null;
  }

  private Object depthValue(Class<?> expect, Object value, Map<String, Object> map,
                            Type type, int depth, Bonfig config, Class<?> clazz, RefType fieldType) {

//    if (fieldType == null) {
//
//      return value;
//    }
//    String fts = fieldType.string();
//    int ftix = fts.indexOf("<");
//    String ftm = ftix > 0 ? fts.substring(0, ftix) : fts;
//
//    // 如果 期望的类型是  Object , 表明该字段的类型就是泛型所指定的类型 T
//    if (Object.class.getName().equals(expect.getName())) {
//
//      return value;
//    }
//    // 否则, 泛型类型是该字段类型的泛型, e.g. Map<String, T>
//    Object o = this.newInstance(clazz, config, fieldType);
//    return value;

    if (Map.class.isAssignableFrom(expect)) {

    }

    return value;
  }

  private Object newInstance(Class<?> clazz, Bonfig config, RefType fieldType) {
    if (Map.class.isAssignableFrom(clazz)) {
      return config.bmap().create();
    }
    if (Iterable.class.isAssignableFrom(clazz)) {
      return config.bcollection().create();
    }
    if (clazz.isInterface())
      throw new EoReflectException(""); // todo can not support interface new instance
    return ReflectKit.newInstance(clazz).build();
  }

}
