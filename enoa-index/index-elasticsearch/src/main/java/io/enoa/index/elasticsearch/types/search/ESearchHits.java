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
package io.enoa.index.elasticsearch.types.search;

import java.util.List;

public class ESearchHits<T> {
  private ETotal total;
  private Double maxScore;
  private List<EHits<T>> hits;

  public ETotal getTotal() {
    return total;
  }

  public ESearchHits<T> setTotal(ETotal total) {
    this.total = total;
    return this;
  }

  public Double getMaxScore() {
    return maxScore;
  }

  public ESearchHits<T> setMaxScore(Double maxScore) {
    this.maxScore = maxScore;
    return this;
  }

  public List<EHits<T>> getHits() {
    return hits;
  }

  public ESearchHits<T> setHits(List<EHits<T>> hits) {
    this.hits = hits;
    return this;
  }

  @Override
  public String toString() {
    return "ESearchHits{" +
      "total=" + total +
      ", maxScore=" + maxScore +
      ", hits=" + hits +
      '}';
  }
}
