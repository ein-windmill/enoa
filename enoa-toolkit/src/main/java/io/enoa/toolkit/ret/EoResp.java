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

import io.enoa.toolkit.mark.IMarkIx;
import io.enoa.toolkit.value.EnoaValue;

import java.util.HashMap;

public class EoResp extends HashMap<String, Object> implements Ret {

  public enum Stat implements IMarkIx {
    /**
     * 未知状态
     */
    UNKNOWN(0),
    /**
     * 操作成功
     */
    OK(1),
    /**
     * 操作失败
     */
    FAIL(2),
    /**
     * 等待接下来的操作
     */
    FUTURE(3),
    //
    ;

    private final int ix;

    Stat(int ix) {
      this.ix = ix;
    }

    @Override
    public int ix() {
      return ix;
    }

    public static Stat of(int ix) {
      for (Stat stat : Stat.values()) {
        if (stat.ix == ix)
          return stat;
      }
      return UNKNOWN;
    }
  }

  public static EoResp build(Stat stat, String message, Object data) {
    return new EoResp()
      .stat(stat)
      .message(message)
      .data(data);
  }

  public static EoResp build(Stat stat, Object data) {
    return build(stat, null, data);
  }

  public static EoResp build(Stat stat, String message) {
    return build(stat, message, null);
  }

  public static EoResp build(Stat stat) {
    return build(stat, null, null);
  }

  public static EoResp ok() {
    return build(Stat.OK, null, null);
  }

  public static EoResp ok(String message, Object data) {
    return build(Stat.OK, message, data);
  }

  public static EoResp ok(String message) {
    return build(Stat.OK, message, null);
  }

  public static EoResp ok(Object data) {
    return build(Stat.OK, null, data);
  }

  public static EoResp fail(String message, Object data) {
    return build(Stat.FAIL, message, data);
  }

  public static EoResp fail(String message) {
    return build(Stat.FAIL, message, null);
  }

  public static EoResp fail(Object data) {
    return build(Stat.FAIL, null, data);
  }

  public static EoResp fail() {
    return build(Stat.FAIL, null, null);
  }


  public Stat stat() {
    return Stat.of(this.value("stat").integer());
  }


  public EoResp stat(Stat stat) {
    this.set("stat", stat.ix);
    return this;
  }

  public String message() {
    return this.value("message").string();
  }

  public EoResp message(String message) {
    this.set("message", message);
    return this;
  }

  public <T> T data() {
    return this.value("data").as();
  }

  public EoResp data(Object data) {
    this.set("data", data);
    return this;
  }

  private EnoaValue value(String name) {
    return EnoaValue.with(super.get(name));
  }

  public EoResp set(String name, Object value) {
    super.put(name, value);
    return this;
  }
}
