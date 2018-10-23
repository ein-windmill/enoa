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
package io.enoa.docker.ret.docker.image;

import io.enoa.docker.ret.AbstractDRRet;
import io.enoa.toolkit.map.Kv;

import java.util.List;

public class EContainerConfig extends AbstractDRRet {

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
  private final EHealthcheck healthcheck;
  private final Boolean argsescaped;
  private final String image;
  private final Kv volumes;
  private final String workingdir;
  private final List<String> entrypoint;
  private final Boolean networkdisabled;
  private final String macaddress;
  private final List<String> onbuild;
  private final Kv labels;
  private final String stopsignal;
  private final Integer stoptimeout;
  private final List<String> shell;

  public EContainerConfig(Builder builder) {
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
    this.healthcheck = builder.healthcheck;
    this.argsescaped = builder.argsescaped;
    this.image = builder.image;
    this.volumes = builder.volumes;
    this.workingdir = builder.workingdir;
    this.entrypoint = builder.entrypoint;
    this.networkdisabled = builder.networkdisabled;
    this.macaddress = builder.macaddress;
    this.onbuild = builder.onbuild;
    this.labels = builder.labels;
    this.stopsignal = builder.stopsignal;
    this.stoptimeout = builder.stoptimeout;
    this.shell = builder.shell;
  }

  public String hostname() {
    return this.hostname;
  }

  public String domainname() {
    return this.domainname;
  }

  public String user() {
    return this.user;
  }

  public Boolean attachstdin() {
    return this.attachstdin;
  }

  public Boolean attachstdout() {
    return this.attachstdout;
  }

  public Boolean attachstderr() {
    return this.attachstderr;
  }

  public Kv exposedports() {
    return this.exposedports;
  }

  public Boolean tty() {
    return this.tty;
  }

  public Boolean openstdin() {
    return this.openstdin;
  }

  public Boolean stdinonce() {
    return this.stdinonce;
  }

  public List<String> env() {
    return this.env;
  }

  public List<String> cmd() {
    return this.cmd;
  }

  public EHealthcheck healthcheck() {
    return this.healthcheck;
  }

  public Boolean argsescaped() {
    return this.argsescaped;
  }

  public String image() {
    return this.image;
  }

  public Kv volumes() {
    return this.volumes;
  }

  public String workingdir() {
    return this.workingdir;
  }

  public List<String> entrypoint() {
    return this.entrypoint;
  }

  public Boolean networkdisabled() {
    return this.networkdisabled;
  }

  public String macaddress() {
    return this.macaddress;
  }

  public List<String> onbuild() {
    return this.onbuild;
  }

  public Kv labels() {
    return this.labels;
  }

  public String stopsignal() {
    return this.stopsignal;
  }

  public Integer stoptimeout() {
    return this.stoptimeout;
  }

  public List<String> shell() {
    return this.shell;
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
    private EHealthcheck healthcheck;
    private Boolean argsescaped;
    private String image;
    private Kv volumes;
    private String workingdir;
    private List<String> entrypoint;
    private Boolean networkdisabled;
    private String macaddress;
    private List<String> onbuild;
    private Kv labels;
    private String stopsignal;
    private Integer stoptimeout;
    private List<String> shell;


    public EContainerConfig build() {
      return new EContainerConfig(this);
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

    public Builder healthcheck(EHealthcheck healthcheck) {
      this.healthcheck = healthcheck;
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

    public Builder entrypoint(List<String> entrypoint) {
      this.entrypoint = entrypoint;
      return this;
    }

    public Builder networkdisabled(Boolean networkdisabled) {
      this.networkdisabled = networkdisabled;
      return this;
    }

    public Builder macaddress(String macaddress) {
      this.macaddress = macaddress;
      return this;
    }

    public Builder onbuild(List<String> onbuild) {
      this.onbuild = onbuild;
      return this;
    }

    public Builder labels(Kv labels) {
      this.labels = labels;
      return this;
    }

    public Builder stopsignal(String stopsignal) {
      this.stopsignal = stopsignal;
      return this;
    }

    public Builder stoptimeout(Integer stoptimeout) {
      this.stoptimeout = stoptimeout;
      return this;
    }

    public Builder shell(List<String> shell) {
      this.shell = shell;
      return this;
    }
  }


}
