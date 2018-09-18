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
package io.enoa.stove.template;

import io.enoa.stove.template.command.StoveCommand;

import java.util.HashMap;
import java.util.Map;

public class SPM {

  private Map<String, StoveCommand> pkg = new HashMap<>();

  public SPM install(StoveCommand cmd) {
    if (this.pkg.containsKey(cmd.name()))
      throw new IllegalArgumentException("Alreay exists this command.");
    this.pkg.put(cmd.name(), cmd);
    return this;
  }

  public StoveCommand command(String name) {
    return this.pkg.get(name);
  }

  public boolean exists(String name) {
    return this.pkg.containsKey(name);
  }

}
