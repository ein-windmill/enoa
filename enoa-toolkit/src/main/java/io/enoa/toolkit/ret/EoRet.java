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
package io.enoa.toolkit.ret;

import io.enoa.toolkit.map.EoMap;

public interface EoRet extends Ret, EoMap<EoRet> {

  static ERetBuilder builder() {
    return new ERetBuilder();
  }

  static ERetBuilder builder(boolean stat) {
    return builder().stat(stat);
  }

  boolean ok();

  boolean fail();

  EoResp resp(boolean fill);

  default EoResp resp() {
    return this.resp(Boolean.TRUE);
  }

}
