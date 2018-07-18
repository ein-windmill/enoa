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
package io.enoa.toolkit.ret;

import java.util.HashMap;

public class ERetBuilder {

  private EoRetImpl ret;

  ERetBuilder() {
    this.ret = new EoRetImpl();
    this.ret.set("stat", Boolean.FALSE);
  }

  public EoRet build() {
    return this.ret;
  }

  public ERetBuilder stat(boolean stat) {
    this.ret.set("stat", stat);
    return this;
  }

  public ERetBuilder ok() {
    return this.stat(Boolean.TRUE);
  }

  public ERetBuilder fail() {
    return this.stat(Boolean.FALSE);
  }

  public ERetBuilder set(String key, Object value) {
    this.ret.set(key, value);
    return this;
  }

  public ERetBuilder setIf(String key, Object value) {
    if (this.ret.fail())
      return this;
    return this.set(key, value);
  }

  private static class EoRetImpl extends HashMap<String, Object> implements EoRet {
    @Override
    public boolean ok() {
      return this.bool("stat");
    }

    @Override
    public boolean fail() {
      return !this.bool("stat");
    }

    @Override
    public EoResp resp() {
      return EoResp.build(this.ok() ? EoResp.Stat.OK : EoResp.Stat.FAIL);
    }
  }

}
