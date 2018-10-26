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
package io.enoa.toolkit.ret;

import io.enoa.toolkit.map.Kv;

import java.util.HashMap;
import java.util.Optional;

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

    private boolean skipcase;

    @Override
    public boolean ok() {
      return this.bool("stat");
    }

    @Override
    public boolean fail() {
      return !this.bool("stat");
    }

    @Override
    public EoResp resp(boolean fill) {
      EoResp resp = EoResp.build(this.ok() ? EoResp.Stat.OK : EoResp.Stat.FAIL);
      if (!fill)
        return resp;
      Kv kv = Kv.create();
      this.forEach((key, value) -> {
        if ("stat".equals(key))
          return;
        kv.set(key, value);
      });
      if (kv.isEmpty())
        return resp;
      resp.data(kv);
      return resp;
    }

    public EoRet skipcase() {
      return this.skipcase(Boolean.TRUE);
    }

    public EoRet skipcase(boolean skip) {
      this.skipcase = skip;
      return this;
    }

    @Override
    public Object get(Object key) {
      if (!this.skipcase)
        return super.get(key);

      Optional<String> first = this.keySet().stream()
        .filter(k -> k.equalsIgnoreCase(key.toString()))
        .findFirst();
      return first.map(super::get).orElse(null);
    }

  }

}
