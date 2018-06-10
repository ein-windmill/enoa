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
package io.enoa.http.provider.httphelper;

import io.enoa.http.protocol.enoa.HttpHandler;
import io.enoa.http.protocol.enoa.HttpRequest;
import io.enoa.promise.builder.PromiseBuilder;

import java.util.List;
import java.util.concurrent.ExecutorService;

class HttpHandlerExecutor {

  private static class Holder {
    private static final HttpHandlerExecutor INSTANCE = new HttpHandlerExecutor();
  }

  static HttpHandlerExecutor instance() {
    return Holder.INSTANCE;
  }

  private ExecutorService executorService;

  private ExecutorService executorService() {
    if (executorService == null) {
      executorService = PromiseBuilder.executor().enqueue("HttpHelper Handler Dispatcher", false);
    }
    return executorService;
  }

  void handle(List<HttpHandler> handlers, HttpRequest request) {
    this.executorService().execute(() -> {
      String oldName = Thread.currentThread().getName();
      Thread.currentThread().setName(request.url().substring(0, request.url().length() < 70 ? request.url().length() : 70).concat("..."));
      try {
        handlers.forEach(handler -> handler.handle(request));
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        Thread.currentThread().setName(oldName);
      }
    });
  }

}
