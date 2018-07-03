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
package io.enoa.repeater.provider.jetty.ts;

import io.enoa.repeater.EoxConfig;
import io.enoa.repeater.factory.name.EoxNameRuleFactory;
import io.enoa.repeater.http.*;
import io.enoa.toolkit.alg.UnitConvKit;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.convert.ConvertKit;
import io.enoa.toolkit.file.FileKit;
import io.enoa.toolkit.stream.StreamKit;
import io.enoa.toolkit.text.TextKit;
import io.enoa.toolkit.thr.EoException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

class JettyRequestWrapper implements Request {
  private HttpServletRequest request;
  private EoxConfig config;
  private List<UFile> ufiles = new ArrayList<>();
  private RequestBody body;
  private Cookie[] cookies;

  JettyRequestWrapper(HttpServletRequest request, EoxConfig config, EoxNameRuleFactory rule) {
    this.request = request;
    this.config = config;

    String contentType = this.header("content-type");
    if (TextKit.notBlank(contentType))
      contentType = contentType.toLowerCase();
    if (TextKit.notBlank(contentType) && contentType.startsWith("multipart/form-data")) {
      try {
        String clength = this.header("content-length");
        Long contentLength = Long.parseLong(clength);
        if (contentLength > UnitConvKit.convert(config.maxUploadSize(), UnitConvKit.Unit.MB, UnitConvKit.Unit.BYTE))
          throw new EoException("Posted content length of {0} exceeds limit of {1}", contentLength, this.config.maxUploadSize());

        FileKit.mkdirs(config.tmp());

        for (Part part : this.request.getParts()) {
          String[] infos = this.partInfo(part);
          if (CollectionKit.isEmpty(infos) || infos.length != 2)
            continue;
          String originName = infos[1];


          UFile.Builder ufile = new UFile.Builder()
            .name(infos[0])
            .tmp(config.tmp())
            .originName(originName);

          if (config.holdFile()) {
            byte[] binary = StreamKit.bytes(part.getInputStream());
            this.ufiles.add(ufile.filename(originName).binary(binary).build());
          } else {
            String filename = rule.name(this.config.tmp(), originName);
            part.write(filename);
            this.ufiles.add(ufile.filename(filename).path(Paths.get(this.config.tmp().toString()).resolve(filename)).build());
          }

//          String filename = rule.name(this.config.tmp(), originName);
//          part.write(filename);
////          File file = new File(String.format("%s%s%s", this.config.tmp(), File.separator, filename));
//          this.ufiles.add(new UFile.Builder()
//            .name(infos[0])
//            .originName(originName)
//            .filename(filename)
//            .path(Paths.get(this.config.tmp().toString(), filename))
//            .build());
        }
        this.ufiles = Collections.unmodifiableList(this.ufiles);
      } catch (IOException | ServletException e) {
        throw new EoException(e.getMessage(), e);
      }
    }
  }

  private String[] partInfo(Part part) {
    String contentDispositionHeader = part.getHeader("content-disposition");
    String[] elements = contentDispositionHeader.split(";");
    String name = "";
    for (String element : elements) {
      if (element.trim().startsWith("name"))
        name = element.substring(element.indexOf('=') + 1).trim().replace("\"", "");
      if (!element.trim().startsWith("filename")) {
        continue;
      }
      return new String[]{
        name,
        element.substring(element.indexOf('=') + 1).trim().replace("\"", "")
      };
    }
    return CollectionKit.emptyArray(String.class);
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
    if (TextKit.isBlank(qs))
      return this.request.getRequestURL().toString();
//    return String.format("%s?%s", this.request.getRequestURL().toString(), qs);
    return TextKit.union(this.request.getRequestURL().toString(), "?", qs);
  }

  @Override
  public RequestBody body() {
    try {
      if (this.body != null)
        return this.body;

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
    if (TextKit.isBlank(val))
      return def;
    return ConvertKit.integer(val);
  }

  @Override
  public Long cookieToLong(String name, Long def) {
    String val = this.cookie(name);
    if (TextKit.isBlank(val))
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
    String val = this.para(name);
    return ConvertKit.string(val, def);
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
    return this.request.getParameterMap();
  }

  @Override
  public String[] paraNames() {
    Enumeration<String> parameterNames = this.request.getParameterNames();
    Set<String> rets = new HashSet<>();
    while (parameterNames.hasMoreElements()) {
      rets.add(parameterNames.nextElement());
    }
    return rets.toArray(new String[rets.size()]);
  }

  @Override
  public String[] paraValues(String name) {
    return this.request.getParameterValues(name);
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
    UFile[] files = this.files();
    if (CollectionKit.isEmpty(files))
      return CollectionKit.emptyArray(UFile.class);
    return Arrays.stream(files).filter(f -> f.name().equals(name)).toArray(UFile[]::new);
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
    CollectionKit.clear(this.ufiles);
    this.body = null;
  }

}
