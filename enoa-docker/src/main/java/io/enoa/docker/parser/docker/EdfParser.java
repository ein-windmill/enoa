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
import io.enoa.docker.dket.docker.system.Edf;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.map.Kv;

import java.util.Map;

class EdfParser extends AbstractParser<Edf> {

  private static class Holder {
    private static final EdfParser INSTANCE = new EdfParser();
  }

  static EdfParser instance() {
    return Holder.INSTANCE;
  }

  @Override
  public Edf ok(DockerConfig config, DResp resp) {
    Kv kv = config.json().parse(resp.string(), Kv.class);
    if (CollectionKit.isEmpty(kv))
      return null;
    Edf.Builder builder = new Edf.Builder()
      .layerssize(kv.longer("LayersSize"));
    Object cct = kv.get("Containers");
    if (cct instanceof Map) {
      Kv _k = Kv.by((Map) cct);
      builder.container(EContainerListParser.instance().container(_k));
      CollectionKit.clear(_k);
    }
    Object ict = kv.get("Images");
    if (ict instanceof Map) {
      Kv _k = Kv.by((Map) ict);
      builder.image(EImageListParser.instance().image(_k));
      CollectionKit.clear(_k);
    }
    Object vct = kv.get("Volumes");
    if (vct instanceof Map) {
      Kv _k = Kv.by((Map) vct);
      builder.volume(EVolumeParser.instance().volume(_k));
      CollectionKit.clear(_k);
    }
    return builder.build();
  }
}
