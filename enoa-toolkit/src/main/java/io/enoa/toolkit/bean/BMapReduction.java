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

import io.enoa.toolkit.EoConst;
import io.enoa.toolkit.bean.reduction._MapToBean;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.toolkit.namecase.INameCase;
import io.enoa.toolkit.namecase.NamecaseKit;
import io.enoa.toolkit.namecase.NamecaseType;
import io.enoa.toolkit.number.NumberKit;
import io.enoa.toolkit.reflect.ReflectKit;
import io.enoa.toolkit.text.TextKit;
import io.enoa.toolkit.thr.EoException;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class BMapReduction {

  private static class Holder {
    private static final BMapReduction INSTANCE = new BMapReduction();
  }

  public static BMapReduction instance() {
    return Holder.INSTANCE;
  }

  private BMapReduction() {

  }

  public <R> R objet(Map<String, Object> map, Type type) {
    return objet(map, type, BeanKit.config());
  }

  public <R> R objet(Map<String, Object> map, Type type, Bonfig config) {
    return objet(map, type, EoConst.DEPTH, config);
  }

  public <R> R objet(Map<String, Object> map, Type type, int depth, Bonfig config) {
    return _MapToBean.instance().object(map, type, depth, config);
  }


  public <R> R map(Map map, Class<R> clazz) {
    return map(map, clazz, NamecaseKit.namecase(NamecaseType.CASE_NONE), true);
  }

  public <R> R map(Map map, Class<R> clazz, boolean skipError) {
    return map(map, clazz, NamecaseKit.namecase(NamecaseType.CASE_NONE), skipError);
  }

  public <R> R map(Map map, Class<R> clazz, INameCase namecase) {
    return map(map, clazz, namecase, true);
  }

  public <R> R map(Map map, Class<R> clazz, INameCase namecase, boolean skipError) {
    if (map == null)
      return null;
    if (clazz == null)
      return null;

    R ret = ReflectKit.newInstance(clazz).build();
    Method[] methods = clazz.getMethods();
    for (Method method : methods) {
      String name = method.getName();
      Object val = map.get(name);
      if (val == null) {
        if (!name.startsWith("set"))
          continue;
        name = TextKit.firstToLower(name.substring(3));
        val = map.get(name);
        if (val == null) {
          val = map.get(namecase.convert(name));
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
    return ret;
  }

  public <R> List<R> maps(List<Map> maps, Class<R> clazz) {
    return maps(maps, clazz, NamecaseKit.namecase(NamecaseType.CASE_NONE), true);
  }

  public <R> List<R> maps(List<Map> maps, Class<R> clazz, INameCase namecase) {
    return maps(maps, clazz, namecase, true);
  }

  public <R> List<R> maps(List<Map> maps, Class<R> clazz, boolean skipError) {
    return maps(maps, clazz, NamecaseKit.namecase(NamecaseType.CASE_NONE), skipError);
  }

  public <R> List<R> maps(List<Map> maps, Class<R> clazz, INameCase namecase, boolean skipError) {
    if (maps == null)
      return Collections.emptyList();
    List<R> rets = new ArrayList<>();
    maps.forEach(map -> rets.add(map(map, clazz, namecase, skipError)));
    return rets;
  }


}
