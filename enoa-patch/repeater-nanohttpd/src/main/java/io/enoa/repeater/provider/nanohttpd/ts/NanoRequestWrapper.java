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
package io.enoa.repeater.provider.nanohttpd.ts;

import fi.iki.elonen.NanoHTTPD;
import io.enoa.repeater.EoxConfig;
import io.enoa.repeater.factory.http.EoxAbstractCosRequest;
import io.enoa.repeater.factory.name.EoxNameRuleFactory;
import io.enoa.repeater.http.Cookie;
import io.enoa.repeater.http.Method;
import io.enoa.repeater.http.Request;
import io.enoa.repeater.http.RequestBody;
import io.enoa.repeater.kit.http.EnoaHttpKit;
import io.enoa.repeater.provider.nanohttpd.server.plus._RepeaterNanoHTTPDSession;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.convert.ConvertKit;
import io.enoa.toolkit.file.FileKit;
import io.enoa.toolkit.text.TextKit;
import io.enoa.toolkit.thr.EoException;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

class NanoRequestWrapper extends EoxAbstractCosRequest {

  private _RepeaterNanoHTTPDSession session;
  private EoxConfig config;
  private final Map<String, String> body = new HashMap<>();
  private Map<String, String[]> paraMap;
  private Cookie[] cookies;

  NanoRequestWrapper(_RepeaterNanoHTTPDSession session, EoxConfig config, EoxNameRuleFactory rule)
    throws IOException, NanoHTTPD.ResponseException {
    this.session = session;
    this.config = config;
    String contentType = this.header("content-type");
    if (TextKit.blankn(contentType))
      contentType = contentType.toLowerCase();
    if ((this.method() == Method.POST || this.method() == Method.PUT) &&
      (TextKit.blankn(contentType) && !contentType.startsWith("multipart/form-data"))) {
      this.session.parseBody(body);
    }
    if (this.method() == Method.PUT) {
      this.parsePutPara(contentType);
    }

    if (TextKit.blankn(contentType) && contentType.startsWith("multipart/form-data"))
      super.handleUpload(session.getInputStream(), config, rule);
  }

  /**
   * nanohttpd 在 put 請求中, 會將 body 信息寫入到零時文件中,
   * 判斷屬於表單提交還原成參數放置與 paraMap.
   */
  private void parsePutPara(String contentType) {
    if (contentType != null && contentType.contains("x-www-form-urlencoded")) {
      String content = this.body.get("content");
      if (TextKit.blanky(content))
        return;
      String body = FileKit.read(Paths.get(content)).string();
      if (TextKit.blanky(body))
        return;
      Map<String, List<String>> para = EnoaHttpKit.parsePara(body);
      this.paraMap = super.mapListToArray(para);
      this.paraMap = Collections.unmodifiableMap(this.paraMap);
    }
  }

  @Override
  public Object originRequest() {
    return this.session.originRequest();
  }

  @Override
  public Method method() {
    Method method = Method.of(this.header("X-HTTP-Method-Override"));
    return method == null ? Method.of(this.session.getMethod().name()) : method;
  }

  @Override
  public String context() {
    return this.session.context();
  }

  @Override
  public String uri() {
    return this.session.getUri();
  }

  @Override
  public String url() {
    String qs = this.session.getQueryParameterString();
    if (TextKit.blanky(qs))
      return this.uri();
//    return String.format("%s?%s", this.uri(), this.session.getQueryParameterString());
    return TextKit.union(this.uri(), "?", this.session.getQueryParameterString());
  }

  @Override
  public RequestBody body() {
    return RequestBody.create(this.body.get("postData"), this.config.charset());
  }

  @Override
  public Cookie[] cookies() {
    if (this.cookies != null)
      return this.cookies;

    NanoHTTPD.CookieHandler cookie = this.session.getCookies();
    List<Cookie> cks = new ArrayList<>();
    cookie.forEach(key -> {
      Cookie ck = new Cookie.Builder()
        .name(key)
        .value(cookie.read(key))
        .build();
      cks.add(ck);
    });
    this.cookies = cks.toArray(new Cookie[cks.size()]);
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
    Cookie cob = this.cookieObject(name);
    if (cob == null)
      return def;
    return cob.value() == null ? def : cob.value();
  }

  @Override
  public Integer cookieToInt(String name, Integer def) {
    String val = this.cookie(name);
    return val == null ? def : Integer.parseInt(val);
  }

  @Override
  public Long cookieToLong(String name, Long def) {
    String val = this.cookie(name);
    return val == null ? def : Long.parseLong(val);
  }

  @Override
  public String[] headerNames() {
    Set<String> hns = this.session.getHeaders().keySet();
    return hns.toArray(new String[hns.size()]);
  }

  @Override
  public String header(String name) {
    for (String n : this.headerNames())
      if (n.equalsIgnoreCase(name))
        return this.session.getHeaders().get(n);
    return null;
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
    this.paraMap = super.paraMap(this.session.getParameters());
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
    Map<String, String[]> stringMap = this.paraMap();
    if (stringMap == null)
      return CollectionKit.emptyArray(String.class);
    return stringMap.get(name);
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
    try {
      return this.session.attr(name);
    } catch (Exception e) {
      throw new EoException("Can not get attribute: {0}", e, e.getMessage());
    }
  }

  @Override
  public <T> Request attr(String name, T data) {
    this.session.attr(name, data);
    return this;
  }

  @Override
  public String[] attrNames() {
    return this.session.attrNames();
  }

  @Override
  public Request rmAttr(String name) {
    this.session.rmAttr(name);
    return this;
  }

  @Override
  public void clear() {
    super.clear();
    this.session.clear();
    CollectionKit.clear(this.body);
    CollectionKit.clear(this.cookies);
  }

}
