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
package io.enoa.yosart.plugin.eml;

import io.enoa.eml.Eml;
import io.enoa.eml.EmlConfig;
import io.enoa.yosart.YoPlugin;
import io.enoa.yosart.thr.OyPluginException;

public class EmlPlugin implements YoPlugin {

  private String name;
  private EmlConfig config;

  public EmlPlugin(String host, int port) {
    this("main", host, port, true);
  }

  public EmlPlugin(String host, int port, boolean ssl) {
    this("main", new EmlConfig.Builder()
      .host(host)
      .port(port)
      .ssl(ssl)
      .skipError()
      .build());
  }

  public EmlPlugin(String host, int port, boolean ssl, String user, String passwd) {
    this("main", new EmlConfig.Builder()
      .host(host)
      .port(port)
      .ssl(ssl)
      .user(user)
      .passwd(passwd)
      .skipError()
      .auth()
      .build());
  }

  public EmlPlugin(String host, int port, String user, String passwd) {
    this("main", host, port, true, user, passwd);
  }

  public EmlPlugin(EmlConfig config) {
    this.name = "main";
    this.config = config;
  }

  public EmlPlugin(String name, String host, int port) {
    this(name, host, port, true);
  }

  public EmlPlugin(String name, String host, int port, boolean ssl) {
    this(name, new EmlConfig.Builder()
      .host(host)
      .port(port)
      .ssl(ssl)
      .skipError()
      .build());
  }

  public EmlPlugin(String name, String host, int port, boolean ssl, String user, String passwd) {
    this(name, new EmlConfig.Builder()
      .host(host)
      .port(port)
      .ssl(ssl)
      .user(user)
      .passwd(passwd)
      .skipError()
      .auth()
      .build());
  }

  public EmlPlugin(String name, String host, int port, String user, String passwd) {
    this(name, host, port, true, user, passwd);
  }

  public EmlPlugin(String name, EmlConfig config) {
    this.name = name;
    this.config = config;
  }


  @Override
  public String name() {
    return "EmailPlugin";
  }

  @Override
  public String version() {
    return "1";
  }

  @Override
  public String description() {
    return "Email plugin";
  }

  @Override
  public boolean start() throws OyPluginException {
    Eml.epm().install(this.name, this.config);
    return true;
  }

  @Override
  public boolean stop() throws OyPluginException {
    Eml.epm().clear();
    return true;
  }
}
