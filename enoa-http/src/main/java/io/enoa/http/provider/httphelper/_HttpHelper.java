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
package io.enoa.http.provider.httphelper;

import io.enoa.chunk.Chunk;
import io.enoa.http.*;
import io.enoa.http.protocol.*;
import io.enoa.http.protocol.enoa.IHttpHandler;
import io.enoa.http.protocol.enoa.IHttpReporter;
import io.enoa.http.provider.httphelper.async.HttpHelperExecutor;
import io.enoa.http.provider.httphelper.conn._HttpHelperConn;
import io.enoa.http.provider.httphelper.http.req._HttpHelperRequest;
import io.enoa.http.proxy.HttpProxy;

import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class _HttpHelper implements Http {


  private HttpMethod method;
  private Charset charset;
  private EoUrl url;
  private boolean traditional;
  private boolean encode;
  private String raw;
  private String contentType;
  private HttpProxy proxy;
  private HttpAuth auth;
  private byte[] binary;
  private Set<HttpCookie> cookies;
  private Set<HttpPara> paras;
  private Set<HttpHeader> headers;
  private List<HttpFormData> formDatas;
  private HttpHelperConfig config;
  private EoExecutor executor;

  private List<IHttpHandler> handlers;
  private List<IHttpReporter> reporters;

  _HttpHelper() {
    this.traditional = true;
    this.encode = false;
    this.contentType = "application/x-www-form-urlencoded";
    this.method = HttpMethod.GET;
    this.charset = Charset.forName("UTF-8");
    this.headers = new HashSet<>();
    this.headers.add(new HttpHeader("User-Agent", "Mozilla/5.0 Enoa/1.7.3-snapshot HttpHelper/4.0"));
    this.executor = HttpHelperExecutor.instance();
  }

  private Object[] toArr(Object value) {
    int len = Array.getLength(value);
    Object[] ret = new Object[len];
    for (int i = 0; i < len; ++i) {
      ret[i] = Array.get(value, i);
    }
    return ret;
  }


  private _HttpHelperRequest readyrequest() {
    if (this.config == null)
      this.config = new HttpHelperConfig.Builder().build();
    if (this.config.debug())
      this.handler(IHttpHandler.def());

    RequestBuilder builder = new RequestBuilder();
    builder.method = this.method;
    builder.charset = this.charset;
    builder.url = this.url;
    builder.traditional = this.traditional;
    builder.encode = this.encode;
    builder.raw = this.raw;
    builder.contentType = this.contentType;
    builder.proxy = this.proxy;
    builder.auth = this.auth;
    builder.binary = this.binary;
    builder.cookies = this.cookies;
    builder.paras = this.paras;
    builder.headers = this.headers;
    builder.formDatas = this.formDatas;
    builder.config = this.config;
    return builder.build();
  }

  @Override
  public HttpResponse emit() {
    _HttpHelperRequest request = this.readyrequest();

    // run handlers
    if (this.handlers != null)
      HttpExtExecutor.instance().handle(this.handlers, request);

    // do request
    _HttpHelperConn conn = new _HttpHelperConn(this.config, request);
    HttpResponse response = conn.execute();

    // run reporters
    if (this.reporters != null)
      HttpExtExecutor.instance().report(this.reporters, response);

    return response;
  }

  @Override
  public HttpResponse chunk(Chunk chunk) {
    _HttpHelperRequest request = this.readyrequest();

    // run handlers
    if (this.handlers != null)
      HttpExtExecutor.instance().handle(this.handlers, request);

    // do request
    _HttpHelperConn conn = new _HttpHelperConn(this.config, request);
    // HttpResponse response =
    HttpResponse response = conn.chunked(chunk);

    // run reporters
    if (this.reporters != null)
      HttpExtExecutor.instance().report(this.reporters, response);

    return response;
  }

  @Override
  public Http executor(EoExecutor executor) {
    if (executor == null)
      throw new IllegalArgumentException("executor == null");
    this.executor = executor;
    return this;
  }

  @Override
  public HttpPromise enqueue(Chunk chunk) {
    return this.executor.enqueue(this.url, this, chunk);
  }

  @Override
  public HttpPromise enqueue() {
    return this.executor.enqueue(this.url, this);
  }

  @Override
  public Http handler(IHttpHandler handler) {
    if (this.handlers == null)
      this.handlers = new ArrayList<>();
    this.handlers.add(handler);
    return this;
  }

  @Override
  public Http reporter(IHttpReporter reporter) {
    if (this.reporters == null)
      this.reporters = new ArrayList<>();
    this.reporters.add(reporter);
    return this;
  }

  @Override
  public Http method(HttpMethod method) {
    if (method == null)
      throw new IllegalArgumentException("method == null");
    this.method = method;
    return this;
  }

  @Override
  public Http config(EoHttpConfig config) {
    this.config = config instanceof HttpHelperConfig ? (HttpHelperConfig) config : new HttpHelperConfig(config);
    return this;
  }

  @Override
  public Http charset(Charset charset) {
    if (charset == null)
      throw new IllegalArgumentException("charset == null");
    this.charset = charset;
    return this;
  }

  @Override
  public Http url(EoUrl url) {
    if (url == null)
      throw new IllegalArgumentException("url == null");
    this.url = url;
    HttpPara[] paras = url.paras();
    if (paras.length == 0)
      return this;
    if (this.paras == null)
      this.paras = new HashSet<>();
    this.paras.addAll(Stream.of(paras).collect(Collectors.toSet()));
    return this;
  }

  @Override
  public Http traditional(boolean traditional) {
    this.traditional = traditional;
    return this;
  }

  @Override
  public Http encode(boolean encode) {
    this.encode = encode;
    return this;
  }

  private Http para(String name, Object value, boolean array) {
    if (this.paras == null)
      this.paras = new HashSet<>();
    if (value instanceof Path) {
      return this.para(name, (Path) value);
    }

    Set<HttpPara> itm = new HashSet<>();
    Iterator<HttpPara> iterator = this.paras.iterator();
    while (iterator.hasNext()) {
      HttpPara para = iterator.next();
      if (!para.name().equals(name))
        continue;
      // 当前添加的参数之前已添加, 将是否数组标志改为 true
      array = true;
      // 若之前添加的参数标记已是数组, 不进行操作
      if (para.array())
        continue;
      // 否则改变之前添加的参数标记为数组
      iterator.remove();
      itm.add(new HttpPara(para.name(), para.value(), true));
    }
    itm.add(new HttpPara(name, value == null ? "" : String.valueOf(value), array));
    this.paras.addAll(itm);
    itm.clear();
    return this;
  }

  @Override
  public Http para(String name, Object value) {
    if (name == null)
      return this;
    if (value != null && value.getClass().isArray()) {
      return this.para(name, this.toArr(value));
    }
    return this.para(name, value == null ? "" : value.toString(), false);
  }

  @Override
  public Http para(String name, Object[] values) {
    if (name == null)
      return this;
    for (Object value : values) {
      this.para(name, value, false);
    }
    return this;
  }

  @Override
  public Http para(String name, Collection values) {
    if (name == null)
      return this;
    values.forEach(c -> this.para(name, c, false));
    return this;
  }

  @Override
  public Http para(String name, Path path) {
    if (path == null)
      throw new IllegalArgumentException("path == null");
    if (!Files.isRegularFile(path))
      throw new RuntimeException(new FileNotFoundException("Not found this file: " + path.toString()));
    if (this.formDatas == null)
      this.formDatas = new ArrayList<>();
    this.formDatas.add(new HttpFormData(name, path));
    return this;
  }

  @Override
  public Http para(String name, String filename, byte[] bytes) {
    if (name == null)
      throw new IllegalArgumentException("name == null");
    if (this.formDatas == null)
      this.formDatas = new ArrayList<>();
    this.formDatas.add(new HttpFormData(name, filename == null ? name : filename, bytes));
    return this;
  }

  @Override
  public Http para(Path path) {
    return this.para(null, path);
  }

  @Override
  public Http raw(String raw, String contentType) {
    if (raw == null)
      throw new IllegalArgumentException("raw == null");
    this.raw = raw;
    if (contentType != null)
      this.contentType = contentType;
    return this;
  }

  @Override
  public Http header(HttpHeader header) {
    if (header == null)
      throw new IllegalArgumentException("header == null");

    HttpHeader rmHeader = null;
    for (HttpHeader ahr : this.headers) {
      if (!ahr.name().equalsIgnoreCase(header.name())) {
        continue;
      }
      rmHeader = ahr;
      if (header.name().equalsIgnoreCase("cookie"))
        header = new HttpHeader(ahr.name(), ahr.value().concat("; ").concat(header.value()));
      break;
    }
    if (rmHeader != null)
      this.headers.remove(rmHeader);
    this.headers.add(header);
    if ("content-type".equals(header.name().toLowerCase()))
      this.contentType(header.value());
    return this;
  }

  @Override
  public Http cookie(String name, String value) {
    if (name == null)
      throw new IllegalArgumentException("name == null");
    if (value == null)
      throw new IllegalArgumentException("value == null");
    if (this.cookies == null)
      this.cookies = new HashSet<>();
    this.cookies.add(new HttpCookie.Builder().name(name).value(value).build());
    return this;
  }

  @Override
  public Http contentType(String contentType) {
    this.contentType = contentType;
    return this;
  }

  @Override
  public Http proxy(HttpProxy proxy) {
    if (proxy == null)
      throw new IllegalArgumentException("proxy == null");
    this.proxy = proxy;
    return this;
  }

  @Override
  public Http auth(HttpAuth auth) {
    if (auth == null)
      throw new IllegalArgumentException("auth == null");
    this.auth = auth;
    return this;
  }

  @Override
  public Http binary(byte[] bytes) {
    if (bytes == null)
      throw new IllegalArgumentException("bytes == null");
    this.binary = bytes;
    return this;
  }

  @Override
  public String toString() {
    return super.toString();
  }
}
