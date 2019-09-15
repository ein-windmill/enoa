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
package io.enoa.docker.async.docker.origin;

import io.enoa.docker.command.docker.origin.EOriginDockerSecret;
import io.enoa.docker.command.docker.origin.OriginDocker;
import io.enoa.docker.dket.docker.DResp;
import io.enoa.docker.dqp.common.DQPFilter;
import io.enoa.docker.enqueue.EnqueueDocker;
import io.enoa.docker.enqueue.EnqueueDoneargDocker;

public class EAsyncOriginDockerSecret {

  private OriginDocker docker;
  private EOriginDockerSecret secret;

  EAsyncOriginDockerSecret(OriginDocker docker) {
    this.docker = docker;
    this.secret = docker.secret();
  }

  public EnqueueDoneargDocker<DResp> list() {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.secret.list());
  }

  public EnqueueDoneargDocker<DResp> list(DQPFilter dqp) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.secret.list(dqp));
  }

  public EnqueueDoneargDocker<DResp> create(String body) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.secret.create(body));
  }

  public EnqueueDoneargDocker<DResp> inspect(String id) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.secret.inspect(id));
  }

  public EnqueueDoneargDocker<DResp> remove(String id) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.secret.remove(id));
  }

  public EnqueueDoneargDocker<DResp> update(String id, long version, String body) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.secret.update(id, version, body));
  }
}
