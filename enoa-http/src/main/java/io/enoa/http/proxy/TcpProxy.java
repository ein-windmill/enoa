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
package io.enoa.http.proxy;


import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;

/**
 * Created by ein on 2016/9/19.
 */
public class TcpProxy implements HttpProxy {

  private String host, user, passwd;
  private int port;

  public TcpProxy(String host, int port, String user, String passwd) {
    if (host == null || "".equals(host))
      throw new IllegalArgumentException("Host can not be null");
    this.host = host;
    this.port = port;
    this.user = user;
    this.passwd = passwd;
  }

  public TcpProxy(String host, Integer port) {
    this(host, port, null, null);
  }


  @Override
  public Proxy proxy() {
    Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(this.host, this.port));
    if (this.user == null)
      return proxy;
    Authenticator.setDefault(new Authenticator() {
      @Override
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(user, (passwd == null ? "" : passwd).toCharArray());
      }
    });
    return proxy;
  }

  @Override
  public String toString() {
    return "TcpProxy{" +
      "host='" + host + '\'' +
      ", user='" + user + '\'' +
      ", passwd='" + passwd + '\'' +
      ", port=" + port +
      '}';
  }
}
