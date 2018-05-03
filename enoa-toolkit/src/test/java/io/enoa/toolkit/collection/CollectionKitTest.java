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
package io.enoa.toolkit.collection;

import io.enoa.toolkit.random.RandomKit;
import io.enoa.toolkit.text.UnicodeKit;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CollectionKitTest {


  @Test
  public void testUniq() {
    String[] arr = new String[]{"a", "b", "a"};
    arr = CollectionKit.distinct(arr);
    for (String a : arr) {
      System.out.println(a);
    }
  }

  private List<String> genList() {
    int start = 0x3100;
    int end = 0x9fa5;
    List<String> rets = new ArrayList<>();
    int t = 10;
    while (t > 0) {
      int position = RandomKit.nextInt(start, end);
      String code = Integer.toHexString(position);
      rets.add(UnicodeKit.character("\\u" + code));
      t -= 1;
    }
    return rets;
  }

  @Test
  public void testSplit() {
    List<String> sts = this.genList();
    List<List<String>> sps = CollectionKit.split(sts, 3);
    System.out.println(sps);
  }

  @Test
  public void testParts() {
    List<String> sts = this.genList();
    List<List<String>> sps = CollectionKit.parts(sts, 3);
    System.out.println(sps);
  }
}
