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
package io.enoa.http;

import io.enoa.http.protocol.HttpResponse;
import io.enoa.http.protocol.chunk.Chunk;
import io.enoa.toolkit.binary.EnoaBinary;
import org.junit.Ignore;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

@Ignore
public class ChunkedTest {

  @Test
  public void testChunked() {
    AtomicInteger ix = new AtomicInteger();

    Chunk chunk = Chunk.builder(bytes -> {
      System.out.print(EnoaBinary.create(bytes).string());
      ix.addAndGet(1);
    })
      .stopper(() -> ix.get() == 10)
      .build();

    HttpResponse response = Http.request(EoUrl.with("http://localhost:2375/containers/nginx/stats"))
      .chunk(chunk);
    System.out.println();
    System.out.println(response.body().string());
  }

}
