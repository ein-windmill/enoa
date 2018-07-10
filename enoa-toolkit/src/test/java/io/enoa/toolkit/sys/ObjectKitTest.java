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
package io.enoa.toolkit.sys;

import io.enoa.toolkit.bean.BeanKit;
import io.enoa.toolkit.namecase.NamecaseKit;
import io.enoa.toolkit.namecase.NamecaseType;
import org.junit.Test;

import java.io.Serializable;
import java.util.Map;

public class ObjectKitTest {


  public static class Entity implements Serializable {
    private String id;
    private String name;
    private String description;
    private int age;

    public String getId() {
      return id;
    }

    public void setId(String id) {
      this.id = id;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getDescription() {
      return description;
    }

    public void setDescription(String description) {
      this.description = description;
    }

    public int getAge() {
      return age;
    }

    public void setAge(int age) {
      this.age = age;
    }

    @Override
    public String toString() {
      return "Entity{" +
        "id='" + id + '\'' +
        ", name='" + name + '\'' +
        ", description='" + description + '\'' +
        ", age=" + age +
        '}';
    }
  }


  @Test
  public void testConvert() {
    Entity e = new Entity();
    e.setId("1");
    e.setName("Jack");
    e.setDescription(null);

    Map<String, Object> map = BeanKit.convert().map(e, BeanKit.config().builder().namecase(NamecaseKit.namecase(NamecaseType.CASE_UNDERLINE)).build());
    map.put("test_z", "1");
    map.put("age", null);
    System.out.println(map);

    Entity e1 = BeanKit.reduction().map(map, Entity.class, NamecaseKit.namecase(NamecaseType.CASE_CAMEL_UPPER));
    System.out.println(e1);


  }

}
