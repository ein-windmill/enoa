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
package io.enoa.shell;

import io.enoa.chunk.Chunk;

public interface Shell {

  static EPMShell epm() {
    return EPMShell.instance();
  }

  static Shell use() {
    return epm().use();
  }

  static Shell use(String name) {
    return epm().use(name);
  }

  default void command(String command) {
    this.command(command, null);
  }

  default void command(String... commands) {
    this.command(commands, null);
  }

  default void command(String command, Chunk chunk) {
    this.command(new String[]{command}, chunk);
  }

  void command(String[] commands, Chunk chunk);

}
