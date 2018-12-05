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
import io.enoa.chunk.ChunkCaller;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.concurrent.CyclicBarrier;

class EShellReader implements Runnable {

  private static final byte[] EMPTY = new byte[0];

  private final InputStream stream;
  private final Chunk chunk;

  private volatile boolean stopped;
  private ByteArrayOutputStream baos;
  private CyclicBarrier barrier;

  EShellReader(InputStream stream, Chunk chunk, CyclicBarrier barrier) {
    this.stream = stream;
    this.stopped = false;
    this.chunk = chunk;
    this.barrier = barrier;
  }

  @Override
  public void run() {
    this.baos = new ByteArrayOutputStream();
    try (BufferedInputStream bis = new BufferedInputStream(this.stream)) {
      ChunkCaller caller = this.chunk == null ? null : this.chunk.caller();
      byte[] buff = new byte[2048];
      int rc;
      while ((rc = bis.read(buff)) != -1) {
        if (caller != null) {
          caller.call(Arrays.copyOf(buff, rc));
        }
        this.baos.write(buff, 0, rc);
      }

      if (caller != null)
        caller.destroy();

      this.barrier.await();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.stopped = true;
      synchronized (this) {
        this.notify();
      }
    }
  }

  public byte[] bytes() {
    if (!this.stopped) {
      synchronized (this) {
        try {
          this.wait();
        } catch (InterruptedException ignore) {
        }
      }
    }
    return this.baos == null ? EMPTY : this.baos.toByteArray();
  }

}
