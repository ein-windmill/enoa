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
package io.enoa.docker.command.geneic;

import io.enoa.docker.DockerConfig;
import io.enoa.docker.command.origin.EOriginPlugin;
import io.enoa.docker.command.origin.OriginDocker;
import io.enoa.docker.dqp.common.DQPFilter;
import io.enoa.docker.dqp.plugin.DQPPluginInstall;
import io.enoa.docker.dqp.plugin.DQPPluginUpgrade;
import io.enoa.docker.dret.DRet;

import java.util.Collection;

public class EGeneicDockerPlugin {

  private OriginDocker docker;
  private DockerConfig config;
  private EOriginPlugin plugins;

  EGeneicDockerPlugin(OriginDocker docker) {
    this.docker = docker;
    this.config = docker._dockerconfig();
    this.plugins = docker.plugin();
  }


  public DRet list() {
    return null;
  }


  public DRet list(DQPFilter dqp) {
    return null;
  }


  public DRet privileges() {
    return null;
  }


  public DRet privileges(String remote) {
    return null;
  }


  public DRet install() {
    return null;
  }


  public DRet install(DQPPluginInstall dqp) {
    return null;
  }


  public DRet inspect(String id) {
    return null;
  }


  public DRet remove(String id) {
    return null;
  }


  public DRet remove(String id, boolean force) {
    return null;
  }


  public DRet enable(String id) {
    return null;
  }


  public DRet enable(String id, int timeout) {
    return null;
  }


  public DRet disable(String id) {
    return null;
  }


  public DRet upgrade(String id) {
    return null;
  }


  public DRet upgrade(String id, DQPPluginUpgrade dqp) {
    return null;
  }


  public DRet create(String id) {
    return null;
  }


  public DRet create(String id, String raw) {
    return null;
  }


  public DRet push(String id) {
    return null;
  }


  public DRet set(String id) {
    return null;
  }


  public DRet set(String id, String arg) {
    return null;
  }


  public DRet set(String id, String... args) {
    return null;
  }


  public DRet set(String id, Collection<String> args) {
    return null;
  }
}
