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
package io.enoa.http.provider.httphelper;

import io.enoa.http.EoHttpConfig;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class HttpHelperConfig implements EoHttpConfig {

  private final int connectionTimeout;
  private final int soTimeout;
  private final boolean debug;
  private final Set<String> chunktype;


  private HttpHelperConfig(Builder builder) {
    this.connectionTimeout = builder.connectionTimeout;
    this.soTimeout = builder.soTimeout;
    this.debug = builder.debug;
    this.chunktype = builder.chunktype;
  }

  public HttpHelperConfig(EoHttpConfig config) {
    this(new Builder(config));
  }

  @Override
  public int connectionTimeout() {
    return this.connectionTimeout;
  }

  @Override
  public int soTimeout() {
    return this.soTimeout;
  }

  @Override
  public boolean debug() {
    return this.debug;
  }

  @Override
  public Set<String> chunktype() {
    return this.chunktype == null ? Collections.emptySet() : this.chunktype;
  }

  public static class Builder {
    private int connectionTimeout;
    private int soTimeout;
    private boolean debug;
    private Set<String> chunktype;


    public Builder() {
      this.connectionTimeout = 20000;
      this.soTimeout = 30000;
      this.debug = Boolean.FALSE;
    }

    private Builder(EoHttpConfig config) {
      this();
      this.connectionTimeout = config.connectionTimeout();
      this.soTimeout = config.soTimeout();
      this.chunktype = config.chunktype();
    }

    public HttpHelperConfig build() {
      return new HttpHelperConfig(this);
    }

    public Builder connectionTimeout(int connectionTimeout) {
      this.connectionTimeout = connectionTimeout;
      return this;
    }

    public Builder soTimeout(int soTimeout) {
      this.soTimeout = soTimeout;
      return this;
    }

    public Builder debug() {
      return this.debug(Boolean.TRUE);
    }

    public Builder debug(boolean debug) {
      this.debug = debug;
      return this;
    }

    public Builder chunktype(String chunktype) {
      if (this.chunktype == null)
        this.chunktype = new HashSet<>();
      this.chunktype.add(chunktype);
      return this;
    }

    public Builder chunktype(Set<String> chunktype) {
      this.chunktype = chunktype;
      return this;
    }
  }
}
