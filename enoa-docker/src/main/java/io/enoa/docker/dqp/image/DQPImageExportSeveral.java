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

import java.util.ArrayList;
import java.util.List;

public class DQPImageExportSeveral implements DQP {

  /**
   * Comma Separated array of string
   *
   * Image names to filter by
   */
  private List<String> names;

  public static DQPImageExportSeveral create() {
    return new DQPImageExportSeveral();
  }

  public DQPImageExportSeveral names(String name) {
    if (this.names == null)
      this.names = new ArrayList<>();
    this.names.add(name);
    return this;
  }

  public DQPImageExportSeveral names(List<String> names) {
    this.names = names;
    return this;
  }

  @Override
  public DQR dqr() {
    DQR dqr = DQR.create()
      .put("names", this.names);
    return dqr;
  }
}
