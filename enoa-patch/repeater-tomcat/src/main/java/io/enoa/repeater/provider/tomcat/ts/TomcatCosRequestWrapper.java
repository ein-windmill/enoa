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
package io.enoa.repeater.provider.tomcat.ts;

import io.enoa.repeater.EoxConfig;
import io.enoa.repeater.factory.http.EoxAbstractCosRequest;
import io.enoa.repeater.factory.name.EoxNameRuleFactory;
import io.enoa.repeater.http.Cookie;
import io.enoa.repeater.http.Method;
import io.enoa.repeater.http.Request;
import io.enoa.repeater.http.RequestBody;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.convert.ConvertKit;
import io.enoa.toolkit.stream.StreamKit;
import io.enoa.toolkit.text.TextKit;
import io.enoa.toolkit.thr.EoException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

class TomcatCosRequestWrapper extends EoxAbstractCosRequest {

  private EoxConfig config;
  private HttpServletRequest request;
  private RequestBody body;
  private Map<String, String[]> paraMap;
  private Cookie[] cookies;

  TomcatCosRequestWrapper(HttpServletRequest request, EoxConfig config, EoxNameRuleFactory rule) throws IOException {
    this.request = request;
    this.config = config;

    // 鉴定是文件上传才进行解析, 不阻挡 post data 解析
    String contentType = this.header("content-type");
    if (TextKit.blankn(contentType))
      contentType = contentType.toLowerCase();
    if (TextKit.blankn(contentType) && contentType.startsWith("multipart/form-data"))
      super.handleUpload(request.getInputStream(), config, rule);
  }

  @Override
  public Object originRequest() {
    return this.request;
  }

  @Override
  public Method method() {
    Method method = Method.of(this.header("X-HTTP-Method-Override"));
    return method == null ? Method.of(this.request.getMethod()) : method;
  }

  @Override
  public String context() {
    return this.request.getContextPath();
  }

  @Override
  public String uri() {
    return this.request.getRequestURI();
  }

  @Override
  public String url() {
    String qs = this.request.getQueryString();
    if (TextKit.blanky(qs))
      return this.request.getRequestURL().toString();
//    return String.format("%s?%s", this.request.getRequestURL().toString(), qs);
    return TextKit.union(this.request.getRequestURL().toString(), "?", qs);
  }

  @Override
  public RequestBody body() {
    try {
      if (this.body != null)
        return this.body;

      String contentType = this.header("content-type");
      if (TextKit.blankn(contentType))
        contentType = contentType.toLowerCase();
      if (TextKit.blankn(contentType) && contentType.startsWith("multipart/form-data"))
        return null;

      byte[] binary = StreamKit.bytes(this.request.getInputStream());
      this.body = RequestBody.create(binary, this.config.charset());
      return this.body;
    } catch (Exception e) {
      throw new EoException(e.getMessage(), e);
    }
  }

  @Override
  public Cookie[] cookies() {
    if (this.cookies != null)
      return this.cookies;

    javax.servlet.http.Cookie[] cookies = this.request.getCookies();
    if (CollectionKit.isEmpty(cookies))
      return CollectionKit.emptyArray(Cookie.class);
    this.cookies = Stream.of(cookies)
      .map(c -> {
        Cookie.Builder builder = new Cookie.Builder()
          .name(c.getName())
          .value(c.getValue())
          .path(c.getPath());
        if (c.isHttpOnly())
          builder.httpOnly();
        if (c.getSecure())
          builder.secure();
        return builder.build();
      }).toArray(Cookie[]::new);
    return this.cookies;
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
    Enumeration<String> headerNames = this.request.getHeaderNames();
    List<String> names = new ArrayList<>();
    while (headerNames.hasMoreElements()) {
      names.add(headerNames.nextElement());
    }
    return names.toArray(new String[names.size()]);
  }

  @Override
  public String header(String name) {
    for (String n : this.headerNames())
      if (n.equalsIgnoreCase(name))
        return this.request.getHeader(n);
    return null;
  }

  @Override
  public String para(String name, String def) {
    String[] paras = this.paraValues(name);
    if (CollectionKit.isEmpty(paras))
      return def;
    return ConvertKit.string(paras[0], def);
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
    this.paraMap = super.paraMap(super.mapArrayToList(this.request.getParameterMap()));
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
    return (T) this.request.getAttribute(name);
  }

  @Override
  public <T> Request attr(String name, T data) {
    this.request.setAttribute(name, data);
    return this;
  }

  @Override
  public String[] attrNames() {
    Set<String> rets = new HashSet<>();
    Enumeration<String> attrs = this.request.getAttributeNames();
    while (attrs.hasMoreElements()) {
      rets.add(attrs.nextElement());
    }
    return rets.toArray(new String[rets.size()]);
  }

  @Override
  public Request rmAttr(String name) {
    this.request.removeAttribute(name);
    return this;
  }

  @Override
  public void clear() {
    super.clear();
    this.body = null;
    CollectionKit.clear(this.cookies);
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
