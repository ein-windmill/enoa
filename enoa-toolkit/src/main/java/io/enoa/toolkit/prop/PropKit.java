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
package io.enoa.toolkit.prop;

import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.text.TextKit;
import io.enoa.toolkit.thr.EoException;
import io.enoa.toolkit.value.EnoaValue;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

/**
 * 忽略文件名, 工程启动后即将所有 properties 结尾的
 * 配置文件均载入, 可通过此类直接获取
 */
public class PropKit {

  private PropKit() {
  }

  private static Map<String, EnoaValue> PROPS = new ConcurrentHashMap<>();

  static {
    loadProps("properties");
  }

  private static void loadProps(String... suffix) {
    PROPS.clear();
    Enumeration<URL> urls;
    try {
      urls = Thread.currentThread().getContextClassLoader().getResources("");
    } catch (IOException e) {
      throw new EoException(e);
    }
    while (urls.hasMoreElements()) {
      URL url = urls.nextElement();
      File fds = new File(url.getPath());
      File[] fs = fds.listFiles(f ->
        Stream.of(suffix).anyMatch(s ->
          f.getAbsolutePath()
            .endsWith(s.startsWith(".") ? s : TextKit.union(".", s))));
      if (CollectionKit.isEmpty(fs))
        continue;
      for (File f : fs) {
        Prop prop = new Prop(f);
        prop.keys().forEach(key -> PROPS.put(key, prop.value(key)));
      }
    }
  }

  public static void reload() {
    loadProps("properties");
  }

  public static void reload(String suffix) {
    loadProps(suffix);
  }

  public static void reload(String... suffix) {
    loadProps(suffix);
  }

  public static void add(String filename) {
    add(new Prop(filename));
  }

  public static void add(Prop prop) {
    Set<String> keys = prop.keys();
    keys.forEach(key -> PROPS.put(key, prop.value(key)));
  }

  public static void clear() {
    PROPS.clear();
  }

  public static EnoaValue value(String key) {
    EnoaValue value = PROPS.get(key);
    return value == null ? EnoaValue.NULL : value;
  }

  public static String string(String key) {
    return value(key).string();
  }

  public static String string(String key, String def) {
    return value(key).string(def);
  }

  public static Integer integer(String key) {
    return value(key).integer();
  }

  public static Integer integer(String key, Integer def) {
    return value(key).integer(def);
  }

  public static Long longer(String key) {
    return value(key).longer();
  }

  public static Long longer(String key, Long def) {
    return value(key).longer(def);
  }

  public static Boolean bool(String key) {
    return value(key).bool();
  }

  public static Boolean bool(String key, Boolean def) {
    return value(key).bool(def);
  }

  public static Set<String> keys() {
    return PROPS.keySet();
  }

  public static boolean exists(String key) {
    return PROPS.containsKey(key);
  }

}
