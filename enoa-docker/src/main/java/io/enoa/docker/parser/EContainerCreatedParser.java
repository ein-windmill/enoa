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

import io.enoa.docker.DockerConfig;
import io.enoa.docker.dret.DRet;
import io.enoa.docker.dret.container.EContainerCreated;
import io.enoa.toolkit.map.Kv;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class EContainerCreatedParser extends AbstractParser<EContainerCreated> {

  private static class Holder {
    private static final EContainerCreatedParser INSTANCE = new EContainerCreatedParser();
  }

  static EContainerCreatedParser instance() {
    return Holder.INSTANCE;
  }

  @Override
  public DRet<EContainerCreated> ok(DockerConfig config, String origin) {
    Kv kv = config.json().parse(origin, Kv.class);
    EContainerCreated.Builder builder = new EContainerCreated.Builder();
    builder.id(kv.string("Id"))
      .warnings(this.warnings(kv));
    return DRet.ok(origin, builder.build());
  }

  private List<String> warnings(Kv kv) {
    Object warnings = kv.get("Warnings");
    if (!(warnings instanceof Collection))
      return Collections.emptyList();
    Collection _wars = (Collection) warnings;
    return (List<String>) _wars.stream().collect(Collectors.toList());
  }
}
