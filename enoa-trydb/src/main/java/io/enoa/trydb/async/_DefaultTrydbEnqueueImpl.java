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
package io.enoa.trydb.async;

import io.enoa.promise.DoneArgPromise;
import io.enoa.promise.arg.PromiseArg;
import io.enoa.promise.arg.PromiseCapture;
import io.enoa.promise.arg.PromiseVoid;
import io.enoa.promise.builder.EGraenodPromiseBuilder;
import io.enoa.promise.builder.PromiseBuilder;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.trydb.promise.TrydbPromise;

import java.util.concurrent.ExecutorService;

class _DefaultTrydbEnqueueImpl<T> implements EnqueueTrydb<T> {

  private static class Holder {
    private static final ExecutorService TRYDB_ENQUEUE = PromiseBuilder.executor().enqueue("Trydb Dispatcher");
  }

  private AsyncRunner<T> executor;

  _DefaultTrydbEnqueueImpl(AsyncRunner<T> executor) {
    this.executor = executor;
  }

  @Override
  public TrydbPromise<T> enqueue() {
    EGraenodPromiseBuilder<T> donearg = PromiseBuilder.donearg();
    Holder.TRYDB_ENQUEUE.execute(() -> {
      try {
        String oldName = Thread.currentThread().getName();
//        Thread.currentThread().setName(TextKit.union("ENOA-TRYDB-ENQUEUE-", oldName));
        T ret = this.executor.run();
        if (CollectionKit.isEmpty(donearg.dones()))
          return;
        donearg.dones().forEach(done -> done.execute(ret));
      } catch (Exception e) {
        if (CollectionKit.isEmpty(donearg.captures())) {
          e.printStackTrace();
          return;
        }
        donearg.captures().forEach(capture -> capture.execute(e));
      } finally {
        if (donearg.always() != null)
          donearg.always().execute();
      }
    });

    DoneArgPromise<T> promise = donearg.build();
    return new TrydbPromise<T>() {
      @Override
      public TrydbPromise<T> done(PromiseArg<T> done) {
        promise.done(done);
        return this;
      }

      @Override
      public TrydbPromise<T> capture(PromiseCapture capture) {
        promise.capture(capture);
        return this;
      }

      @Override
      public void always(PromiseVoid always) {
        promise.always(always);
      }
    };
  }
}
