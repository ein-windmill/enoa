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
import io.enoa.docker.dret.common.EGenericResource;
import io.enoa.docker.dret.common.EResources;
import io.enoa.docker.dret.node.*;
import io.enoa.docker.dret.swarm.EVersion;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.date.DateKit;
import io.enoa.toolkit.map.Kv;

import java.util.*;

class ENodeParser extends AbstractParser<ENode> {

  private static class Holder {
    private static final ENodeParser INSTANCE = new ENodeParser();
  }

  static ENodeParser instance() {
    return Holder.INSTANCE;
  }

  @Override
  public ENode ok(DockerConfig config, DResp resp) {
    Kv kv = config.json().parse(resp.string(), Kv.class);
    ENode node = this.node(kv);
    CollectionKit.clear(kv);
    return node;
  }

  ENode node(Kv kv) {
    ENode.Builder builder = new ENode.Builder()
      .id(kv.string("ID"))
      .createdat(DateKit.parse(kv.string("CreatedAt"), "yyyy-MM-dd'T'HH:mm:ss.SSS"))
      .updatedat(DateKit.parse(kv.string("UpdatedAt"), "yyyy-MM-dd'T'HH:mm:ss.SSS"))
      .version(this.version(kv))
      .spec(this.spec(kv))
      .description(this.description(kv))
      .status(this.status(kv, "Status"))
      .managerstatus(this.status(kv, "ManagerStatus"));
    return builder.build();
  }


  private ENodeStatus status(Kv kv, String key) {
    Object ot = kv.get(key);
    if (!(ot instanceof Map))
      return null;
    Kv stk = Kv.by((Map) ot);
    ENodeStatus.Builder builder = new ENodeStatus.Builder()
      .state(stk.string("State"))
      .message(stk.string("Message"))
      .addr(stk.string("Addr"));
    CollectionKit.clear(stk);
    return builder.build();
  }

  private EVersion version(Kv kv) {
    Object vto = kv.get("Version");
    if (!(vto instanceof Map))
      return null;
    Kv ver = Kv.by((Map) vto);
    EVersion.Builder builder = new EVersion.Builder()
      .index(ver.integer("Index"));
    return builder.build();
  }

  private ENodeSpec spec(Kv kv) {
    Object spt = kv.get("Spec");
    if (!(spt instanceof Map))
      return null;
    Kv spec = Kv.by((Map) spt);
    ENodeSpec.Builder builder = new ENodeSpec.Builder()
      .availability(spec.string("Availability"))
      .name(spec.string("Name"))
      .role(spec.string("Role"))
      .labels(AEExtra.kv(spec, "Labels"));
    CollectionKit.clear(spec);
    return builder.build();
  }

  EPlatform platform(Kv kv) {
    EPlatform.Builder pb = new EPlatform.Builder()
      .architecture(kv.string("Architecture", kv.string("architecture")))
      .os(kv.string("OS", kv.string("os")))
      .osversion(kv.string("OSVersion", kv.string("osVersion")))
      .osfeatures(AEExtra.array(kv, "OSFeatures"))
      .variant(kv.string("Variant", kv.string("variant")))
      .features(AEExtra.array(kv, "Features"));
    return pb.build();
  }

  private ENodeDescription description(Kv kv) {
    Object dot = kv.get("Description");
    if (!(dot instanceof Map))
      return null;
    Kv desn = Kv.by((Map) dot);
    ENodeDescription.Builder builder = new ENodeDescription.Builder()
      .hostname(desn.string("Hostname"));

    Object plo = desn.get("Platform");
    if (plo instanceof Map) {
      Kv pak = Kv.by((Map) plo);
      builder.platform(this.platform(pak));
      CollectionKit.clear(pak);
    }

    Object rot = desn.get("Resources");
    if (rot instanceof Map) {
      Kv rok = Kv.by((Map) rot);
      EResources.Builder erb = new EResources.Builder()
        .nanocpus(rok.longer("NanoCPUs"))
        .memorybytes(rok.longer("MemoryBytes"));
      erb.genericresources(this.genericresources(rok, "GenericResources"));
      CollectionKit.clear(rok);
      builder.resources(erb.build());
    }


    Object ent = desn.get("Engine");
    if (ent instanceof Map) {
      Kv enk = Kv.by((Map) ent);
      ENodeEngine.Builder neb = new ENodeEngine.Builder()
        .engineversion(enk.string("EngineVersion"))
        .labels(AEExtra.kv(enk, "Labels"));
      Object pto = enk.get("Plugins");
      if (pto instanceof Collection) {
        Collection pcs = (Collection) pto;
        List<ENodePlugin> plugins = new ArrayList<>(pcs.size());
        pcs.forEach(p -> {
          if (!(p instanceof Map))
            return;
          Kv pk = Kv.by((Map) p);
          ENodePlugin.Builder npb = new ENodePlugin.Builder()
            .type(pk.string("Type"))
            .name(pk.string("Name"));
          plugins.add(npb.build());
        });
      }
      builder.engine(neb.build());
    }

    builder.tlsinfo(AEExtra.tls(desn, "TLSInfo"));
    return builder.build();
  }

  List<EGenericResource> genericresources(Kv kv, String key) {
    Object gr = kv.get(key);
    if (!(gr instanceof Collection))
      return Collections.emptyList();
    Collection ees = (Collection) gr;
    List<EGenericResource> rets = new ArrayList<>(ees.size());
    ees.forEach(e -> {
      if (!(e instanceof Map))
        return;
      Kv ek = Kv.by((Map) e);
      EGenericResource.Builder egrb = new EGenericResource.Builder();
      Object nrs = ek.get("NamedResourceSpec");
      Object drs = ek.get("DiscreteResourceSpec");

      if (nrs instanceof Map) {
        Kv nrk = Kv.by((Map) nrs);
        ENodeResourceSpec.Builder nrsb = new ENodeResourceSpec.Builder()
          .kind(nrk.string("Kind"))
          .value(nrk.integer("Value"));
        egrb.namedresourcespec(nrsb.build());
        CollectionKit.clear(nrk);
      }
      if (drs instanceof Map) {
        Kv drk = Kv.by((Map) drs);
        ENodeResourceSpec.Builder nrsb = new ENodeResourceSpec.Builder()
          .kind(drk.string("Kind"))
          .value(drk.integer("Value"));
        egrb.discreteresourcespec(nrsb.build());
        CollectionKit.clear(drk);
      }
      rets.add(egrb.build());
      CollectionKit.clear(ek);
    });
    return rets;
  }


}
