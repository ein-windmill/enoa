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
package io.enoa.index.solr.ret;

import io.enoa.toolkit.sys.ObjectKit;

import java.util.List;

public class SResponse<T> implements _Ret {

  private Integer numFound;
  private Long start;
  private List<T> docs;

  public Integer getNumFound() {
    return numFound;
  }

  public void setNumFound(Integer numFound) {
    this.numFound = numFound;
  }

  public Long getStart() {
    return start;
  }

  public void setStart(Long start) {
    this.start = start;
  }

  public List<T> getDocs() {
    return docs;
  }

  public void setDocs(List<T> docs) {
    this.docs = docs;
  }

  @Override
  public String toString() {
    return ObjectKit.buildToString(this);
  }
}
