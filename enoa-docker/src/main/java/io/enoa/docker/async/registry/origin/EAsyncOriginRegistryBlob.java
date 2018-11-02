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

import io.enoa.docker.command.registry.origin.OriginBlob;
import io.enoa.docker.command.registry.origin.OriginRegistry;
import io.enoa.docker.dket.registry.RResp;
import io.enoa.docker.enqueue.EnqueueDocker;
import io.enoa.docker.enqueue.EnqueueDoneargDocker;

public class EAsyncOriginRegistryBlob {

  private OriginRegistry registry;
  private OriginBlob blob;

  EAsyncOriginRegistryBlob(OriginRegistry registry) {
    this.blob = registry.blob();
    this.registry = registry;
  }

  public EnqueueDoneargDocker<RResp> find(String repository, String digest) {
    return EnqueueDocker.donearg(this.registry._registryconfig().executor(), () -> this.blob.find(repository, digest));
  }

  public EnqueueDoneargDocker<RResp> delete(String repository, String digest) {
    return EnqueueDocker.donearg(this.registry._registryconfig().executor(), () -> this.blob.delete(repository, digest));
  }
}
