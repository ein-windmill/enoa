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
package io.enoa.gateway.data;

import io.enoa.gateway.auth.GatewayAuth;
import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.toolkit.http.UriKit;

public class GMapping {

  private String name;
  private String source;
  private String dest;
  private GatewayAuth auth;

  public GMapping(String name, String source, String dest, GatewayAuth auth) {
    if (source == null)
      throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.gateway.mapping_source_null"));
    if (dest == null)
      throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.gateway.mapping_dest_null"));

    this.name = name;
    this.source = UriKit.correct(source);
    this.dest = dest;
    this.auth = auth;
  }

  public GMapping(String name, String source, String dest) {
    this(name, source, dest, null);
  }

  public GMapping(String source, String dest, GatewayAuth auth) {
    this(null, source, dest, auth);
  }

  public GMapping(String source, String dest) {
    this(null, source, dest, null);
  }

  public String name() {
    return this.name;
  }

  public String source() {
    return this.source;
  }

  public String dest() {
    return this.dest;
  }

  public GatewayAuth auth() {
    return this.auth;
  }

  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + this.source.hashCode();
    result = 31 * result + this.dest.hashCode();
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof GMapping))
      return false;
    if (this == obj)
      return true;
    GMapping that = (GMapping) obj;
    return that.source.equals(this.source)
      && that.dest.equals(this.dest);
  }

  @Override
  public String toString() {
    return "GMapping{" +
      "name='" + name + '\'' +
      ", source='" + source + '\'' +
      ", dest='" + dest + '\'' +
      ", auth=" + auth +
      '}';
  }
}
