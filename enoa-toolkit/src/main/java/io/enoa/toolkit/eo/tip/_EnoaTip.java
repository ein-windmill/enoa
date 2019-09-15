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
package io.enoa.toolkit.eo.tip;

import io.enoa.toolkit.is.Is;
import io.enoa.toolkit.prop.Prop;
import io.enoa.toolkit.sys.OSKit;
import io.enoa.toolkit.text.TextKit;

import java.io.Serializable;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import java.util.stream.Stream;

/**
 * enoa - io.enoa.toolkit.eo.tip
 */
class _EnoaTip implements Serializable {

  private Map<String, String> prop;


  _EnoaTip() {
    this.prop = new TreeMap<>();
  }

  _EnoaTip add(String file, Prop prop) {
    Properties properties = prop.properties();
    String suffix;
    if (file.endsWith("default.eo.properties")) {
      suffix = "default";
    } else {
      file = file.replace(".eo.properties", "");
      String[] fns = file.split("_");
      String local = fns[fns.length - 2];
      String lang = fns[fns.length - 1];
      suffix = local.concat("_").concat(lang);
    }
//    properties.stringPropertyNames().forEach(name -> this.prop.put(String.format("%s.%s", name, suffix), prop.get(name)));
    properties.stringPropertyNames().forEach(name -> this.prop.put(TextKit.union(name, ".", suffix), prop.string(name)));
    properties.clear();
    return this;
  }

  private String format(String text, Object... args) {
    if (Is.not().truthy(text))
      return text;
    if (Is.empty(args))
      return text;
    Object[] fags = Stream.of(args).map(o -> o == null ? null : o.toString()).toArray(Object[]::new);
    return TextKit.format(text, fags);
  }

  String message(String key, Object... args) {
    if (Is.not().truthy(key))
      throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.toolkit.tip_key_not_null"));

//    String tip = this.prop.get(String.format("%s.%s", key, OSKit.language()));
    String tip = this.prop.get(TextKit.union(key, ".", OSKit.language()));
    if (Is.truthy(tip))
      return format(tip, args);

    tip = this.prop.get(key.concat(".default"));
    if (Is.truthy(tip))
      return format(tip, args);

    return String.format("Not found message key: `%s`", key);
  }

  boolean exists(String key) {
    return this.prop.containsKey(key);
  }

}
