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
package io.enoa.toolkit.reflect;

import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.text.TextReader;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TypeKit {


  public static RefType reftype(Type type) {
    /*
    List<Map<String, Map<String, Set<Integer>>>>
     */
    return reftype(TextReader.with(type.getTypeName()));
  }

  private static RefType reftype(TextReader reader) {
    StringBuilder clazz = new StringBuilder();
    RefType.Builder supcz = new RefType.Builder();
    do {
      char ch = reader.next();
      if (ch == '<') {
        supcz.type(clazz.toString());
        RefType[] generics = generics(reader);
        supcz.generics(generics);
        clazz.delete(0, clazz.length());
        return supcz.build();
      }
      clazz.append(ch);
    } while (reader.hasNext());
    return supcz.type(clazz.toString()).build();
  }

  private static RefType[] generics(TextReader reader) {
    StringBuilder clazz = new StringBuilder();
    List<RefType> generics = new ArrayList<>();
    RefType.Builder builder = new RefType.Builder();
    do {
      char ch = reader.next();
      if (ch == ' ') {
        continue;
      }
      if (ch == '<') {
        builder.type(clazz.toString());
        builder.generics(generics(reader));
        clazz.delete(0, clazz.length());
        generics.add(builder.build());
        builder = new RefType.Builder();
        continue;
      }
      if (ch == ',') {
        builder.type(clazz.toString());
        generics.add(builder.build());
        clazz.delete(0, clazz.length());
        builder = new RefType.Builder();
        continue;
      }
      if (ch == '>') {
        if (clazz.length() != 0) {
          builder.type(clazz.toString());
          generics.add(builder.build());
          clazz.delete(0, clazz.length());
        }
        RefType[] reftypes = generics.toArray(new RefType[generics.size()]);
        CollectionKit.clear(generics);
        return reftypes;
      }
      clazz.append(ch);
    } while (reader.hasNext());
    return CollectionKit.emptyArray(RefType.class);
  }

}
