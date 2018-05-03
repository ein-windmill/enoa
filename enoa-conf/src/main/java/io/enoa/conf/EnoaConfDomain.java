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
import io.enoa.toolkit.mark.IMarkVal;

import java.io.Serializable;

public class EnoaConfDomain extends Kv implements Serializable {

//  private String path;
//  private String name;
//  private Env env;
//  private Origin origin;
//  private Kv conf;

  public enum Origin {
    ENVIRONMENT,
    DISK,
    GIT
  }

  public enum Env implements IMarkVal {
    //    UNKNOWN("unknown"),
    DEVELOPMENT("dev"),
    PRODUCT("product"),
    TEST("test"),;
    private final String val;

    Env(String val) {
      this.val = val;
    }

    @Override
    public String val() {
      return val;
    }

    public static Env of(String val) {
      for (Env env : Env.values())
        if (env.val().equalsIgnoreCase(val))
          return env;
      return null;
    }
  }

  private EnoaConfDomain(Origin origin, String path, String name, Env env, Kv conf) {
//    this.origin = origin;
//    this.path = path;
//    this.name = name;
//    this.env = env;
//    this.conf = conf;
    super.set("origin", origin)
      .set("path", path)
      .set("name", name)
      .set("env", env)
      .set("conf", conf);
  }

  public static EnoaConfDomain create(Origin origin, String path, String name, Env env, Kv conf) {
    return new EnoaConfDomain(origin, path, name, env, conf);
  }

  public String path() {
    return super.string("path");
  }

  public String name() {
    return super.string("name");
  }

  public Env env() {
    return super.as("env");
  }

  public Kv conf() {
    return super.as("conf");
  }

  public Origin origin() {
    return super.as("origin");
  }

}
