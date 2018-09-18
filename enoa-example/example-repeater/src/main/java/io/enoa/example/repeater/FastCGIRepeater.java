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
package io.enoa.example.repeater;

import io.enoa.repeater.Repeater;
import io.enoa.repeater.provider.fastcgi.server.FastCGIPrivoder;

public class FastCGIRepeater extends AbstractRepeaterExample {

  public static void main(String[] args) {
    FastCGIRepeater repeater = new FastCGIRepeater();
    repeater.start();
  }

  @Override
  protected void start() {
    Repeater.createServer(new FastCGIPrivoder())
      .accessor(super.accessor())
      .log(super.log())
      .config(super.config())
      .listen(PORT);
  }
}
