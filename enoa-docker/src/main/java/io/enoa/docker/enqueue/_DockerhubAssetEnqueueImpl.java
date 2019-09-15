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

import io.enoa.docker.dket.dockerhub.HRet;
import io.enoa.docker.promise.DockerAssetPromise;
import io.enoa.promise.AssetPromise;
import io.enoa.promise.Promise;
import io.enoa.promise.arg.*;
import io.enoa.promise.async.AsyncRunner;
import io.enoa.promise.builder.EPAssetPromiseBuilder;

import java.util.concurrent.ExecutorService;

class _DockerhubAssetEnqueueImpl<PARA> implements EnqueueAssetDocker<HRet<PARA>> {

  private ExecutorService executor;
  private AsyncRunner<HRet<PARA>> runner;

  _DockerhubAssetEnqueueImpl(ExecutorService executor, AsyncRunner<HRet<PARA>> runner) {
    this.executor = executor;
    this.runner = runner;
  }

  @Override
  public DockerAssetPromise<HRet<PARA>> enqueue() {
    EPAssetPromiseBuilder<HRet<PARA>> builder = Promise.builder().asset();
    this.executor.execute(new _EnqueueThread(builder, this.runner));
    AssetPromise<HRet<PARA>> promise = builder.build();
    return new DockerAssetPromise<HRet<PARA>>() {
      @Override
      public DockerAssetPromise<HRet<PARA>> failthrow(PromiseArg<HRet<PARA>> arg) {
        promise.failthrow(arg);
        return this;
      }

      @Override
      public DockerAssetPromise<HRet<PARA>> asset(PromiseBool<HRet<PARA>> bool) {
        promise.asset(bool);
        return this;
      }

      @Override
      public DockerAssetPromise<HRet<PARA>> capture(PromiseCapture capture) {
        promise.capture(capture);
        return this;
      }

      @Override
      public <P> DockerAssetPromise<HRet<PARA>> then(PromiseThen<Object, P> then) {
        promise.then(then);
        return this;
      }

      @Override
      public <T> DockerAssetPromise<HRet<PARA>> execute(PromiseArg<T> arg) {
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
