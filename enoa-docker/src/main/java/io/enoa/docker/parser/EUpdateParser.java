package io.enoa.docker.parser;

import io.enoa.docker.DockerConfig;
import io.enoa.docker.dret.container.EUpdate;
import io.enoa.toolkit.map.Kv;

class EUpdateParser extends AbstractParser<EUpdate> {

  private static class Holder {
    private static final EUpdateParser INSTANCE = new EUpdateParser();
  }

  static EUpdateParser instance() {
    return Holder.INSTANCE;
  }


  @Override
  public EUpdate ok(DockerConfig config, String origin) {
    Kv kv = config.json().parse(origin, Kv.class);
    EUpdate.Builder builder = new EUpdate.Builder();
    builder.warnings(AEExtra.stringarray(kv, "Warnings"));
    return builder.build();
  }
}
