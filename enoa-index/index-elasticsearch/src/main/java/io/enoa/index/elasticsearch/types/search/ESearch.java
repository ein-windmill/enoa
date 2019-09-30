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

import io.enoa.index.elasticsearch.types.EShards;
import io.enoa.index.elasticsearch.types.error.EError;
import io.enoa.index.elasticsearch.types.error.IEError;

public class ESearch<T> implements IEError<Void> {

  private EError error;
  private Integer status;

  private Integer took;
  private Boolean timedOut;
  private EShards _shards;
  private ESearchHits<T> hits;

  public Integer getTook() {
    return took;
  }

  public ESearch<T> setTook(Integer took) {
    this.took = took;
    return this;
  }

  public Boolean getTimedOut() {
    return timedOut;
  }

  public ESearch<T> setTimedOut(Boolean timedOut) {
    this.timedOut = timedOut;
    return this;
  }

  public EShards get_shards() {
    return _shards;
  }

  public ESearch<T> set_shards(EShards _shards) {
    this._shards = _shards;
    return this;
  }

  public ESearchHits<T> getHits() {
    return hits;
  }

  public ESearch<T> setHits(ESearchHits<T> hits) {
    this.hits = hits;
    return this;
  }

  @Override
  public EError getError() {
    return error;
  }

  public ESearch<T> setError(EError error) {
    this.error = error;
    return this;
  }

  @Override
  public Integer getStatus() {
    return status;
  }

  public ESearch<T> setStatus(Integer status) {
    this.status = status;
    return this;
  }

  @Override
  public String toString() {
    return "ESearch{" +
      "error=" + error +
      ", status=" + status +
      ", took=" + took +
      ", timedOut=" + timedOut +
      ", _shards=" + _shards +
      ", hits=" + hits +
      '}';
  }
}
