/*
 * Copyright 2016 ikidou
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
package io.enoa.index.elasticsearch.edat;

import io.enoa.http.protocol.HttpResponse;
import io.enoa.http.protocol.HttpResponseBody;
import io.enoa.index.elasticsearch.ElasticsearchConfig;
import io.enoa.index.elasticsearch.types.bulk.EBulk;
import io.enoa.index.elasticsearch.types.search.ESearch;
import io.enoa.json.EnoaJson;
import io.enoa.toolkit.is.Is;
import io.enoa.typebuilder.TypeBuilder;

import java.lang.reflect.Type;
import java.util.Optional;

public class Edat {

  private ElasticsearchConfig config;
  private HttpResponse response;

  public Edat(ElasticsearchConfig config, HttpResponse response) {
    this.config = config;
    this.response = response;
  }

}
