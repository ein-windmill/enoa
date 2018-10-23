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
package io.enoa.docker.ret.docker.volume;

import io.enoa.docker.ret.AbstractDockerRet;
import io.enoa.toolkit.map.Kv;

import java.util.Date;

public class EVolume extends AbstractDockerRet {

  private String name;
  private String driver;
  private String mountpoint;
  private Date createdat;
  private Kv status;
  private Kv labels;
  private String scope;
  private Kv options;
  private EUseageData useagedata;

  public EVolume(Builder builder) {
    this.name = builder.name;
    this.driver = builder.driver;
    this.mountpoint = builder.mountpoint;
    this.createdat = builder.createdat;
    this.status = builder.status;
    this.labels = builder.labels;
    this.scope = builder.scope;
    this.options = builder.options;
    this.useagedata = builder.useagedata;
  }

  public String name() {
    return name;
  }

  public String driver() {
    return driver;
  }

  public String mountpoint() {
    return mountpoint;
  }

  public Date createdat() {
    return createdat;
  }

  public Kv status() {
    return status;
  }

  public Kv labels() {
    return labels;
  }

  public String scope() {
    return scope;
  }

  public Kv options() {
    return options;
  }

  public EUseageData useagedata() {
    return useagedata;
  }

  public static class Builder {
    private String name;
    private String driver;
    private String mountpoint;
    private Date createdat;
    private Kv status;
    private Kv labels;
    private String scope;
    private Kv options;
    private EUseageData useagedata;


    public EVolume build() {
      return new EVolume(this);
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder driver(String driver) {
      this.driver = driver;
      return this;
    }

    public Builder mountpoint(String mountpoint) {
      this.mountpoint = mountpoint;
      return this;
    }

    public Builder createdat(Date createdat) {
      this.createdat = createdat;
      return this;
    }

    public Builder status(Kv status) {
      this.status = status;
      return this;
    }

    public Builder labels(Kv labels) {
      this.labels = labels;
      return this;
    }

    public Builder scope(String scope) {
      this.scope = scope;
      return this;
    }

    public Builder options(Kv options) {
      this.options = options;
      return this;
    }

    public Builder useagedata(EUseageData useagedata) {
      this.useagedata = useagedata;
      return this;
    }
  }


}
