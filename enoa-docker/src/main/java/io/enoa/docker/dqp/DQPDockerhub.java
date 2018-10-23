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
package io.enoa.docker.dqp;

import io.enoa.docker.dqp.common.DQPPage;
import io.enoa.docker.dqp.dockerhub.DQPSearch;

public class DQPDockerhub {

  private static class Holder {
    private static final DQPDockerhub INSTANCE = new DQPDockerhub();
  }

  static DQPDockerhub instance() {
    return Holder.INSTANCE;
  }

  private DQPDockerhub() {

  }

  public DQPSearch search() {
    return DQPSearch.create();
  }

  public DQPPage page() {
    return DQPPage.create();
  }

  public DQPPage page(Integer pagesize) {
    return DQPPage.create();
  }

}
