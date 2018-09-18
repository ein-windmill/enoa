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
package io.enoa.repeater.provider.fastcgi.ts;

import io.enoa.repeater.EoxConfig;
import io.enoa.repeater.factory.http.EoxAbstractCosRequest;
import io.enoa.repeater.factory.name.EoxNameRuleFactory;
import io.enoa.repeater.http.Cookie;
import io.enoa.repeater.http.Method;
import io.enoa.repeater.http.Request;
import io.enoa.repeater.http.RequestBody;
import io.enoa.repeater.kit.http.EnoaHttpKit;
import io.enoa.repeater.provider.fastcgi.server.plus._RepeaterFastCGIRequest;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.convert.ConvertKit;
import io.enoa.toolkit.stream.StreamKit;
import io.enoa.toolkit.text.TextKit;

import java.io.ByteArrayInputStream;
import java.util.*;

class FastCGIRequestWrapper extends EoxAbstractCosRequest {

  private EoxConfig config;
  private _RepeaterFastCGIRequest request;
  private Map<String, String> prop;
  private String body;
  private Map<String, String[]> paraMap;

  FastCGIRequestWrapper(EoxConfig config, EoxNameRuleFactory rule, _RepeaterFastCGIRequest request) {
    this.config = config;
    this.request = request;
    this.prop = this.request.prop();
    this.initBody();

    String contentType = this.header("content-type");
    if (TextKit.blankn(contentType))
      contentType = contentType.toLowerCase();

    if (TextKit.blankn(contentType) && contentType.startsWith("multipart/form-data"))
      super.handleUpload(new ByteArrayInputStream(this.request.data()), config, rule);
  }

  private void initBody() {
    String contentType = this.header("content-type");
    if (TextKit.blanky(contentType))
      return;
    if (contentType.startsWith("multipart/form-data"))
      return;
    if (contentType.startsWith("application/x-www-form-urlencoded"))
      return;
    this.body = StreamKit.string(this.request.data(), this.config.charset());
  }

  @Override
  public Object originRequest() {
    return this.request.originRequest();
  }

  @Override
  public Method method() {
    Method method = Method.of(this.header("X-HTTP-Method-Override"));
    return method == null ? Method.of(this.prop.get("REQUEST_METHOD")) : method;
  }

  @Override
  public String context() {
    return this.request.context();
  }

  @Override
  public String uri() {
    String path = this.prop.get("SCRIPT_NAME");
    String lastSlash = path.substring(path.lastIndexOf("/"));
    if (lastSlash.contains("."))
      path = path.substring(0, path.lastIndexOf("/"));
    return path;
  }

  @Override
  public String url() {
    String uri = this.uri();
    if (TextKit.blanky(this.prop.get("QUERY_STRING")))
      return uri;
//    return String.format("%s?%s", uri, this.prop.get("QUERY_STRING"));
    return TextKit.union(uri, "?", this.prop.get("QUERY_STRING"));
  }

  @Override
  public RequestBody body() {
    return RequestBody.create(this.body, this.config.charset());
  }

  @Override
  public Cookie[] cookies() {
    String cookie = this.header("cookie");
    if (TextKit.blanky(cookie))
      return CollectionKit.emptyArray(Cookie.class);
    return EnoaHttpKit.parseCookie(cookie);
  }

  @Override
  public Cookie cookieObject(String name) {
    for (Cookie cookie : this.cookies())
      if (cookie.name().equals(name))
        return cookie;
    return null;
  }

  @Override
  public String cookie(String name, String def) {
    Cookie cookie = this.cookieObject(name);
    if (cookie == null)
      return def;
    return cookie.value();
  }

  @Override
  public Integer cookieToInt(String name, Integer def) {
    String val = this.cookie(name);
    if (TextKit.blanky(val))
      return def;
    return ConvertKit.integer(val);
  }

  @Override
  public Long cookieToLong(String name, Long def) {
    String val = this.cookie(name);
    if (TextKit.blanky(val))
      return def;
    return ConvertKit.longer(val);
  }

  @Override
  public String[] headerNames() {
    return this.prop.keySet()
      .stream()
      .filter(name -> name.startsWith("HTTP_") && TextKit.blankn(this.prop.get(name)))
      .map(name -> name.replace("HTTP_", "").replace("_", "-"))
      .toArray(String[]::new);
  }

  @Override
  public String header(String name) {
//    String n = String.format("HTTP_%s", name.replace("-", "_").toUpperCase());
    String n = TextKit.union("HTTP_", name.replace("-", "_").toUpperCase());
    return this.prop.get(n);
  }

  @Override
  public String para(String name, String def) {
    String[] paras = this.paraValues(name);
    if (CollectionKit.isEmpty(paras))
      return def;
    return ConvertKit.string(paras[0], def, Boolean.TRUE);
  }

  @Override
  public Integer paraToInt(String name, Integer def) {
    return ConvertKit.integer(this.para(name), def);
  }

  @Override
  public Long paraToLong(String name, Long def) {
    return ConvertKit.longer(this.para(name), def);
  }

  @Override
  public Boolean paraToBoolean(String name, Boolean def) {
    return ConvertKit.bool(this.para(name), def);
  }

  @Override
  public Double paraToDouble(String name, Double def) {
    return ConvertKit.doubler(this.para(name), def);
  }


  @Override
  public Date paraToDate(String name, String format, Date def) {
    return ConvertKit.date(this.para(name), format, def);
  }

  @Override
  public Map<String, String[]> paraMap() {
    if (this.paraMap != null)
      return this.paraMap;

    String contentType = this.header("content-type");
    if (TextKit.blankn(contentType))
      contentType = contentType.toLowerCase();

    Map<String, List<String>> ret = EnoaHttpKit.parsePara(this.prop.get("QUERY_STRING"));
    if (TextKit.blankn(contentType) && contentType.startsWith("multipart/form-data")) {
      return super.paraMap(ret);
    }
    if (CollectionKit.isEmpty(ret))
      ret = new HashMap<>();

    if (TextKit.blankn(contentType) && contentType.startsWith("application/x-www-form-urlencoded")) {
      String body = StreamKit.string(this.request.data(), this.config.charset());
      Map<String, List<String>> dataPara = EnoaHttpKit.parsePara(body);
      if (CollectionKit.notEmpty(dataPara)) {
        ret = super.mergeMap(ret, dataPara);
        CollectionKit.clear(dataPara);
      }
    }
    this.paraMap = super.paraMap(ret);
    return this.paraMap;
  }

  @Override
  public String[] paraNames() {
    Map<String, String[]> paraMap = this.paraMap();
    if (CollectionKit.isEmpty(paraMap))
      return CollectionKit.emptyArray(String.class);
    Set<String> paras = paraMap.keySet();
    return paras.toArray(new String[paras.size()]);
  }

  @Override
  public String[] paraValues(String name) {
    Map<String, String[]> paraMap = this.paraMap();
    if (CollectionKit.isEmpty(paraMap))
      return CollectionKit.emptyArray(String.class);
    return paraMap.get(name);
  }

  @Override
  public Integer[] paraValuesToInt(String name) {
    String[] vals = this.paraValues(name);
    Integer[] rets = new Integer[vals.length];
    for (int i = 0; i < vals.length; i++) {
      rets[i] = ConvertKit.integer(vals[i]);
    }
    return rets;
  }

  @Override
  public Long[] paraValuesToLong(String name) {
    String[] vals = this.paraValues(name);
    Long[] rets = new Long[vals.length];
    for (int i = 0; i < vals.length; i++) {
      rets[i] = ConvertKit.longer(vals[i]);
    }
    return rets;
  }

  @Override
  public <T> T attr(String name) {
    return this.request.attr(name);
  }

  @Override
  public <T> Request attr(String name, T data) {
    this.request.attr(name, data);
    return this;
  }

  @Override
  public String[] attrNames() {
    return this.request.attrNames();
  }

  @Override
  public Request rmAttr(String name) {
    this.request.rmAttr(name);
    return this;
  }

  @Override
  public void clear() {
    this.body = null;
    this.request.clear();
    CollectionKit.clear(this.prop);
  }

//  @Override
//  public <T> T bean(Class<T> clazz) {
//    return EoxInjector.injectBean(clazz, this, false);
//  }
//
//  @Override
//  public <T> T bean(Class<T> clazz, boolean skipConvertError) {
//    return EoxInjector.injectBean(clazz, this, skipConvertError);
//  }
//
//  @Override
//  public <T> T bean(Class<T> clazz, String name) {
//    return EoxInjector.injectBean(clazz, name, this, false);
//  }
//
//  @Override
//  public <T> T bean(Class<T> clazz, String name, boolean skipConvertError) {
//    return EoxInjector.injectBean(clazz, name, this, skipConvertError);
//  }

}
