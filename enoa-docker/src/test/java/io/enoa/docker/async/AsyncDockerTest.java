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
package io.enoa.docker.async;

import io.enoa.docker.AbstractDockerTest;
import io.enoa.docker.Docker;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class AsyncDockerTest extends AbstractDockerTest {

  private void sleep() {
    try {
      TimeUnit.SECONDS.sleep(3L);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void info() {
    Docker.async()
      .origin()
      .info()
      .enqueue()
      .done(resp -> {
        System.out.println(resp.string());
      });
    this.sleep();
  }

}
