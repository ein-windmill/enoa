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

public class Serializer {

  public static EPMSerialization epm() {
    return EPMSerialization.instance();
  }

  public static EoSerializer use() {
    return epm().serializer();
  }

  public static EoSerializer use(String name) {
    return epm().serializer(name);
  }

  public static <T> byte[] serialize(T object) {
    return use().serialize(object);
  }

  public static <T> T reduction(byte[] bytes) {
    return use().reduction(bytes);
  }

}
