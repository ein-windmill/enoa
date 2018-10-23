package io.enoa.docker.parser.docker;

import io.enoa.docker.DockerConfig;
import io.enoa.docker.ret.docker.DResp;
import io.enoa.docker.ret.docker.container.EUpdate;
import io.enoa.toolkit.map.Kv;

class EUpdateParser extends AbstractParser<EUpdate> {

  private static class Holder {
    private static final EUpdateParser INSTANCE = new EUpdateParser();
  }

  static EUpdateParser instance() {
    return Holder.INSTANCE;
  }


  @Override
  public EUpdate ok(DockerConfig config, DResp resp) {
    Kv kv = config.json().parse(resp.string(), Kv.class);
    EUpdate.Builder builder = new EUpdate.Builder();
    builder.warnings(AEExtra.stringarray(kv, "Warnings"));
    return builder.build();
  }
}
