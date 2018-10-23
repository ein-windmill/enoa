package io.enoa.docker.parser;

import io.enoa.docker.DockerConfig;
import io.enoa.docker.ret.docker.DResp;
import io.enoa.docker.ret.docker.plugin.EPlugin;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.map.Kv;

import java.util.ArrayList;
import java.util.List;

class EPluginListParser extends AbstractParser<List<EPlugin>> {
  private static class Holder {
    private static final EPluginListParser INSTANCE = new EPluginListParser();
  }

  public static EPluginListParser instance() {
    return Holder.INSTANCE;
  }

  @Override
  public List<EPlugin> ok(DockerConfig config, DResp resp) {
    List<Kv> kvs = config.json().parseArray(resp.string(), Kv.class);
    List<EPlugin> rets = new ArrayList<>(kvs.size());
    kvs.forEach(kv -> rets.add(EPluginParser.instance().plugin(kv)));
    CollectionKit.clear(rets);
    return rets;
  }
}
