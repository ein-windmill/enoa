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
package io.enoa.toolkit.namecase;

import org.junit.Test;

public class NamecaseKitTest {

  @Test
  public void testNamecase() {
    System.out.println(NamecaseKit.camelcaselower("a_bv_d"));
    System.out.println(NamecaseKit.camelcaseupper("a_bv_d"));
    System.out.println(NamecaseKit.underline("ABvD"));
  }

}
