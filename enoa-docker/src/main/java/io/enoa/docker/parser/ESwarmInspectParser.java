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
import io.enoa.docker.dret.common.EDriver;
import io.enoa.docker.dret.swarm.*;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.date.DateKit;
import io.enoa.toolkit.map.Kv;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

class ESwarmInspectParser extends AbstractParser<ESwarmInspect> {

  private static class Holder {
    private static final ESwarmInspectParser INSTANCE = new ESwarmInspectParser();
  }

  static ESwarmInspectParser instance() {
    return Holder.INSTANCE;
  }

  @Override
  public ESwarmInspect ok(DockerConfig config, DResp resp) {
    Kv kv = config.json().parse(resp.string(), Kv.class);
    if (CollectionKit.isEmpty(kv))
      return null;
    ESwarmInspect.Builder builder = new ESwarmInspect.Builder()
      .id(kv.string("ID"))
      .createdat(DateKit.parse(kv.string("CreatedAt"), "yyyy-MM-dd'T'HH:mm:ss.SSS"))
      .updatedat(DateKit.parse(kv.string("UpdatedAt"), "yyyy-MM-dd'T'HH:mm:ss.SSS"))
      .rootrotationinprogress(kv.bool("RootRotationInProgress"))
      .version(AEExtra.version(kv))
      .spec(this.spec(kv))
      .tlsinfo(AEExtra.tls(kv, "TLSInfo"))
      .jointokens(this.jointokens(kv));
    CollectionKit.clear(kv);
    return builder.build();
  }

  private EJoinTokens jointokens(Kv kv) {
    Object jt = kv.get("JoinTokens");
    if (!(jt instanceof Map))
      return null;
    Kv tm = Kv.by((Map) jt);
    EJoinTokens.Builder builder = new EJoinTokens.Builder()
      .worker(tm.string("Worker"))
      .manager(tm.string("Manager"));
    CollectionKit.clear(tm);
    return builder.build();
  }

//  private ETLSInfo tls(Kv kv) {
//    Object tsc = kv.get("TLSInfo");
//    if (!(tsc instanceof Map)) {
//      return null;
//    }
//    Kv tls = Kv.by((Map) tsc);
//    ETLSInfo.Builder tlsb = new ETLSInfo.Builder()
//      .trustroot(tls.string("TrustRoot"))
//      .certissuersubject(tls.string("CertIssuerSubject"))
//      .certissuerpublickey(tls.string("CertIssuerPublicKey"));
//    CollectionKit.clear(tls);
//    return tlsb.build();
//  }

  private ESwarmSpec spec(Kv kv) {
    Object spo = kv.get("Spec");
    if (!(spo instanceof Map))
      return null;
    Kv spec = Kv.by((Map) spo);
    ESwarmSpec.Builder builder = new ESwarmSpec.Builder()
      .name(spec.string("Name"))
      .labels(AEExtra.kv(spec, "Labels"));

    Object oton = spec.get("Orchestration");
    if (oton instanceof Map) {
      Kv orche = Kv.by((Map) oton);
      EOrchestration.Builder eob = new EOrchestration.Builder()
        .taskhistoryretentionlimit(orche.integer("TaskHistoryRetentionLimit"));
      CollectionKit.clear(orche);
      builder.orchestration(eob.build());
    }
    Object rfo = spec.get("Raft");
    if (rfo instanceof Map) {
      Kv raft = Kv.by((Map) rfo);
      ERaft.Builder rfb = new ERaft.Builder()
        .snapshotinterval(raft.longer("SnapshotInterval"))
        .keepoldsnapshots(raft.longer("KeepOldSnapshots"))
        .logentriesforslowfollowers(raft.longer("LogEntriesForSlowFollowers"))
        .electiontick(raft.integer("ElectionTick"))
        .heartbeattick(raft.integer("HeartbeatTick"));
      CollectionKit.clear(raft);
      builder.raft(rfb.build());
    }
    Object dist = spec.get("Dispatcher");
    if (dist instanceof Map) {
      Kv disp = Kv.by((Map) dist);
      EDispatcher.Builder drb = new EDispatcher.Builder()
        .heartbeatperiod(disp.longer("HeartbeatPeriod"));
      builder.dispatcher(drb.build());
    }
    Object cact = spec.get("CAConfig");
    if (cact instanceof Map) {
      Kv cacfg = Kv.by((Map) cact);
      ECAConfig.Builder cab = new ECAConfig.Builder()
        .nodecertexpiry(cacfg.longer("NodeCertExpiry"))
        .signingcacert(cacfg.string("SigningCACert"))
        .signingcakey(cacfg.string("SigningCAKey"))
        .forcerotate(cacfg.longer("ForceRotate"));
      Object extc = cacfg.get("ExternalCAs");
      if (extc instanceof Collection) {
        Collection exts = (Collection) extc;
        List<EExternalCA> cas = new ArrayList<>(exts.size());
        exts.forEach(ex -> {
          if (!(ex instanceof Map))
            return;
          Kv etern = Kv.by((Map) ex);
          EExternalCA.Builder lcab = new EExternalCA.Builder()
            .protocol(etern.string("Protocol"))
            .url(etern.string("URL"))
            .cacert(etern.string("CACert"))
            .options(AEExtra.kv(etern, "Options"));
          cas.add(lcab.build());
          CollectionKit.clear(etern);
        });
        CollectionKit.clear(exts);
        cab.externalcas(cas);
      }
      builder.caconfig(cab.build());
    }
    Object ete = spec.get("EncryptionConfig");
    if (ete instanceof Map) {
      Kv enc = Kv.by((Map) ete);
      EEncryptionConfig.Builder cgb = new EEncryptionConfig.Builder()
        .autolockmanagers(enc.bool("AutoLockManagers"));
      CollectionKit.clear(enc);
      builder.encryptionconfig(cgb.build());
    }
    Object tdo = spec.get("TaskDefaults");
    if (tdo instanceof Map) {
      Kv tdm = Kv.by((Map) tdo);
      ETaskDefaults.Builder tdb = new ETaskDefaults.Builder();
      Object ldr = tdm.get("LogDriver");
      if (ldr instanceof Map) {
        Kv ldm = Kv.by((Map) ldr);
        EDriver.Builder edb = new EDriver.Builder()
          .name(ldm.string("Name"))
          .options(AEExtra.kv(ldm, "Options"));
        CollectionKit.clear(ldm);
        tdb.logdriver(edb.build());
      }
      CollectionKit.clear(tdm);
      builder.taskdefaults(tdb.build());
    }
    return builder.build();
  }

}

