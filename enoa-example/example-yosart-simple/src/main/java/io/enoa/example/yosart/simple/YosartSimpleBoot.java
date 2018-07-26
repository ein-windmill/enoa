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
package io.enoa.example.yosart.simple;

import io.enoa.repeater.http.Method;
import io.enoa.yosart.Yosart;
import io.enoa.yosart.resp.Resp;

public class YosartSimpleBoot {

  private void start() {
    Yosart.createServer()
      .handle(Method.GET, "/", (req) -> Resp.with(req).render("yosart").end())
      .listen();
  }

  public static void main(String[] args) {
    YosartSimpleBoot boot = new YosartSimpleBoot();
    boot.start();
  }

}
