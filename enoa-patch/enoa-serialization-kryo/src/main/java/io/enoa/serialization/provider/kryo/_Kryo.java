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
package io.enoa.serialization.provider.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import io.enoa.serialization.EoSerializer;
import org.objenesis.strategy.StdInstantiatorStrategy;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

class _Kryo implements EoSerializer {

  private static class Holder {
    private static final _Kryo INSTANCE = new _Kryo();
  }

  static _Kryo instance() {
    return Holder.INSTANCE;
  }

  private final Kryo kryo = new Kryo();

  private _Kryo() {
    this.kryo.setReferences(true);
    this.kryo.setRegistrationRequired(false);
    ((Kryo.DefaultInstantiatorStrategy) kryo.getInstantiatorStrategy())
      .setFallbackInstantiatorStrategy(new StdInstantiatorStrategy());
  }

  @Override
  public <T> byte[] serialize(T object) {
    if (object == null)
      throw new IllegalArgumentException("Serialize data can not be null.");

    try (Output output = new Output(new ByteArrayOutputStream())) {
      kryo.writeClassAndObject(output, object);
      return output.toBytes();
    } catch (KryoException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  @Override
  public <T> T reduction(byte[] bytes) {
    if (bytes == null || bytes.length == 0)
      return null;

    try (Input input = new Input(new ByteArrayInputStream(bytes))) {
      return (T) kryo.readClassAndObject(input);
    } catch (KryoException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }


}
