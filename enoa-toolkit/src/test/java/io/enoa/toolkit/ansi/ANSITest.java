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
package io.enoa.toolkit.ansi;

import org.junit.Test;

public class ANSITest {

  private ANSI ansi() {
    return ANSI.up()
      .append("normal")
      .newline()
      .underline("underline", ANSI.Color.GREEN)
      .lf()
      .bold("bold", ANSI.Color.BLACK, ANSI.Background.YELLOW)
      .lf()
      .red("red")
      .lf()
      .green("green")
      .lf()
      .blue("blue")
      .lf()
      .black("black", ANSI.Background.WHITE)
      .lf()
      .white("white", ANSI.Background.BLACK)
      .lf()
      .background("background", ANSI.Background.RED)
      .lf()
      .append("fux", ANSI.Color.RED, ANSI.Background.WHITE);
  }


  @Test
  public void testUp() {
    System.out.println(this.ansi().string());
  }

  @Test
  public void testDown() {
    String ansi = this.ansi().string();
    String down = ANSI.down(ansi);
    System.out.println(ansi);
    System.out.println();
    System.out.println(down);
  }

  @Test
  public void testHtml() {
    System.out.println(this.ansi().html());
    System.out.println();
    System.out.println(this.ansi().br().html());
    System.out.println();
    System.out.println(this.ansi().color(ANSI.Color.RED, "#b71c1c").html());
    System.out.println();
    System.out.println(this.ansi().color(ANSI.Color.RED, "#e57373").html());
  }

  @Test
  public void testAnsi() {
    String string = ANSI.up().append("Step => name", ANSI.Color.RED, ANSI.Background.BLACK).string();
    System.out.println(string);
  }

  @Test
  public void testWrap() {
    String string = this.ansi().string();
//    System.out.println(string);
//    System.out.println();
    ANSIWrapper wrap = ANSI.wrap(string);
    ANSI ansis = wrap.ansis();
    System.out.println(ansis);
    System.out.println(wrap.text());
    System.out.println(wrap.html());
  }

  @Test
  public void testW2() {
    String text =
      "drwxr-xr-x    2 root     root             0 Nov 28  2018 \u001b[1;34m.\u001b[m\n" +
      "drwxr-xr-x    1 root     root          4096 Nov 28 11:36 \u001b[1;34m..\u001b[m\n" +
      "drwxr-xr-x    2 root     root             0 Nov 28  2018 \u001b[1;34m.git\u001b[m\n" +
      "-rwxr-xr-x    1 root     root             9 Nov 28  2018 \u001b[1;32mREADME.md\u001b[m";

    ANSI ansis = ANSI.wrap(text).ansis();
    System.out.println(ansis);
  }
}
