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

import java.util.List;

public class ECAConfig extends AbstractDockerRet {

  private final Long nodecertexpiry;
  private final String signingcacert;
  private final String signingcakey;
  private final Long forcerotate;
  private final List<EExternalCA> externalcas;

  public ECAConfig(Builder builder) {
    this.nodecertexpiry = builder.nodecertexpiry;
    this.signingcacert = builder.signingcacert;
    this.signingcakey = builder.signingcakey;
    this.forcerotate = builder.forcerotate;
    this.externalcas = builder.externalcas;
  }

  public Long nodecertexpiry() {
    return this.nodecertexpiry;
  }

  public String signingcacert() {
    return this.signingcacert;
  }

  public String signingcakey() {
    return this.signingcakey;
  }

  public Long forcerotate() {
    return this.forcerotate;
  }

  public List<EExternalCA> externalcas() {
    return this.externalcas;
  }

  public static class Builder {

    private Long nodecertexpiry;
    private String signingcacert;
    private String signingcakey;
    private Long forcerotate;
    private List<EExternalCA> externalcas;

    public ECAConfig build() {
      return new ECAConfig(this);
    }

    public Builder nodecertexpiry(Long nodecertexpiry) {
      this.nodecertexpiry = nodecertexpiry;
      return this;
    }

    public Builder signingcacert(String signingcacert) {
      this.signingcacert = signingcacert;
      return this;
    }

    public Builder signingcakey(String signingcakey) {
      this.signingcakey = signingcakey;
      return this;
    }

    public Builder forcerotate(Long forcerotate) {
      this.forcerotate = forcerotate;
      return this;
    }

    public Builder externalcas(List<EExternalCA> externalcas) {
      this.externalcas = externalcas;
      return this;
    }
  }

}
