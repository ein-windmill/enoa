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
package io.enoa.toolkit.http;

import io.enoa.toolkit.mark.IMarkVal;
import io.enoa.toolkit.text.TextKit;

public class ARL {


  public enum Protocol implements IMarkVal {
    HTTP("http"),

    HTTPS("https"),
    //
    ;

    private final String val;

    Protocol(String val) {
      this.val = val;
    }

    @Override
    public String val() {
      return val;
    }

    public static Protocol of(String text) {
      if (TextKit.blanky(text))
        return null;
      for (Protocol protocol : Protocol.values()) {
        if (protocol.val.equalsIgnoreCase(text))
          return protocol;
      }
      return null;
    }
  }


  private final Protocol protocol;
  private final String host;
  private final Integer port;
  private final String remain;
  private final String paras;
  private final String path;


  private ARL(Builder builder) {
    this.protocol = builder.protocol;
    this.host = builder.host;
    this.port = builder.port;
    this.remain = builder.remain;
    this.paras = builder.paras;
    this.path = builder.path;
  }

  public String path() {
    return path;
  }

  public String paras() {
    return paras;
  }

  public Protocol protocol() {
    return protocol;
  }

  public String host() {
    return host;
  }

  public Integer port() {
    return port;
  }

  public String remain() {
    return remain;
  }

  @Override
  public String toString() {
    return "ARL{" +
      "protocol=" + protocol +
      ", host='" + host + '\'' +
      ", port=" + port +
      ", remain='" + remain + '\'' +
      ", paras='" + paras + '\'' +
      ", path='" + path + '\'' +
      '}';
  }

  public static class Builder {

    private Protocol protocol;
    private String host;
    private Integer port;
    private String remain;
    private String paras;
    private String path;

    public ARL build() {
      return new ARL(this);
    }


    public Builder path(String path) {
      this.path = path;
      return this;
    }

    public Builder protocol(Protocol protocol) {
      this.protocol = protocol;
      return this;
    }

    public Builder host(String host) {
      this.host = host;
      return this;
    }

    public Builder port(Integer port) {
      this.port = port;
      return this;
    }

    public Builder remain(String remain) {
      this.remain = remain;
      return this;
    }

    public Builder paras(String paras) {
      this.paras = paras;
      return this;
    }
  }


}
