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

public class EHits<T> {

  private String _index;
  private String _type;
  private String _id;
  private Double _score;
  private T _source;

  public String get_index() {
    return _index;
  }

  public EHits<T> set_index(String _index) {
    this._index = _index;
    return this;
  }

  public String get_type() {
    return _type;
  }

  public EHits<T> set_type(String _type) {
    this._type = _type;
    return this;
  }

  public String get_id() {
    return _id;
  }

  public EHits<T> set_id(String _id) {
    this._id = _id;
    return this;
  }

  public Double get_score() {
    return _score;
  }

  public EHits<T> set_score(Double _score) {
    this._score = _score;
    return this;
  }

  public T get_source() {
    return _source;
  }

  public EHits<T> set_source(T _source) {
    this._source = _source;
    return this;
  }

  @Override
  public String toString() {
    return "EHits{" +
      "_index='" + _index + '\'' +
      ", _type='" + _type + '\'' +
      ", _id='" + _id + '\'' +
      ", _score=" + _score +
      ", _source=" + _source +
      '}';
  }
}
