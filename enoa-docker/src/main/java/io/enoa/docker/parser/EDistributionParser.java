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
import io.enoa.docker.dret.DResp;
import io.enoa.docker.dret.distribution.EDescriptor;
import io.enoa.docker.dret.distribution.EDistribution;
import io.enoa.docker.dret.node.EPlatform;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.map.Kv;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

class EDistributionParser extends AbstractParser<EDistribution> {

  private static class Holder {
    private static final EDistributionParser INSTANCE = new EDistributionParser();
  }

  static EDistributionParser instance() {
    return Holder.INSTANCE;
  }

  @Override
  public EDistribution ok(DockerConfig config, DResp resp) {
    Kv kv = config.json().parse(resp.string(), Kv.class);
    if (CollectionKit.isEmpty(kv))
      return null;
    EDistribution.Builder builder = new EDistribution.Builder()
      .descriptor(this.descriptor(kv));
    Object pms = kv.get("Platforms");
    if (pms instanceof Collection) {
      Collection ms = (Collection) pms;
      List<EPlatform> platforms = new ArrayList<>(ms.size());
      ms.forEach(m -> {
        if (!(m instanceof Map))
          return;
        Kv mk = Kv.by((Map) m);
        platforms.add(ENodeParser.instance().platform(mk));
        CollectionKit.clear(mk);
      });
      builder.platforms(platforms);
    }
    CollectionKit.clear(kv);
    return builder.build();
  }

  private EDescriptor descriptor(Kv kv) {
    Object dct = kv.get("Descriptor");
    if (!(dct instanceof Map))
      return null;
    Kv dk = Kv.by((Map) dct);
    EDescriptor.Builder builder = new EDescriptor.Builder()
      .mediatype(dk.string("mediaType"))
      .digest(dk.string("digest"))
      .size(dk.longer("size"))
      .urls(AEExtra.array(dk, "URLs"));
    CollectionKit.clear(dk);
    return builder.build();
  }
}
