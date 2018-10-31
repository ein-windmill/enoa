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
package io.enoa.docker.dqp.docker.container;

import io.enoa.docker.dket.mark.DRestart;
import io.enoa.docker.dqp.DQP;
import io.enoa.docker.dqp.DQR;

import java.util.List;

public class DQPContainerRun implements DQP {

  public static DQPContainerRun create() {
    return new DQPContainerRun();
  }

  private Boolean detach;
  private Boolean interactive;
  private Boolean tty;
  private String user;
  private List<String> attach;
  private String workdir;
  private Integer cpushares;
  private List<String> env;
  private Integer memory;
  private List<String> publish;
  private String hostname;
  private List<String> volume;
  private List<String> capadd;
  private List<String> capdrop;
  private String cidfile;
  private String cpuset;
  private List<String> device;
  private List<String> dns;
  private List<String> dnssearch;
  private String entrypoint;
  private List<String> envfile;
  private List<String> expose;
  private List<String> link;
  private List<String> lxcconf;
  private String name;
  private String net;
  private Boolean privileged;
  private DRestart restart;
  private Boolean rm;
  private Boolean sigproxy;


  public DQPContainerRun() {
    this.detach = Boolean.FALSE;
    this.interactive = Boolean.FALSE;
    this.tty = Boolean.FALSE;
    this.cpushares = 0;
    this.net = "bridge";
    this.privileged = Boolean.FALSE;
    this.restart = DRestart.NO;
    this.rm = Boolean.FALSE;
    this.sigproxy = Boolean.FALSE;
  }

  public DQPContainerRun detach(Boolean detach) {
    this.detach = detach;
    return this;
  }

  public DQPContainerRun interactive(Boolean interactive) {
    this.interactive = interactive;
    return this;
  }

  public DQPContainerRun tty(Boolean tty) {
    this.tty = tty;
    return this;
  }

  public DQPContainerRun user(String user) {
    this.user = user;
    return this;
  }

  public DQPContainerRun attach(List<String> attach) {
    this.attach = attach;
    return this;
  }

  public DQPContainerRun workdir(String workdir) {
    this.workdir = workdir;
    return this;
  }

  public DQPContainerRun cpushares(Integer cpushares) {
    this.cpushares = cpushares;
    return this;
  }

  public DQPContainerRun env(List<String> env) {
    this.env = env;
    return this;
  }

  public DQPContainerRun memory(Integer memory) {
    this.memory = memory;
    return this;
  }

  public DQPContainerRun publish(List<String> publish) {
    this.publish = publish;
    return this;
  }

  public DQPContainerRun hostname(String hostname) {
    this.hostname = hostname;
    return this;
  }

  public DQPContainerRun volume(List<String> volume) {
    this.volume = volume;
    return this;
  }

  public DQPContainerRun capadd(List<String> capadd) {
    this.capadd = capadd;
    return this;
  }

  public DQPContainerRun capdrop(List<String> capdrop) {
    this.capdrop = capdrop;
    return this;
  }

  public DQPContainerRun cidfile(String cidfile) {
    this.cidfile = cidfile;
    return this;
  }

  public DQPContainerRun cpuset(String cpuset) {
    this.cpuset = cpuset;
    return this;
  }

  public DQPContainerRun device(List<String> device) {
    this.device = device;
    return this;
  }

  public DQPContainerRun dns(List<String> dns) {
    this.dns = dns;
    return this;
  }

  public DQPContainerRun dnssearch(List<String> dnssearch) {
    this.dnssearch = dnssearch;
    return this;
  }

  public DQPContainerRun entrypoint(String entrypoint) {
    this.entrypoint = entrypoint;
    return this;
  }

  public DQPContainerRun envfile(List<String> envfile) {
    this.envfile = envfile;
    return this;
  }

  public DQPContainerRun expose(List<String> expose) {
    this.expose = expose;
    return this;
  }

  public DQPContainerRun link(List<String> link) {
    this.link = link;
    return this;
  }

  public DQPContainerRun lxcconf(List<String> lxcconf) {
    this.lxcconf = lxcconf;
    return this;
  }

  public DQPContainerRun name(String name) {
    this.name = name;
    return this;
  }

  public DQPContainerRun net(String net) {
    this.net = net;
    return this;
  }

  public DQPContainerRun privileged(Boolean privileged) {
    this.privileged = privileged;
    return this;
  }

  public DQPContainerRun restart(DRestart restart) {
    this.restart = restart;
    return this;
  }

  public DQPContainerRun rm(Boolean rm) {
    this.rm = rm;
    return this;
  }

  public DQPContainerRun sigproxy(Boolean sigproxy) {
    this.sigproxy = sigproxy;
    return this;
  }

  @Override
  public DQR dqr() {
    return null;
  }
}
