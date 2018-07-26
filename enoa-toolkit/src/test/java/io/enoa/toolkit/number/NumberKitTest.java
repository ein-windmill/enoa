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
package io.enoa.toolkit.number;

import org.junit.Test;

import java.math.BigDecimal;

public class NumberKitTest {


  @Test
  public void testConvert() {
    BigDecimal bd0 = new BigDecimal(2.10001010);
    Number c0 = NumberKit.to(bd0, Double.class);
    System.out.println(c0);
    Number c1 = NumberKit.to(c0, int.class);
    System.out.println(c1);
    Integer c2 = NumberKit.integer(bd0);
    System.out.println(c2);
    Long c3 = NumberKit.longer(bd0);
    System.out.println(c3);
    BigDecimal c4 = NumberKit.bigdecimal(310.19304);
    System.out.println(c4);
  }

  @Test
  public void isNumber() {
    String[] texts = new String[]{
      "+.9e-012",
      "-.9e-012",
      ".1",
      ".",
      ".0",
      ".0e1",
    };
    for (String text : texts) {
      boolean ret = NumberKit.isNumber(text);
      if (ret) {
        Number num = NumberKit.bigdecimal(text);
        System.out.println(ret + "  => " + NumberKit.bigdecimal(num));
      } else {
        System.out.println(ret + " => " + text);
      }
    }
  }

}
