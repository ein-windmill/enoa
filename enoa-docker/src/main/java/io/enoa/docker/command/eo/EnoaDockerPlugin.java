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
package io.enoa.docker.command.eo;

import io.enoa.docker.command.geneic.EGeneicDockerPlugin;
import io.enoa.docker.command.geneic.GeneicDocker;
import io.enoa.docker.dqp.common.DQPFilter;
import io.enoa.docker.dqp.plugin.DQPPluginInstall;
import io.enoa.docker.dqp.plugin.DQPPluginUpgrade;
import io.enoa.docker.ret.docker.DRet;
import io.enoa.docker.ret.docker.plugin.EPlugin;
import io.enoa.docker.ret.docker.plugin.EPluginPrivilege;
import io.enoa.docker.parser.DIParser;
import io.enoa.toolkit.value.Void;

import java.util.Collection;
import java.util.List;

public class EnoaDockerPlugin {


  private GeneicDocker docker;
  private EGeneicDockerPlugin plugins;

  EnoaDockerPlugin(GeneicDocker docker) {
    this.docker = docker;
    this.plugins = docker.plugin();
  }

  public DRet<List<EPlugin>> list() {
    return this.plugins.list(DIParser.pluginlist());
  }

  public DRet<List<EPlugin>> list(DQPFilter dqp) {
    return this.plugins.list(DIParser.pluginlist(), dqp);
  }

  public DRet<List<EPluginPrivilege>> privileges() {
    return this.plugins.privileges(DIParser.pluginprivilege());
  }

  public DRet<List<EPluginPrivilege>> privileges(String remote) {
    return this.plugins.privileges(DIParser.pluginprivilege(), remote);
  }

  public DRet<List<EPluginPrivilege>> install() {
    return this.plugins.install(DIParser.pluginprivilege());
  }

  public DRet<List<EPluginPrivilege>> install(DQPPluginInstall dqp) {
    return this.plugins.install(DIParser.pluginprivilege(), dqp);
  }

  public DRet<EPlugin> inspect(String id) {
    return this.plugins.inspect(DIParser.plugin(), id);
  }

  public DRet<EPlugin> remove(String id) {
    return this.plugins.remove(DIParser.plugin(), id);
  }

  public DRet<EPlugin> remove(String id, boolean force) {
    return this.plugins.remove(DIParser.plugin(), id, force);
  }

  public DRet<Void> enable(String id) {
    return this.plugins.enable(id);
  }

  public DRet<Void> enable(String id, int timeout) {
    return this.plugins.enable(id, timeout);
  }

  public DRet<Void> disable(String id) {
    return this.plugins.disable(id);
  }

  public DRet<Void> upgrade(String id) {
    return this.plugins.upgrade(id);
  }

  public DRet<Void> upgrade(String id, DQPPluginUpgrade dqp) {
    return this.plugins.upgrade(id, dqp);
  }

  public DRet<Void> create(String id) {
    return this.plugins.create(id);
  }

  public DRet<Void> create(String id, String raw) {
    return this.plugins.create(id, raw);
  }

  public DRet<Void> push(String id) {
    return this.plugins.push(id);
  }

  public DRet<Void> set(String id) {
    return this.plugins.set(id);
  }

  public DRet<Void> set(String id, String arg) {
    return this.plugins.set(id, arg);
  }

  public DRet<Void> set(String id, String... args) {
    return this.plugins.set(id, args);
  }

  public DRet<Void> set(String id, Collection<String> args) {
    return this.plugins.set(id, args);
  }


}
