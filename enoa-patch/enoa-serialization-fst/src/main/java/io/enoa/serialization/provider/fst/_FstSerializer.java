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
package io.enoa.serialization.provider.fst;

import io.enoa.serialization.EoSerializer;
import org.nustaq.serialization.FSTObjectInput;
import org.nustaq.serialization.FSTObjectOutput;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

class _FstSerializer implements EoSerializer {

  private static class Holder {
    private static final _FstSerializer INSTANCE = new _FstSerializer();
  }

  static _FstSerializer instance() {
    return Holder.INSTANCE;
  }

  @Override
  public <T> byte[] serialize(T object) {
    if (object == null)
      throw new IllegalArgumentException("Serialize data can not be null.");

    try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
         FSTObjectOutput output = new FSTObjectOutput(baos)) {
      output.writeObject(object);
      output.flush();
      return baos.toByteArray();
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  @Override
  public <T> T reduction(byte[] bytes) {
    if (bytes == null || bytes.length == 0)
      return null;

    try (FSTObjectInput input = new FSTObjectInput(new ByteArrayInputStream(bytes))) {
      return (T) input.readObject();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
