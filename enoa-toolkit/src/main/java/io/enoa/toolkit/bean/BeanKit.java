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
package io.enoa.toolkit.bean;

import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.toolkit.map.Kv;
import io.enoa.toolkit.map.OKv;
import io.enoa.toolkit.namecase.INameCase;
import io.enoa.toolkit.namecase.NamecaseKit;
import io.enoa.toolkit.namecase.NamecaseType;
import io.enoa.toolkit.number.NumberKit;
import io.enoa.toolkit.sys.ReflectKit;
import io.enoa.toolkit.text.TextKit;
import io.enoa.toolkit.thr.EoException;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.stream.Collectors;

public class BeanKit {

  public static Kv kv(Object bean) {
    return Kv.by(map(bean, true));
  }

  public static Kv kv(Object bean, boolean skipError) {
    return Kv.by(map(bean, skipError));
  }

  public static Kv kv(Object bean, INameCase namecase) {
    return Kv.by(map(bean, namecase));
  }

  public static Kv kv(Object bean, INameCase namecase, boolean skipError) {
    return Kv.by(map(bean, namecase, skipError));
  }

  public static OKv okv(Object bean) {
    return OKv.by(map(bean, true));
  }

  public static OKv okv(Object bean, boolean skipError) {
    return OKv.by(map(bean, skipError));
  }

  public static OKv okv(Object bean, INameCase namecase) {
    return OKv.by(map(bean, namecase));
  }

  public static OKv okv(Object bean, INameCase namecase, boolean skipError) {
    return OKv.by(map(bean, namecase, skipError));
  }

  public static Map<String, Object> map(Object bean) {
    return map(bean, true);
  }

  public static Map<String, Object> map(Object bean, boolean skipError) {
    return map(bean, NamecaseKit.namecase(NamecaseType.CASE_NONE), skipError);
  }

  public static Map<String, Object> map(Object bean, INameCase namecase) {
    return map(bean, namecase, true);
  }

  public static Map<String, Object> map(Object bean, INameCase namecase, boolean skipError) {
    try {
      if (bean == null)
        return null;
      Map<String, Object> ret = new HashMap<>();
      BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
      PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
      if (propertyDescriptors != null && propertyDescriptors.length > 0) {
        for (PropertyDescriptor descriptor : propertyDescriptors) {
          String propertyName = descriptor.getName();
          if ("class".equals(propertyName)) {
            continue;
          }
          Method readMethod = descriptor.getReadMethod();
          Object result = readMethod.invoke(bean);
          ret.put(namecase.convert(propertyName), result);
        }
      }
      return ret;
    } catch (Exception e) {
      if (skipError)
        return null;
      throw new RuntimeException(e.getMessage(), e);
    }
  }


  public static List<Kv> kvs(List beans) {
    return maps(beans, true).stream().map(Kv::by).collect(Collectors.toList());
  }

  public static List<Kv> kvs(List beans, boolean skipError) {
    return maps(beans, skipError).stream().map(Kv::by).collect(Collectors.toList());
  }

  public static List<Kv> kvs(List beans, INameCase namecase) {
    return maps(beans, namecase).stream().map(Kv::by).collect(Collectors.toList());
  }

  public static List<Kv> kvs(List beans, INameCase namecase, boolean skipError) {
    return maps(beans, namecase, skipError).stream().map(Kv::by).collect(Collectors.toList());
  }

  public static List<OKv> okv(List beans) {
    return maps(beans, true).stream().map(OKv::by).collect(Collectors.toList());
  }

  public static List<OKv> okv(List beans, boolean skipError) {
    return maps(beans, skipError).stream().map(OKv::by).collect(Collectors.toList());
  }

  public static List<OKv> okv(List beans, INameCase namecase) {
    return maps(beans, namecase).stream().map(OKv::by).collect(Collectors.toList());
  }

  public static List<OKv> okv(List beans, INameCase namecase, boolean skipError) {
    return maps(beans, namecase, skipError).stream().map(OKv::by).collect(Collectors.toList());
  }

  public static List<Map<String, Object>> maps(List beans) {
    return maps(beans, NamecaseKit.namecase(NamecaseType.CASE_NONE), true);
  }

  public static List<Map<String, Object>> maps(List beans, boolean skipError) {
    return maps(beans, NamecaseKit.namecase(NamecaseType.CASE_NONE), true);
  }

  public static List<Map<String, Object>> maps(List beans, INameCase namecase) {
    return maps(beans, namecase, true);
  }

  public static List<Map<String, Object>> maps(List beans, INameCase namecase, boolean skipError) {
    List<Map<String, Object>> rets = new ArrayList<>();
    beans.forEach(bean -> {
      Map<String, Object> item = map(bean, namecase, skipError);
      rets.add(item);
    });
    return rets;
  }

  public static <R> R reductionMap(Map map, Class<R> clazz) {
    return reductionMap(map, clazz, NamecaseKit.namecase(NamecaseType.CASE_NONE), true, false);
  }

  public static <R> R reductionMap(Map map, Class<R> clazz, boolean skipError, boolean ignorecase) {
    return reductionMap(map, clazz, NamecaseKit.namecase(NamecaseType.CASE_NONE), skipError, ignorecase);
  }

  public static <R> R reductionMap(Map map, Class<R> clazz, INameCase namecase) {
    return reductionMap(map, clazz, namecase, true, false);
  }

  public static <R> R reductionMap(Map mapx, Class<R> clazz, INameCase namecase, boolean skipError, boolean ignorecase) {
    if (mapx == null)
      return null;
    if (clazz == null)
      return null;
    Map _m;
    if (ignorecase) {
      _m = new HashMap(mapx.size());
      mapx.forEach((key, val) -> {
        _m.put(key == null ? null : key.toString().toUpperCase(), val);
      });
    } else {
      _m = mapx;
    }

    R ret = ReflectKit.newInstance(clazz);
    Method[] methods = clazz.getMethods();
    for (Method method : methods) {
      if (method.getParameterCount() != 1)
        continue;
      String name = method.getName();
      Object val = _m.get(ignorecase ? TextKit.upper(name) : name);
      if (val == null) {
        if (!name.startsWith("set"))
          continue;
        name = TextKit.lowerFirst(name.substring(3));
        val = _m.get(ignorecase ? TextKit.upper(name) : name);
        if (val == null) {
          val = _m.get(namecase.convert(ignorecase ? TextKit.upper(name) : name));
          if (val == null)
            continue;
        }
      }

      try {
        if (val instanceof Number) {
          Class<?>[] pts = method.getParameterTypes();
          if (CollectionKit.isEmpty(pts))
            throw new IllegalArgumentException("NOT FOUND ARGUMENTS");
          method.invoke(ret, NumberKit.to((Number) val, pts[0]));
        } else {
          method.invoke(ret, val);
        }
      } catch (IllegalArgumentException e) {
        if (skipError) {
          continue;
        }
        Parameter[] paras = method.getParameters();
        throw new EoException(EnoaTipKit.message("eo.tip.toolkit.object_reduction_by_map_method_ill_arg",
          ReflectKit.methodString(clazz, method),
          CollectionKit.notEmpty(paras) ? paras[0].getType().getName() : "null",
          ReflectKit.intuitiveType(val.getClass().getName())),
          e);
      } catch (Exception e) {
        if (skipError) {
          continue;
        }
        throw new RuntimeException(e.getMessage(), e);
      }
    }
    CollectionKit.clear(_m);
    return ret;
  }

  public static <R> List<R> reductionMaps(List<Map> maps, Class<R> clazz) {
    return reductionMaps(maps, clazz, NamecaseKit.namecase(NamecaseType.CASE_NONE), true, false);
  }

  public static <R> List<R> reductionMaps(List<Map> maps, Class<R> clazz, INameCase namecase) {
    return reductionMaps(maps, clazz, namecase, true, false);
  }

  public static <R> List<R> reductionMaps(List<Map> maps, Class<R> clazz, boolean skipError, boolean ignorecase) {
    return reductionMaps(maps, clazz, NamecaseKit.namecase(NamecaseType.CASE_NONE), skipError, ignorecase);
  }

  public static <R> List<R> reductionMaps(List<Map> maps, Class<R> clazz, INameCase namecase, boolean skipError, boolean ignorecase) {
    if (maps == null)
      return Collections.emptyList();
    List<R> rets = new ArrayList<>();
    maps.forEach(map -> rets.add(reductionMap(map, clazz, namecase, skipError, ignorecase)));
    return rets;
  }

}
