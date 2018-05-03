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
package io.enoa.http.proxy;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;

/**
 * Created by ein on 2016/9/19.
 */
public class SocketProxy implements HttpProxy {

  private String host;
  private Integer port;

  public SocketProxy(String host, Integer port) {
    if (host == null || "".equals(host))
      throw new IllegalArgumentException("Host can not be null");
    if (port == null)
      throw new IllegalArgumentException("Port can not be null");
    this.host = host;
    this.port = port;
  }

  @Override
  public Proxy proxy() {
    SocketAddress addr = new InetSocketAddress(this.host, this.port);
    Proxy proxy = new Proxy(Proxy.Type.SOCKS, addr);
    return proxy;
  }

  @Override
  public String toString() {
    return "SocketProxy{" +
      "host='" + host + '\'' +
      ", port=" + port +
      '}';
  }
}
