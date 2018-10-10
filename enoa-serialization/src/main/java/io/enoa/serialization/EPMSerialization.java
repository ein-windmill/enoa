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
package io.enoa.serialization;

import io.enoa.serialization.provider.jdk.JdkSerializeProvider;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EPMSerialization {


  private static class Holder {
    private static final EPMSerialization INSTANCE = new EPMSerialization();
  }

  static EPMSerialization instance() {
    return Holder.INSTANCE;
  }

  private EPMSerialization() {
    this.MAP.put("-main", new JdkSerializeProvider());
  }

  private Map<String, EoSerializationFactory> MAP = new ConcurrentHashMap<>();

  private Boolean exists(String name) {
    return this.MAP.containsKey(name);
  }

  public void install(EoSerializationFactory factory) {
    this.install("main", factory);
  }

  public void install(String name, EoSerializationFactory factory) {
    if (factory == null)
      throw new IllegalArgumentException("Factory can not be null.");
    if (this.exists(name))
      throw new IllegalArgumentException("Serialization exists this name => " + name);
    this.MAP.put(name, factory);
    if (name.equals("main"))
      this.MAP.remove("-main");
  }

  public EoSerializationFactory factory() {
    EoSerializationFactory factory = this.factory("main");
    return factory == null ? this.factory("-main") : factory;
  }

  public EoSerializationFactory factory(String name) {
    return this.MAP.get(name);
  }

  public EoSerializer serializer() {
    EoSerializer serializer = this.serializer("main");
    return serializer == null ? this.serializer("-main") : serializer;
  }

  public EoSerializer serializer(String name) {
    return this.factory(name).serializer();
  }

}
