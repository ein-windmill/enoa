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
      .background("background", ANSI.Background.RED);
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
}
