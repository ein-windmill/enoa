///*
// * Copyright (c) 2018, enoa (fewensa@enoa.io)
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *     http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package io.enoa.index.solr.result;
//
//import io.enoa.http.protocol.HttpResponse;
//import io.enoa.index.solr.action.SActionExecutor;
//import io.enoa.index.solr.parser.SParser;
//import io.enoa.index.solr.report.ISolrReporter;
//
//import java.util.ArrayList;
//import java.util.List;
//
//class _DefaultSolrResult<T> implements ISolrResult {
//
//  private SParser<T> parser;
//  private HttpResponse response;
//  private List<ISolrReporter> reporters;
//
//  _DefaultSolrResult(HttpResponse response, SParser<T> parser) {
//    this.response = response;
//    this.parser = parser;
//  }
//
//  @Override
//  public ISolrResult report(ISolrReporter reporter) {
//    if (this.reporters == null)
//      this.reporters = new ArrayList<>();
//    this.reporters.add(reporter);
//    return this;
//  }
//
//  @Override
//  public T value() {
//    if (this.reporters != null) {
//      SActionExecutor.reporter().execute(() -> {
//        String oldName = Thread.currentThread().getName();
//        try {
//          for (ISolrReporter reporter : this.reporters) {
//            reporter.report(this.response);
//          }
//        } catch (Exception e) {
//          e.printStackTrace();
//        } finally {
//          //
//        }
//      });
//    }
//    return this.parser.result(this.response);
//  }
//
//}
