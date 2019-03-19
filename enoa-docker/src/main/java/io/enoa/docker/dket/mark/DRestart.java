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
package io.enoa.docker.dket.mark;

import io.enoa.toolkit.mark.IMarkVal;
import io.enoa.toolkit.text.TextKit;

public enum DRestart implements IMarkVal {

  NO("no"),
  on_failure("on-failure"),
  always("always"),
  //
  ;

  private final String val;

  DRestart(String val) {
    this.val = val;
  }

  @Override
  public String val() {
    return val;
  }

  public static DRestart of(String val) {
    if (TextKit.blanky(val))
      return NO;
    for (DRestart restart : DRestart.values()) {
      if (restart.val.equalsIgnoreCase(val))
        return restart;
    }
    return NO;
  }
}
