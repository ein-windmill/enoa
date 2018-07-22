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
package io.enoa.serialization.provider.jdk;

import io.enoa.serialization.EoSerializer;

import java.io.*;

class _JdkSerializer implements EoSerializer {
  @Override
  public <T> byte[] serialize(T object) {
    if (object == null)
      throw new IllegalArgumentException("Serialize data can not be null.");

    ObjectOutputStream output = null;
    try {
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      output = new ObjectOutputStream(bos);
      output.writeObject(object);
      output.flush();
      return bos.toByteArray();
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage(), e);
    } finally {
      if (output != null)
        try {
          output.close();
        } catch (Exception e) {
          // skip
        }
    }
  }

  @Override
  public <T> T reduction(byte[] bytes) {
    if (bytes == null || bytes.length == 0)
      return null;

    try (ObjectInputStream input = new ObjectInputStream(new ByteArrayInputStream(bytes))) {
      return (T) input.readObject();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
