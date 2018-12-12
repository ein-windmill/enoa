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
import io.enoa.shell.reader.EShellReader;
import io.enoa.shell.ret.ShellResult;
import io.enoa.toolkit.EoConst;
import io.enoa.toolkit.collection.CollectionKit;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.CyclicBarrier;

class EnoaShell implements Shell {


  private List<String> commands;
  private Chunk chunk;
  private Path directory;
  private Map<String, String> env;
  private Charset charset;

  EnoaShell() {
    this.commands = new ArrayList<>();
    this.charset = EoConst._CHARSET_OS;
  }

  @Override
  public ShellResult emit() {
    try {
      CyclicBarrier barrier = new CyclicBarrier(2);
      ProcessBuilder builder = new ProcessBuilder(this.commands)
        .redirectErrorStream(true);
      if (this.directory != null) {
        builder.directory(this.directory.toFile());
      }
      if (this.env != null) {
        builder.environment().putAll(this.env);
      }
      Process process = builder.start();

      try (InputStream is = process.getInputStream()) {
        EShellReader outreader = new EShellReader(is, this.chunk, barrier);
        Thread outhread = new Thread(outreader);
        outhread.start();

        barrier.await();
        int exitvalue = process.waitFor();
        byte[] bytes = outreader.bytes();
        outhread.interrupt();
        return ShellResult.create(exitvalue, bytes, this.charset);
      }
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage(), e);
    } finally {
      CollectionKit.clear(this.commands);
      CollectionKit.clear(this.env);
    }
  }

  @Override
  public Shell command(String... commands) {
    if (commands == null)
      return this;
    this.commands.addAll(Arrays.asList(commands));
    return this;
  }

  @Override
  public Shell charset(Charset charset) {
    this.charset = charset;
    return this;
  }

  @Override
  public Shell chunk(Chunk chunk) {
    this.chunk = chunk;
    return this;
  }

  @Override
  public Shell directory(Path directory) {
    this.directory = directory;
    return this;
  }

  @Override
  public Shell env(String name, String value) {
    if (this.env == null)
      this.env = new HashMap<>();
    this.env.put(name, value);
    return this;
  }

  @Override
  public Shell env(Map<String, String> env) {
    this.env = env;
    return this;
  }

}
