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
package io.enoa.docker.parser;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class __PEXtra {

  public static List<String> stringarray(Map map, String key) {
    Object sarr = map.get(key);
    if (!(sarr instanceof Collection)) {
      return Collections.emptyList();
    }
    return (List<String>) ((Collection) sarr).stream()
      .collect(Collectors.toCollection((Supplier<Collection<Object>>) () -> new LinkedList<>()));
  }

  public static List<String[]> listarray(Map map, String key) {
    Object pa = map.get(key);
    if (!(pa instanceof Collection))
      return Collections.emptyList();
    Collection cas = (Collection) pa;
    List<String[]> rets = new ArrayList<>(cas.size());
    cas.forEach(ca -> {
      if (!(ca instanceof Collection))
        return;
      Collection cs = (Collection) ca;
      rets.add((String[]) cs.toArray(new String[cs.size()]));
    });
    return rets;
  }

  public static String[] array(Map map, String key) {
    List<String> stringarray = stringarray(map, key);
    return stringarray.toArray(new String[stringarray.size()]);
  }

}
