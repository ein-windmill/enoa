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
package io.enoa.promise.builder;

import java.util.concurrent.*;

public class EPXEnoaPromiseExecutorBuilder {

  private static class Holder {
    private static final EPXEnoaPromiseExecutorBuilder INSTANCE = new EPXEnoaPromiseExecutorBuilder();
  }

  private EPXEnoaPromiseExecutorBuilder() {
  }

  static EPXEnoaPromiseExecutorBuilder instance() {
    return Holder.INSTANCE;
  }

  public ExecutorService enqueue(String name) {
    return this.single(name);
  }

  public ExecutorService enqueue(String name, boolean daemon) {
    return this.single(name, daemon);
  }

  public ExecutorService single(String name) {
    return this.single(name, Boolean.FALSE);
  }

  public ExecutorService single(String name, boolean daemon) {
    return Executors.newSingleThreadExecutor(runnable -> {
      Thread result = new Thread(runnable, name);
      result.setDaemon(daemon);
      return result;
    });
  }

  public ExecutorService multiple(String name) {
    return this.multiple(name, Boolean.FALSE);
  }

  public ExecutorService multiple(String name, boolean daemon) {
    return new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS,
      new SynchronousQueue<>(), runnable -> {
      Thread result = new Thread(runnable, name);
      result.setDaemon(daemon);
      return result;
    });
  }

}
