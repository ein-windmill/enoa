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
package io.enoa.docker.async.docker.origin;

import io.enoa.docker.async.docker.EnqueueDocker;
import io.enoa.docker.async.docker.EnqueueDoneargDocker;
import io.enoa.docker.command.docker.origin.EOriginDockerPlugin;
import io.enoa.docker.command.docker.origin.OriginDocker;
import io.enoa.docker.dket.docker.DResp;
import io.enoa.docker.dqp.common.DQPFilter;
import io.enoa.docker.dqp.docker.plugin.DQPPluginInstall;
import io.enoa.docker.dqp.docker.plugin.DQPPluginUpgrade;

import java.util.Collection;

public class EAsyncOriginDockerPlugin {

  private OriginDocker docker;
  private EOriginDockerPlugin plugin;

  EAsyncOriginDockerPlugin(OriginDocker docker) {
    this.docker = docker;
    this.plugin = docker.plugin();
  }


  public EnqueueDoneargDocker<DResp> list() {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.plugin.list());
  }

  public EnqueueDoneargDocker<DResp> list(DQPFilter dqp) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.plugin.list(dqp));
  }

  public EnqueueDoneargDocker<DResp> privileges() {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.plugin.privileges());
  }

  public EnqueueDoneargDocker<DResp> privileges(String remote) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.plugin.privileges(remote));
  }

  public EnqueueDoneargDocker<DResp> install() {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.plugin.install());
  }

  public EnqueueDoneargDocker<DResp> install(DQPPluginInstall dqp) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.plugin.install(dqp));
  }

  public EnqueueDoneargDocker<DResp> inspect(String id) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.plugin.inspect(id));
  }

  public EnqueueDoneargDocker<DResp> remove(String id) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.plugin.remove(id));
  }

  public EnqueueDoneargDocker<DResp> remove(String id, boolean force) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.plugin.remove(id, force));
  }

  public EnqueueDoneargDocker<DResp> enable(String id) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.plugin.enable(id));
  }

  public EnqueueDoneargDocker<DResp> enable(String id, int timeout) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.plugin.enable(id, timeout));
  }

  public EnqueueDoneargDocker<DResp> disable(String id) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.plugin.disable(id));
  }

  public EnqueueDoneargDocker<DResp> upgrade(String id) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.plugin.upgrade(id));
  }

  public EnqueueDoneargDocker<DResp> upgrade(String id, DQPPluginUpgrade dqp) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.plugin.upgrade(id, dqp));
  }

  public EnqueueDoneargDocker<DResp> create(String id) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.plugin.create(id));
  }

  public EnqueueDoneargDocker<DResp> create(String id, String raw) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.plugin.create(id, raw));
  }

  public EnqueueDoneargDocker<DResp> push(String id) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.plugin.push(id));
  }

  public EnqueueDoneargDocker<DResp> set(String id) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.plugin.set(id));
  }

  public EnqueueDoneargDocker<DResp> set(String id, String arg) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.plugin.set(id, arg));
  }

  public EnqueueDoneargDocker<DResp> set(String id, String... args) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.plugin.set(id, args));
  }

  public EnqueueDoneargDocker<DResp> set(String id, Collection<String> args) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.plugin.set(id, args));
  }
}
