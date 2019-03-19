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
package io.enoa.docker.dket.common;

import io.enoa.docker.dket.AbstractDRRet;

public class ExposedPort extends AbstractDRRet {


  private final Integer port;
  private final NetProtocol protocol;


  public ExposedPort(Builder builder) {
    this.protocol = builder.protocol;
    this.port = builder.port;
  }

  public Integer port() {
    return port;
  }

  public NetProtocol protocol() {
    return protocol;
  }

  public static class Builder {

    private Integer port;
    private NetProtocol protocol;

    public ExposedPort build() {
      return new ExposedPort(this);
    }

    public Builder port(Integer port) {
      this.port = port;
      return this;
    }

    public Builder protocol(NetProtocol protocol) {
      this.protocol = protocol;
      return this;
    }
  }

}
