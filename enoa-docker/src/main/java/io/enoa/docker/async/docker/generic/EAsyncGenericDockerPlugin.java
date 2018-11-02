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
package io.enoa.docker.async.docker.generic;

import io.enoa.docker.async.docker.EnqueueAssetDocker;
import io.enoa.docker.async.docker.EnqueueDocker;
import io.enoa.docker.command.docker.generic.EGenericDockerPlugin;
import io.enoa.docker.command.docker.generic.GenericDocker;
import io.enoa.docker.dket.docker.DRet;
import io.enoa.docker.dqp.common.DQPFilter;
import io.enoa.docker.dqp.docker.plugin.DQPPluginInstall;
import io.enoa.docker.dqp.docker.plugin.DQPPluginUpgrade;
import io.enoa.docker.parser.docker.DIParser;
import io.enoa.toolkit.value.Void;

import java.util.Collection;
import java.util.List;

public class EAsyncGenericDockerPlugin {

  private GenericDocker docker;
  private EGenericDockerPlugin plugin;

  EAsyncGenericDockerPlugin(GenericDocker docker) {
    this.docker = docker;
    this.plugin = docker.plugin();
  }


  public <T> EnqueueAssetDocker<DRet<List<T>>> list(DIParser<List<T>> parser) {
    return EnqueueDocker.asset(this.docker._dockerconfig().executor(), () -> this.plugin.list(parser));
  }

  public <T> EnqueueAssetDocker<DRet<List<T>>> list(DIParser<List<T>> parser, DQPFilter dqp) {
    return EnqueueDocker.asset(this.docker._dockerconfig().executor(), () -> this.plugin.list(parser, dqp));
  }

  public <T> EnqueueAssetDocker<DRet<List<T>>> privileges(DIParser<List<T>> parser) {
    return EnqueueDocker.asset(this.docker._dockerconfig().executor(), () -> this.plugin.privileges(parser));
  }

  public <T> EnqueueAssetDocker<DRet<List<T>>> privileges(DIParser<List<T>> parser, String remote) {
    return EnqueueDocker.asset(this.docker._dockerconfig().executor(), () -> this.plugin.privileges(parser, remote));
  }

  public <T> EnqueueAssetDocker<DRet<List<T>>> install(DIParser<List<T>> parser) {
    return EnqueueDocker.asset(this.docker._dockerconfig().executor(), () -> this.plugin.install(parser));
  }

  public <T> EnqueueAssetDocker<DRet<List<T>>> install(DIParser<List<T>> parser, DQPPluginInstall dqp) {
    return EnqueueDocker.asset(this.docker._dockerconfig().executor(), () -> this.plugin.install(parser, dqp));
  }

  public <T> EnqueueAssetDocker<DRet<T>> inspect(DIParser<T> parser, String id) {
    return EnqueueDocker.asset(this.docker._dockerconfig().executor(), () -> this.plugin.inspect(parser, id));
  }

  public <T> EnqueueAssetDocker<DRet<T>> remove(DIParser<T> parser, String id) {
    return EnqueueDocker.asset(this.docker._dockerconfig().executor(), () -> this.plugin.remove(parser, id));
  }

  public <T> EnqueueAssetDocker<DRet<T>> remove(DIParser<T> parser, String id, boolean force) {
    return EnqueueDocker.asset(this.docker._dockerconfig().executor(), () -> this.plugin.remove(parser, id, force));
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
