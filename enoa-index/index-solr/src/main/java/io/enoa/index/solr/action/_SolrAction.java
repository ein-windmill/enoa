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

import io.enoa.index.solr.parser.OriginParser;
import io.enoa.index.solr.parser.SParser;
import io.enoa.promise.DoneArgPromise;
import io.enoa.promise.builder.EGraenodPromiseBuilder;
import io.enoa.promise.builder.PromiseBuilder;
import io.enoa.toolkit.collection.CollectionKit;

public interface _SolrAction {

  default String emit() {
    return this.emit(OriginParser.create());
  }

  default DoneArgPromise enqueue() {
    return this.enqueue(OriginParser.create());
  }

  <T> T emit(SParser<T> parser);

  default <T> DoneArgPromise enqueue(SParser<T> parser) {
    EGraenodPromiseBuilder donearg = PromiseBuilder.donearg();
    SActionExecutor.select().execute(() -> {
      try {
        T ret = this.emit(parser);
        if (CollectionKit.isEmpty(donearg.dones()))
          return;
        donearg.dones().forEach(done -> done.execute(ret));
      } catch (Exception e) {
        if (CollectionKit.isEmpty(donearg.captures()))
          return;
        donearg.captures().forEach(capture -> capture.execute(e));
      } finally {
        if (donearg.always() != null)
          donearg.always().execute();
      }
    });
    return donearg.build();
  }


}
