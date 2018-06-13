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
package io.enoa.index.solr.result;

import io.enoa.index.solr.report.ISolrReporter;
import io.enoa.promise.builder.PromiseBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

class _DefaultSolrResult<T> implements ISolrResult<T> {

  private T ret;
  private List<ISolrReporter<T>> reporters;

  _DefaultSolrResult(T ret) {
    this.ret = ret;
  }

  private static class Holder0 {
    private static ExecutorService ES = PromiseBuilder.executor().enqueue("EnoaSolr Report Dispatcher");
  }

  @Override
  public ISolrResult<T> report(ISolrReporter<T> reporter) {
    if (this.reporters == null)
      this.reporters = new ArrayList<>();
    this.reporters.add(reporter);
    return this;
  }

  @Override
  public T value() {
    if (this.reporters != null) {
      Holder0.ES.execute(() -> {
        String oldName = Thread.currentThread().getName();
        try {
          for (ISolrReporter<T> reporter : this.reporters) {
            reporter.report(this.ret);
          }
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          //
        }
      });
    }
    return this.ret;
  }
}
