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
package io.enoa.toolkit.text;

import org.junit.Test;

public class TextKitTest {

  @Test
  public void testRemove() {
//    String t0 = TextKit.removeRightChar(";;;;Some text;;;;  ", ';');
//    System.out.println(t0);
//    String t1 = TextKit.removeLeftChar("  ;;;;Some text;;;;", ';');
//    System.out.println(t1);
//    String t2 = TextKit.removeRightChar(";;;;Some text;;;;  a", ';');
//    System.out.println(t2);
//    String t3 = TextKit.removeLeftChar("a  ;;;;Some text;;;;", ';');
//    System.out.println(t3);

    System.out.println(TextKit.removeRightChar("abdc\n\n", '\n'));
  }

}
