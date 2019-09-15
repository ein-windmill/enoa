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

import io.enoa.docker.dket.registry.RRet;
import io.enoa.docker.promise.DockerAssetPromise;
import io.enoa.promise.AssetPromise;
import io.enoa.promise.Promise;
import io.enoa.promise.arg.*;
import io.enoa.promise.async.AsyncRunner;
import io.enoa.promise.builder.EPAssetPromiseBuilder;

import java.util.concurrent.ExecutorService;

class _RegistryAssetEnqueueImpl<PARA> implements EnqueueAssetDocker<RRet<PARA>> {

  private ExecutorService executor;
  private AsyncRunner<RRet<PARA>> runner;

  _RegistryAssetEnqueueImpl(ExecutorService executor, AsyncRunner<RRet<PARA>> runner) {
    this.executor = executor;
    this.runner = runner;
  }

  @Override
  public DockerAssetPromise<RRet<PARA>> enqueue() {
    EPAssetPromiseBuilder<RRet<PARA>> builder = Promise.builder().asset();
    this.executor.execute(new _EnqueueThread(builder, this.runner));
    AssetPromise<RRet<PARA>> promise = builder.build();
    return new DockerAssetPromise<RRet<PARA>>() {
      @Override
      public DockerAssetPromise<RRet<PARA>> failthrow(PromiseArg<RRet<PARA>> arg) {
        promise.failthrow(arg);
        return this;
      }

      @Override
      public DockerAssetPromise<RRet<PARA>> asset(PromiseBool<RRet<PARA>> bool) {
        promise.asset(bool);
        return this;
      }

      @Override
      public DockerAssetPromise<RRet<PARA>> capture(PromiseCapture capture) {
        promise.capture(capture);
        return this;
      }

      @Override
      public <P> DockerAssetPromise<RRet<PARA>> then(PromiseThen<Object, P> then) {
        promise.then(then);
        return this;
      }

      @Override
      public <T> DockerAssetPromise<RRet<PARA>> execute(PromiseArg<T> arg) {
        promise.execute(arg);
        return this;
      }

      @Override
      public void always(PromiseVoid always) {
        promise.always(always);
      }
    };

  }
}
