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
package io.enoa.toolkit.binary;

import io.enoa.toolkit.EoConst;
import io.enoa.toolkit.text.TextKit;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

public abstract class EnoaBinary implements Serializable {

  public abstract Charset charset();

  public abstract byte[] bytes();

  public String string() {
    CharsetDecoder decoder = this.charset().newDecoder();
    ByteBuffer byteBuffer = ByteBuffer.wrap(this.bytes());
    CharBuffer charBuffer = CharBuffer.allocate(byteBuffer.limit());
    decoder.decode(byteBuffer, charBuffer, true);
    charBuffer.flip();
    String ret = charBuffer.toString();
    charBuffer.clear();
    byteBuffer.clear();
    return ret;
  }

  public static EnoaBinary create(byte[] buffer) {
    return create(buffer, EoConst.CHARSET);
  }

  public static EnoaBinary create(byte[] buffer, Charset charset) {
    return new EnoaBinary() {
      @Override
      public Charset charset() {
        return charset;
      }

      @Override
      public byte[] bytes() {
        return buffer;
      }
    };
  }

  @Override
  public String toString() {
    return TextKit.union("EnoaBinary{charset=", this.charset().name(), ", body=", TextKit.ellipsis(string(), 1000), "}");
  }
}
