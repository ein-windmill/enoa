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

import io.enoa.docker.enqueue.EnqueueAssetDocker;
import io.enoa.docker.enqueue.EnqueueDocker;
import io.enoa.docker.command.docker.eo.EnoaDockerSecret;
import io.enoa.docker.command.docker.eo.EoDocker;
import io.enoa.docker.dket.docker.DRet;
import io.enoa.docker.dket.docker.common.ECreated;
import io.enoa.docker.dket.docker.secret.ESecret;
import io.enoa.docker.dqp.common.DQPFilter;
import io.enoa.toolkit.value.Void;

import java.util.List;

public class EAsyncEnoaDockerSecret {

  private EoDocker docker;
  private EnoaDockerSecret secret;

  EAsyncEnoaDockerSecret(EoDocker docker) {
    this.secret = docker.secret();
    this.docker = docker;
  }


  public EnqueueAssetDocker<DRet<List<ESecret>>> list() {
    return EnqueueDocker.asseter(this.docker._dockerconfig().executor(), () -> this.secret.list());
  }

  public EnqueueAssetDocker<DRet<List<ESecret>>> list(DQPFilter dqp) {
    return EnqueueDocker.asseter(this.docker._dockerconfig().executor(), () -> this.secret.list(dqp));
  }

  public EnqueueAssetDocker<DRet<ECreated>> create(String body) {
    return EnqueueDocker.asseter(this.docker._dockerconfig().executor(), () -> this.secret.create(body));
  }

  public EnqueueAssetDocker<DRet<ESecret>> inspect(String id) {
    return EnqueueDocker.asseter(this.docker._dockerconfig().executor(), () -> this.secret.inspect(id));
  }

  public EnqueueAssetDocker<DRet<Void>> remove(String id) {
    return EnqueueDocker.asseter(this.docker._dockerconfig().executor(), () -> this.secret.remove(id));
  }

  public EnqueueAssetDocker<DRet<Void>> update(String id, long version, String body) {
    return EnqueueDocker.asseter(this.docker._dockerconfig().executor(), () -> this.secret.update(id, version, body));
  }
}
