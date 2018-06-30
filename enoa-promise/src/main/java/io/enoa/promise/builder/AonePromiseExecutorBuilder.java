/*
 * Copyright (c) 2018, enoa (ein.windmill@outlook.com)
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
package io.enoa.promise.builder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class AonePromiseExecutorBuilder {

  private static class Holder {
    private static final AonePromiseExecutorBuilder INSTANCE = new AonePromiseExecutorBuilder();
  }

  private AonePromiseExecutorBuilder() {
  }

  static AonePromiseExecutorBuilder instance() {
    return Holder.INSTANCE;
  }

  public ExecutorService enqueue(String name) {
    return this.enqueue(name, false);
  }

  public ExecutorService enqueue(String name, boolean daemon) {
    return new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS,
      new SynchronousQueue<>(), runnable -> {
      Thread result = new Thread(runnable, name);
      result.setDaemon(daemon);
      return result;
    });
  }

}
