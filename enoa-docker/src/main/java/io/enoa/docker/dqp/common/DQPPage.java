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
package io.enoa.docker.dqp.common;

import io.enoa.docker.dqp.DQP;
import io.enoa.docker.dqp.DQR;

public class DQPPage<T> implements DQP {

  private Integer pagenumber;
  private Integer pagesize;

  public static <J> DQPPage<J> create() {
    return new DQPPage<>();
  }

  public static <J> DQPPage<J> create(Integer pagesize) {
    return new DQPPage<>(pagesize);
  }

  public DQPPage() {
    this.pagenumber = 1;
    this.pagesize = 15;
  }

  public DQPPage(Integer pagesize) {
    this();
    this.pagesize = pagesize;
  }

  public T pagenumber(Integer pagenumber) {
    this.pagenumber = pagenumber;
    return (T) this;
  }

  public T pagesize(Integer pagesize) {
    this.pagesize = pagesize;
    return (T) this;
  }

  @Override
  public DQR dqr() {
    return DQR.create()
      .put("page", this.pagenumber)
      .put("page_size", this.pagesize);
  }
}
