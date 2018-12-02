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

import java.io.Serializable;

public class Chunk implements Serializable {

  private IChunkRunner<byte[]> runner;
  private IChunkStopper stopper;
  private ChunkCaller caller;

  public static Chunk.Builder builder(IChunkRunner<byte[]> runner) {
    return new Builder(runner);
  }

  private Chunk(Builder builder) {
    this.runner = builder.runner;
    this.stopper = builder.stopper;
    this.caller = new ChunkCaller(this);
  }

  public ChunkCaller caller() {
    return this.caller;
  }

  public IChunkRunner<byte[]> runner() {
    return runner;
  }

  public IChunkStopper stopper() {
    return stopper;
  }

  public static class Builder {
    private IChunkRunner<byte[]> runner;
    private IChunkStopper stopper;

    public Builder() {
    }

    public Builder(IChunkRunner<byte[]> runner) {
      this.runner = runner;
    }

    public Chunk build() {
      return new Chunk(this);
    }

    public Builder runner(IChunkRunner<byte[]> runner) {
      this.runner = runner;
      return this;
    }

    public Builder stopper(IChunkStopper stopper) {
      this.stopper = stopper;
      return this;
    }
  }

}
