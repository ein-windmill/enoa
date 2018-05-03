/*
 * Copyright (c) 2018, enoa (ein.windmill@outlook.com)
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
package io.enoa.example.stove;

import io.enoa.stove.template.Stove;
import io.enoa.stove.template.StoveConfig;
import io.enoa.toolkit.path.PathKit;

public class StoveExample {

  private StoveConfig conf0() {
    return new StoveConfig.Builder()
      .debug()
      .path(PathKit.bootPath().resolve("src/main/resources/template"))
//      .suffix("html")
      .build();
  }

  private void start() {
    Stove stove = Stove.engine(this.conf0());

//    String body0 = stove.template("tpl0.html").render();
//    System.out.println(body0);

    String body1 = stove.template("tpl1.sql").render();
//    System.out.println(body1);
  }

  public static void main(String[] args) {
    StoveExample stove = new StoveExample();
    stove.start();
  }

}
