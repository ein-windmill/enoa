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
package io.enoa.docker.dqp.network;

import io.enoa.docker.dqp.DQP;
import io.enoa.docker.dqp.DQR;
import io.enoa.docker.dqp.image.DQPImageList;
import io.enoa.json.Json;

import java.util.ArrayList;
import java.util.List;

public class DQPNetworkList implements DQP {

  /**
   * string
   * <p>
   * JSON encoded value of the filters (a map[string][]string) to process on the networks list. Available filters:
   * <p>
   * driver=<driver-name> Matches a network's driver.
   * id=<network-id> Matches all or part of a network ID.
   * label=<key> or label=<key>=<value> of a network label.
   * name=<network-name> Matches all or part of a network name.
   * scope=["swarm"|"global"|"local"] Filters networks by scope (swarm, global, or local).
   * type=["custom"|"builtin"] Filters networks by type. The custom keyword returns all user-defined networks.
   */
  private List<String> filters;

  public static DQPImageList create() {
    return new DQPImageList();
  }

  public DQPNetworkList filters(String filter) {
    if (this.filters == null)
      this.filters = new ArrayList<>();
    this.filters.add(filter);
    return this;
  }

  public DQPNetworkList filters(List<String> filters) {
    this.filters = filters;
    return this;
  }

  @Override
  public DQR dqr() {
    return DQR.create()
      .putIf("filters", Json.toJson(this.filters));
  }
}
