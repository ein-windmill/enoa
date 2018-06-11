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
package io.enoa.index.solr.action;

import io.enoa.promise.builder.PromiseBuilder;

import java.util.concurrent.ExecutorService;

public class SActionExecutor {

  private static class Holder0 {
    private static ExecutorService ES = PromiseBuilder.executor().enqueue("Solr select");
  }

  private static class Holder1 {
    private static ExecutorService ES = PromiseBuilder.executor().enqueue("Solr update");
  }

  public static ExecutorService select() {
    return Holder0.ES;
  }

  public static ExecutorService update() {
    return Holder1.ES;
  }

}
