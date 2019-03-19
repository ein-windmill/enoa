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
package io.enoa.docker.dqp.docker.volume;

import io.enoa.docker.dqp.DQR;
import io.enoa.docker.dqp.common.DQPFilter;

public class DQPVolumeList extends DQPFilter<DQPVolumeList> {

//  private List<String> filters;

  public static DQPVolumeList create() {
    return new DQPVolumeList();
  }

  public DQPVolumeList() {
  }

//  public DQPVolumeList filters(String filter) {
//    if (this.filters == null)
//      this.filters = new ArrayList<>();
//    this.filters.add(filter);
//    return this;
//  }
//
//  public DQPVolumeList filters(List<String> filters) {
//    this.filters = filters;
//    return this;
//  }

  @Override
  public DQR dqr() {
//    DQR dqr = DQR.create();
//    if (CollectionKit.notEmpty(this.filters))
//      dqr.put("filters", Json.toJson(this.filters));
//    return dqr;
    return super.dqr();
  }
}
