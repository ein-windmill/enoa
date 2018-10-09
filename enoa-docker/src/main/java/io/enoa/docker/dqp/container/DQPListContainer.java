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
package io.enoa.docker.dqp.container;

import io.enoa.docker.dqp.DQP;
import io.enoa.docker.dqp.DPara;

public class DQPListContainer implements DQP {

  /**
   * boolean
   * default false
   * <p>
   * Return all containers. By default, only running containers are shown
   */
  private boolean all;
  /**
   * Return this number of most recently created containers, including non-running ones.
   */
  private Integer limit;
  /**
   * boolean
   * default false
   * <p>
   * Return the size of container as fields SizeRw and SizeRootFs.
   */
  private boolean size;
  /**
   * Filters to process on the container list, encoded as JSON (a map[string][]string).
   * For example, {"status": ["paused"]} will only return paused containers. Available
   */
  private FilterListContainer filter;

  public DQPListContainer() {

  }

  public static DQPListContainer create() {
    return new DQPListContainer();
  }

  public DQPListContainer all(boolean all) {
    this.all = all;
    return this;
  }

  public DQPListContainer limit(Integer limit) {
    this.limit = limit;
    return this;
  }

  public DQPListContainer size(boolean size) {
    this.size = size;
    return this;
  }

  public FilterListContainer filter() {
    if (this.filter == null)
      this.filter = new FilterListContainer(this);
    return this.filter;
  }

  @Override
  public DPara para() {
    DPara dqr = DPara.create()
      .put("all", this.all)
      .put("limit", this.limit)
      .put("size", this.size);
    if (this.filter != null)
      dqr.put(this.filter.dqr());
    return dqr;
  }

}
