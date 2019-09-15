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
package io.enoa.yosart.kit.yo;

import io.enoa.repeater.http.Method;
import io.enoa.toolkit.is.Is;
import io.enoa.yosart.Oysart;
import io.enoa.yosart.kernel.resources.OyResource;

import java.util.Map;

public class OyResourceKit {


  public static OyResource choose(Method method, String action) {
    Map<String, OyResource[]> resources = Oysart.resources();
    if (resources == null)
      return null;
    OyResource[] res = resources.get(action);
    if (Is.empty(res))
      return null;

    for (OyResource re : res) {
      if (Is.empty(re.methods()))
        return re;
      for (Method rme : re.methods()) {
        if (method == Method.OPTIONS || rme == method)
          return re;
      }
    }
    return null;
  }

}
