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
package io.enoa.toolkit.ethernet;

public class MAC {

  private final byte[] bytes;

  MAC(byte[] bytes) {
    this.bytes = bytes;
  }

  public String string() {
    return this.string(":");
  }

  public String string(CharSequence delimiter) {
    if (this.bytes == null)
      return null;
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < this.bytes.length; i++) {
      if (i != 0) {
        builder.append(delimiter);
      }
      String hex = Integer.toHexString(this.bytes[i] & 0xFF);
      builder.append(hex.length() == 1 ? 0 + hex : hex);
    }
    return builder.toString().toUpperCase();
  }

  public byte[] bytes() {
    return this.bytes;
  }

  @Override
  public String toString() {
    return this.string();
  }
}
