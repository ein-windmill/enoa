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

import io.enoa.docker.command.geneic.EGeneicDockerVolume;
import io.enoa.docker.command.geneic.GeneicDocker;
import io.enoa.docker.dqp.common.DQPFilter;
import io.enoa.docker.dqp.volume.DQPVolumeCreate;
import io.enoa.docker.dqp.volume.DQPVolumeList;
import io.enoa.docker.dret.DRet;
import io.enoa.docker.dret.volume.EVolume;
import io.enoa.docker.dret.volume.EVolumeLs;
import io.enoa.docker.dret.volume.EVolumePrune;
import io.enoa.docker.parser.DIParser;
import io.enoa.toolkit.value.Void;

public class EnoaDockerVolume {


  private GeneicDocker docker;
  private EGeneicDockerVolume volumes;

  EnoaDockerVolume(GeneicDocker docker) {
    this.docker = docker;
    this.volumes = docker.volume();
  }


  public DRet<EVolumeLs> list() {
    return this.volumes.list(DIParser.volumelist());
  }

  public DRet<EVolumeLs> list(DQPVolumeList dqp) {
    return this.volumes.list(DIParser.volumelist(), dqp);
  }

  public DRet<EVolume> create(String body) {
    return this.volumes.create(DIParser.volume(), body);
  }

  public DRet<EVolume> create(DQPVolumeCreate dqp) {
    return this.volumes.create(DIParser.volume(), dqp);
  }

  public DRet<EVolume> inspect(String id) {
    return this.volumes.inspect(DIParser.volume(), id);
  }

  public DRet<Void> remove(String id) {
    return this.volumes.remove(id);
  }

  public DRet<Void> remove(String id, Boolean force) {
    return this.volumes.remove(id, force);
  }

  public DRet<EVolumePrune> prune() {
    return this.volumes.prune(DIParser.volumeprune());
  }

  public DRet<EVolumePrune> prune(DQPFilter dqp) {
    return this.volumes.prune(DIParser.volumeprune(), dqp);
  }


}
