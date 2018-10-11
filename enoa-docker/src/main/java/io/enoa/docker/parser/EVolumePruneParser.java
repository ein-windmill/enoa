package io.enoa.docker.parser;

import io.enoa.docker.DockerConfig;
import io.enoa.docker.dret.DResp;
import io.enoa.docker.dret.volume.EVolumePrune;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.map.Kv;

class EVolumePruneParser extends AbstractParser<EVolumePrune> {

  private static class Holder {
    private static final EVolumePruneParser INSTANCE = new EVolumePruneParser();
  }

  static EVolumePruneParser instance() {
    return Holder.INSTANCE;
  }

  @Override
  public EVolumePrune ok(DockerConfig config, DResp resp) {
    Kv kv = config.json().parse(resp.string(), Kv.class);
    if (CollectionKit.isEmpty(kv))
      return null;
    EVolumePrune.Builder builder = new EVolumePrune.Builder()
      .spacereclaimed(kv.integer("SpaceReclaimed"))
      .volumesdeleted(AEExtra.stringarray(kv, "VolumesDeleted"));
    CollectionKit.clear(kv);
    return builder.build();
  }
}
