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
import io.enoa.docker.command.docker.origin.EOriginDockerVolume;
import io.enoa.docker.command.docker.origin.OriginDocker;
import io.enoa.docker.dqp.common.DQPFilter;
import io.enoa.docker.dqp.docker.volume.DQPVolumeCreate;
import io.enoa.docker.dqp.docker.volume.DQPVolumeList;
import io.enoa.docker.ret.docker.DResp;
import io.enoa.docker.ret.docker.DRet;
import io.enoa.docker.parser.docker.DIParser;
import io.enoa.toolkit.value.Void;

public class EGeneicDockerVolume {

  private OriginDocker docker;
  private DockerConfig config;
  private EOriginDockerVolume volumes;

  EGeneicDockerVolume(OriginDocker docker) {
    this.docker = docker;
    this.config = docker._dockerconfig();
    this.volumes = docker.volume();
  }

  public <T> DRet<T> list(DIParser<T> parser) {
    return this.list(parser, null);
  }

  public <T> DRet<T> list(DIParser<T> parser, DQPVolumeList dqp) {
    DResp resp = this.volumes.list(dqp);
    return parser.parse(this.config, resp);
  }

  public <T> DRet<T> create(DIParser<T> parser, String body) {
    DResp resp = this.volumes.create(body);
    return parser.parse(this.config, resp);
  }

  public <T> DRet<T> create(DIParser<T> parser, DQPVolumeCreate dqp) {
    DResp resp = this.volumes.create(dqp);
    return parser.parse(this.config, resp);
  }

  public <T> DRet<T> inspect(DIParser<T> parser, String id) {
    DResp resp = this.volumes.inspect(id);
    return parser.parse(this.config, resp);
  }

  public DRet<Void> remove(String id) {
    return this.remove(id, Boolean.FALSE);
  }

  public DRet<Void> remove(String id, Boolean force) {
    DResp resp = this.volumes.remove(id, force);
    return DIParser.voidx().parse(this.config, resp);
  }

  public <T> DRet<T> prune(DIParser<T> parser) {
    return this.prune(parser, null);
  }

  public <T> DRet<T> prune(DIParser<T> parser, DQPFilter dqp) {
    DResp resp = this.volumes.prune(dqp);
    return parser.parse(this.config, resp);
  }

}
