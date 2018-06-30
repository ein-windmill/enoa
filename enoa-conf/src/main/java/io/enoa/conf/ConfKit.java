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
package io.enoa.conf;

import io.enoa.toolkit.map.Kv;
import io.enoa.toolkit.value.EnoaValue;

import java.util.List;

public class ConfKit {


  public static List<EnoaConfDomain> confs() {
    return EnoaConf.confList();
  }

  public static EnoaValue value(String key) {
    List<EnoaConfDomain> confs = confs();
    for (EnoaConfDomain domain : confs) {
      Kv conf = domain.conf();
      if (conf.exists(key))
        return conf.value(key);
    }
    return EnoaValue.NULL;
  }

  public static <T> T as(String key) {
    return value(key).as();
  }

  public static String string(String key, String def) {
    return value(key).string(def);
  }

  public static String string(String key) {
    return value(key).string();
  }

  public static Integer integer(String key, Integer def) {
    return value(key).integer(def);
  }

  public static Integer integer(String key) {
    return value(key).integer();
  }

  public static Boolean bool(String key, Boolean def) {
    return value(key).bool(def);
  }

  public static Boolean bool(String key) {
    return value(key).bool();
  }

  public static Long longer(String key, Long def) {
    return value(key).longer(def);
  }

  public static Long longer(String key) {
    return value(key).longer();
  }

}
