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

import io.enoa.toolkit.text.TextKit;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ObjectKit {

  public static String string(Object object) {
    return object == null ? null : object.toString();
  }


  public static String buildToString(Object object) {
    Class<?> clazz = object.getClass();

    String cname = clazz.getName();
    StringBuilder tos = new StringBuilder();

    Set<String> mnames = Stream.of(clazz.getMethods())
      .filter(m -> !m.getName().startsWith("set"))
      .filter(m -> m.getParameterTypes().length == 0)
      .map(Method::getName)
      .collect(Collectors.toSet());

    do {
      Field[] dfields = clazz.getDeclaredFields();
      try {
        for (Field field : dfields) {
          if (field.isAccessible()) {
            Object o = field.get(object);
            System.out.println(1);
            tos.append(field.getName())
              .append(": ");
            tos.append(o);
            tos.append(", ");
          } else {

            String fname = field.getName();
            Method method = null;

            if (mnames.stream().anyMatch(fname::equals)) {
              method = clazz.getMethod(fname);
            } else {
              String nfname = TextKit.union("get", TextKit.firstToUpper(fname));
              if (mnames.stream().anyMatch(nfname::equals)) {
                method = clazz.getMethod(nfname);
              }
            }
            if (method == null)
              continue;
            Object o = method.invoke(object);
            tos.append(fname)
              .append(": ")
              .append(o)
              .append(", ");
          }
        }
      } catch (Exception e) {
        throw new RuntimeException(e.getMessage(), e);
      }

      clazz = clazz.getSuperclass();
    } while (clazz != null);
    if (tos.length() > 0)
      tos.delete(tos.length() - 2, tos.length());

    tos.insert(0, cname.substring(cname.lastIndexOf(".") + 1) + " => { ");
    tos.append(" }");
    return tos.toString();
  }


}
