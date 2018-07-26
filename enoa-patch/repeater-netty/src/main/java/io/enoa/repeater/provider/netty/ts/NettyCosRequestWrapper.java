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
package io.enoa.repeater.provider.netty.ts;

import io.enoa.repeater.EoxConfig;
import io.enoa.repeater.factory.http.EoxAbstractCosRequest;
import io.enoa.repeater.factory.name.EoxNameRuleFactory;
import io.enoa.repeater.http.Cookie;
import io.enoa.repeater.http.Method;
import io.enoa.repeater.http.Request;
import io.enoa.repeater.http.RequestBody;
import io.enoa.repeater.kit.http.EnoaHttpKit;
import io.enoa.repeater.provider.netty.server.plus._RepeaterNettyRequest;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.convert.ConvertKit;
import io.enoa.toolkit.stream.StreamKit;
import io.enoa.toolkit.text.TextKit;
import io.enoa.toolkit.thr.EoException;
import io.netty.buffer.ByteBufInputStream;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

class NettyCosRequestWrapper extends EoxAbstractCosRequest {


  private static final HttpDataFactory factory = new DefaultHttpDataFactory(DefaultHttpDataFactory.MAXSIZE);
  private _RepeaterNettyRequest request;
  private ByteBufInputStream inputStream;
  private EoxConfig config;
  // para map cache
  private Map<String, String[]> paraMap;
  private Cookie[] cookies;
  private RequestBody body;

  NettyCosRequestWrapper(_RepeaterNettyRequest request, EoxConfig config, EoxNameRuleFactory rule) {
    this.request = request;
    this.config = config;
    this.inputStream = new ByteBufInputStream(request.content());

    String contentType = this.header("content-type");
    if (TextKit.blankn(contentType))
      contentType = contentType.toLowerCase();
    if (TextKit.blankn(contentType) && contentType.startsWith("multipart/form-data"))
      super.handleUpload(this.inputStream, config, rule);
  }

  private HttpPostRequestDecoder decoder() {
    return new HttpPostRequestDecoder(factory, this.request, this.config.charset());
  }

  @Override
  public Object originRequest() {
    return this.request.originRequest();
  }

  @Override
  public Method method() {
    Method method = Method.of(this.header("X-HTTP-Method-Override"));
    return method == null ? Method.of(this.request.method().name()) : method;
  }

  @Override
  public String context() {
    return this.request.context();
  }

  @Override
  public String uri() {
    String uri = this.request.uri();
    String[] uris = uri.split("\\?");
    return uris[0];
  }

  @Override
  public String url() {
    return this.request.uri();
  }

  @Override
  public RequestBody body() {
    if (this.body != null)
      return this.body;

    String contentType = this.header("content-type");
    if (TextKit.blankn(contentType))
      contentType = contentType.toLowerCase();
    if (TextKit.blankn(contentType) && contentType.startsWith("multipart/form-data"))
      return null;
    try {
      byte[] binary = StreamKit.bytes(this.inputStream);
      this.body = RequestBody.create(binary, this.config.charset());
      return this.body;
    } catch (IOException e) {
      throw new EoException(e.getMessage(), e);
    }
  }

  @Override
  public Cookie[] cookies() {
    if (this.cookies == null)
      this.cookies = NettyParser.parseCookies(this.header("Cookie"));
    return this.cookies;
  }

  @Override
  public Cookie cookieObject(String name) {
    Cookie[] cookies = this.cookies();
    for (Cookie cookie : cookies)
      if (cookie.name().equals(name))
        return cookie;
    return null;
  }

  @Override
  public String cookie(String name, String def) {
    Cookie cookie = this.cookieObject(name);
    return cookie == null ? def : cookie.value();
  }

  @Override
  public Integer cookieToInt(String name, Integer def) {
    String cookie = this.cookie(name);
    return ConvertKit.integer(cookie, def);
  }

  @Override
  public Long cookieToLong(String name, Long def) {
    String cookie = this.cookie(name);
    return ConvertKit.longer(cookie, def);
  }

  @Override
  public String[] headerNames() {
    HttpHeaders headers = this.request.headers();
    Set<String> names = headers.names();
    return names.toArray(new String[names.size()]);
  }

  @Override
  public String header(String name) {
    String[] headerNames = this.headerNames();
    for (String n : headerNames)
      if (n.equalsIgnoreCase(name.toLowerCase()))
        return this.request.headers().get(n);
    return null;
  }

  @Override
  public String para(String name, String def) {
    String[] paras = this.paraValues(name);
    if (CollectionKit.isEmpty(paras))
      return def;
    return ConvertKit.ruleString(paras[0], def);
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

    Map<String, List<String>> ret = new HashMap<>();
    QueryStringDecoder queryDecoder = new QueryStringDecoder(this.url(), this.config.charset());
    Map<String, List<String>> paras = queryDecoder.parameters();
    paras.forEach(ret::put);
    if (this.method() == Method.GET) {
      return super.mapListToArray(ret);
    }
    String contentType = this.header("content-type");
    if (TextKit.blanky(contentType))
      return super.mapListToArray(ret);

    contentType = contentType.toLowerCase();
    if (contentType.startsWith("application/x-www-form-urlencoded")) {
//      HttpPostRequestDecoder decoder = this.decoder();
//      List<InterfaceHttpData> datas = decoder.getBodyHttpDatas();
//      NettyParser.ParseRet parseRet = NettyParser.parsePostData(datas, this.config, this.rule);
//      ret = super.mergeMap(ret, parseRet.paras());
//      parseRet.clear();
//      decoder.cleanFiles();

      String body = null;
      RequestBody rb = this.body();
      if (rb != null)
        body = rb.string();
      if (TextKit.blankn(body)) {
        Map<String, List<String>> bodyParas;
        try {
          bodyParas = EnoaHttpKit.parsePara(URLDecoder.decode(body, this.config.charset().name()));
        } catch (UnsupportedEncodingException e) {
          throw new RuntimeException(e.getMessage(), e);
        }
        ret = super.mergeMap(ret, bodyParas);
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
    super.clear();
    this.request.clear();
//    CollectionKit.clear(this.paraMap);
    CollectionKit.clear(this.cookies);
  }

}
