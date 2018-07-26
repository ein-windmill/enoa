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
package io.enoa.toolkit.reflect;

import io.enoa.typebuilder.TypeBuilder;
import org.junit.Ignore;
import org.junit.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.*;

public class ReflectKitTest {

  public String test(String arg0) {
    return null;
  }

  @Test
  public void methodString() {
    Class<ReflectKitTest> clazz = ReflectKitTest.class;
    for (Method method : clazz.getMethods()) {
      System.out.println(ReflectKit.methodString(clazz, method));
    }
  }


  @Test
  @Ignore
  public void testNewInstanceType() {
    // fail

    Type type0 = TypeBuilder.with(List.class)
      .beginSubType(Map.class)
      .type(String.class)
      .beginSubType(Set.class)
      .type(Integer.class)
      .endSubType()
      .endSubType()
      .build();
    List trt0 = ReflectKit.newInstance(type0)
      .interfaceFor(LinkedList.class)
      .build();
    System.out.println(trt0);

    List<Map<String, Set<Integer>>> a = new ArrayList<>();
    Class<? extends List<Map<String, Set<Integer>>>> czz = (Class<? extends List<Map<String, Set<Integer>>>>) a.getClass();
    try {
      List<Map<String, Set<Integer>>> list = czz.newInstance();
      System.out.println(1);
    } catch (Exception e) {
      e.printStackTrace();
    }
    System.out.println(1);
  }


  @Test
  public void testNewInstanceString() {
    String czn = Map.class.getName();
    Map<String, Integer> map = ReflectKit.newInstance(czn)
      .interfaceFor(TreeMap.class)
      .build();
    map.put("a", 1);
    System.out.println(map);
    Integer a = map.get("a");
    System.out.println(a);
  }

  @Test
  public void testType() {
    Type type = TypeBuilder.with(List.class)
      .beginSubType(Map.class)
      .type(String.class)
      .beginSubType(Set.class)
      .type(Integer.class)
      .endSubType()
      .endSubType()
      .build();

//    Type type = TypeBuilder.with(Map.class)
//      .type(String.class)
//      .beginSubType(Map.class)
//      .type(String.class)
//      .type(Integer.class)
//      .endSubType()
//      .build();
//    RefType reftype = TypeKit.reftype(new ParameterizedTypeImpl(new Type[]{String.class}, List.class, List.class));
    RefType reftype = TypeKit.reftype(type);
    System.out.println(reftype);

  }
}
