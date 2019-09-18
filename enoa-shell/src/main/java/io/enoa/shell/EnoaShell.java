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
import io.enoa.promise.Promise;
import io.enoa.promise.arg.PromiseArg;
import io.enoa.promise.arg.PromiseCapture;
import io.enoa.promise.arg.PromiseVoid;
import io.enoa.promise.builder.EPDoneArgPromiseBuilder;
import io.enoa.shell.reader.EShellReader;
import io.enoa.shell.ret.ShellResult;
import io.enoa.toolkit.EoConst;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.is.Is;
import io.enoa.toolkit.sys.EnvKit;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;

class EnoaShell implements Shell {

  private List<String> commands;
  private Chunk chunk;
  private Path directory;
  private Map<String, String> env;
  private Charset charset;
  private ExecutorService executor;
  private boolean original;

  EnoaShell() {
    this.commands = new ArrayList<>();
    this.charset = EoConst._CHARSET_OS;
    this.original = Boolean.FALSE;
  }

  private static class ExecutorHolder {
    private static final ExecutorService EXECUTOR_SERVICE = Promise.builder().executor().enqueue("shell-enqueue");
  }

  private ExecutorService executor() {
    return this.executor == null ? ExecutorHolder.EXECUTOR_SERVICE : this.executor;
  }

  private List<String> commands() {
    if (Is.empty(this.commands))
      return this.commands;

    if (this.original)
      return this.commands;

    String os = EnvKit.string("os.name");
    if (Is.not().truthy(os))
      return this.commands;

    os = os.toLowerCase();
    if (!os.contains("win"))
      return this.commands;

    String first = this.commands.get(0);
    if (first.contains("cmd") || first.contains("command"))
      return this.commands;

    boolean oldwin = os.contains("95");
    List<String> cmds = new ArrayList<>(this.commands.size() + 2);
    cmds.add(oldwin ? "command.com" : "cmd.exe");
    cmds.add("/c");
    cmds.addAll(this.commands);
    CollectionKit.clear(this.commands);
    this.commands = cmds;
    return this.commands;
  }

  @Override
  public ShellResult emit() {
    try {
      CyclicBarrier barrier = new CyclicBarrier(2);
      List<String> commands = this.commands();
      if (Is.empty(commands))
        return ShellResult.create(CollectionKit.emptyArray(String.class), -1, new byte[0]);

      ProcessBuilder builder = new ProcessBuilder(commands)
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
        String[] cmds = commands.toArray(new String[commands.size()]);
        return ShellResult.create(cmds, exitvalue, bytes, this.charset);
      }
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage(), e);
    } finally {
      CollectionKit.clear(this.commands);
      CollectionKit.clear(this.env);
    }
  }

  @Override
  public DoneArgPromise<ShellResult> enqueue() {
    EPDoneArgPromiseBuilder<ShellResult> builder = Promise.builder().donearg();
    this.executor().execute(() -> {
      try {
        ShellResult ret = this.emit();
        List<PromiseArg<ShellResult>> dones = builder.dones();
        if (Is.not().empty(dones)) {
          dones.forEach(done -> done.execute(ret));
        }
      } catch (Exception e) {
        List<PromiseCapture> captures = builder.captures();
        if (Is.array(captures)) {
          e.printStackTrace();
          return;
        }
        captures.forEach(capture -> capture.execute(e));
      } finally {
        PromiseVoid always = builder.always();
        if (always != null)
          always.execute();
      }
    });
    return builder.build();
  }

  @Override
  public Shell original(boolean original) {
    this.original = original;
    return this;
  }

  @Override
  public Shell executor(ExecutorService executor) {
    this.executor = executor;
    return this;
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
