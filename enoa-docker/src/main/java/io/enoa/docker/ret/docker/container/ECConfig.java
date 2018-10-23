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
package io.enoa.docker.ret.docker.container;

import io.enoa.docker.ret.AbstractDockerRet;
import io.enoa.toolkit.map.Kv;

import java.util.List;

public class ECConfig extends AbstractDockerRet {

  private final String hostname;
  private final String domainname;
  private final String user;
  private final Boolean attachstdin;
  private final Boolean attachstdout;
  private final Boolean attachstderr;
  private final Kv exposedports;
  private final Boolean tty;
  private final Boolean openstdin;
  private final Boolean stdinonce;
  private final List<String> env;
  private final List<String> cmd;
  private final Boolean argsescaped;
  private final String image;
  private final Kv volumes;
  private final String workingdir;
  private final Object entrypoint;
  private final Object onbuild;
  private final Object labels;

  public ECConfig(Builder builder) {
    this.hostname = builder.hostname;
    this.domainname = builder.domainname;
    this.user = builder.user;
    this.attachstdin = builder.attachstdin;
    this.attachstdout = builder.attachstdout;
    this.attachstderr = builder.attachstderr;
    this.exposedports = builder.exposedports;
    this.tty = builder.tty;
    this.openstdin = builder.openstdin;
    this.stdinonce = builder.stdinonce;
    this.env = builder.env;
    this.cmd = builder.cmd;
    this.argsescaped = builder.argsescaped;
    this.image = builder.image;
    this.volumes = builder.volumes;
    this.workingdir = builder.workingdir;
    this.entrypoint = builder.entrypoint;
    this.onbuild = builder.onbuild;
    this.labels = builder.labels;
  }


  public String hostname() {
    return hostname;
  }

  public String domainname() {
    return domainname;
  }

  public String user() {
    return user;
  }

  public Boolean attachstdin() {
    return attachstdin;
  }

  public Boolean attachstdout() {
    return attachstdout;
  }

  public Boolean attachstderr() {
    return attachstderr;
  }

  public Kv exposedports() {
    return exposedports;
  }

  public Boolean tty() {
    return tty;
  }

  public Boolean openstdin() {
    return openstdin;
  }

  public Boolean stdinonce() {
    return stdinonce;
  }

  public List<String> env() {
    return env;
  }

  public List<String> cmd() {
    return cmd;
  }

  public Boolean argsescaped() {
    return argsescaped;
  }

  public String image() {
    return image;
  }

  public Kv volumes() {
    return volumes;
  }

  public String workingdir() {
    return workingdir;
  }

  public Object entrypoint() {
    return entrypoint;
  }

  public Object onbuild() {
    return onbuild;
  }

  public Object labels() {
    return labels;
  }

  public static class Builder {

    private String hostname;
    private String domainname;
    private String user;
    private Boolean attachstdin;
    private Boolean attachstdout;
    private Boolean attachstderr;
    private Kv exposedports;
    private Boolean tty;
    private Boolean openstdin;
    private Boolean stdinonce;
    private List<String> env;
    private List<String> cmd;
    private Boolean argsescaped;
    private String image;
    private Kv volumes;
    private String workingdir;
    private Object entrypoint;
    private Object onbuild;
    private Object labels;

    public ECConfig build() {
      return new ECConfig(this);
    }

    public Builder hostname(String hostname) {
      this.hostname = hostname;
      return this;
    }

    public Builder domainname(String domainname) {
      this.domainname = domainname;
      return this;
    }

    public Builder user(String user) {
      this.user = user;
      return this;
    }

    public Builder attachstdin(Boolean attachstdin) {
      this.attachstdin = attachstdin;
      return this;
    }

    public Builder attachstdout(Boolean attachstdout) {
      this.attachstdout = attachstdout;
      return this;
    }

    public Builder attachstderr(Boolean attachstderr) {
      this.attachstderr = attachstderr;
      return this;
    }

    public Builder exposedports(Kv exposedports) {
      this.exposedports = exposedports;
      return this;
    }

    public Builder tty(Boolean tty) {
      this.tty = tty;
      return this;
    }

    public Builder openstdin(Boolean openstdin) {
      this.openstdin = openstdin;
      return this;
    }

    public Builder stdinonce(Boolean stdinonce) {
      this.stdinonce = stdinonce;
      return this;
    }

    public Builder env(List<String> env) {
      this.env = env;
      return this;
    }

    public Builder cmd(List<String> cmd) {
      this.cmd = cmd;
      return this;
    }

    public Builder argsescaped(Boolean argsescaped) {
      this.argsescaped = argsescaped;
      return this;
    }

    public Builder image(String image) {
      this.image = image;
      return this;
    }

    public Builder volumes(Kv volumes) {
      this.volumes = volumes;
      return this;
    }

    public Builder workingdir(String workingdir) {
      this.workingdir = workingdir;
      return this;
    }

    public Builder entrypoint(Object entrypoint) {
      this.entrypoint = entrypoint;
      return this;
    }

    public Builder onbuild(Object onbuild) {
      this.onbuild = onbuild;
      return this;
    }

    public Builder labels(Object labels) {
      this.labels = labels;
      return this;
    }
  }

}
