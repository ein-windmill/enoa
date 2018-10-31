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
package io.enoa.docker.dket.docker.swarm;

import io.enoa.docker.dket.AbstractDRRet;
import io.enoa.toolkit.map.Kv;

public class EExternalCA extends AbstractDRRet {

  private final String protocol;
  private final String url;
  private final Kv options;
  private final String cacert;

  public EExternalCA(Builder builder) {
    this.protocol = builder.protocol;
    this.url = builder.url;
    this.options = builder.options;
    this.cacert = builder.cacert;
  }

  public String protocol() {
    return this.protocol;
  }

  public String url() {
    return this.url;
  }

  public Kv options() {
    return this.options;
  }

  public String cacert() {
    return this.cacert;
  }

  public static class Builder {

    private String protocol;
    private String url;
    private Kv options;
    private String cacert;

    public EExternalCA build() {
      return new EExternalCA(this);
    }

    public Builder protocol(String protocol) {
      this.protocol = protocol;
      return this;
    }

    public Builder url(String url) {
      this.url = url;
      return this;
    }

    public Builder options(Kv options) {
      this.options = options;
      return this;
    }

    public Builder cacert(String cacert) {
      this.cacert = cacert;
      return this;
    }
  }

}
