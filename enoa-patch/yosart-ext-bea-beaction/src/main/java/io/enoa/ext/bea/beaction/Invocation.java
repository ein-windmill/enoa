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
package io.enoa.ext.bea.beaction;

import io.enoa.yosart.kernel.http.YoRequest;
import io.enoa.yosart.resp.Resp;

public class Invocation {

  private int next;
  private YoRequest req;
  private Resp resp;

  Invocation(YoRequest req, Resp resp) {
    this.next = 0;
    this.req = req;
    this.resp = resp;
  }

  boolean nextIor() {
    return this.next == 1;
  }

  public YoRequest req() {
    return this.req;
  }

  public Resp resp() {
    return this.resp;
  }

  public void next() {
    this.next = 1;
  }

}
