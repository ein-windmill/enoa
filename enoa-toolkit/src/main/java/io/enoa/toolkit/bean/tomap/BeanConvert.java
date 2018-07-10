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
package io.enoa.toolkit.bean.tomap;

import io.enoa.toolkit.bean.Bonfig;
import io.enoa.toolkit.bean.bory.PriorityStrategy;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.convert.ConvertKit;
import io.enoa.toolkit.reflect.ReflectKit;
import io.enoa.toolkit.text.TextKit;
import io.enoa.toolkit.thr.EoException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;

class BeanConvert {

  private static class Holder {
    private static final BeanConvert INSTANCE = new BeanConvert();
  }

  static BeanConvert instance() {
    return Holder.INSTANCE;
  }

  Map<String, Object> convert(Object bean, int depth, Bonfig config) {
    Class<?> bclazz = bean.getClass();
    if (ConvertKit.supportConvert(bclazz)) {
      Map<String, Object> map = config.bmap().create();
      String _key = ConvertKit.string(ConvertKit.to(ConvertKit.string(bean), bclazz));
      map.put(_key == null ? config.nullKey() : config.namecase().convert(_key), null);
      return map;
    }

    if (bean instanceof Map) {
      return _BeanToMap.map(bean, depth, config);
    }

    return this.convertBean(bean, depth, config, bclazz);
  }

  private Map<String, Object> convertBean(Object bean, int depth, Bonfig config, Class<?> bclazz) {
    Map<String, Object> ret = config.bmap().create();
    List<Field> pubfields = ReflectKit.fields(bclazz);
    List<Field> declareds = ReflectKit.declaredFields(bclazz);
    Method[] methods = bclazz.getMethods();

    // 先訪問 public field
    pubfields.forEach(field -> {
      String fname = config.namecase().convert(field.getName());
      try {
        Object _val = field.get(bean);
        _val = this.depthValue(fname, _val, depth, config);
        ret.put(fname, _val);
      } catch (IllegalAccessException e) {
        if (!config.skipError())
          throw new EoException(e.getMessage(), e);
        e.printStackTrace();
      }
    });

    for (Method method : methods) {
      Class<?>[] ptypes = method.getParameterTypes();
      // 不考慮有參數的方法
      if (CollectionKit.notEmpty(ptypes))
        continue;

      String mname = method.getName();

      int gix = mname.indexOf("get");
      if (gix == 0 && mname.length() > 3) {
        String _mn0 = mname.substring(3);
        if (declareds.stream().noneMatch(field -> field.getName().equals(TextKit.firstToLower(_mn0))))
          continue;
        this.fillMap(bean, _mn0, method, ret, depth, config);
        continue;
      }

      gix = mname.indexOf("is");
      if (gix == 0 && mname.length() > 2) {
        String _mn0 = mname.substring(2);
        if (declareds.stream().noneMatch(field -> field.getName().equals(TextKit.firstToLower(_mn0))))
          continue;
        this.fillMap(bean, _mn0, method, ret, depth, config);
        continue;
      }

      if (declareds.stream().anyMatch(field -> field.getName().equals(mname))) {
        this.fillMap(bean, mname, method, ret, depth, config);
      }
    }
    CollectionKit.clear(pubfields, declareds);
    return ret;
  }

  private void fillMap(Object bean, String mname, Method method, Map<String, Object> ret, int depth, Bonfig config) {
    mname = config.namecase().convert(mname);
    if (ret.containsKey(mname)) {
      if (config.priority() == PriorityStrategy.FIELD)
        return;
    }
    try {
      Object _val = method.invoke(bean);
      if (_val == null) {
        ret.put(mname, null);
        return;
      }
      _val = this.depthValue(mname, _val, depth, config);
      ret.put(mname, _val);
    } catch (Exception e) {
      if (!config.skipError())
        throw new EoException(e.getMessage(), e);
      e.printStackTrace();
    }
  }

  private Object depthValue(String key, Object value, int depth, Bonfig config) {
    Class<?> vclazz = value.getClass();
    if (ConvertKit.supportConvert(vclazz)) {
      return value;
    }
    if (value instanceof Iterable) {
      Collection<Object> collection = config.bcollection().create();
      for (Object _itv : ((Iterable) value)) {
        if (ConvertKit.supportConvert(_itv.getClass())) {
          collection.add(_itv);
          continue;
        }
        collection.add(_BeanToMap.map(_itv, depth, config));
      }
      return collection;
    }
    if (value instanceof Enum) {
      return config.enumer().convert((Enum) value);
    }

    return _BeanToMap.map(value, depth, config);
  }

}
