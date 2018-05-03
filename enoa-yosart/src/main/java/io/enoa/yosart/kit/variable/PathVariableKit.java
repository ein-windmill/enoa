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
package io.enoa.yosart.kit.variable;

import io.enoa.toolkit.EoConst;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.map.Kv;
import io.enoa.yosart.kernel.http.PathVariable;

import java.util.Set;

public class PathVariableKit {

  public static PathVariable parse(String action, Set<String> uris) {
    for (String uri : uris) {
      if (!uri.contains(EoConst.RESTFUL_RECOGNIZE))
        continue;
      String[] actionArr = action.split("/");
      String[] uriArr = uri.split("/");
      // 判斷長度是否相同
      if (actionArr.length != uriArr.length)
        continue;
      // 判斷非變量區域是否一致, 若一致則命中 uri
      /*
      /party/again/152435/where/Jin/2  <==>  /party/again/:id/where/:where/:times
       */
      boolean hint = true;
      Kv pa = Kv.create();
      for (int i = uriArr.length; i-- > 0; ) {
        String ua = uriArr[i], aa = actionArr[i];
        if (ua.contains(EoConst.RESTFUL_RECOGNIZE)) {
          // 变量区域  写入 path variable
          pa.set(ua.replace(EoConst.RESTFUL_RECOGNIZE, ""), aa.trim());
          continue;
        }
        // 非變量區域相同, 通過
        if (ua.equals(aa)) {
          continue;
        }
        // 不同直接放棄
        hint = false;
        break;
      }
      if (!hint) {
        CollectionKit.clear(pa);
        continue;
      }
      return new PathVariable.Builder().uri(uri).set(pa).build();
    }
    return null;
  }

}
