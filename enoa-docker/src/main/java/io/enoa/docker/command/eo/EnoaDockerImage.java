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

import io.enoa.docker.command.geneic.EGeneicDockerImage;
import io.enoa.docker.command.geneic.GeneicDocker;
import io.enoa.docker.dqp.image.DQPBuild;
import io.enoa.docker.dqp.image.DQPListImage;
import io.enoa.docker.dret.DRet;
import io.enoa.docker.dret.image.EImage;
import io.enoa.docker.parser.DIParser;
import io.enoa.docker.stream.DStream;
import io.enoa.toolkit.map.Kv;

import java.util.List;

public class EnoaDockerImage {


  private GeneicDocker docker;
  private EGeneicDockerImage image;

  EnoaDockerImage(GeneicDocker docker) {
    this.docker = docker;
    this.image = docker.image();
  }

  public DRet<List<EImage>> list() {
    return this.image.list(DIParser.image());
  }

  public DRet<List<EImage>> list(DQPListImage dqp) {
    return this.image.list(DIParser.image(), dqp);
  }

  public DRet<List<Kv>> build(DQPBuild dqp, String dockerfile) {
    return this.image.build(dqp, dockerfile);
  }

  public DRet<List<Kv>> build(DQPBuild dqp, String dockerfile, DStream<Kv> dstream) {
    return this.image.build(dqp, dockerfile, dstream);
  }

}
