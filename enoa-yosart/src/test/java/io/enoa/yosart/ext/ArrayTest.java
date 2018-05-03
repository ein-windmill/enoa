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
package io.enoa.yosart.ext;

import io.enoa.json.kit.JsonKit;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ArrayTest {

  @Test
  public void testArray() {
    Object[] arr = new Object[4];
    arr[0] = 1;
    arr[1] = null;
    arr[3] = 'a';

    Object o1 = arr[1];

    arr = this.genArr(1, 2, null, 'a', null);
    String o2 = (String) arr[2];
    System.out.println(JsonKit.toJson(arr));
  }

  private Object[] genArr(Object... arr) {
    return arr;
  }

  @Test
  public void testKeySet() {
    Map<String, Object> map = new HashMap<>();
    map.put("1", "a");
    map.put("2", "b");
    map.put("3", "c");
    map.put("4", "d");
    Set<String> set0 = map.keySet();
    map.put("5", "e");
    Set<String> set1 = map.keySet();
    map.put("6", "f");
  }
}
