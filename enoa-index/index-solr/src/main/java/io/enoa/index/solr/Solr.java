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
package io.enoa.index.solr;

import io.enoa.http.EoHttp;

public class Solr {

  public static EPMSolr epm() {
    return EPMSolr.instance();
  }

  public static EoSolr use(String name) {
    SolrConfig config = epm().config(name);
    return new EnoaSolrImpl(config);
  }

  public static EoSolr use() {
    return use("main");
  }

  public static EoSolr http(EoHttp http) {
    return use().http(http);
  }

  public static EoSolrOperate core(String core) {
    return use().core(core);
  }


}
