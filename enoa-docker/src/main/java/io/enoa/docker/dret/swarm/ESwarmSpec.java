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
package io.enoa.docker.dret.swarm;

import io.enoa.docker.dret.AbstractDRet;
import io.enoa.toolkit.map.Kv;

public class ESwarmSpec extends AbstractDRet {

  private final String name;
  private final Kv labels;
  private final EOrchestration orchestration;
  private final ERaft raft;
  private final EDispatcher dispatcher;
  private final ECAConfig caconfig;
  private final EEncryptionConfig encryptionconfig;
  private final ETaskDefaults taskdefaults;

  public ESwarmSpec(Builder builder) {
    this.name = builder.name;
    this.labels = builder.labels;
    this.orchestration = builder.orchestration;
    this.raft = builder.raft;
    this.dispatcher = builder.dispatcher;
    this.caconfig = builder.caconfig;
    this.encryptionconfig = builder.encryptionconfig;
    this.taskdefaults = builder.taskdefaults;
  }

  public String name() {
    return this.name;
  }

  public Kv labels() {
    return this.labels;
  }

  public EOrchestration orchestration() {
    return this.orchestration;
  }

  public ERaft raft() {
    return this.raft;
  }

  public EDispatcher dispatcher() {
    return this.dispatcher;
  }

  public ECAConfig caconfig() {
    return this.caconfig;
  }

  public EEncryptionConfig encryptionconfig() {
    return this.encryptionconfig;
  }

  public ETaskDefaults taskdefaults() {
    return this.taskdefaults;
  }

  public static class Builder {

    private String name;
    private Kv labels;
    private EOrchestration orchestration;
    private ERaft raft;
    private EDispatcher dispatcher;
    private ECAConfig caconfig;
    private EEncryptionConfig encryptionconfig;
    private ETaskDefaults taskdefaults;

    public ESwarmSpec build() {
      return new ESwarmSpec(this);
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder labels(Kv labels) {
      this.labels = labels;
      return this;
    }

    public Builder orchestration(EOrchestration orchestration) {
      this.orchestration = orchestration;
      return this;
    }

    public Builder raft(ERaft raft) {
      this.raft = raft;
      return this;
    }

    public Builder dispatcher(EDispatcher dispatcher) {
      this.dispatcher = dispatcher;
      return this;
    }

    public Builder caconfig(ECAConfig caconfig) {
      this.caconfig = caconfig;
      return this;
    }

    public Builder encryptionconfig(EEncryptionConfig encryptionconfig) {
      this.encryptionconfig = encryptionconfig;
      return this;
    }

    public Builder taskdefaults(ETaskDefaults taskdefaults) {
      this.taskdefaults = taskdefaults;
      return this;
    }
  }


}
