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
package io.enoa.log;

import io.enoa.log.kit.LogKit;
import io.enoa.log.provider.jdk.JdkLogProvider;
import io.enoa.log.provider.slf4j.Slf4JLogProvider;

/**
 * Logger manager, switch log provider
 */
public class EnoaLogMgr {

  private static class Holder {
    private static final EnoaLogMgr INSTSNCE = new EnoaLogMgr();
  }

  private EnoaLogMgr() {

  }

  public static EnoaLogMgr instance() {
    return Holder.INSTSNCE;
  }


  private static EoLogFactory defLogFactory;

  static {
    init();
  }

  static void init() {
    if (defLogFactory != null)
      return;
    try {
      Class.forName("org.slf4j.Logger");
      defLogFactory = new Slf4JLogProvider();
    } catch (ClassNotFoundException e) {
      defLogFactory = new JdkLogProvider();
    }
  }

  public static EnoaLog getLog(Class<?> clazz) {
    if (defLogFactory == null)
      throw new IllegalArgumentException("LogFactory can not be null.");

    return defLogFactory.logger(clazz);
  }

  public static EnoaLog getLog(String name) {
    if (defLogFactory == null)
      throw new IllegalArgumentException("LogFactory can not be null.");

    return defLogFactory.logger(name);
  }

  public void defLogFactory(EoLogFactory factory) {
    if (factory == null)
      throw new IllegalArgumentException("LogFactory can not be null.");

    defLogFactory = factory;
    LogKit.syncLog();
  }

}
