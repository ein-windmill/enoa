/*
 * Copyright (c) 2018, enoa (ein.windmill@outlook.com)
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
package io.enoa.eml;

import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.toolkit.map.Kv;
import io.enoa.toolkit.text.TextKit;

public class EmlConfig {

  private final String user;
  private final String passwd;
  private final String host;
  private final int port;
  private final boolean debug;
  private final boolean ssl;
  private final boolean auth;
  private final Kv other;
  private final boolean skipError;

  private EmlConfig(Builder builder) {
    this.user = builder.user;
    this.passwd = builder.passwd;
    this.host = builder.host;
    this.port = builder.port;
    this.debug = builder.debug;
    this.ssl = builder.ssl;
    this.auth = builder.auth;
    this.other = builder.other;
    this.skipError = builder.skipError;
  }

  public String user() {
    return this.user;
  }

  public String passwd() {
    return this.passwd;
  }

  public String host() {
    return this.host;
  }

  public int port() {
    return this.port;
  }

  public boolean debug() {
    return this.debug;
  }

  public boolean ssl() {
    return this.ssl;
  }

  public boolean auth() {
    return this.auth;
  }

  public Kv other() {
    return this.other;
  }

  public boolean skipError() {
    return this.skipError;
  }


  public static class Builder {
    private String user;
    private String passwd;
    private String host;
    private int port;
    private boolean debug;
    private boolean ssl;
    private boolean auth;
    private Kv other;
    private boolean skipError;

    public Builder() {
      this.debug = Boolean.FALSE;
      this.ssl = Boolean.FALSE;
      this.auth = Boolean.FALSE;
      this.skipError = Boolean.FALSE;
      this.port = 0;
    }

    public EmlConfig build() {
//      if (this.port == 0)
//        throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.email.conf_port_fail"));

      return new EmlConfig(this);
    }

    public Builder user(String user) {
      this.user = user;
      return this;
    }

    public Builder passwd(String passwd) {
      this.passwd = passwd;
      return this;
    }

    public Builder host(String host) {
      this.host = host;
      return this;
    }

    public Builder port(int port) {
      this.port = port;
      return this;
    }

    public Builder debug() {
      this.debug = true;
      return this;
    }

    public Builder ssl() {
      return this.ssl(true);
    }

    public Builder ssl(boolean ssl) {
      this.ssl = ssl;
      return this;
    }

    public Builder auth() {
      return this.auth(true);
    }

    public Builder auth(boolean auth) {
      this.auth = auth;
      return this;
    }

    public Builder other(String name, String value) {
      if (TextKit.isBlank(name))
        throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.email.conf_other_name_null"));

      if (this.other == null)
        this.other = Kv.create();
      this.other.set(name.toLowerCase(), value);
      return this;
    }

    public Builder skipError() {
      return this.skipError(true);
    }

    public Builder skipError(boolean skip) {
      this.skipError = skip;
      return this;
    }

  }

}
