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
import io.enoa.docker.dket.docker.volume.EVolume;
import io.enoa.docker.dket.docker.volume.EVolumeLs;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.is.Is;
import io.enoa.toolkit.map.Kv;

import java.util.*;

class EVolumeListParser extends AbstractParser<EVolumeLs> {

  private static class Holder {
    private static final EVolumeListParser INSTANCE = new EVolumeListParser();
  }

  static EVolumeListParser instance() {
    return Holder.INSTANCE;
  }

  @Override
  public EVolumeLs ok(DockerConfig config, DResp resp) {
    Kv kv = config.json().parse(resp.string(), Kv.class);
    if (Is.empty(kv))
      return null;
    EVolumeLs.Builder builder = new EVolumeLs.Builder()
      .warnings(AEExtra.stringarray(kv, "Warnings"))
      .volumes(this.volumes(kv));
    return builder.build();
  }


  private List<EVolume> volumes(Kv kv) {
    Object vos = kv.get("Volumes");
    if (!(vos instanceof Collection))
      return Collections.emptyList();
    Collection vums = (Collection) vos;
    List<EVolume> rets = new ArrayList<>(vums.size());
    vums.forEach(vum -> {
      if (!(vum instanceof Map))
        return;
      Kv mk = Kv.by((Map) vum);
      rets.add(EVolumeParser.instance().volume(mk));
      CollectionKit.clear(mk);
    });
    return rets;
  }

}
