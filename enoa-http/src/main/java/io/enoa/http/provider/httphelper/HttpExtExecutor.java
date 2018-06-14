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

import io.enoa.http.protocol.HttpResponse;
import io.enoa.http.protocol.enoa.HttpRequest;
import io.enoa.http.protocol.enoa.IHttpHandler;
import io.enoa.http.protocol.enoa.IHttpReporter;
import io.enoa.promise.builder.PromiseBuilder;

import java.util.List;
import java.util.concurrent.ExecutorService;

class HttpExtExecutor {

  private static class Holder {
    private static final HttpExtExecutor INSTANCE = new HttpExtExecutor();
  }

  static HttpExtExecutor instance() {
    return Holder.INSTANCE;
  }

  private ExecutorService executorService;

  private ExecutorService executorService() {
    if (executorService == null) {
      executorService = PromiseBuilder.executor().enqueue("HttpHelper Ext Dispatcher", false);
    }
    return executorService;
  }

  void handle(List<IHttpHandler> handlers, HttpRequest request) {
    if (handlers == null)
      return;
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

  void report(List<IHttpReporter> reporters, HttpResponse response) {
    if (reporters == null)
      return;
    this.executorService().execute(() -> {
      String oldName = Thread.currentThread().getName();
      Thread.currentThread().setName(response.url().substring(0, response.url().length() < 70 ? response.url().length() : 70).concat("..."));
      try {
        reporters.forEach(reporter -> reporter.report(response));
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        Thread.currentThread().setName(oldName);
      }
    });
  }

}
