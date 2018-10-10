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
package io.enoa.docker.dqp.image;

import io.enoa.docker.dqp.DQP;
import io.enoa.docker.dqp.DQR;
import io.enoa.json.Json;

import java.util.ArrayList;
import java.util.List;

public class DQPPruneImage implements DQP {

  /**
   * string
   * <p>
   * Filters to process on the prune list, encoded as JSON (a map[string][]string). Available filters:
   * <p>
   * dangling=<boolean> When set to true (or 1), prune only unused and untagged images. When set to false (or 0), all unused images are pruned.
   * until=<string> Prune images created before this timestamp. The <timestamp> can be Unix timestamps, date formatted timestamps, or Go duration strings (e.g. 10m, 1h30m) computed relative to the daemon machine’s time.
   * label (label=<key>, label=<key>=<value>, label!=<key>, or label!=<key>=<value>) Prune images with (or without, in case label!=... is used) the specified labels.
   */
  private List<String> filters;

  public static DQPPruneImage create() {
    return new DQPPruneImage();
  }

  public DQPPruneImage() {
  }

  public DQPPruneImage filters(String filter) {
    if (this.filters == null)
      this.filters = new ArrayList<>();
    this.filters.add(filter);
    return this;
  }

  public DQPPruneImage filters(List<String> filters) {
    this.filters = filters;
    return this;
  }

  @Override
  public DQR dqr() {
    DQR dqr = DQR.create();
    if (this.filters != null)
      dqr.put("filters", Json.toJson(this.filters));
    return dqr;
  }
}
