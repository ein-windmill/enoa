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
package io.enoa.index.solr.ret;

import io.enoa.toolkit.map.Kv;
import io.enoa.toolkit.sys.ObjectKit;

public class SHeader implements _Result {

  private Integer status;
  private Integer QTime;
  private Kv params;

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Integer getQTime() {
    return QTime;
  }

  public void setQTime(Integer QTime) {
    this.QTime = QTime;
  }

  public Kv getParams() {
    return params;
  }

  public void setParams(Kv params) {
    this.params = params;
  }

  @Override
  public String toString() {
    return ObjectKit.buildToString(this);
  }
}
