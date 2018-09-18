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
package io.enoa.repeater.cos;

import java.io.IOException;
import java.io.InputStream;

public class EoxInputStream extends InputStream {

  private InputStream stream;


  public EoxInputStream(InputStream stream) {
    this.stream = stream;
  }

  public int readLine(byte[] b, int off, int len) throws IOException {
    if (len <= 0) {
      return 0;
    }
    int count = 0, c;
    while ((c = this.stream.read()) != -1) {
      b[off++] = (byte) c;
      count++;
      if (c == '\n' || count == len) {
        break;
      }
    }
    return count > 0 ? count : -1;
  }

  public int read(byte[] buf, int off, int len) throws IOException {
    return this.stream.read(buf, off, len);
  }

  public int read() throws IOException {
    return this.stream.read();
  }

}
