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
package io.enoa.docker.dret.image;

import io.enoa.docker.dret.AbstractDRet;

public class EIPrune extends AbstractDRet {

  private Integer spacereclaimed;

  public EIPrune(Builder builder) {
    this.spacereclaimed = builder.spacereclaimed;
  }

  public Integer spacereclaimed() {
    return this.spacereclaimed;
  }

  public static class Builder {
    private Integer spacereclaimed;

    public EIPrune build() {
      return new EIPrune(this);
    }

    public Builder spacereclaimed(Integer spacereclaimed) {
      this.spacereclaimed = spacereclaimed;
      return this;
    }
  }
}
