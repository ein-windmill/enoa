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
package io.enoa.toolkit.bean;

import io.enoa.toolkit.EoConst;
import io.enoa.toolkit.bean.tomap._BeanToMap;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.map.Kv;
import io.enoa.toolkit.map.OKv;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BeanConvert {

  private static class Holder {
    private static final BeanConvert INSTANCE = new BeanConvert();
  }

  static BeanConvert instance() {
    return Holder.INSTANCE;
  }

  private BeanConvert() {

  }

  public Kv kv(Object bean) {
    return this.kv(bean, BeanKit.config());
  }

  public Kv kv(Object bean, Bonfig config) {
    return this.kv(bean, EoConst.DEPTH, config);
  }

  public Kv kv(Object bean, int depth, Bonfig config) {
    Map<String, Object> map = this.map(bean, depth, config);
    Kv kv = Kv.by(map);
    CollectionKit.clear(map);
    return kv;
  }

  public OKv okv(Object bean) {
    return this.okv(bean, BeanKit.config());
  }

  public OKv okv(Object bean, Bonfig config) {
    return this.okv(bean, EoConst.DEPTH, config);
  }

  public OKv okv(Object bean, int depth, Bonfig config) {
    Map<String, Object> map = this.map(bean, depth, config);
    OKv okv = OKv.by(map);
    CollectionKit.clear(map);
    return okv;
  }

  public Map<String, Object> map(Object bean) {
    return this.map(bean, BeanKit.config());
  }

  public Map<String, Object> map(Object bean, Bonfig config) {
    return this.map(bean, EoConst.DEPTH, config);
  }

  public Map<String, Object> map(Object bean, int depth, Bonfig config) {
    return _BeanToMap.map(bean, depth, config);
  }

  public List<Kv> kvs(List beans) {
    return this.kvs(beans, BeanKit.config());
  }

  public List<Kv> kvs(List beans, Bonfig config) {
    return this.kvs(beans, EoConst.DEPTH, config);
  }

  public List<Kv> kvs(List beans, int depth, Bonfig config) {
    List<Map<String, Object>> maps = this.maps(beans, depth, config);
    if (CollectionKit.isEmpty(maps))
      return Collections.emptyList();
    List<Kv> ret = maps.stream().map(Kv::by).collect(Collectors.toList());
    CollectionKit.clear(maps);
    return ret;
  }

  public List<OKv> okvs(List beans) {
    return this.okvs(beans, BeanKit.config());
  }

  public List<OKv> okvs(List beans, Bonfig config) {
    return this.okvs(beans, EoConst.DEPTH, config);
  }

  public List<OKv> okvs(List beans, int depth, Bonfig config) {
    List<Map<String, Object>> maps = this.maps(beans, depth, config);
    if (CollectionKit.isEmpty(maps))
      return Collections.emptyList();
    List<OKv> ret = maps.stream().map(OKv::by).collect(Collectors.toList());
    CollectionKit.clear(maps);
    return ret;
  }

  public List<Map<String, Object>> maps(List beans) {
    return this.maps(beans, BeanKit.config());
  }

  public List<Map<String, Object>> maps(List beans, Bonfig config) {
    return this.maps(beans, EoConst.DEPTH, config);
  }

  public List<Map<String, Object>> maps(List beans, int depth, Bonfig config) {
    if (CollectionKit.isEmpty(beans))
      return Collections.emptyList();
    List<Map<String, Object>> rets = new ArrayList<>();
    beans.forEach(bean -> {
      Map<String, Object> item = this.map(bean, depth, config);
      rets.add(item);
    });
    return rets;
  }

}
