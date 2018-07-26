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
package io.enoa.repeater.factory.http;

import io.enoa.repeater.http.Request;
import io.enoa.toolkit.collection.CollectionKit;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class EoxAbstractRequest implements Request {

  protected Map<String, String[]> mapListToArray(Map<String, List<String>> map) {
    if (CollectionKit.isEmpty(map))
      return null;
    Map<String, String[]> ret = new HashMap<>();
    map.forEach((k, v) -> ret.put(k, v.toArray(new String[v.size()])));
    return ret;
  }

  protected Map<String, List<String>> mapArrayToList(Map<String, String[]> map) {
    if (CollectionKit.isEmpty(map))
      return null;
    Map<String, List<String>> ret = new HashMap<>();
    map.forEach((k, v) -> ret.put(k, Stream.of(v).collect(Collectors.toList())));
    return ret;
  }

  protected Map<String, List<String>> mergeMap(Map<String, List<String>> map1, Map<String, List<String>> map2) {

    if (CollectionKit.isEmpty(map1))
      return new ConcurrentHashMap<>(map2);
    if (CollectionKit.isEmpty(map2))
      return map1;

    Map<String, List<String>> nm1 = new ConcurrentHashMap<>(map1),
      nm2 = new ConcurrentHashMap<>(map2);

    Set<String> nmk1 = nm1.keySet();
    Set<String> nmk2 = new HashSet<>(nm2.keySet());
    Set<String> nmk = new HashSet<>(nmk1);
    nmk.addAll(nmk2);

    Map<String, List<String>> merge = new ConcurrentHashMap<>();
    nmk.forEach(k -> {
      List<String> nm1v = nm1.get(k);
      List<String> nm2v = nm2.get(k);
      List<String> mtv = merge.get(k);
      if (mtv != null) {
        if (nm1v != null)
          mtv.addAll(nm1v);
        if (nm2v != null)
          mtv.addAll(nm2v);
        return;
      }
      mtv = new ArrayList<>();
      if (nm1v != null)
        mtv.addAll(nm1v);
      if (nm2v != null)
        mtv.addAll(nm2v);
      merge.put(k, mtv);
    });
    CollectionKit.clear(nm1, nm2);
    CollectionKit.clear(nmk1, nmk2, nmk);
    return merge;
  }

  protected Map<String, String[]> paraMap(Map<String, List<String>> map1, Map<String, List<String>> map2) {
    Map<String, List<String>> merge = this.mergeMap(map1, map2);
    Map<String, String[]> ret = this.mapListToArray(merge);
    CollectionKit.clear(merge);
    if (ret == null)
      return null;
    return Collections.unmodifiableMap(ret);
  }


}
