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
package io.enoa.docker.dqp;

import io.enoa.http.protocol.HttpPara;
import io.enoa.json.Json;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.map.Kv;
import io.enoa.toolkit.value.EnoaValue;

import java.io.Serializable;
import java.util.*;

public class DPara implements Serializable {

  private Kv kv;

  private DPara() {
    this.kv = Kv.create();
  }

  private static final DPara EMPTY = new DPara();

  public static DPara empty() {
    return EMPTY;
  }

  public static DPara create() {
    return new DPara();
  }

  public DPara put(String name, Object value) {
    if (name == null)
      return this;
    if (value == null)
      return this;
    Object _v = this.kv.get(name);
    if (_v == null) {
      this.kv.set(name, value);
      return this;
    }
    List _lo;
    Object _o = this.kv.get(name);
    if (_o instanceof List) {
      _lo = (List) _o;
      _lo.add(value);
    } else {
      _lo = new ArrayList();
      _lo.add(_o);
      _lo.add(value);
    }
    this.kv.set(name, _lo);
    return this;
  }

  public DPara put(String name, Collection collection) {
    if (collection == null)
      return this;
    collection.forEach(col -> this.put(name, col));
    return this;
  }

  public DPara put(DPara dqr) {
    if (dqr == null)
      return this;
    if (CollectionKit.isEmpty(dqr.kv))
      return this;
    dqr.kv.forEach(this::put);
    return this;
  }

  public Set<HttpPara> http() {
    if (CollectionKit.isEmpty(this.kv))
      return Collections.emptySet();
    Set<HttpPara> paras = new HashSet<>();
    this.kv.forEach((key, val) -> {
      if (val instanceof Collection) {
        ((Collection) val).forEach(obj -> paras.add(new HttpPara(key, EnoaValue.with(obj).string())));
      } else {
        paras.add(new HttpPara(key, EnoaValue.with(val).string()));
      }
    });
    return paras;
  }

  public String string() {
    StringBuilder text = new StringBuilder();
    this.kv.forEach((key, val) -> {
      if (val instanceof Collection) {
        ((Collection) val).forEach(obj -> text.append(key).append("=").append(EnoaValue.with(val).string()));
      } else {
        text.append(key).append("=").append(EnoaValue.with(val).string());
      }
      text.append("&");
    });
    if (text.length() == 0)
      return null;
    return text.deleteCharAt(text.length() - 1).toString();
  }

  public String json() {
    return Json.toJson(this.kv);
  }

  @Override
  public String toString() {
    return this.json();
  }
}
