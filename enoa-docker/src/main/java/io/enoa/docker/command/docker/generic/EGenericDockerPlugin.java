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
package io.enoa.docker.command.docker.generic;

import io.enoa.docker.DockerConfig;
import io.enoa.docker.command.docker.origin.EOriginDockerPlugin;
import io.enoa.docker.command.docker.origin.OriginDocker;
import io.enoa.docker.dqp.common.DQPFilter;
import io.enoa.docker.dqp.docker.plugin.DQPPluginInstall;
import io.enoa.docker.dqp.docker.plugin.DQPPluginUpgrade;
import io.enoa.docker.parser.docker.DIParser;
import io.enoa.docker.dket.docker.DResp;
import io.enoa.docker.dket.docker.DRet;
import io.enoa.toolkit.value.Void;

import java.util.Collection;
import java.util.List;

public class EGenericDockerPlugin {

  private OriginDocker docker;
  private DockerConfig config;
  private EOriginDockerPlugin plugins;

  EGenericDockerPlugin(OriginDocker docker) {
    this.docker = docker;
    this.config = docker._dockerconfig();
    this.plugins = docker.plugin();
  }


  public <T> DRet<List<T>> list(DIParser<List<T>> parser) {
    return this.list(parser, null);
  }

  public <T> DRet<List<T>> list(DIParser<List<T>> parser, DQPFilter dqp) {
    DResp resp = this.plugins.list(dqp);
    return parser.parse(this.config, resp);
  }

  public <T> DRet<List<T>> privileges(DIParser<List<T>> parser) {
    return this.privileges(parser, null);
  }

  public <T> DRet<List<T>> privileges(DIParser<List<T>> parser, String remote) {
    DResp resp = this.plugins.privileges(remote);
    return parser.parse(this.config, resp);
  }

  public <T> DRet<List<T>> install(DIParser<List<T>> parser) {
    return this.install(parser, null);
  }

  public <T> DRet<List<T>> install(DIParser<List<T>> parser, DQPPluginInstall dqp) {
    DResp resp = this.plugins.install(dqp);
    return parser.parse(this.config, resp);
  }

  public <T> DRet<T> inspect(DIParser<T> parser, String id) {
    DResp resp = this.plugins.inspect(id);
    return parser.parse(this.config, resp);
  }

  public <T> DRet<T> remove(DIParser<T> parser, String id) {
    DResp resp = this.plugins.remove(id);
    return parser.parse(this.config, resp);
  }

  public <T> DRet<T> remove(DIParser<T> parser, String id, boolean force) {
    DResp resp = this.plugins.remove(id, force);
    return parser.parse(this.config, resp);
  }

  public DRet<Void> enable(String id) {
    DResp resp = this.plugins.enable(id);
    return DIParser.voidx().parse(this.config, resp);
  }

  public DRet<Void> enable(String id, int timeout) {
    DResp resp = this.plugins.enable(id, timeout);
    return DIParser.voidx().parse(this.config, resp);
  }

  public DRet<Void> disable(String id) {
    DResp resp = this.plugins.disable(id);
    return DIParser.voidx().parse(this.config, resp);
  }

  public DRet<Void> upgrade(String id) {
    return this.upgrade(id, null);
  }

  public DRet<Void> upgrade(String id, DQPPluginUpgrade dqp) {
    DResp resp = this.plugins.upgrade(id, dqp);
    return DIParser.voidx().parse(this.config, resp);
  }

  public DRet<Void> create(String id) {
    return this.create(id, null);
  }

  public DRet<Void> create(String id, String raw) {
    DResp resp = this.plugins.create(id, raw);
    return DIParser.voidx().parse(this.config, resp);
  }

  public DRet<Void> push(String id) {
    DResp resp = this.plugins.push(id);
    return DIParser.voidx().parse(this.config, resp);
  }

  public DRet<Void> set(String id) {
    DResp resp = this.plugins.set(id);
    return DIParser.voidx().parse(this.config, resp);
  }

  public DRet<Void> set(String id, String arg) {
    DResp resp = this.plugins.set(id, arg);
    return DIParser.voidx().parse(this.config, resp);
  }

  public DRet<Void> set(String id, String... args) {
    DResp resp = this.plugins.set(id, args);
    return DIParser.voidx().parse(this.config, resp);
  }

  public DRet<Void> set(String id, Collection<String> args) {
    DResp resp = this.plugins.set(id, args);
    return DIParser.voidx().parse(this.config, resp);
  }

}
