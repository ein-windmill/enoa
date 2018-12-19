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
package io.enoa.chunk;


import java.io.ByteArrayOutputStream;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class ChunkCaller {

  private Chunk chunk;
  private ExecutorService executor;
  private Queue<Byte> queues;
  private AtomicBoolean finish;
  private volatile boolean changed;

  ChunkCaller(Chunk chunk) {
    this.chunk = chunk;
    this.changed = false;

    this.queues = new ConcurrentLinkedDeque<>();
    this.finish = new AtomicBoolean(Boolean.FALSE);
    this.executor = Executors.newSingleThreadExecutor();

    this.listen();
  }

  public void destroy() {
    this.destroy(Boolean.FALSE);
  }

  void destroy(boolean await) {
    while (await && !this.queues.isEmpty()) {
      try {
        TimeUnit.MILLISECONDS.sleep(100);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      break;
    }
    this.finish.set(Boolean.TRUE);
  }

  public void call(byte[] bytes) {
    for (byte b : bytes) {
      this.queues.offer(b);
    }
  }

  private void threadname(Thread thread) {
    String oldname = thread.getName();
    int fix = oldname.indexOf("-"),
      lix = oldname.lastIndexOf("-");

    String group = oldname.substring(fix + 1, lix);
    thread.setName(this.chunk + "-" + group);
  }

  private void listen() {
    this.executor.execute(() -> {
      this.threadname(Thread.currentThread());
      try (ByteArrayOutputStream temp = new ByteArrayOutputStream()) {
        boolean precr = false; //
        while (true) {
          boolean empty = this.queues.isEmpty();
          if (this.chunk.stopper().stop() || (this.finish.get() && this.queues.isEmpty())) {
            break;
          }

          if (empty) {
            this.changed = false;
            continue;
          }

          Byte b = this.queues.poll();
          if (b == '\r' || b == '\n') {
            if (b == '\r') {
              precr = true;
              continue;
            }

            this.call(temp, precr ? "\r\n" : String.valueOf(b));
            if (precr)
              precr = false;
            continue;
          } else {
            if (precr) {
              this.call(temp, "\r");
              precr = false;
            }
          }
          temp.write(b);
          this.changed = true;
        }

        try {
          this.executor.shutdown();
          this.queues.clear();
        } catch (Exception e) {
          e.printStackTrace();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
  }

  private void call(ByteArrayOutputStream temp, String linebreak) {
    byte[] bytes = temp.toByteArray();
    boolean empty = bytes.length == 0;
    if (empty && !this.changed) {
      return;
    }
    this.chunk.runner().run(bytes, linebreak);
    temp.reset();
  }

}
