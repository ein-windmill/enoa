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
package io.enoa.index.solr.action;

import io.enoa.http.protocol.HttpResponse;
import io.enoa.index.solr.parser.SParser;
import io.enoa.promise.DoneArgPromise;
import io.enoa.promise.builder.EPDoneArgPromiseBuilder;
import io.enoa.promise.Promise;
import io.enoa.toolkit.collection.CollectionKit;

public interface _SolrAction {

  default HttpResponse emit() {
    return this.emit(resp -> resp);
  }

  default DoneArgPromise<HttpResponse> enqueue() {
    return this.enqueue(resp -> resp);
  }

  <T> T emit(SParser<T> parser);

  default <T> DoneArgPromise<T> enqueue(SParser<T> parser) {
    EPDoneArgPromiseBuilder<T> builder = Promise.builder().donearg();
    SActionExecutor.select().execute(() -> {
      try {
        T ret = this.emit(parser);

        if (CollectionKit.isEmpty(builder.dones()))
          return;
        builder.dones().forEach(done -> done.execute(ret));
      } catch (Exception e) {
        if (CollectionKit.isEmpty(builder.captures()))
          return;
        builder.captures().forEach(capture -> capture.execute(e));
      } finally {
        if (builder.always() != null)
          builder.always().execute();
      }
    });
    return builder.build();
  }


}
