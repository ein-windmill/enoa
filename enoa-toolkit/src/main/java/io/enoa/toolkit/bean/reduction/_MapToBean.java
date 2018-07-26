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
package io.enoa.toolkit.bean.reduction;

import io.enoa.toolkit.bean.Bonfig;
import io.enoa.toolkit.reflect.RefType;
import io.enoa.toolkit.reflect.ReflectKit;
import io.enoa.toolkit.reflect.TypeKit;

import java.lang.reflect.Type;
import java.util.Map;

public class _MapToBean {

  private static class Holder {
    private static final _MapToBean INSTANCE = new _MapToBean();
  }

  public static _MapToBean instance() {
    return Holder.INSTANCE;
  }

  public <T> T object(Map<String, Object> map, Type type, int depth, Bonfig config) {
    if (map == null)
      return null;

    if (depth-- < 0)
      return null;

    RefType reftype = TypeKit.reftype(type);
    Class<?> clazz = ReflectKit.newInstance(reftype.type()).clazz();

    if (Map.class.isAssignableFrom(clazz)) {
      return MapReduction.instance().object(map, type, depth, config);
    }

//    Map<String, GenericMapping> generic = ReflectKit.mapFieldGeneric(clazz, type);
    return BeanReduction.instance().object(map, type, depth, config, clazz, null);
  }

}
