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
package io.enoa.docker.dqp.dockerhub;

import io.enoa.docker.dqp.DQR;
import io.enoa.docker.dqp.common.DQPPage;

public class DQPSearch extends DQPPage<DQPSearch> {

  private String q;
  private Boolean automated;
  private Boolean official;
  private Integer pullcount;
  private Integer starcount;

  public static DQPSearch create() {
    return new DQPSearch();
  }

  public DQPSearch() {
    this.pullcount = 0;
    this.starcount = 0;
    this.automated = Boolean.FALSE;
    this.official = Boolean.FALSE;
  }

  public DQPSearch q(String q) {
    this.q = q;
    return this;
  }

  public DQPSearch automated() {
    return this.automated(Boolean.TRUE);
  }

  public DQPSearch automated(Boolean automated) {
    this.automated = automated;
    return this;
  }

  public DQPSearch official() {
    this.official(Boolean.TRUE);
    return this;
  }

  public DQPSearch official(Boolean official) {
    this.official = official;
    return this;
  }

  public DQPSearch pullcount(Integer pullcount) {
    this.pullcount = pullcount;
    return this;
  }

  public DQPSearch starcount(Integer starcount) {
    this.starcount = starcount;
    return this;
  }

  @Override
  public DQR dqr() {
    DQR dqr = DQR.create()
      .put(super.dqr());
    dqr.put("q", this.q);
    if (this.automated)
      dqr.put("is_automated", Boolean.TRUE);
    if (this.official)
      dqr.put("is_official", Boolean.TRUE);
    if (this.pullcount != 0)
      dqr.put("pull_count", this.pullcount);
    if (this.starcount != 0)
      dqr.put("star_count", this.starcount);
    return dqr;
  }
}
