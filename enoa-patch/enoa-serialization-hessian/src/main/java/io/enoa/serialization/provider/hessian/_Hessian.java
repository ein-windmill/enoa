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
package io.enoa.serialization.provider.hessian;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import io.enoa.serialization.EoSerializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

class _Hessian implements EoSerializer {

  private static class Holder {
    private static final _Hessian INSTANCE = new _Hessian();
  }

  static _Hessian instance() {
    return Holder.INSTANCE;
  }

  @Override
  public <T> byte[] serialize(T object) {
    if (object == null)
      throw new IllegalArgumentException("Serialize data can not be null.");

    HessianOutput output = null;
    try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
      output = new HessianOutput(baos);
//      output.startMessage();
      output.writeObject(object);
//      output.completeMessage();
      return baos.toByteArray();
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage(), e);
    } finally {
      if (output != null)
        try {
          output.close();
        } catch (IOException e) {
          // skip
        }
    }
  }

  @Override
  public <T> T reduction(byte[] bytes) {
    if (bytes == null || bytes.length == 0)
      return null;

    HessianInput input = null;
    try {
      input = new HessianInput(new ByteArrayInputStream(bytes));
      return (T) input.readObject();
    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
      if (input != null) {
        try {
          input.close();
        } catch (Exception e) {
          // skip
        }
      }
    }
  }
}
