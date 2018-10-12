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
package io.enoa.docker.dret.system;

import io.enoa.docker.dret.AbstractDRet;

public class EMonitor extends AbstractDRet {

  private final String type;
  private final String action;
  private final Long time;
  private final EActor actor;

  public EMonitor(Builder builder) {
    this.type = builder.type;
    this.action = builder.action;
    this.actor = builder.actor;
    this.time = builder.time;
  }

  public String type() {
    return this.type;
  }

  public String action() {
    return this.action;
  }

  public Long time() {
    return this.time;
  }

  public EActor actor() {
    return this.actor;
  }

  public static class Builder {

    private String type;
    private String action;
    private Long time;
    private EActor actor;

    public EMonitor build() {
      return new EMonitor(this);
    }

    public Builder type(String type) {
      this.type = type;
      return this;
    }

    public Builder action(String action) {
      this.action = action;
      return this;
    }

    public Builder time(Long time) {
      this.time = time;
      return this;
    }

    public Builder actor(EActor actor) {
      this.actor = actor;
      return this;
    }
  }
}

