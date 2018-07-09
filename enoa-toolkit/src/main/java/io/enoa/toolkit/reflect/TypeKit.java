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
import io.enoa.toolkit.thr.EoReflectException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TypeKit {


  public static RefType reftype(Type type) {
    if (type instanceof ParameterizedType) {
      return reftypeForType((ParameterizedType) type);
    }

    if (type instanceof Class) {
      return new RefType.Builder()
        .type(((Class) type).getName())
        .build();
    }

    String typeName = type.getTypeName();
    if (typeName.contains("@")) {
      throw new EoReflectException("Can not support convert this type to reftype.");
    }
    /*
    List<Map<String, Map<String, Set<Integer>>>>
     */
    return reftypeForName(TextReader.with(typeName));
  }


  /**
   * ParameterizedType 转换
   *
   * @param type type
   * @return RefType
   */
  private static RefType reftypeForType(ParameterizedType type) {
    RefType.Builder builder = new RefType.Builder();
    Class raw0 = (Class) type.getRawType();
    builder.type(raw0.getName())
      .generics(genericsForType(type));
    return builder.build();
  }

  private static RefType[] genericsForType(ParameterizedType type) {
    List<RefType> reftypes = new ArrayList<>();
    Type[] args = type.getActualTypeArguments();
    for (Type arg : args) {
      RefType.Builder builder = new RefType.Builder();
      if (arg instanceof Class) {
        builder.type(((Class) arg).getName());
        reftypes.add(builder.build());
        continue;
      }
      if (arg instanceof ParameterizedType) {
        builder.type(((Class) ((ParameterizedType) arg).getRawType()).getName())
          .generics(genericsForType((ParameterizedType) arg));
        reftypes.add(builder.build());
        continue;
      }
    }
    RefType[] _ret = reftypes.toArray(new RefType[reftypes.size()]);
    CollectionKit.clear(reftypes);
    return _ret;
  }

  /**
   * 根据 type name 进行解析
   * Type 接口中 getTypeName 方法可以获取当前 Type 的具体类型,
   * 然而 getTypeName 是通过调用 toString 方法实现的, 如果实现者
   * 没有实现 toString 方法, 那么将无法获取到实际类型
   *
   * @param reader TextReader
   * @return RefType
   */
  private static RefType reftypeForName(TextReader reader) {
    StringBuilder clazz = new StringBuilder();
    RefType.Builder supcz = new RefType.Builder();
    do {
      char ch = reader.next();
      if (ch == '<') {
        supcz.type(clazz.toString());
        RefType[] generics = genericsForName(reader);
        supcz.generics(generics);
        clazz.delete(0, clazz.length());
        return supcz.build();
      }
      clazz.append(ch);
    } while (reader.hasNext());
    return supcz.type(clazz.toString()).build();
  }

  private static RefType[] genericsForName(TextReader reader) {
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
        builder.generics(genericsForName(reader));
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
