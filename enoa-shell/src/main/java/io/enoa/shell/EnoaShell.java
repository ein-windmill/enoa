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

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.concurrent.*;

public class EnoaShell implements Shell {

  private static ExecutorService executor = new ThreadPoolExecutor(
    0,
    Integer.MAX_VALUE,
    3L,
    TimeUnit.SECONDS,
    new SynchronousQueue<>());


  @Override
  public void command(String command, Chunk chunk) {
    try {
      final Process process = Runtime.getRuntime().exec(command);
      process.getOutputStream().close();
      try (InputStream in = process.getInputStream();
           InputStream err = process.getErrorStream()) {

        EShellReader outreader = new EShellReader(in, chunk);
        EShellReader errreader = new EShellReader(err, chunk);
        Thread outthread = new Thread(outreader);
        Thread errthread = new Thread(errreader);
        outthread.start();
        errthread.start();

        Future<Integer> executeFuture = executor.submit(() -> {
          process.waitFor();
          return process.exitValue();
        });
        int exitCode = executeFuture.get(5000, TimeUnit.MILLISECONDS);


        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println(exitCode);
        System.out.println(new String(outreader.bytes(), Charset.forName("BIG5")));
        System.out.println(new String(errreader.bytes(), Charset.forName("BIG5")));

        outthread.interrupt();
        errthread.interrupt();
      }

    } catch (Exception e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

}
