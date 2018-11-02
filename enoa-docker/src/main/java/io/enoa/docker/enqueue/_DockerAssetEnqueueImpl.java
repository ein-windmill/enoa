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
import io.enoa.docker.promise.DockerAssetPromise;
import io.enoa.promise.AssetPromise;
import io.enoa.promise.arg.*;
import io.enoa.promise.async.AsyncRunner;
import io.enoa.promise.builder.EPAssetPromiseBuilder;
import io.enoa.promise.builder.PromiseBuilder;
import io.enoa.toolkit.collection.CollectionKit;

import java.util.List;
import java.util.concurrent.ExecutorService;

class _DockerAssetEnqueueImpl<PARA> implements EnqueueAssetDocker<DRet<PARA>> {

  private ExecutorService executor;
  private AsyncRunner<DRet<PARA>> runner;

  _DockerAssetEnqueueImpl(ExecutorService executor, AsyncRunner<DRet<PARA>> runner) {
    this.executor = executor;
    this.runner = runner;
  }

  @Override
  public DockerAssetPromise<DRet<PARA>> enqueue() {
    EPAssetPromiseBuilder<DRet<PARA>> builder = PromiseBuilder.asset();
    this.executor.execute(() -> {
      try {
        String oldName = Thread.currentThread().getName();
//        DRet<PARA> ret = this.runner.run();
        DRet<PARA> ret = this.runner.run();

        List<PromiseBool> assets = builder.assets();
        boolean pass = Boolean.TRUE;
        for (PromiseBool asset : assets) {
          boolean asret = asset.execute(ret);
          if (asret)
            continue;
          pass = Boolean.FALSE;
          break;
        }
        if (!pass) {
          List<PromiseArg> failthrows = builder.failthrows();
          for (PromiseArg failthrow : failthrows) {
            failthrow.execute(ret);
          }
          return;
        }

        Object tmp = ret;
        List<PromiseThen> thens = builder.thens();
        for (PromiseThen then : thens) {
          tmp = then.execute(tmp);
        }

        List<PromiseArg> executes = builder.executes();
        for (PromiseArg execute : executes) {
          execute.execute(tmp);
        }
      } catch (Exception e) {
        List<PromiseCapture> captures = builder.captures();
        if (CollectionKit.isEmpty(captures)) {
          e.printStackTrace();
          return;
        }
        for (PromiseCapture capture : captures) {
          capture.execute(e);
        }
      } finally {
        if (builder.always() != null)
          builder.always().execute();
      }
    });
    AssetPromise<DRet<PARA>> promise = builder.build();
    return new DockerAssetPromise<DRet<PARA>>() {
      @Override
      public DockerAssetPromise<DRet<PARA>> failthrow(PromiseArg<DRet<PARA>> arg) {
        promise.failthrow(arg);
        return this;
      }

      @Override
      public DockerAssetPromise<DRet<PARA>> asset(PromiseBool<DRet<PARA>> bool) {
        promise.asset(bool);
        return this;
      }

      @Override
      public DockerAssetPromise<DRet<PARA>> capture(PromiseCapture capture) {
        promise.capture(capture);
        return this;
      }

      @Override
      public <P> DockerAssetPromise<DRet<PARA>> then(PromiseThen<Object, P> then) {
        promise.then(then);
        return this;
      }

      @Override
      public <T> DockerAssetPromise<DRet<PARA>> execute(PromiseArg<T> arg) {
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
