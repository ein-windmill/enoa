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
package io.enoa.http.provider.httphelper.async;

import io.enoa.http.EoEmit;
import io.enoa.http.EoExecutor;
import io.enoa.http.EoUrl;
import io.enoa.http.protocol.HttpPromise;

import java.util.concurrent.*;

public class HttpHelperExecutor implements EoExecutor {

  private static final HttpHelperExecutor instance = new HttpHelperExecutor();

  public static HttpHelperExecutor instance() {
    return instance;
  }

  private ExecutorService executorService;

  private static ThreadFactory threadFactory(final String name, final boolean daemon) {
    return runnable -> {
      Thread result = new Thread(runnable, name);
      result.setDaemon(daemon);
      return result;
    };
  }

  private ExecutorService executorService() {
    if (executorService == null) {
      executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS,
        new SynchronousQueue<>(), threadFactory("HttpHelper Dispatcher", false));
    }
    return executorService;
  }

  @Override
  public HttpPromise enqueue(EoUrl url, EoEmit emit) {
    HttpHelperPromise promise = new HttpHelperPromise();
    this.executorService().execute(new HttpHelperAsync(url, emit, promise));
    return promise;
  }
}
