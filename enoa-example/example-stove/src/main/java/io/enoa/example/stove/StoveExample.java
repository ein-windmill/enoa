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
import io.enoa.toolkit.map.Kv;
import io.enoa.toolkit.path.PathKit;

import java.util.ArrayList;

public class StoveExample {

  private StoveConfig conf0() {
    return new StoveConfig.Builder()
      .debug()
      .path(PathKit.debugPath().resolve("src/main/resources/template"))
//      .suffix("html")
      .build();
  }

  private void start() {
    Stove stove = Stove.engine(this.conf0());

    String body0 = stove.template("tpl0.html").render(
      Kv.by("title", "Test Tile")
        .set("user", Kv.by("name", "jack")
          .set("age", 10)
          .set("inf", Kv.by("gender", "male")
            .set("h", 999)
            .set("sym", "LM")))
        .set("co0", true)
        .set("co1", false)
        .set("arr", new ArrayList<Kv>() {{
          add(Kv.by("u0", Kv.by("A 0", new ArrayList<Kv>() {{
            add(Kv.by("who", new Who("Tom", "Tom Real", "Tmo")));
            add(Kv.by("who", new Who("Mnti", "Mnti Real", "Mni")));
            add(Kv.by("who", new Who("Joim", "Joim Real", "Jom")));
            add(Kv.by("who", new Who("Ajki", "Ajki Real", "Aji")));
            add(Kv.by("who", new Who("Homn", "Homn Real", "Hmn")));
          }})));
          add(Kv.by("u0", Kv.by("A 0", new ArrayList<Kv>() {{
            add(Kv.by("who", new Who("Jack", "Jack Real", "Jck")));
            add(Kv.by("who", new Who("Mark", "Mark Real", "Mak")));
            add(Kv.by("who", new Who("Dunk", null, "Duk")));
            add(Kv.by("who", new Who("Juni", "Juni Real", "Jui")));
            add(Kv.by("who", new Who("Zark", "Zark Real", "Zak")));
          }})));
        }})
    );
    System.out.println(body0);

//    String body1 = stove.template("tpl1.sql").render();
//    System.out.println(body1);
  }


  public static class Who {
    private String name;
    private String real;
    private String nick;

    public Who(String name, String real, String nick) {
      this.name = name;
      this.real = real;
      this.nick = nick;
    }

    public String name() {
      return this.name;
    }

    public String getReal() {
      return this.real;
    }

    public String nick() {
      return this.nick;
    }

    @Override
    public String toString() {
      return "Who{" +
        "name='" + name + '\'' +
        ", real='" + real + '\'' +
        ", nick='" + nick + '\'' +
        '}';
    }
  }

  public static void main(String[] args) {
    StoveExample stove = new StoveExample();
    stove.start();
  }

}
