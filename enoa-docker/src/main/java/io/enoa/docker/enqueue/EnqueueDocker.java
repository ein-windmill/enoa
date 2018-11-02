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
package io.enoa.docker.enqueue;

import io.enoa.docker.dket.docker.DRet;
import io.enoa.docker.dket.dockerhub.HRet;
import io.enoa.docker.dket.registry.RRet;
import io.enoa.promise.async.AsyncRunner;

import java.util.concurrent.ExecutorService;

public interface EnqueueDocker<T> {

  static <PARA> EnqueueDoneargDocker<PARA> donearg(ExecutorService executor, AsyncRunner<PARA> runner) {
    return new _DockerDoneargEnqueueImpl<>(executor, runner);
  }

  static <PARA> EnqueueAssetDocker<DRet<PARA>> asseterdocker(ExecutorService executor, AsyncRunner<DRet<PARA>> runner) {
    return new _DockerAssetEnqueueImpl<>(executor, runner);
  }

  static <PARA> EnqueueAssetDocker<HRet<PARA>> asseterhub(ExecutorService executor, AsyncRunner<HRet<PARA>> runner) {
    return new _DockerhubAssetEnqueueImpl<>(executor, runner);
  }

  static <PARA> EnqueueAssetDocker<RRet<PARA>> asseterregistry(ExecutorService executor, AsyncRunner<RRet<PARA>> runner) {
    return new _RegistryAssetEnqueueImpl<>(executor, runner);
  }

  T enqueue();

}
