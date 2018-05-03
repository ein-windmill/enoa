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
package io.enoa.repeater.provider.tio.ts;

import io.enoa.repeater.EoxConfig;
import io.enoa.repeater.factory.http.EoxAbstractRequest;
import io.enoa.repeater.factory.name.EoxNameRuleFactory;
import io.enoa.repeater.http.*;
import io.enoa.repeater.kit.http.EnoaHttpKit;
import io.enoa.repeater.provider.tio.server.plus._RepeaterTioRequest;
import io.enoa.toolkit.alg.UnitConvKit;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.convert.ConvertKit;
import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.toolkit.file.FileKit;
import io.enoa.toolkit.text.TextKit;
import io.enoa.toolkit.thr.EoException;
import org.tio.http.common.UploadFile;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

class TioRequestWrapper extends EoxAbstractRequest {

  private _RepeaterTioRequest request;
  private EoxConfig config;
  private EoxNameRuleFactory rule;
  private List<UFile> ufiles = new ArrayList<>();
  private Map<String, String[]> paraMap;
  private Cookie[] cookies;

  TioRequestWrapper(_RepeaterTioRequest request, EoxConfig config, EoxNameRuleFactory rule) throws IOException {
    this.request = request;
    this.config = config;
    this.rule = rule;
    String contentType = this.header("content-type");
    if (TextKit.notBlank(contentType) && contentType.startsWith("multipart/form-data"))
      this.handleUpload();
  }

  private String[] httpInfos() {
    String headerString = this.request.getHeaderString();
    String[] headerLine = headerString.split("\n");
    String reqinfo = headerLine[0];
    return reqinfo.split(" ");
  }

  private void handleUpload() throws IOException {
    String clength = this.header("content-length");
    Long contentLength = Long.parseLong(clength);
    if (contentLength > UnitConvKit.convert(config.maxUploadSize(), UnitConvKit.Unit.MB, UnitConvKit.Unit.BYTE))
      throw new EoException(EnoaTipKit.message("eo.tip.repeater.upload_exceed_length", contentLength, this.config.maxUploadSize()));

    Map<String, Object[]> params = this.request.getParams();
    if (CollectionKit.isEmpty(params))
      return;

//    List<String> rmkeys = new ArrayList<>();
    for (String k : params.keySet()) {
      Object[] v = params.get(k);
      for (Object o : v) {
        if (!(o instanceof UploadFile))
          continue;
        UploadFile uf = (UploadFile) o;

        UFile.Builder ufile = new UFile.Builder()
          .name(k)
          .tmp(this.config.tmp())
          .originName(uf.getName());

        if (this.config.holdFile()) {
          this.ufiles.add(ufile.filename(uf.getName()).binary(uf.getData()).build());
        } else {
          String newname = this.rule.name(this.config.tmp(), uf.getName());
          Path newfilepath = this.config.tmp().resolve(newname);
          FileKit.write(newfilepath, ByteBuffer.wrap(uf.getData()));
          this.ufiles.add(ufile.filename(newname).path(newfilepath).build());
        }

//        String newname = this.rule.name(this.config.tmp(), uf.getName());
//        File newfile = new File(this.config.tmp().toString(), newname);
//        OutputStream fileOut = new BufferedOutputStream(new FileOutputStream(newfile));
//        byte[] data = uf.getData();
//        fileOut.write(data, 0, data.length);
//        fileOut.close();
////        rmkeys.add(k);
//        this.ufiles.add(
//          new UFile.Builder()
//            .name(k)
//            .originName(uf.getName())
//            .filename(newname)
//            .path(Paths.get(newfile.toURI()))
//            .build()
//        );
      }
    }
    this.ufiles = Collections.unmodifiableList(this.ufiles);
//    rmkeys.forEach(params::remove);
//    CollectionKit.clear(rmkeys);
  }

  @Override
  public Object originRequest() {
    return this.request.originRequest();
  }

  @Override
  public Method method() {
    Method method = Method.of(this.header("X-HTTP-Method-Override"));
    return method == null ? Method.of(this.httpInfos()[0]) : method;
  }

  @Override
  public String context() {
    return this.request.context();
  }

  @Override
  public String uri() {
    String url = this.url();
    if (!url.contains("?"))
      return url;
    return url.substring(0, url.indexOf("?"));
  }

  @Override
  public String url() {
    return this.httpInfos()[1];
  }

  @Override
  public RequestBody body() {
    return RequestBody.create(this.request.getBodyString(), this.config.charset());
  }

  @Override
  public Cookie[] cookies() {
    if (this.cookies != null)
      return this.cookies;

    List<org.tio.http.common.Cookie> cookies = this.request.getCookies();
    if (CollectionKit.isEmpty(cookies))
      return CollectionKit.emptyArray(Cookie.class);
    List<Cookie> rets = new ArrayList<>();
    for (org.tio.http.common.Cookie cookie : cookies) {
      Cookie ret = new Cookie.Builder()
        .name(cookie.getName())
        .value(cookie.getValue())
        .build();
      rets.add(ret);
    }
    this.cookies = rets.toArray(new Cookie[rets.size()]);
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
    if (cookie == null)
      return def;
    return cookie.value();
  }

  @Override
  public Integer cookieToInt(String name, Integer def) {
    String cookie = this.cookie(name);
    return ConvertKit.integer(cookie, def);
  }

  @Override
  public Long cookieToLong(String name, Long def) {
    String cookie = this.cookie(name);
    return ConvertKit.longx(cookie, def);
  }

  @Override
  public String[] headerNames() {
    Set<String> names = this.request.getHeaders().keySet();
    return names.toArray(new String[names.size()]);
  }

  @Override
  public String header(String name) {
    for (String n : this.headerNames())
      if (n.equalsIgnoreCase(name.toLowerCase()))
        return this.request.getHeader(n);
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
    return ConvertKit.longx(this.para(name), def);
  }

  @Override
  public Boolean paraToBoolean(String name, Boolean def) {
    return ConvertKit.bool(this.para(name), def);
  }

  @Override
  public Double paraToDouble(String name, Double def) {
    return ConvertKit.doublex(this.para(name), def);
  }

  @Override
  public Date paraToDate(String name, String format, Date def) {
    return ConvertKit.date(this.para(name), format, def);
  }

  @Override
  public Map<String, String[]> paraMap() {
    if (this.paraMap != null)
      return this.paraMap;

    Map<String, Object[]> paras = this.request.getParams();
    String contentType = this.header("content-type");
    if (TextKit.notBlank(contentType))
      contentType = contentType.toLowerCase();

    if (paras == null && TextKit.notBlank(contentType) && !contentType.startsWith("multipart/form-data"))
      return null;

    Map<String, String[]> rets = new HashMap<>();

    // tio 在 文件上传或请求体提交时无法解析 url 参数, 此处进行分析进行合并
    Map<String, List<String>> urlPara = EnoaHttpKit.parsePara(this.url());

    if (paras != null && TextKit.notBlank(contentType) && contentType.startsWith("multipart/form-data")) {
      for (String k : paras.keySet()) {
        Object[] vos = paras.get(k);
        String[] vals = Stream.of(vos)
          .filter(v -> v != null && !(v instanceof UploadFile))
          .map(Object::toString)
          .toArray(String[]::new);
        if (CollectionKit.notEmpty(vals))
          rets.put(k, vals);
      }
      rets = super.paraMap(super.mapArrayToList(rets), urlPara);
      CollectionKit.clear(urlPara);
      this.paraMap = rets;
      return this.paraMap;
    }

    if (paras != null) {
      for (String k : paras.keySet()) {
        Object[] v = paras.get(k);
        String[] vals = Stream.of(v)
          .filter(Objects::nonNull)
          .map(Object::toString)
          .toArray(String[]::new);
        if (CollectionKit.notEmpty(vals))
          rets.put(k, vals);
      }
      if (TextKit.notBlank(contentType) && !contentType.startsWith("application/x-www-form-urlencoded")) {
        rets = super.paraMap(super.mapArrayToList(rets), urlPara);
        CollectionKit.clear(urlPara);
        this.paraMap = rets;
        return this.paraMap;
      }
      CollectionKit.clear(urlPara);
      this.paraMap = Collections.unmodifiableMap(rets);
      return this.paraMap;
    }

    return null;
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
      rets[i] = ConvertKit.longx(vals[i]);
    }
    return rets;
  }

  @Override
  public <T> T attr(String name) {
//    Object obj = this.request.getAttribute(name);
//    if (obj instanceof SerializableObject) {
//      return ((SerializableObject<T>) obj).target();
//    }
//    return (T) this.request.getAttribute(name);
    return this.request.attr(name);
  }

  @Override
  public <T> Request attr(String name, T data) {
//    if (data instanceof Serializable) {
//      this.request.setAttribute(name, (Serializable) data);
//      return this;
//    }
//    this.request.setAttribute(name, new SerializableObject<>(data));
//    return this;
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

  @Override
  public UFile[] files() {
    String contentType = this.header("content-type");
    if (TextKit.isBlank(contentType) || !contentType.startsWith("multipart/form-data"))
      return CollectionKit.emptyArray(UFile.class);
    return this.ufiles.toArray(new UFile[this.ufiles.size()]);
  }

  @Override
  public UFile[] files(String name) {
    return this.ufiles.stream().filter(u -> u.name().equals(name)).toArray(UFile[]::new);
  }

  @Override
  public UFile file(String name) {
    UFile[] files = this.files(name);
    if (CollectionKit.isEmpty(files))
      return null;
    return files[0];
  }

  @Override
  public void clear() {
    this.request.clear();
    CollectionKit.clear(this.ufiles);
    CollectionKit.clear(this.cookies);
  }


//  private class SerializableObject<T> implements Serializable {
//    private T target;
//
//    SerializableObject(T target) {
//      this.target = target;
//    }
//
//    T target() {
//      return this.target;
//    }
//  }

}
