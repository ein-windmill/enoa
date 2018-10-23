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
package io.enoa.docker.ret.docker.container;

import io.enoa.docker.ret.AbstractDockerRet;

import java.util.List;

public class ECPrune extends AbstractDockerRet {

  private List<String> containersdeleted;
  private Integer spacereclaimed;

  public ECPrune(Builder builder) {
    this.containersdeleted = builder.containersdeleted;
    this.spacereclaimed = builder.spacereclaimed;
  }

  public List<String> containersdeleted() {
    return containersdeleted;
  }

  public Integer spacereclaimed() {
    return spacereclaimed;
  }

  public static class Builder {

    private List<String> containersdeleted;
    private Integer spacereclaimed;

    public ECPrune build() {
      return new ECPrune(this);
    }

    public Builder containersdeleted(List<String> containersdeleted) {
      this.containersdeleted = containersdeleted;
      return this;
    }

    public Builder spacereclaimed(Integer spacereclaimed) {
      this.spacereclaimed = spacereclaimed;
      return this;
    }
  }

}
