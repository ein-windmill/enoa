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
package io.enoa.http.provider.httphelper.http.resp;

import io.enoa.http.protocol.chunk.Chunk;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

class ChunkCaller {

  private Charset charset;
  private Chunk chunk;
  private ExecutorService executor;
  private Queue<Byte> queues;
  private AtomicBoolean finish;

  ChunkCaller(Chunk chunk, Charset charset) {
    this.chunk = chunk;
    this.charset = charset;
    this.queues = new ConcurrentLinkedDeque<>();
    this.finish = new AtomicBoolean(Boolean.FALSE);
    this.executor = Executors.newSingleThreadExecutor();
    this.run();
  }

  void destroy() {
    this.finish.set(Boolean.TRUE);
  }

  void call(byte[] bytes) {
    for (byte b : bytes) {
      this.queues.offer(b);
    }
  }

  private void threadname(Thread thread) {
    String oldname = thread.getName();
    int fix = oldname.indexOf("-"),
      lix = oldname.lastIndexOf("-");

    String group = oldname.substring(fix + 1, lix);
    thread.setName(chunk + "-" + group);
  }

  private void run() {
    this.executor.execute(() -> {
      this.threadname(Thread.currentThread());
      try (ByteArrayOutputStream temp = new ByteArrayOutputStream()) {
        while (true) {
          if (this.finish.get() || this.chunk.stopper().stop()) {
            try {
              this.executor.shutdown();
              this.queues.clear();
            } catch (Exception e) {
              e.printStackTrace();
            }
            break;
          }

          if (this.queues.isEmpty())
            continue;

          Byte b = this.queues.poll();
          if (b == '\r' || b == '\n') {
            byte[] bytes = temp.toByteArray();
//            if (bytes.length > 0)
            this.chunk.runner().run(bytes);
            temp.reset();
            continue;
          }
          temp.write(b);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
  }

}
