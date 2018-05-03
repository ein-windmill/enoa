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
package io.enoa.db;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EnoaDbMgr {

  private static class Holder {
    private static final EnoaDbMgr INSTANCE = new EnoaDbMgr();
  }

  public static EnoaDbMgr instance() {
    return Holder.INSTANCE;
  }

  private static Map<String, EnoaDb> edbm = new ConcurrentHashMap<>();

  private boolean exists(String name) {
    return edbm.keySet().stream().anyMatch(k -> k.equals(name));
  }

  public void start(EoDbFactory db, EoDbConfig config) {
    if (this.exists(config.name()))
      throw new RuntimeException("this db plugin name is exists. => " + config.name());

    EnoaDb eodb = db.db(config);
    eodb.start();
    edbm.put(config.name(), eodb);
  }

  public void stop(EoDbConfig config) {
    edbm.get(config.name()).stop();
  }

}
