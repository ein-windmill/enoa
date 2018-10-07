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
import io.enoa.docker.dret.container.*;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.map.Kv;
import io.enoa.toolkit.value.EnoaValue;

import java.util.*;
import java.util.stream.Collectors;

public class DockerPsParser extends AbstractParser<List<EContainer>> {

  private static class Holder {
    private static final DockerPsParser INSTANCE = new DockerPsParser();
  }

  static DockerPsParser instance() {
    return Holder.INSTANCE;
  }

  @Override
  public DRet<List<EContainer>> ok(DockerConfig config, String origin) {
    List<EContainer> rets;
    List<Kv> kvs = config.json().parseArray(origin, Kv.class);
    if (CollectionKit.isEmpty(kvs)) {
      rets = Collections.emptyList();
      return DRet.ok(origin, rets);
    }
    rets = new ArrayList<>(kvs.size());

    kvs.forEach(kv -> {
      EContainer.Builder builder = new EContainer.Builder();
      builder.id(kv.string("Id"))
        .names(this.names(kv))
        .image(kv.string("Image"))
        .imageid(kv.string("ImageID"))
        .command(kv.string("Command"))
        .created(kv.longer("Created"))
        .ports(this.ports(kv));
      // fixme container ps labels
      builder.labels(kv.get("Labels"));
      builder.state(kv.string("State"))
        .status(kv.string("Status"))
        .hostconfig(this.hostconfig(kv))
        .networksetting(this.networksetting(kv))
        .mounts(this.mounts(kv));

      rets.add(builder.build());
    });
    return DRet.ok(origin, rets);
  }

  private List<String> names(Kv kv) {
    Object names = kv.get("Names");
    if (names instanceof Collection) {
      List<String> _ns = (List<String>) ((Collection) names).stream()
        .collect(Collectors.toList());
      return _ns;
    }
    return Collections.emptyList();
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

  private EHostConfig hostconfig(Kv kv) {
    Object hostconfig = kv.get("HostConfig");
    if (hostconfig instanceof Map) {
      Map hgm = (Map) hostconfig;
      if (CollectionKit.notEmpty(hgm)) {
        EHostConfig.Builder hcgb = new EHostConfig.Builder()
          .networkmode(EnoaValue.with(hgm.get("NetworkMode")).string());
        return hcgb.build();
      }
    }
    return null;
  }

  private ENetworkSetting networksetting(Kv kv) {
    Object networksettings = kv.get("NetworkSettings");
    if (!(networksettings instanceof Map))
      return null;
    ENetworkSetting.Builder nsb = new ENetworkSetting.Builder();
    Map mssm = (Map) networksettings;
    Object networks = mssm.get("Networks");
    if (networks instanceof Map) {
      ENetwork.Builder nkb = new ENetwork.Builder();
      Map nksm = (Map) networks;
      Object bridge = nksm.get("bridge");
      if (bridge instanceof Map) {
        Map bgm = (Map) bridge;
        EBridge.Builder ebb = new EBridge.Builder();
        // fixme IPAMConfig Links Aliases DriverOpts
        ebb.ipamconfig(bgm.get("IPAMConfig"))
          .links(bgm.get("Links"))
          .aliases(bgm.get("Aliases"))
          .networkid(EnoaValue.with(bgm.get("NetworkID")).string())
          .endpointid(EnoaValue.with(bgm.get("EndpointID")).string())
          .gateway(EnoaValue.with(bgm.get("Gateway")).string())
          .ipaddress(EnoaValue.with(bgm.get("IPAddress")).string())
          .ipprefixlen(EnoaValue.with(bgm.get("IPPrefixLen")).integer())
          .ipv6gateway(EnoaValue.with(bgm.get("IPv6Gateway")).string())
          .globalipv6address(EnoaValue.with(bgm.get("GlobalIPv6Address")).string())
          .globalipv6prefixlen(EnoaValue.with(bgm.get("GlobalIPv6PrefixLen")).integer())
          .macaddress(EnoaValue.with(bgm.get("MacAddress")).string())
          .driveropts(bgm.get("DriverOpts"));
        nkb.bridge(ebb.build());
      }
      nsb.network(nkb.build());
    }
    return nsb.build();
  }

  private List<EMount> mounts(Kv kv) {
    Object mounts = kv.get("Mounts");
    if (!(mounts instanceof Collection))
      return Collections.emptyList();
    Collection mts = (Collection) mounts;
    List<EMount> rets = new ArrayList<>(mts.size());
    mts.forEach(mt -> {
      if (!(mt instanceof Map))
        return;
      Map mtm = (Map) mt;
      EMount.Builder builder = new EMount.Builder()
        .type(EnoaValue.with(mtm.get("Type")).string())
        .name(EnoaValue.with(mtm.get("Name")).string())
        .source(EnoaValue.with(mtm.get("Source")).string())
        .destination(EnoaValue.with(mtm.get("Destination")).string())
        .driver(EnoaValue.with(mtm.get("Driver")).string())
        .mode(EnoaValue.with(mtm.get("Mode")).string())
        .rw(EnoaValue.with(mtm.get("RW")).bool())
        .propagation(EnoaValue.with(mtm.get("Propagation")).string());
      rets.add(builder.build());
    });
    return rets;
  }

}
