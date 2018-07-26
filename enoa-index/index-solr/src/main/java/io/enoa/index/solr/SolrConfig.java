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
package io.enoa.index.solr;

public class SolrConfig {

  private final String host;
  private final String user;
  private final String passwd;
  private final boolean debug;

  public SolrConfig(Builder builder) {
    this.host = builder.host;
    this.user = builder.user;
    this.passwd = builder.passwd;
    this.debug = builder.debug;
  }

  public String host() {
    return host;
  }

  public String user() {
    return user;
  }

  public String passwd() {
    return passwd;
  }

  public boolean debug() {
    return debug;
  }

  public static class Builder {
    private String host;
    private String user;
    private String passwd;
    private boolean debug;

    public Builder() {
      this.debug = Boolean.FALSE;
    }

    public SolrConfig build() {
      return new SolrConfig(this);
    }

    public Builder host(String host) {
      this.host = host;
      return this;
    }

    public Builder user(String user) {
      this.user = user;
      return this;
    }

    public Builder passwd(String passwd) {
      this.passwd = passwd;
      return this;
    }

    public Builder debug(boolean debug) {
      this.debug = debug;
      return this;
    }
  }

}
