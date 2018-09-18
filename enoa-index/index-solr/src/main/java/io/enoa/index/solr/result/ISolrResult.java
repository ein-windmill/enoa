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
//import io.enoa.index.solr.parser.SParser;
//import io.enoa.index.solr.report.ISolrReporter;
//
//@Deprecated
//public interface ISolrResult<T> {
//
//  static <PS> _DefaultSolrResult<PS> create(HttpResponse response, SParser<PS> parser) {
//    return new _DefaultSolrResult<>(response, parser);
//  }
//
//  @Deprecated
//  ISolrResult<T> report(ISolrReporter reporter);
//
//  @Deprecated
//  T value();
//
//}
