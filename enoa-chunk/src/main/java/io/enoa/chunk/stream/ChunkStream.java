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

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public interface ChunkStream {

  static <RET> ChunkStream generic(ChunkConverter<RET> converter, IChunkRunner<RET> runner, IChunkStopper stopper) {
    return new CSGenericChunkStream<>(converter, runner, stopper);
  }

  static <RET> ChunkStream generic(ChunkConverter<RET> converter, IChunkRunner<RET> runner) {
    return generic(converter, runner, IChunkStopper.def());
  }

  static ChunkStream string(IChunkRunner<String> runner, Charset charset, IChunkStopper stopper) {
    return new CSStringChunkStream(runner, charset, stopper);
  }

  static ChunkStream string(IChunkRunner<String> runner, Charset charset) {
    return string(runner, charset, IChunkStopper.def());
  }

  static ChunkStream string(IChunkRunner<String> runner) {
    return string(runner, StandardCharsets.UTF_8, IChunkStopper.def());
  }

  Chunk chunk();

}
