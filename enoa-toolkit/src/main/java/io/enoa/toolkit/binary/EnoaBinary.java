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
package io.enoa.toolkit.binary;

import io.enoa.toolkit.EoConst;
import io.enoa.toolkit.digest.DigestKit;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Arrays;

public abstract class EnoaBinary implements Serializable {

  private String string;

  public abstract Charset charset();

  public abstract byte[] bytes();

  public String string(Charset charset) {
    if (this.string != null)
      return this.string;

    CharsetDecoder decoder = charset.newDecoder();
    ByteBuffer byteBuffer = ByteBuffer.wrap(this.bytes());
    CharBuffer charBuffer = CharBuffer.allocate(byteBuffer.limit());
    decoder.decode(byteBuffer, charBuffer, true);
    charBuffer.flip();
    String ret = charBuffer.toString();
    charBuffer.clear();
    byteBuffer.clear();
    this.string = ret;
    return ret;
  }

  public String string() {
    return this.string(this.charset());
  }

  public ByteBuffer bytebuffer() {
    return ByteBuffer.wrap(this.bytes());
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
  public int hashCode() {
    return Arrays.hashCode(this.bytes());
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;

    EnoaBinary that = (EnoaBinary) obj;
    return DigestKit.slowEquals(this.bytes(), that.bytes());
  }

  @Override
  public String toString() {
    return Arrays.toString(this.bytes());
  }
}
