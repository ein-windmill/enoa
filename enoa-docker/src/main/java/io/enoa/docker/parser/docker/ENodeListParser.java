package io.enoa.docker.parser.docker;

import io.enoa.docker.DockerConfig;
import io.enoa.docker.dket.docker.DResp;
import io.enoa.docker.dket.docker.node.ENode;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.map.Kv;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class ENodeListParser extends AbstractParser<List<ENode>> {

  private static class Holder {
    private static final ENodeListParser INSTANCE = new ENodeListParser();
  }

  static ENodeListParser instance() {
    return Holder.INSTANCE;
  }

  @Override
  public List<ENode> ok(DockerConfig config, DResp resp) {
    List<Kv> kvs = config.json().parseArray(resp.string(), Kv.class);
    if (CollectionKit.isEmpty(kvs))
      return Collections.emptyList();
    List<ENode> rets = new ArrayList<>(kvs.size());
    kvs.forEach(kv -> rets.add(ENodeParser.instance().node(kv)));
    CollectionKit.clear(kvs);
    return rets;
  }
}
