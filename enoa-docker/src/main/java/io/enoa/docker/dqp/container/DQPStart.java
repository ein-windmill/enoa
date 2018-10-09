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
import io.enoa.toolkit.text.TextKit;

public class DQPStart implements DQP {

  /**
   * detachKeys
   * string
   * <p>
   * Override the key sequence for detaching a container. Format is a single character [a-Z] or ctrl-<value> where <value> is one of: a-z, @, ^, [, , or _.
   */
  private String detachkeys;

  public static DQPStart create() {
    return new DQPStart();
  }

  public DQPStart() {
  }

  public DQPStart detachkeys(String detachkeys) {
    this.detachkeys = detachkeys;
    return this;
  }

  @Override
  public DPara para() {
    DPara dqr = DPara.create();
    if (TextKit.blankn(this.detachkeys))
      dqr.put("detachKeys", this.detachkeys);
    return dqr;
  }
}
