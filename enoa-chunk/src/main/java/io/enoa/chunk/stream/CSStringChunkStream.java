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
package io.enoa.chunk.stream;

import io.enoa.chunk.Chunk;
import io.enoa.chunk.IChunkRunner;
import io.enoa.chunk.IChunkStopper;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

class CSStringChunkStream implements ChunkStream {

  private final IChunkRunner<String> runner;
  private final IChunkStopper stopper;
  private final Charset charset;

  CSStringChunkStream(IChunkRunner<String> runner, Charset charset, IChunkStopper stopper) {
    this.runner = runner;
    this.stopper = stopper;
    this.charset = charset;
  }

  @Override
  public Chunk chunk() {
    return ChunkStream.generic(
      bytes -> this.string(bytes, this.charset),
      this.runner,
      this.stopper
    ).chunk();
  }

  private String string(byte[] bytes, Charset charset) {
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

}
