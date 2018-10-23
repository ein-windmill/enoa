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
package io.enoa.docker.dqp.docker.common;

import io.enoa.docker.dqp.DQP;
import io.enoa.docker.dqp.DQR;
import io.enoa.json.Json;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.convert.ConvertKit;
import io.enoa.toolkit.map.Kv;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DQPFilter<T extends DQPFilter> implements DQP {

  /**
   * string
   * <p>
   * Filters to process on the prune list, encoded as JSON (a map[string][]string).
   * <p>
   * Available filters:
   * <p>
   * until=<timestamp> Prune networks created before this timestamp. The <timestamp> can be Unix timestamps,
   * date formatted timestamps, or Go duration strings (e.g. 10m, 1h30m) computed relative to the daemon machine’s time.
   * label (label=<key>, label=<key>=<value>, label!=<key>, or label!=<key>=<value>) Prune networks with (or without,
   * in case label!=... is used) the specified labels.
   */
  private Map<String, Set<String>> filter;

  public static DQPFilter create() {
    return new DQPFilter();
  }

  public DQPFilter() {
  }

  public T filter(String name, String value) {
    if (this.filter == null)
      this.filter = new HashMap<>();
    Set<String> arr = this.filter.get(name);
    if (CollectionKit.isEmpty(arr))
      arr = new HashSet<>();
    arr.add(value);
    this.filter.put(name, arr);
    return (T) this;
  }

  public T filter(String name, String... vals) {
    return this.filter(name, Stream.of(vals).collect(Collectors.toSet()));
  }

  public T filter(String name, Collection<String> vals) {
    if (this.filter == null)
      this.filter = new HashMap<>();
    Set<String> arr = this.filter.get(name);
    if (CollectionKit.isEmpty(arr))
      arr = new HashSet<>();
    arr.addAll(vals);
    this.filter.put(name, arr);
    return (T) this;
  }

  public T filter(Map<String, Set<String>> filter) {
    this.filter = filter;
    return (T) this;
  }

  public T filter(Kv filter) {
    filter.forEach((key, val) -> this.filter(key, ConvertKit.string(val)));
    return (T) this;
  }

  @Override
  public DQR dqr() {
    DQR dqr = DQR.create();
    if (this.filter != null) {
      dqr.putIf("filters", Json.toJson(this.filter));
    }
    return dqr;
  }
}
