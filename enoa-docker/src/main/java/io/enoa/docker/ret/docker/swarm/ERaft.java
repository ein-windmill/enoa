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
package io.enoa.docker.ret.docker.swarm;

import io.enoa.docker.ret.AbstractDockerRet;

public class ERaft extends AbstractDockerRet {

  private final Long snapshotinterval;
  private final Long keepoldsnapshots;
  private final Long logentriesforslowfollowers;
  private final Integer electiontick;
  private final Integer heartbeattick;

  public ERaft(Builder builder) {
    this.snapshotinterval = builder.snapshotinterval;
    this.keepoldsnapshots = builder.keepoldsnapshots;
    this.logentriesforslowfollowers = builder.logentriesforslowfollowers;
    this.electiontick = builder.electiontick;
    this.heartbeattick = builder.heartbeattick;
  }

  public Long snapshotinterval() {
    return this.snapshotinterval;
  }

  public Long keepoldsnapshots() {
    return this.keepoldsnapshots;
  }

  public Long logentriesforslowfollowers() {
    return this.logentriesforslowfollowers;
  }

  public Integer electiontick() {
    return this.electiontick;
  }

  public Integer heartbeattick() {
    return this.heartbeattick;
  }

  public static class Builder {
    private Long snapshotinterval;
    private Long keepoldsnapshots;
    private Long logentriesforslowfollowers;
    private Integer electiontick;
    private Integer heartbeattick;

    public ERaft build() {
      return new ERaft(this);
    }


    public Builder snapshotinterval(Long snapshotinterval) {
      this.snapshotinterval = snapshotinterval;
      return this;
    }

    public Builder keepoldsnapshots(Long keepoldsnapshots) {
      this.keepoldsnapshots = keepoldsnapshots;
      return this;
    }

    public Builder logentriesforslowfollowers(Long logentriesforslowfollowers) {
      this.logentriesforslowfollowers = logentriesforslowfollowers;
      return this;
    }

    public Builder electiontick(Integer electiontick) {
      this.electiontick = electiontick;
      return this;
    }

    public Builder heartbeattick(Integer heartbeattick) {
      this.heartbeattick = heartbeattick;
      return this;
    }
  }


}
