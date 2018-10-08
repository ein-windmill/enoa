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
import io.enoa.docker.dret.container.EContainer;
import io.enoa.docker.dret.container.EPort;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.map.Kv;
import io.enoa.toolkit.value.EnoaValue;

import java.util.*;

class EListContainerParser extends AbstractParser<List<EContainer>> {

  private static class Holder {
    private static final EListContainerParser INSTANCE = new EListContainerParser();
  }

  static EListContainerParser instance() {
    return Holder.INSTANCE;
  }

  @Override
  public List<EContainer> ok(DockerConfig config, String origin) {
    List<EContainer> rets;
    List<Kv> kvs = config.json().parseArray(origin, Kv.class);
    if (CollectionKit.isEmpty(kvs)) {
      rets = Collections.emptyList();
      return rets;
    }
    rets = new ArrayList<>(kvs.size());

    kvs.forEach(kv -> {
      EContainer.Builder builder = new EContainer.Builder();
      builder.id(kv.string("Id"))
        .names(AEExtra.stringarray(kv, "Names"))
        .image(kv.string("Image"))
        .imageid(kv.string("ImageID"))
        .command(kv.string("Command"))
        .created(kv.longer("Created"))
        .ports(this.ports(kv));
      // fixme container list labels
      builder.labels(kv.get("Labels"));
      builder.state(kv.string("State"))
        .status(kv.string("Status"))
        .hostconfig(AEExtra.hostconfig(kv))
        .networksetting(AEExtra.networksetting(kv))
        .mounts(AEExtra.mounts(kv));

      rets.add(builder.build());
    });
    CollectionKit.clear(kvs);
    return rets;
  }

  private List<EPort> ports(Kv kv) {
    Object ports = kv.get("Ports");
    if (ports instanceof Collection) {
      Collection _pots = (Collection) ports;
      List<EPort> pots = new ArrayList<>(_pots.size());
      _pots.forEach(p -> {
        if (!(p instanceof Map))
          return;
        Map mp = (Map) p;
        EPort.Builder epb = new EPort.Builder()
          .ip(EnoaValue.with(mp.get("IP")).string())
          .privateport(EnoaValue.with(mp.get("PrivatePort")).integer())
          .publicport(EnoaValue.with(mp.get("PublicPort")).integer())
          .type(EnoaValue.with(mp.get("Type")).string());
        pots.add(epb.build());
      });
      return pots;
    }
    return Collections.emptyList();
  }


}
