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

import io.enoa.toolkit.EoConst;
import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.toolkit.value.EnoaValue;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Properties;
import java.util.Set;

/**
 * @see https://github.com/jfinal/jfinal/blob/master/src/main/java/com/jfinal/kit/Prop.java
 * <p>
 * enoa - io.enoa.toolkit.prop
 */
public class Prop {
  private Properties properties = null;

  public Prop(String fileName) {
    this(fileName, EoConst.CHARSET);
  }

  public Prop(String fileName, Charset encoding) {
    try (InputStream inputStream = getClassLoader().getResourceAsStream(fileName)) {
      if (inputStream == null) {
        throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.toolkit.prop_file_notfound_classpath", fileName));
      }
      properties = new Properties();
      properties.load(new InputStreamReader(inputStream, encoding));
    } catch (IOException e) {
      throw new RuntimeException(EnoaTipKit.message("eo.tip.toolkit.prop_load_error"), e);
    }
  }

  public Prop(File file) {
    this(file, EoConst.CHARSET);
  }

  public Prop(File file, Charset encoding) {
    if (file == null) {
      throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.toolkit.prop_file_null"));
    }
    if (!file.isFile()) {
      throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.toolkit.prop_file_notfound_file", file.getName()));
    }

    try (InputStream inputStream = new FileInputStream(file)) {
      new FileInputStream(file);
      properties = new Properties();
      properties.load(new InputStreamReader(inputStream, encoding));
    } catch (IOException e) {
      throw new RuntimeException(EnoaTipKit.message("eo.tip.toolkit.prop_load_error"), e);
    }
  }

  private ClassLoader getClassLoader() {
    ClassLoader ret = Thread.currentThread().getContextClassLoader();
    return ret != null ? ret : getClass().getClassLoader();
  }

  public EnoaValue value(String key) {
    return EnoaValue.with(properties.getProperty(key));
  }

  public String string(String key) {
    return this.value(key).string();
  }

  public String string(String key, String def) {
    return this.value(key).string(def);
  }

  public Integer integer(String key) {
    return this.value(key).integer();
  }

  public Integer integer(String key, Integer def) {
    return this.value(key).integer(def);
  }

  public Long longer(String key) {
    return this.value(key).longer();
  }

  public Long longer(String key, Long def) {
    return this.value(key).longer(def);
  }

  public Boolean bool(String key) {
    return this.value(key).bool();
  }

  public Boolean bool(String key, Boolean def) {
    return this.value(key).bool(def);
  }

  public Set<String> keys() {
    return properties.stringPropertyNames();
  }

  public boolean exists(String key) {
    return properties.containsKey(key);
  }

  public Properties properties() {
    return properties;
  }
}
