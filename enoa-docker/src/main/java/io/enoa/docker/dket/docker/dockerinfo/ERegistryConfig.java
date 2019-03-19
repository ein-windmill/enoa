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
package io.enoa.docker.dket.docker.dockerinfo;

import io.enoa.docker.dket.AbstractDRRet;

import java.util.Map;

public class ERegistryConfig extends AbstractDRRet {

  private final String[] allownondistributableartifactscidrs;
  private final String[] allownondistributableartifactshostnames;
  private final String[] insecureregistrycidrs;
  private final String[] mirrors;
  private final Map<String, EIndex> indexconfigs;

  private ERegistryConfig(Builder builder) {
    this.allownondistributableartifactscidrs = builder.allownondistributableartifactscidrs;
    this.allownondistributableartifactshostnames = builder.allownondistributableartifactshostnames;
    this.insecureregistrycidrs = builder.insecureregistrycidrs;
    this.mirrors = builder.mirrors;
    this.indexconfigs = builder.indexconfigs;
  }

  public String[] allownondistributableartifactscidrs() {
    return this.allownondistributableartifactscidrs;
  }

  public String[] allownondistributableartifactshostnames() {
    return this.allownondistributableartifactshostnames;
  }

  public String[] insecureregistrycidrs() {
    return this.insecureregistrycidrs;
  }

  public String[] mirrors() {
    return this.mirrors;
  }

  public Map<String, EIndex> indexconfigs() {
    return this.indexconfigs;
  }

  public static class Builder {

    private String[] allownondistributableartifactscidrs;
    private String[] allownondistributableartifactshostnames;
    private String[] insecureregistrycidrs;
    private String[] mirrors;
    private Map<String, EIndex> indexconfigs;

    public Builder() {
    }

    public ERegistryConfig build() {
      return new ERegistryConfig(this);
    }

    public Builder allownondistributableartifactscidrs(String[] allownondistributableartifactscidrs) {
      this.allownondistributableartifactscidrs = allownondistributableartifactscidrs;
      return this;
    }

    public Builder allownondistributableartifactshostnames(String[] allownondistributableartifactshostnames) {
      this.allownondistributableartifactshostnames = allownondistributableartifactshostnames;
      return this;
    }

    public Builder insecureregistrycidrs(String[] insecureregistrycidrs) {
      this.insecureregistrycidrs = insecureregistrycidrs;
      return this;
    }

    public Builder mirrors(String[] mirrors) {
      this.mirrors = mirrors;
      return this;
    }

    public Builder indexconfigs(Map<String, EIndex> indexconfigs) {
      this.indexconfigs = indexconfigs;
      return this;
    }
  }
}
