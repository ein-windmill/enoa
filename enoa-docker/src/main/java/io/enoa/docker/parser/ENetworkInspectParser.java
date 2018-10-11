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
import io.enoa.docker.dret.network.EIPAM;
import io.enoa.docker.dret.network.EIPAMConfig;
import io.enoa.docker.dret.network.ENetwork;
import io.enoa.docker.dret.network.ENetworkContainer;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.date.DateKit;
import io.enoa.toolkit.map.Kv;
import io.enoa.toolkit.value.EnoaValue;

import java.util.*;
import java.util.stream.Collectors;

class ENetworkInspectParser extends AbstractParser<ENetwork> {

  private static class Holder {
    private static final ENetworkInspectParser INSTANCE = new ENetworkInspectParser();
  }

  static ENetworkInspectParser instance() {
    return Holder.INSTANCE;
  }

  @Override
  public ENetwork ok(DockerConfig config, DResp resp) {
    Kv kv = config.json().parse(resp.string(), Kv.class);
    return network(kv);
  }

  static ENetwork network(Kv kv) {
    ENetwork.Builder builder = new ENetwork.Builder()
      .name(kv.string("Name"))
      .id(kv.string("Id"))
      .created(DateKit.parse(kv.string("Created"), "yyyy-MM-dd'T'HH:mm:ss.SSS"))
      .scope(kv.string("Scope"))
      .driver(kv.string("Driver"))
      .enableipv6(kv.bool("EnableIPv6"))
      .internal(kv.bool("Internal"))
      .attachable(kv.bool("Attachable"))
      .ingress(kv.bool("Ingress"))
      .ipam(ipam(kv))
      .containers(containers(kv))
      .options(AEExtra.kv(kv, "Options"))
      .labels(AEExtra.kv(kv, "Labels"));
    return builder.build();
  }


  private static Map<String, ENetworkContainer> containers(Kv kv) {
    Object cto = kv.get("Containers");
    if (!(cto instanceof Map))
      return null;
    Map conts = (Map) cto;
    Map<String, ENetworkContainer> ret = new HashMap<>(conts.size());
    conts.forEach((key, val) -> {
      if (!(val instanceof Map))
        return;
      Kv enc = Kv.by((Map) val);
      ENetworkContainer.Builder builder = new ENetworkContainer.Builder()
        .name(enc.string("Name"))
        .endpointid(enc.string("EndpointID"))
        .macaddress(enc.string("MacAddress"))
        .ipv4address(enc.string("IPv4Address"))
        .ipv6address(enc.string("IPv6Address"));
      CollectionKit.clear(enc);
      ret.put(EnoaValue.with(key).string(), builder.build());
    });
    return ret;
  }


  private static EIPAM ipam(Kv kv) {
    Object ipmo = kv.get("IPAM");
    if (!(ipmo instanceof Map))
      return null;
    Kv ipam = Kv.by((Map) ipmo);
    EIPAM.Builder builder = new EIPAM.Builder()
      .driver(ipam.string("Driver", "default"));
    Object cfgo = ipam.get("Config");
    if (cfgo instanceof Collection) {
      Collection cgc = (Collection) cfgo;
      List<EIPAMConfig> cfgs = new ArrayList<>(cgc.size());
      cgc.forEach(c -> {
        if (!(c instanceof Map))
          return;
        Kv cgk = Kv.by((Map) c);
        EIPAMConfig.Builder mcb = new EIPAMConfig.Builder()
          .gateway(cgk.string("Gateway"))
          .subnet(cgk.string("Subnet"));
        cfgs.add(mcb.build());
        CollectionKit.clear(cgk);
      });
      builder.config(cfgs);
    }
    Object ipgo = ipam.get("Options");
    if (ipgo instanceof Collection) {
      Collection ipc = (Collection) ipgo;
      List<Kv> cfgs = (List<Kv>) ipc.stream()
        .map(it -> Kv.by((Map) it))
        .collect(Collectors.toList());
      builder.options(cfgs);
    }
    return builder.build();
  }

}
