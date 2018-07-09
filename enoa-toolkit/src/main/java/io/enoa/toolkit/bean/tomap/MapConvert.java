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
import io.enoa.toolkit.convert.ConvertKit;

import java.util.Map;

class MapConvert {

  private static class Holder {
    private static final MapConvert INSTANCE = new MapConvert();
  }

  static MapConvert instance() {
    return Holder.INSTANCE;
  }

  Map<String, Object> convert(Map source, int depth, Bonfig config) {
    Map<String, Object> ret = config.bmap().create();
    source.forEach((key, val) -> {
      String _key = key == null ? config.nullKey() : config.namecase().convert(ConvertKit.string(key));
      if (val == null) {
        ret.put(_key, null);
        return;
      }
      Class<?> vclazz = val.getClass();
      boolean support = ConvertKit.supportConvert(vclazz);
      if (support) {
        Object _val = ConvertKit.to(ConvertKit.string(val), vclazz);
        ret.put(_key, _val);
        return;
      }
      ret.put(_key, _BeanToMap.map(val, depth, config));
    });
    return ret;
  }

}
