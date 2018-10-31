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
package io.enoa.docker.dket.docker.common;

import io.enoa.docker.dket.AbstractDRRet;

import java.util.List;

public class EResources extends AbstractDRRet {

  private Long nanocpus;
  private Long memorybytes;
  private List<EGenericResource> genericresources;

  public EResources(Builder builder) {
    this.nanocpus = builder.nanocpus;
    this.memorybytes = builder.memorybytes;
    this.genericresources = builder.genericresources;
  }

  public Long nanocpus() {
    return this.nanocpus;
  }

  public Long memorybytes() {
    return this.memorybytes;
  }

  public List<EGenericResource> genericresources() {
    return this.genericresources;
  }

  public static class Builder {

    private Long nanocpus;
    private Long memorybytes;
    private List<EGenericResource> genericresources;

    public EResources build() {
      return new EResources(this);
    }

    public Builder nanocpus(Long nanocpus) {
      this.nanocpus = nanocpus;
      return this;
    }

    public Builder memorybytes(Long memorybytes) {
      this.memorybytes = memorybytes;
      return this;
    }

    public Builder genericresources(List<EGenericResource> genericresources) {
      this.genericresources = genericresources;
      return this;
    }
  }

}
