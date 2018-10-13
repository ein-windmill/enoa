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
package io.enoa.http.provider.httphelper.async;

import io.enoa.http.EoEmit;
import io.enoa.http.EoExecutor;
import io.enoa.http.EoUrl;
import io.enoa.http.protocol.HttpPromise;
import io.enoa.http.protocol.chunk.Chunk;
import io.enoa.promise.builder.PromiseBuilder;

import java.util.concurrent.ExecutorService;

public class HttpHelperExecutor implements EoExecutor {

  private static class Holder {
    private static final HttpHelperExecutor INSTANCE = new HttpHelperExecutor();
  }

  public static HttpHelperExecutor instance() {
    return Holder.INSTANCE;
  }

  private ExecutorService executorService;

  private ExecutorService executorService() {
    if (executorService == null) {
      executorService = PromiseBuilder.executor().enqueue("HttpHelper Dispatcher", false);
    }
    return executorService;
  }

  @Override
  public HttpPromise enqueue(EoUrl url, EoEmit emit, Chunk chunk) {
    HttpHelperPromiseBuilder hpb = new HttpHelperPromiseBuilder();
    this.executorService().execute(new HttpHelperAsync(url, emit, hpb, chunk));
    return hpb.build();
  }
}
