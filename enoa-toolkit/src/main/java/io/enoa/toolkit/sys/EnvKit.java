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
package io.enoa.toolkit.sys;

import io.enoa.toolkit.value.EnoaValue;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class EnvKit {

  private EnvKit() {
  }

  private static Map<String, String> env;
  private static final EnvKit me = new EnvKit();

  static {
    env = new HashMap<>(System.getenv());
    Properties properties = System.getProperties();
    Enumeration<?> enumeration = properties.propertyNames();
    while (enumeration.hasMoreElements()) {
      Object name = enumeration.nextElement();
      env.put(name.toString(), System.getProperty(name.toString()));
    }
  }

  public static EnvKit set(String name, String value) {
    env.put(name, value);
    System.setProperty(name, value);
    return me;
  }

  public static EnoaValue value(String name) {
    return EnoaValue.with(env.get(name));
  }

  public static String string(String name, String def) {
    return value(name).string(def);
  }

  public static String string(String name) {
    return value(name).string();
  }

  public static Integer integer(String name, Integer def) {
    return value(name).integer(def);
  }

  public static Integer integer(String name) {
    return value(name).integer();
  }

  public static Long longer(String name, Long def) {
    return value(name).longer(def);
  }

  public static Long longer(String name) {
    return value(name).longer();
  }

  public static Boolean bool(String name, Boolean def) {
    return value(name).bool(def);
  }

  public static Boolean bool(String name) {
    return value(name).bool();
  }

  public static boolean exists(String name) {
    return string(name) != null;
  }


}
