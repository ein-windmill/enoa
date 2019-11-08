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
package io.enoa.http.protocol;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class HttpHeader {

  private final String name;
  private final String value;

  public HttpHeader(String name, String value) {
    if (name == null)
      throw new IllegalArgumentException("Header name can not be null.");
//    if (value == null)
//      throw new IllegalArgumentException("Header value con not be null.");
    this.name = name;
    this.value = value == null ? "" : value;
  }

  public static Set<HttpHeader> of(String text) {
    if (text == null || "".equals(text))
      return Collections.emptySet();

    String[] lines = text.split("\n");
    Set<HttpHeader> headers = new HashSet<>(lines.length);
    for (String line : lines) {
      int qix = line.indexOf(":");
      int eix = line.indexOf("=");
      if (qix == -1 && eix == -1)
        continue;

      String name, value;
      if (qix != -1) {
        name = line.substring(0, qix);
        value = line.substring(qix + 1);
      } else {
        name = line.substring(0, eix);
        value = line.substring(eix + 1);
      }
      headers.add(new HttpHeader(name, value));
    }
    return headers;
  }

  public String name() {
    return this.name;
  }

  public String value() {
    return this.value;
  }

  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + this.name.toLowerCase().hashCode();
    result = 31 * result + this.value.hashCode();
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof HttpHeader))
      return false;
    if (this == obj)
      return true;
    HttpHeader header = (HttpHeader) obj;
    return this.name.equalsIgnoreCase(header.name) && this.value.equals(header.value);
  }

  @Override
  public String toString() {
    return this.name.concat(": ").concat(this.value);
  }
}
