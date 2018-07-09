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
package io.enoa.toolkit.bean;

import io.enoa.toolkit.map.Kv;
import io.enoa.toolkit.namecase.NamecaseKit;
import io.enoa.toolkit.namecase.NamecaseType;
import io.enoa.toolkit.sys.ObjectKit;
import io.enoa.toolkit.value.EnoaValue;
import io.enoa.typebuilder.TypeBuilder;
import org.junit.Test;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BeanKitTest {


  @Test
  public void testToNum() {
    short f0 = 1;
    Kv kv = Kv.by("field", f0);
    TestEntity te = BeanKit.reductionMap(kv, TestEntity.class, false);
    System.out.println(te);
  }

  @Test
  public void testMap() {
    Bean0 b0 = new Bean0();
    b0.name("jack")
      .things(new ArrayList<Bean1>() {{
        add(new Bean1().thing("thing0"));
        add(new Bean1().thing("thing2"));
      }});
    Map<String, Object> map = BeanKit.map(b0);
    List<Map> things = (List) map.get("things");
    System.out.println(things.get(0).keySet());
//    List<Map> m = EnoaValue.with().as();
    System.out.println(map);
    // todo
  }

  public static class TestEntity {
    private Integer field;

    public Integer getField() {
      return field;
    }

    public TestEntity setField(Integer field) {
      this.field = field;
      return this;
    }

    @Override
    public String toString() {
      return ObjectKit.buildToString(this);
    }
  }

}
