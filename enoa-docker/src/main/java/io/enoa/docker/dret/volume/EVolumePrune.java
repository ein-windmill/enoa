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
package io.enoa.docker.dret.volume;

import io.enoa.docker.dret.AbstractDRet;

import java.util.List;

public class EVolumePrune extends AbstractDRet {


  private List<String> volumesdeleted;
  private Integer spacereclaimed;

  public EVolumePrune(Builder builder) {
    this.volumesdeleted = builder.volumesdeleted;
    this.spacereclaimed = builder.spacereclaimed;
  }

  public List<String> volumesdeleted() {
    return volumesdeleted;
  }

  public Integer spacereclaimed() {
    return spacereclaimed;
  }

  public static class Builder {

    private List<String> volumesdeleted;
    private Integer spacereclaimed;

    public EVolumePrune build() {
      return new EVolumePrune(this);
    }

    public Builder volumesdeleted(List<String> volumesdeleted) {
      this.volumesdeleted = volumesdeleted;
      return this;
    }

    public Builder spacereclaimed(Integer spacereclaimed) {
      this.spacereclaimed = spacereclaimed;
      return this;
    }
  }
}
