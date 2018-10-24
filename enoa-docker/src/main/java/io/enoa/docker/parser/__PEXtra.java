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

import io.enoa.toolkit.date.DateKit;
import io.enoa.toolkit.map.Kv;
import io.enoa.toolkit.value.EnoaValue;

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

  public static Kv kv(Map map, String key) {
    Object o = map.get(key);
    if (!(o instanceof Map))
      return null;
    return Kv.by((Map) o);
  }


  public static Collection<Kv> kvs(Map map, String key) {
    Object o = map.get(key);
    if (!(o instanceof Collection))
      return Collections.emptyList();
    Collection oc = (Collection) o;
    List<Kv> kvs = new ArrayList<>(oc.size());
    oc.forEach(c -> {
      if (!(c instanceof Map)) {
        return;
      }
      kvs.add(Kv.by((Map) c));
    });
//    CollectionKit.clear(oc);
    return kvs;
  }

  public static Date date(Map map, String key) {
    return date(map, key, "yyyy-MM-dd'T'HH:mm:ss");
  }

  public static Date date(Map map, String key, String format) {
    Object o = map.get(key);
    if (o == null)
      return null;
    return DateKit.parse(EnoaValue.with(o).string(), format);
  }
}
