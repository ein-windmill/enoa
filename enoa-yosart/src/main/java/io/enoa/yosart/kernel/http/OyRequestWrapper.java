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
package io.enoa.yosart.kernel.http;

import io.enoa.repeater.http.*;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.convert.ConvertKit;
import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.toolkit.map.Kv;
import io.enoa.toolkit.value.EnoaValue;
import io.enoa.yosart.Oysart;
import io.enoa.yosart.YoExt;
import io.enoa.yosart.kernel.ext.YmSessionExt;
import io.enoa.yosart.kit.inject.EoxInjector;
import io.enoa.yosart.thr.OyExtException;

import java.util.Date;
import java.util.Map;

class OyRequestWrapper implements YoRequest {

  private Session session;

  private Request request;
  private PathVariable variable;
  private Kv data;

  OyRequestWrapper(Request request, PathVariable variable) {
    this.request = request;
    this.variable = variable;
  }

  @Override
  public EnoaValue cookieValue(String name) {
    return EnoaValue.with(this.request.cookie(name));
  }

  @Override
  public EnoaValue paraValue(String name) {
    return EnoaValue.with(this.request.para(name));
  }

  @Override
  public EnoaValue headerValue(String name) {
    return EnoaValue.with(this.request.header(name));
  }

  @Override
  public EnoaValue variableValue(String name) {
    return EnoaValue.with(this.variable(name));
  }

  @Override
  public <T> T bean(Class<T> clazz, String name) {
    return EoxInjector.injectBean(clazz, name, this, false);
  }

  @Override
  public <T> T bean(Class<T> clazz, String name, boolean skipConvertError) {
    return EoxInjector.injectBean(clazz, name, this, skipConvertError);
  }

  @Override
  public String[] variableNames() {
    if (this.variable == null)
      return CollectionKit.emptyArray(String.class);
    return this.variable.names();
  }

  @Override
  public String variable(String name, String def) {
    if (this.variable == null)
      return null;
    Object value = this.variable.value(name);
    return ConvertKit.string(value, def);
  }

  @Override
  public Integer variableToInt(String name, Integer def) {
    String val = this.variable(name);
    return ConvertKit.integer(val, def);
  }

  @Override
  public Boolean variableToBoolean(String name, Boolean def) {
    String val = this.variable(name);
    return ConvertKit.bool(val, def);
  }

  @Override
  public Session session() throws OyExtException {
    if (session != null)
      return session;
    YmSessionExt ext = (YmSessionExt) Oysart.ext(YoExt.Type.SESSION);
    if (ext == null)
      throw new OyExtException(EnoaTipKit.message("eo.tip.yosart.ext_sess_404"));
    session = ext.session(this);
    return session;
  }


  @Override
  public Kv data() {
    if (this.data != null)
      return this.data;
    this.data = Kv.create();
    String[] headerNames = this.headerNames();
    Kv header = Kv.create();
    for (String name : headerNames) {
      header.set(name, this.header(name));
    }
    Map<String, String[]> paraMap = this.paraMap();
    if (CollectionKit.notEmpty(paraMap)) {
      Kv para = Kv.create();
      paraMap.forEach((k, v) -> {
        if (v.length == 1) {
          para.set(k, v[0]);
          return;
        }
        para.set(k, v);
      });
      this.data.set("paras", para);
    }
    String[] variableNames = this.variableNames();
    Kv variable = Kv.create();
    for (String name : variableNames) {
      variable.set(name, this.variable(name));
    }
    String[] attrNames = this.attrNames();
    Kv attr = Kv.create();
    for (String name : attrNames) {
      attr.set(name, this.attr(name));
    }
    this.data.set("context", this.context())
      .set("uri", this.uri())
      .set("url", this.url())
      .set("header", header)
      .set("variable", variable);
    if (CollectionKit.notEmpty(this.files()))
      this.data.set("files", this.files());
    return this.data;
  }

  @Override
  public Object originRequest() {
    return this.request.originRequest();
  }

  @Override
  public Method method() {
    return this.request.method();
  }

  @Override
  public String context() {
    return this.request.context();
  }

  @Override
  public String uri() {
    return this.request.uri();
  }

  @Override
  public String url() {
    return this.request.url();
  }

  @Override
  public RequestBody body() {
    return this.request.body();
  }

  @Override
  public Cookie[] cookies() {
    return this.request.cookies();
  }

  @Override
  public Cookie cookieObject(String name) {
    return this.request.cookieObject(name);
  }

  @Override
  public String cookie(String name, String def) {
    return this.request.cookie(name, def);
  }

  @Override
  public Integer cookieToInt(String name, Integer def) {
    return this.request.cookieToInt(name, def);
  }

  @Override
  public Long cookieToLong(String name, Long def) {
    return this.request.cookieToLong(name, def);
  }

  @Override
  public String[] headerNames() {
    return this.request.headerNames();
  }

  @Override
  public String header(String name) {
    return this.request.header(name);
  }

  @Override
  public String para(String name, String def) {
    return this.request.para(name, def);
  }

  @Override
  public Integer paraToInt(String name, Integer def) {
    return this.request.paraToInt(name, def);
  }

  @Override
  public Long paraToLong(String name, Long def) {
    return this.request.paraToLong(name, def);
  }

  @Override
  public Boolean paraToBoolean(String name, Boolean def) {
    return this.request.paraToBoolean(name, def);
  }

  @Override
  public Double paraToDouble(String name, Double def) {
    return this.request.paraToDouble(name, def);
  }

  @Override
  public Date paraToDate(String name, String format, Date def) {
    return this.request.paraToDate(name, format, def);
  }

  @Override
  public Map<String, String[]> paraMap() {
    return this.request.paraMap();
  }

  @Override
  public String[] paraNames() {
    return this.request.paraNames();
  }

  @Override
  public String[] paraValues(String name) {
    return this.request.paraValues(name);
  }

  @Override
  public Integer[] paraValuesToInt(String name) {
    return this.request.paraValuesToInt(name);
  }

  @Override
  public Long[] paraValuesToLong(String name) {
    return this.request.paraValuesToLong(name);
  }

  @Override
  public <T> T attr(String name) {
    return this.request.attr(name);
  }

  @Override
  public <T> Request attr(String name, T data) {
    return this.request.attr(name, data);
  }

  @Override
  public String[] attrNames() {
    return this.request.attrNames();
  }

  @Override
  public Request rmAttr(String name) {
    return this.request.rmAttr(name);
  }

  @Override
  public UFile[] files() {
    return this.request.files();
  }

  @Override
  public UFile[] files(String name) {
    return this.request.files(name);
  }

  @Override
  public UFile file(String name) {
    return this.request.file(name);
  }

  @Override
  public void clear() {
    this.request.clear();
    if (this.variable != null)
      this.variable.clear();
    if (this.data != null)
      this.data.clear();
  }
}
