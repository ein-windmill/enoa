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
package io.enoa.docker.dqp.image;

import io.enoa.docker.dqp.DQP;
import io.enoa.docker.dqp.DQR;
import io.enoa.json.Json;
import io.enoa.toolkit.collection.CollectionKit;

import java.util.ArrayList;
import java.util.List;

public class DQPSearch implements DQP {

  /**
   * string Required
   * <p>
   * Term to search
   */
  private String term;
  /**
   * integer
   * <p>
   * Maximum number of results to return
   */
  private Integer limit;
  /**
   * string
   * <p>
   * A JSON encoded value of the filters (a map[string][]string) to process on the images list. Available filters:
   * <p>
   * is-automated=(true|false)
   * is-official=(true|false)
   * stars=<number> Matches images that has at least 'number' stars.
   */
  private List<String> filters;

  public static DQPSearch create() {
    return new DQPSearch();
  }

  public DQPSearch() {
  }

  public DQPSearch term(String term) {
    this.term = term;
    return this;
  }

  public DQPSearch limit(Integer limit) {
    this.limit = limit;
    return this;
  }

  public DQPSearch filters(String filter) {
    if (this.filters == null)
      this.filters = new ArrayList<>();
    this.filters.add(filter);
    return this;
  }

  public DQPSearch filters(List<String> filters) {
    this.filters = filters;
    return this;
  }

  @Override
  public DQR dqr() {
    DQR dqr = DQR.create()
      .putIf("term", this.term)
      .putIf("limit", this.limit);
    if (CollectionKit.notEmpty(this.filters))
      dqr.put("filters", Json.toJson(this.filters));
    return dqr;
  }
}
