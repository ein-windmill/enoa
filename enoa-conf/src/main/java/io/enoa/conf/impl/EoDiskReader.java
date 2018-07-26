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
package io.enoa.conf.impl;

import io.enoa.conf.EnoaConfDomain;
import io.enoa.conf._EnoaConfReader;

import java.util.Collections;
import java.util.List;

public class EoDiskReader extends _EnoaConfReader {
  @Override
  public List<EnoaConfDomain> read(String path) {
    if (path.endsWith(".json"))
      return EoJsonReader.me.read(EnoaConfDomain.Origin.DISK, path);
    if (path.endsWith(".properties"))
      return EoPropertiesReader.me.read(EnoaConfDomain.Origin.DISK, path);
    if (path.endsWith(".yml") || path.endsWith(".yaml"))
      return EoYamlReader.me.read(EnoaConfDomain.Origin.DISK, path);
    return Collections.emptyList();
  }
}
