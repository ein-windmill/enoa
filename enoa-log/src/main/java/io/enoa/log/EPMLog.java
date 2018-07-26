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
package io.enoa.log;

import io.enoa.log.provider.jdk.JdkLogProvider;
import io.enoa.log.provider.slf4j.Slf4JLogProvider;

public class EPMLog {

  private EPMLog() {
    try {
      Class.forName("org.slf4j.Logger");
      this.defLogFactory = new Slf4JLogProvider();
    } catch (ClassNotFoundException e) {
      this.defLogFactory = new JdkLogProvider();
    }
  }

  private static class Holder {
    private static final EPMLog INSTANCE = new EPMLog();
  }

  static EPMLog instance() {
    return Holder.INSTANCE;
  }

  private EoLogFactory defLogFactory;


  public void install(EoLogFactory factory) {
    if (factory == null)
      throw new IllegalArgumentException("LogFactory can not be null.");

    this.defLogFactory = factory;
    Log.syncLog();
  }

  public EoLogFactory factory() {
    return this.defLogFactory;
  }

  public EnoaLog log(Class<?> clazz) {
    if (this.defLogFactory == null)
      throw new IllegalArgumentException("LogFactory can not be null.");

    return this.defLogFactory.logger(clazz);
  }

  public EnoaLog log(String name) {
    if (this.defLogFactory == null)
      throw new IllegalArgumentException("LogFactory can not be null.");

    return this.defLogFactory.logger(name);
  }
}
