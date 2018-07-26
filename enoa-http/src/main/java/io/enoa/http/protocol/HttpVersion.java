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

import java.io.IOException;

public enum HttpVersion {

  HTTP_1_0("http/1.0"),

  HTTP_1_1("http/1.1"),

  SPDY_3("spdy/3.1"),

  HTTP_2("h2");

  private final String version;

  HttpVersion(String version) {
    this.version = version;
  }

  public static HttpVersion of(String version) throws IOException {
    if (version.equalsIgnoreCase(HTTP_1_0.version)) return HTTP_1_0;
    if (version.equalsIgnoreCase(HTTP_1_1.version)) return HTTP_1_1;
    if (version.equalsIgnoreCase(HTTP_2.version)) return HTTP_2;
    if (version.equalsIgnoreCase(SPDY_3.version)) return SPDY_3;
    throw new IOException("Unexpected http version: " + version);
  }

  @Override
  public String toString() {
    return version;
  }
}
