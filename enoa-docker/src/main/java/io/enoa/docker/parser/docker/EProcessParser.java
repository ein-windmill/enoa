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
import io.enoa.docker.dket.docker.container.EProcesses;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.is.Is;
import io.enoa.toolkit.map.Kv;

import java.util.Collection;

class EProcessParser extends AbstractParser<EProcesses> {

  private static class Holder {
    private static final EProcessParser INSTANCE = new EProcessParser();
  }

  static EProcessParser instance() {
    return Holder.INSTANCE;
  }

  @Override
  public EProcesses ok(DockerConfig config, DResp resp) {
    Kv kv = config.json().parse(resp.string(), Kv.class);
    if (Is.empty(kv))
      return null;
    EProcesses.Builder builder = new EProcesses.Builder();
    Collection<String> titles = kv.as("Titles");
    builder.titles(titles.toArray(new String[titles.size()]));
//    Collection processes = kv.as("Processes");
//    if (Is.empty(processes))
//      return builder.build();

//    List<String[]> pcs = new ArrayList<>(processes.size());
//    processes.forEach(proc -> {
//      if (!(proc instanceof Collection))
//        return;
//      Collection item = (Collection) proc;
//      pcs.add((String[]) item.toArray(new String[item.size()]));
//    });
    builder.processes(AEExtra.listarray(kv, "Processes"));
    CollectionKit.clear(kv);
    return builder.build();
  }
}
