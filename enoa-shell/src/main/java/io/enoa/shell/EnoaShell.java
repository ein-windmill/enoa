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

import java.nio.file.Paths;
import java.util.concurrent.*;

public class EnoaShell implements Shell {

  private static ExecutorService executor = new ThreadPoolExecutor(
    0,
    Integer.MAX_VALUE,
    3L,
    TimeUnit.SECONDS,
    new SynchronousQueue<>()
  );


  @Override
  public void command(String[] commands, Chunk chunk) {
    try {

      CyclicBarrier barrier = new CyclicBarrier(2);
      Process process = new ProcessBuilder(commands)
//        .inheritIO()
//        .directory(Paths.get("D:\\dev\\enoa\\enoa\\enoa-shell").toFile())
        .redirectErrorStream(true)
        .start();
      EShellReader outreader = new EShellReader(process.getInputStream(), chunk, barrier);
      Thread outhread = new Thread(outreader);
      outhread.start();

      barrier.await();

      Future<Integer> executeFuture = executor.submit((Callable<Integer>) process::waitFor);
      int exitCode = executeFuture.get(5, TimeUnit.SECONDS);
//      int exitCode = executeFuture.get();
//      System.out.println(exitCode);
//      String a = new String(outreader.bytes(), Charset.forName("BIG5"));
//      System.out.println(a);

      outhread.interrupt();
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

}
