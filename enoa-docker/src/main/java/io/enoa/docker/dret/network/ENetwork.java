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
package io.enoa.docker.dret.network;

import io.enoa.docker.dret.AbstractDRet;
import io.enoa.toolkit.map.Kv;

import java.util.Date;
import java.util.Map;

public class ENetwork extends AbstractDRet {

  private String name;
  private String id;
  private Date created;
  private String scope;
  private String driver;
  private Boolean enableipv6;
  private EIPAM ipam;
  private Boolean internal;
  private Boolean attachable;
  private Boolean ingress;
    private Map<String, ENetworkContainer> containers;
  private Kv options;
  private Kv labels;


  public ENetwork(Builder builder) {
    this.name = builder.name;
    this.id = builder.id;
    this.created = builder.created;
    this.scope = builder.scope;
    this.driver = builder.driver;
    this.enableipv6 = builder.enableipv6;
    this.ipam = builder.ipam;
    this.internal = builder.internal;
    this.attachable = builder.attachable;
    this.ingress = builder.ingress;
    this.containers = builder.containers;
    this.options = builder.options;
    this.labels = builder.labels;
  }

  public String name() {
    return name;
  }

  public String id() {
    return id;
  }

  public Date created() {
    return created;
  }

  public String scope() {
    return scope;
  }

  public String driver() {
    return driver;
  }

  public Boolean enableipv6() {
    return enableipv6;
  }

  public EIPAM ipam() {
    return ipam;
  }

  public Boolean internal() {
    return internal;
  }

  public Boolean attachable() {
    return attachable;
  }

  public Boolean ingress() {
    return ingress;
  }

  public Map<String, ENetworkContainer> containers() {
    return containers;
  }

  public Kv options() {
    return options;
  }

  public Kv labels() {
    return labels;
  }

  public static class Builder {
    private String name;
    private String id;
    private Date created;
    private String scope;
    private String driver;
    private Boolean enableipv6;
    private EIPAM ipam;
    private Boolean internal;
    private Boolean attachable;
    private Boolean ingress;
    private Map<String, ENetworkContainer> containers;
    private Kv options;
    private Kv labels;

    public ENetwork build() {
      return new ENetwork(this);
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder id(String id) {
      this.id = id;
      return this;
    }

    public Builder created(Date created) {
      this.created = created;
      return this;
    }

    public Builder scope(String scope) {
      this.scope = scope;
      return this;
    }

    public Builder driver(String driver) {
      this.driver = driver;
      return this;
    }

    public Builder enableipv6(Boolean enableipv6) {
      this.enableipv6 = enableipv6;
      return this;
    }

    public Builder ipam(EIPAM ipam) {
      this.ipam = ipam;
      return this;
    }

    public Builder internal(Boolean internal) {
      this.internal = internal;
      return this;
    }

    public Builder attachable(Boolean attachable) {
      this.attachable = attachable;
      return this;
    }

    public Builder ingress(Boolean ingress) {
      this.ingress = ingress;
      return this;
    }

    public Builder containers(Map<String, ENetworkContainer> containers) {
      this.containers = containers;
      return this;
    }

    public Builder options(Kv options) {
      this.options = options;
      return this;
    }

    public Builder labels(Kv labels) {
      this.labels = labels;
      return this;
    }
  }

}
