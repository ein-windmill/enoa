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
import io.enoa.toolkit.map.Kv;
import io.enoa.toolkit.prop.Prop;

import java.io.File;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class EoPropertiesReader extends _AbstractFileReader {

  static final EoPropertiesReader me = new EoPropertiesReader();

  @Override
  public List<EnoaConfDomain> read(EnoaConfDomain.Origin origin, String path) {
    Prop prop = new Prop(new File(path));
    Kv conf = Kv.create();
    Enumeration<?> enumeration = prop.properties().propertyNames();
    while (enumeration.hasMoreElements()) {
      String key = enumeration.nextElement().toString();
      conf.set(key, prop.string(key));
    }
    return Stream.of(
      EnoaConfDomain.create(origin, path,
        super.extraName(path), super.extraEnv(path), conf)
    ).collect(Collectors.toList());
  }
}
