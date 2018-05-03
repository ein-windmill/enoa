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
package io.enoa.toolkit.map;

import com.alibaba.fastjson.JSON;
import org.junit.Ignore;
import org.junit.Test;

public class ORNvTest {


  @Test
  @Ignore
  public void testNvJson() {
    Nv nv = Nv.create("nv.name", "nv.value");
    String json = JSON.toJSONString(nv);

//    Type type = TypeBuilder.newInstance(Nv.class).addTypeParam(String.class).build();
    Nv znv = JSON.parseObject(json, Nv.class);
    System.out.println(znv);
  }

}
