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

public interface ISolrResult<T> {

  static <DT> ISolrResult<DT> create(DT ret) {
    return new _DefaultSolrResult<>(ret);
  }

  ISolrResult<T> report(ISolrReporter<T> reporter);

  T value();

}
