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
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;

public class Chunk implements Serializable {

  private IChunkRunner<byte[]> runner;
  private IChunkStopper stopper;
  private ChunkCaller caller;

  public static Chunk.Builder builder(IChunkRunner<byte[]> runner) {
    return new Builder(runner);
  }

  public static <RET> Chunk generic(IChunkConverter<RET> converter, IChunkRunner<RET> runner, IChunkStopper stopper) {
    return builder(bytes -> runner.run(converter.convert(bytes))).stopper(stopper).build();
  }

  public static <RET> Chunk generic(IChunkConverter<RET> converter, IChunkRunner<RET> runner) {
    return generic(converter, runner, IChunkStopper.def());
  }

  public static Chunk string(IChunkRunner<String> runner, Charset charset, IChunkStopper stopper) {
    return builder(bytes -> runner.run(string(bytes, charset))).stopper(stopper).build();
  }

  public static Chunk string(IChunkRunner<String> runner, Charset charset) {
    return string(runner, charset, IChunkStopper.def());
  }

  public static Chunk string(IChunkRunner<String> runner) {
    return string(runner, StandardCharsets.UTF_8, IChunkStopper.def());
  }

  private static String string(byte[] bytes, Charset charset) {
    CharsetDecoder decoder = charset.newDecoder();
    ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
    CharBuffer charBuffer = CharBuffer.allocate(byteBuffer.limit());
    decoder.decode(byteBuffer, charBuffer, true);
    charBuffer.flip();
    String ret = charBuffer.toString();
    charBuffer.clear();
    byteBuffer.clear();
    return ret;
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
