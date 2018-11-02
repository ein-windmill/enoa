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
package io.enoa.docker.async.docker.eo;

import io.enoa.docker.async.docker.EnqueueAssetDocker;
import io.enoa.docker.async.docker.EnqueueDocker;
import io.enoa.docker.command.docker.eo.EnoaDockerPlugin;
import io.enoa.docker.command.docker.eo.EoDocker;
import io.enoa.docker.dket.docker.DRet;
import io.enoa.docker.dket.docker.plugin.EPlugin;
import io.enoa.docker.dket.docker.plugin.EPluginPrivilege;
import io.enoa.docker.dqp.common.DQPFilter;
import io.enoa.docker.dqp.docker.plugin.DQPPluginInstall;
import io.enoa.docker.dqp.docker.plugin.DQPPluginUpgrade;
import io.enoa.toolkit.value.Void;

import java.util.Collection;
import java.util.List;

public class EAsyncEnoaDockerPlugin {

  private EnoaDockerPlugin plugin;
  private EoDocker docker;

  EAsyncEnoaDockerPlugin(EoDocker docker) {
    this.plugin = docker.plugin();
    this.docker = docker;
  }


  public EnqueueAssetDocker<DRet<List<EPlugin>>> list() {
    return EnqueueDocker.asset(this.docker._dockerconfig().executor(), () -> this.plugin.list());
  }

  public EnqueueAssetDocker<DRet<List<EPlugin>>> list(DQPFilter dqp) {
    return EnqueueDocker.asset(this.docker._dockerconfig().executor(), () -> this.plugin.list(dqp));
  }

  public EnqueueAssetDocker<DRet<List<EPluginPrivilege>>> privileges() {
    return EnqueueDocker.asset(this.docker._dockerconfig().executor(), () -> this.plugin.privileges());
  }

  public EnqueueAssetDocker<DRet<List<EPluginPrivilege>>> privileges(String remote) {
    return EnqueueDocker.asset(this.docker._dockerconfig().executor(), () -> this.plugin.privileges(remote));
  }

  public EnqueueAssetDocker<DRet<List<EPluginPrivilege>>> install() {
    return EnqueueDocker.asset(this.docker._dockerconfig().executor(), () -> this.plugin.install());
  }

  public EnqueueAssetDocker<DRet<List<EPluginPrivilege>>> install(DQPPluginInstall dqp) {
    return EnqueueDocker.asset(this.docker._dockerconfig().executor(), () -> this.plugin.install(dqp));
  }

  public EnqueueAssetDocker<DRet<EPlugin>> inspect(String id) {
    return EnqueueDocker.asset(this.docker._dockerconfig().executor(), () -> this.plugin.inspect(id));
  }

  public EnqueueAssetDocker<DRet<EPlugin>> remove(String id) {
    return EnqueueDocker.asset(this.docker._dockerconfig().executor(), () -> this.plugin.remove(id));
  }

  public EnqueueAssetDocker<DRet<EPlugin>> remove(String id, boolean force) {
    return EnqueueDocker.asset(this.docker._dockerconfig().executor(), () -> this.plugin.remove(id, force));
  }

  public EnqueueAssetDocker<DRet<Void>> enable(String id) {
    return EnqueueDocker.asset(this.docker._dockerconfig().executor(), () -> this.plugin.enable(id));
  }

  public EnqueueAssetDocker<DRet<Void>> enable(String id, int timeout) {
    return EnqueueDocker.asset(this.docker._dockerconfig().executor(), () -> this.plugin.enable(id, timeout));
  }

  public EnqueueAssetDocker<DRet<Void>> disable(String id) {
    return EnqueueDocker.asset(this.docker._dockerconfig().executor(), () -> this.plugin.disable(id));
  }

  public EnqueueAssetDocker<DRet<Void>> upgrade(String id) {
    return EnqueueDocker.asset(this.docker._dockerconfig().executor(), () -> this.plugin.upgrade(id));
  }

  public EnqueueAssetDocker<DRet<Void>> upgrade(String id, DQPPluginUpgrade dqp) {
    return EnqueueDocker.asset(this.docker._dockerconfig().executor(), () -> this.plugin.upgrade(id, dqp));
  }

  public EnqueueAssetDocker<DRet<Void>> create(String id) {
    return EnqueueDocker.asset(this.docker._dockerconfig().executor(), () -> this.plugin.create(id));
  }

  public EnqueueAssetDocker<DRet<Void>> create(String id, String raw) {
    return EnqueueDocker.asset(this.docker._dockerconfig().executor(), () -> this.plugin.create(id, raw));
  }

  public EnqueueAssetDocker<DRet<Void>> push(String id) {
    return EnqueueDocker.asset(this.docker._dockerconfig().executor(), () -> this.plugin.push(id));
  }

  public EnqueueAssetDocker<DRet<Void>> set(String id) {
    return EnqueueDocker.asset(this.docker._dockerconfig().executor(), () -> this.plugin.set(id));
  }

  public EnqueueAssetDocker<DRet<Void>> set(String id, String arg) {
    return EnqueueDocker.asset(this.docker._dockerconfig().executor(), () -> this.plugin.set(id, arg));
  }

  public EnqueueAssetDocker<DRet<Void>> set(String id, String... args) {
    return EnqueueDocker.asset(this.docker._dockerconfig().executor(), () -> this.plugin.set(id, args));
  }

  public EnqueueAssetDocker<DRet<Void>> set(String id, Collection<String> args) {
    return EnqueueDocker.asset(this.docker._dockerconfig().executor(), () -> this.plugin.set(id, args));
  }
}
