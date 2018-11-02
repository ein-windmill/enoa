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
package io.enoa.docker.async.registry.origin;

import io.enoa.docker.command.registry.origin.OriginRegistry;
import io.enoa.docker.command.registry.origin.OriginUpload;
import io.enoa.docker.dket.registry.RResp;
import io.enoa.docker.enqueue.EnqueueDocker;
import io.enoa.docker.enqueue.EnqueueDoneargDocker;

public class EAsyncOriginRegistryUpload {

  private OriginRegistry registry;
  private OriginUpload upload;

  EAsyncOriginRegistryUpload(OriginRegistry registry) {
    this.registry = registry;
    this.upload = registry.upload();
  }

  public EnqueueDoneargDocker<RResp> create(String repository) {
    return EnqueueDocker.donearg(this.registry._registryconfig().executor(), () -> this.upload.create(repository));
  }

  public EnqueueDoneargDocker<RResp> find(String repository, String uuid) {
    return EnqueueDocker.donearg(this.registry._registryconfig().executor(), () -> this.upload.find(repository, uuid));
  }

  public EnqueueDoneargDocker<RResp> update(String repository, String uuid) {
    return EnqueueDocker.donearg(this.registry._registryconfig().executor(), () -> this.upload.update(repository, uuid));
  }

  public EnqueueDoneargDocker<RResp> delete(String repository, String uuid) {
    return EnqueueDocker.donearg(this.registry._registryconfig().executor(), () -> this.upload.delete(repository, uuid));
  }
}
