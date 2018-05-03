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
package io.enoa.conf.impl;

import io.enoa.conf.EnoaConfDomain;
import io.enoa.log.kit.LogKit;
import io.enoa.toolkit.map.Kv;
import io.enoa.toolkit.text.TextKit;
import io.enoa.toolkit.thr.EoException;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class EoYamlReader extends _AbstractFileReader {

  private static final Yaml yaml = new Yaml();
  static final EoYamlReader me = new EoYamlReader();

  @Override
  public List<EnoaConfDomain> read(EnoaConfDomain.Origin origin, String path) {
    Map map = this.parseYaml(path);
    Kv conf = this.convertMap(map);
    map.clear();

    return Stream.of(
      EnoaConfDomain.create(origin, path, super.extraName(path), super.extraEnv(path), conf)
    ).collect(Collectors.toList());
  }

  private Map parseYaml(String path) {
    try {
      return yaml.loadAs(new FileInputStream(new File(path)), Map.class);
    } catch (FileNotFoundException e) {
      LogKit.error("Load yml file {} fail: {}", path, e.getMessage());
      throw new EoException(e.getMessage(), e);
    }
  }

  private Kv convertMap(Map map) {
    Kv conf = Kv.create();
    for (Object key : map.keySet()) {
      StringBuilder keyBuilder = new StringBuilder();
      keyBuilder.append(key);
      Object val = map.get(key);
      if (val instanceof Map) {
        Kv sub = this.convertMap((Map) val);
        this.mergeKv(conf, keyBuilder.toString(), sub);
        continue;
      }
//      if (val instanceof List) {
//
//      }
      conf.set(keyBuilder.toString(), val);
    }
    return conf;
  }

  private void mergeKv(Kv root, String key, Kv sub) {
//    sub.forEach((k, v) -> root.set(String.format("%s.%s", key, k), v));
    sub.forEach((k, v) -> root.set(TextKit.union(key, ".", k), v));
    sub.clear();
  }

}
