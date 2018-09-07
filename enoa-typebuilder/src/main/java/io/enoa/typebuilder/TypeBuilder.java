/*
 * Copyright 2016 ikidou
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

package io.enoa.typebuilder;

import io.enoa.typebuilder.impl.ParameterizedTypeImpl;
import io.enoa.typebuilder.impl.WildcardTypeImpl;
import io.enoa.typebuilder.thr.TypeException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TypeBuilder {
  private final TypeBuilder parent;
  private final Class raw;
  private final List<Type> args = new ArrayList<>();

  private TypeBuilder(Class raw, TypeBuilder parent) {
    assert raw != null;
    this.raw = raw;
    this.parent = parent;
  }

  public static TypeBuilder with(Class raw) {
    return new TypeBuilder(raw, null);
  }

  private static TypeBuilder with(Class raw, TypeBuilder parent) {
    return new TypeBuilder(raw, parent);
  }

  public TypeBuilder beginSubType(Class raw) {
    return with(raw, this);
  }

  public TypeBuilder endSubType() {
    if (parent == null) {
      throw new TypeException("expect beginSubType() before endSubType()");
    }

    parent.type(getType());

    return parent;
  }

  public TypeBuilder type(Class clazz) {
    return type((Type) clazz);
  }

  public TypeBuilder typeExtends(Class... classes) {
    if (classes == null) {
      throw new NullPointerException("typeExtends() expect not null Class");
    }

    WildcardTypeImpl wildcardType = new WildcardTypeImpl(null, classes);

    return type(wildcardType);
  }

  public TypeBuilder typeSuper(Class... classes) {
    if (classes == null) {
      throw new NullPointerException("typeSuper() expect not null Class");
    }

    WildcardTypeImpl wildcardType = new WildcardTypeImpl(classes, null);

    return type(wildcardType);
  }

  public TypeBuilder type(Type type) {
    if (type == null) {
      throw new NullPointerException("type expect not null Type");
    }

    args.add(type);

    return this;
  }

  public Type build() {
    if (parent != null) {
      throw new TypeException("expect endSubType() before build()");
    }

    return getType();
  }

  private Type getType() {
    if (args.isEmpty()) {
      return raw;
    }
    return new ParameterizedTypeImpl(raw, args.toArray(new Type[args.size()]), null);
  }
}
