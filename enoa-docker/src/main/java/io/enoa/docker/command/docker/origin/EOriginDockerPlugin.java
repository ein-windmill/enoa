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
package io.enoa.docker.command.docker.origin;

import io.enoa.docker.dqp.common.DQPFilter;
import io.enoa.docker.dqp.docker.plugin.DQPPluginInstall;
import io.enoa.docker.dqp.docker.plugin.DQPPluginUpgrade;
import io.enoa.docker.ret.docker.DResp;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface EOriginDockerPlugin {


  default DResp list() {
    return this.list(null);
  }

  DResp list(DQPFilter dqp);

  default DResp privileges() {
    return this.privileges(null);
  }

  DResp privileges(String remote);

  default DResp install() {
    return this.install(null);
  }

  DResp install(DQPPluginInstall dqp);

  DResp inspect(String id);

  default DResp remove(String id) {
    return this.remove(id, Boolean.FALSE);
  }

  DResp remove(String id, boolean force);

  default DResp enable(String id) {
    return this.enable(id, 0);
  }

  DResp enable(String id, int timeout);

  DResp disable(String id);

  default DResp upgrade(String id) {
    return this.upgrade(id, null);
  }

  DResp upgrade(String id, DQPPluginUpgrade dqp);

  default DResp create(String id) {
    return this.create(id, null);
  }

  DResp create(String id, String raw);

  DResp push(String id);

  default DResp set(String id) {
    return this.set(id, Collections.emptyList());
  }

  default DResp set(String id, String arg) {
    return this.set(id, new String[]{arg});
  }

  default DResp set(String id, String... args) {
    return this.set(id, Stream.of(args).collect(Collectors.toList()));
  }

  DResp set(String id, Collection<String> args);


}
