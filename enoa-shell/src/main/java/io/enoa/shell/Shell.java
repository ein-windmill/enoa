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
import io.enoa.promise.DoneArgPromise;
import io.enoa.shell.ret.ShellResult;

import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ExecutorService;

public interface Shell {

  static Shell use(EoShell shell) {
    return shell.shell();
  }

  static Shell use() {
    return use(EoShell.def());
  }

  static Shell actuator() {
    return use();
  }

  static Shell actuator(String command) {
    return use().command(command);
  }

  static Shell actuator(String... commands) {
    return use().command(commands);
  }

  ShellResult emit();

  DoneArgPromise<ShellResult> enqueue();

  default Shell original() {
    return this.original(Boolean.TRUE);
  }

  Shell original(boolean original);

  Shell executor(ExecutorService executor);

  default Shell command(String command) {
    return this.command(new String[]{command});
  }

  Shell command(String... commands);

  Shell charset(Charset charset);

  Shell chunk(Chunk chunk);

  Shell directory(Path directory);

  Shell env(String name, String value);

  Shell env(Map<String, String> env);
}
