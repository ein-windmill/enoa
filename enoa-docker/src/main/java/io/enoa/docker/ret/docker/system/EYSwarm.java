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
package io.enoa.docker.ret.docker.system;

import io.enoa.docker.ret.AbstractDRRet;

import java.util.List;

public class EYSwarm extends AbstractDRRet {

  private String nodeid;
  private String nodeaddr;
  private String localnodestate;
  private Boolean controlavailable;
  private String error;
  private List<EYRemoteManage> remotemanages;
  private Integer nodes;
  private Integer managers;
  private EYCluster cluster;

  public EYSwarm(Builder builder) {
    this.nodeid = builder.nodeid;
    this.nodeaddr = builder.nodeaddr;
    this.localnodestate = builder.localnodestate;
    this.controlavailable = builder.controlavailable;
    this.error = builder.error;
    this.remotemanages = builder.remotemanages;
    this.nodes = builder.nodes;
    this.managers = builder.managers;
    this.cluster = builder.cluster;
  }

  public String nodeid() {
    return this.nodeid;
  }

  public String nodeaddr() {
    return this.nodeaddr;
  }

  public String localnodestate() {
    return this.localnodestate;
  }

  public Boolean controlavailable() {
    return this.controlavailable;
  }

  public String error() {
    return this.error;
  }

  public List<EYRemoteManage> remotemanages() {
    return this.remotemanages;
  }

  public Integer nodes() {
    return this.nodes;
  }

  public Integer managers() {
    return this.managers;
  }

  public EYCluster cluster() {
    return this.cluster;
  }

  public static class Builder {
    private String nodeid;
    private String nodeaddr;
    private String localnodestate;
    private Boolean controlavailable;
    private String error;
    private List<EYRemoteManage> remotemanages;
    private Integer nodes;
    private Integer managers;
    private EYCluster cluster;

    public EYSwarm build() {
      return new EYSwarm(this);
    }

    public Builder nodeid(String nodeid) {
      this.nodeid = nodeid;
      return this;
    }

    public Builder nodeaddr(String nodeaddr) {
      this.nodeaddr = nodeaddr;
      return this;
    }

    public Builder localnodestate(String localnodestate) {
      this.localnodestate = localnodestate;
      return this;
    }

    public Builder controlavailable(Boolean controlavailable) {
      this.controlavailable = controlavailable;
      return this;
    }

    public Builder error(String error) {
      this.error = error;
      return this;
    }

    public Builder nodes(Integer nodes) {
      this.nodes = nodes;
      return this;
    }

    public Builder managers(Integer managers) {
      this.managers = managers;
      return this;
    }

    public Builder cluster(EYCluster cluster) {
      this.cluster = cluster;
      return this;
    }

    public Builder remotemanages(List<EYRemoteManage> remotemanages) {
      this.remotemanages = remotemanages;
      return this;
    }
  }


}
