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
package io.enoa.docker.dket.docker.container;

import io.enoa.docker.dket.AbstractDRRet;

import java.util.Date;

public class ECState extends AbstractDRRet {

  private final String status;
  private final Boolean running;
  private final Boolean paused;
  private final Boolean restarting;
  private final Boolean oomkilled;
  private final Boolean dead;
  private final Integer pid;
  private final Integer exitcode;
  private final String error;
  private final Date startedat;
  private final Date finishedat;

  public ECState(Builder builder) {
    this.status = builder.status;
    this.running = builder.running;
    this.paused = builder.paused;
    this.restarting = builder.restarting;
    this.oomkilled = builder.oomkilled;
    this.dead = builder.dead;
    this.pid = builder.pid;
    this.exitcode = builder.exitcode;
    this.error = builder.error;
    this.startedat = builder.startedat;
    this.finishedat = builder.finishedat;
  }

  public String status() {
    return status;
  }

  public Boolean running() {
    return running;
  }

  public Boolean paused() {
    return paused;
  }

  public Boolean restarting() {
    return restarting;
  }

  public Boolean oomkilled() {
    return oomkilled;
  }

  public Boolean dead() {
    return dead;
  }

  public Integer pid() {
    return pid;
  }

  public Integer exitcode() {
    return exitcode;
  }

  public String error() {
    return error;
  }

  public Date startedat() {
    return startedat;
  }

  public Date finishedat() {
    return finishedat;
  }

  public static class Builder {

    private String status;
    private Boolean running;
    private Boolean paused;
    private Boolean restarting;
    private Boolean oomkilled;
    private Boolean dead;
    private Integer pid;
    private Integer exitcode;
    private String error;
    private Date startedat;
    private Date finishedat;

    public ECState build() {
      return new ECState(this);
    }

    public Builder status(String status) {
      this.status = status;
      return this;
    }

    public Builder running(Boolean running) {
      this.running = running;
      return this;
    }

    public Builder paused(Boolean paused) {
      this.paused = paused;
      return this;
    }

    public Builder restarting(Boolean restarting) {
      this.restarting = restarting;
      return this;
    }

    public Builder oomkilled(Boolean oomkilled) {
      this.oomkilled = oomkilled;
      return this;
    }

    public Builder dead(Boolean dead) {
      this.dead = dead;
      return this;
    }

    public Builder pid(Integer pid) {
      this.pid = pid;
      return this;
    }

    public Builder exitcode(Integer exitcode) {
      this.exitcode = exitcode;
      return this;
    }

    public Builder error(String error) {
      this.error = error;
      return this;
    }

    public Builder startedat(Date startedat) {
      this.startedat = startedat;
      return this;
    }

    public Builder finishedat(Date finishedat) {
      this.finishedat = finishedat;
      return this;
    }
  }
}
