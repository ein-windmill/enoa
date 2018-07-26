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
package io.enoa.toolkit.bean;

import io.enoa.toolkit.digest.UUIDKit;
import io.enoa.toolkit.map.Kv;
import io.enoa.toolkit.sys.ObjectKit;
import io.enoa.typebuilder.TypeBuilder;
import org.junit.Test;

import java.lang.reflect.Type;
import java.util.*;

public class BeanKitTest {


  @Test
  public void testToNum() {
    short f0 = 1;
    Kv kv = Kv.by("field", f0);
    TestEntity te = BeanKit.reduction().map(kv, TestEntity.class, false);
    System.out.println(te);
  }

  private Map<String, Object> map0() {
    Bean0<Bean1, Integer> b0 = new Bean0<>();
    b0.name("jack")
      .things(new ArrayList<Bean1>() {{
        add(new Bean1().thing("thing0"));
        add(new Bean1().thing("thing2"));
      }})
      .rpn(new HashMap<String, Bean2<Integer>>() {{
        put("rp0", new Bean2<Integer>().extId(UUIDKit.next()).resp(0));
        put("rp1", new Bean2<Integer>().extId(UUIDKit.next()).resp(1));
      }});
    return BeanKit.convert().map(b0);
  }

  private Map<String, Object> map3() {
    Bean3<Bean1, Set<Integer>, String> b0 = new Bean3<>();
    b0.name("jack")
      .things(new ArrayList<Bean1>() {{
        add(new Bean1().thing("thing0"));
        add(new Bean1().thing("thing2"));
      }})
      .rpn(new HashMap<String, Bean2<Set<Integer>>>() {{
        put("rp0", new Bean2<Set<Integer>>().extId(UUIDKit.next()).resp(new HashSet<Integer>() {{
          add(0);
        }}));
        put("rp1", new Bean2<Set<Integer>>().extId(UUIDKit.next()).resp(new HashSet<Integer>() {{
          add(1);
        }}));
      }})
      .day("Day 1");
    return BeanKit.convert().map(b0);
  }

  @Test
  public void testMap() {
    Map<String, Object> map = this.map0();
    System.out.println(map);
  }

  @Test
  public void testReduction() {
    Map<String, Object> map = this.map3();
    Type type = TypeBuilder.with(Bean3.class)
      .type(Bean1.class)
      .beginSubType(Set.class)
      .type(Integer.class)
      .endSubType()
      .type(String.class)
      .build();
    Object objet = BeanKit.reduction().objet(map, type);
    System.out.println(objet);
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
