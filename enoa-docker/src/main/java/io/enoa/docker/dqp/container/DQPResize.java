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
package io.enoa.docker.dqp.container;

import io.enoa.docker.dqp.DQP;
import io.enoa.docker.dqp.DPara;

public class DQPResize implements DQP {

  /**
   * w
   * integer
   * <p>
   * Width of the tty session in characters
   */
  private Integer width;
  /**
   * h
   * integer
   * <p>
   * Height of the tty session in characters
   */
  private Integer height;

  public static DQPResize create() {
    return new DQPResize();
  }

  public DQPResize() {
  }

  public DQPResize width(Integer width) {
    this.width = width;
    return this;
  }

  public DQPResize height(Integer height) {
    this.height = height;
    return this;
  }

  @Override
  public DPara para() {
    DPara dqr = DPara.create();
    if (this.height != null)
      dqr.put("h", this.height);
    if (this.width != null)
      dqr.put("w", this.width);
    return dqr;
  }
}
