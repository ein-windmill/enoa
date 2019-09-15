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
package io.enoa.docker.parser.docker;

import io.enoa.docker.DockerConfig;
import io.enoa.docker.dket.docker.DResp;
import io.enoa.docker.dket.docker.container.EContainer;
import io.enoa.docker.dket.docker.container.EPort;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.is.Is;
import io.enoa.toolkit.map.Kv;

import java.util.*;

class EContainerListParser extends AbstractParser<List<EContainer>> {

  private static class Holder {
    private static final EContainerListParser INSTANCE = new EContainerListParser();
  }

  static EContainerListParser instance() {
    return Holder.INSTANCE;
  }

  @Override
  public List<EContainer> ok(DockerConfig config, DResp resp) {
    ;
    List<Kv> kvs = config.json().parseArray(resp.string(), Kv.class);
    if (Is.empty(kvs)) {
      return Collections.emptyList();
    }
    List<EContainer> rets = new ArrayList<>(kvs.size());
    kvs.forEach(kv -> rets.add(this.container(kv)));
    CollectionKit.clear(kvs);
    return rets;
  }

  EContainer container(Kv kv) {
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
    return builder.build();
  }

  private List<EPort> ports(Kv kv) {
    Object ports = kv.get("Ports");
    if (ports instanceof Collection) {
      Collection _pots = (Collection) ports;
      List<EPort> pots = new ArrayList<>(_pots.size());
      _pots.forEach(p -> {
        if (!(p instanceof Map))
          return;
        Kv mp = Kv.by((Map) p);
        EPort.Builder epb = new EPort.Builder()
          .ip(mp.string("IP"))
          .privateport(mp.integer("PrivatePort"))
          .publicport(mp.integer("PublicPort"))
          .type(mp.string("Type"));
        pots.add(epb.build());
      });
      return pots;
    }
    return Collections.emptyList();
  }


}
