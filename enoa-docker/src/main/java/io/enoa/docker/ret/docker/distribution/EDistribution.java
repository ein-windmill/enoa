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
package io.enoa.docker.ret.docker.distribution;

import io.enoa.docker.ret.AbstractDockerRet;
import io.enoa.docker.ret.docker.node.EPlatform;

import java.util.List;


public class EDistribution extends AbstractDockerRet {


  private final EDescriptor descriptor;
  private final List<EPlatform> platforms;

  public EDistribution(Builder builder) {
    this.descriptor = builder.descriptor;
    this.platforms = builder.platforms;
  }

  public EDescriptor descriptor() {
    return this.descriptor;
  }

  public List<EPlatform> platforms() {
    return this.platforms;
  }

  public static class Builder {
    private EDescriptor descriptor;
    private List<EPlatform> platforms;

    public EDistribution build() {
      return new EDistribution(this);
    }

    public Builder descriptor(EDescriptor descriptor) {
      this.descriptor = descriptor;
      return this;
    }

    public Builder platforms(List<EPlatform> platforms) {
      this.platforms = platforms;
      return this;
    }
  }

}
