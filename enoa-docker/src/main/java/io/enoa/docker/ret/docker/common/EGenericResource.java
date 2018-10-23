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
package io.enoa.docker.ret.docker.common;

import io.enoa.docker.ret.AbstractDRRet;
import io.enoa.docker.ret.docker.node.ENodeResourceSpec;

public class EGenericResource extends AbstractDRRet {

  private ENodeResourceSpec discreteresourcespec;
  private ENodeResourceSpec namedresourcespec;

  public EGenericResource(Builder builder) {
    this.discreteresourcespec = builder.discreteresourcespec;
    this.namedresourcespec = builder.namedresourcespec;
  }

  public ENodeResourceSpec discreteresourcespec() {
    return this.discreteresourcespec;
  }

  public ENodeResourceSpec namedresourcespec() {
    return this.namedresourcespec;
  }

  public static class Builder {
    private ENodeResourceSpec discreteresourcespec;
    private ENodeResourceSpec namedresourcespec;

    public EGenericResource build() {
      return new EGenericResource(this);
    }

    public Builder discreteresourcespec(ENodeResourceSpec discreteresourcespec) {
      this.discreteresourcespec = discreteresourcespec;
      return this;
    }

    public Builder namedresourcespec(ENodeResourceSpec namedresourcespec) {
      this.namedresourcespec = namedresourcespec;
      return this;
    }
  }


}
