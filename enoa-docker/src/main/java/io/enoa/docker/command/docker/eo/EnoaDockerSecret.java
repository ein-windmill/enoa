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
package io.enoa.docker.command.docker.eo;

import io.enoa.docker.command.docker.generic.EGeneicDockerSecret;
import io.enoa.docker.command.docker.generic.GenericDocker;
import io.enoa.docker.dqp.common.DQPFilter;
import io.enoa.docker.parser.docker.DIParser;
import io.enoa.docker.ret.docker.DRet;
import io.enoa.docker.ret.docker.common.ECreated;
import io.enoa.docker.ret.docker.secret.ESecret;
import io.enoa.toolkit.value.Void;

import java.util.List;

public class EnoaDockerSecret {


  private GenericDocker docker;
  private EGeneicDockerSecret secrets;

  EnoaDockerSecret(GenericDocker docker) {
    this.docker = docker;
    this.secrets = docker.secret();
  }

  public DRet<List<ESecret>> list() {
    return this.secrets.list(DIParser.secretlist());
  }

  public DRet<List<ESecret>> list(DQPFilter dqp) {
    return this.secrets.list(DIParser.secretlist(), dqp);
  }

  public DRet<ECreated> create(String body) {
    return this.secrets.create(DIParser.created(), body);
  }

  public DRet<ESecret> inspect(String id) {
    return this.secrets.inspect(DIParser.secret(), id);
  }

  public DRet<Void> remove(String id) {
    return this.secrets.remove(id);
  }

  public DRet<Void> update(String id, long version, String body) {
    return this.secrets.update(id, version, body);
  }

}
