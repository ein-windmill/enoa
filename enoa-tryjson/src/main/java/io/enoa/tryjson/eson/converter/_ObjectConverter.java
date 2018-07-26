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
package io.enoa.tryjson.eson.converter;

import io.enoa.toolkit.bean.BeanKit;
import io.enoa.toolkit.bean.Bonfig;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.tryjson.Tsonfig;
import io.enoa.tryjson.eson.Eson;

import java.lang.reflect.Array;
import java.util.*;

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
    Map<String, Object> _map = BeanKit.convert().map(object, depth, new Bonfig.Builder()
      .namecase(conf.namecase())
      .build()
    );
    String json = Eson.json(_map, depth, conf);
    CollectionKit.clear(_map);
    return json;
  }

}
