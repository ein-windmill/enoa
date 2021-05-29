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
import io.enoa.http.provider.httphelper.conn.ssl.TLSv;
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

  private EnoaRequestData data;

  _HttpHelper() {
    this.data = new EnoaRequestData();
    this.data.setTraditional(true);
    this.data.setEncode(false);
    this.data.setContentType("application/x-www-form-urlencoded");
    this.data.setMethod(HttpMethod.GET);
    this.data.setCharset(Charset.forName("UTF-8"));
    Set<HttpHeader> headers = new HashSet<>();
    headers.add(new HttpHeader("User-Agent", "Mozilla/5.0 Enoa/1.7.3-snapshot HttpHelper/4.0"));
    this.data.setHeaders(headers);
    this.data.setExecutor(HttpHelperExecutor.instance());
    this.data.setTlsv(TLSv.V_1_2);
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
    if (this.data.getConfig() == null)
      this.data.setConfig(new HttpHelperConfig.Builder().build());
    if (this.data.getConfig().debug())
      this.handler(IHttpHandler.def());

    RequestBuilder builder = new RequestBuilder();
    builder.method = this.data.getMethod();
    builder.charset = this.data.getCharset();
    builder.url = this.data.getUrl();
    builder.traditional = this.data.isTraditional();
    builder.encode = this.data.isEncode();
    builder.raw = this.data.getRaw();
    builder.contentType = this.data.getContentType();
    builder.proxy = this.data.getProxy();
    builder.auth = this.data.getAuth();
    builder.binary = this.data.getBinary();
    builder.cookies = this.data.getCookies();
    builder.paras = this.data.getParas();
    builder.headers = this.data.getHeaders();
    builder.formDatas = this.data.getFormDatas();
    builder.config = this.data.getConfig();
    builder.tlsv = this.data.getTlsv();
    return builder.build();
  }

  @Override
  public HttpResponse emit() {
    _HttpHelperRequest request = this.readyrequest();

    List<IHttpHandler> handlers = this.data.getHandlers();
    HttpHelperConfig config = this.data.getConfig();
    List<IHttpReporter> reporters = this.data.getReporters();

    // run handlers
    if (handlers != null)
      HttpExtExecutor.instance().handle(handlers, request);

    // do request
    _HttpHelperConn conn = new _HttpHelperConn(config, request);
    HttpResponse response = conn.execute();

    // run reporters
    if (reporters != null)
      HttpExtExecutor.instance().report(reporters, response);

    return response;
  }

  @Override
  public HttpResponse chunk(Chunk chunk) {
    _HttpHelperRequest request = this.readyrequest();

    List<IHttpHandler> handlers = this.data.getHandlers();
    HttpHelperConfig config = this.data.getConfig();
    List<IHttpReporter> reporters = this.data.getReporters();

    // run handlers
    if (handlers != null)
      HttpExtExecutor.instance().handle(handlers, request);

    // do request
    _HttpHelperConn conn = new _HttpHelperConn(config, request);
    // HttpResponse response =
    HttpResponse response = conn.chunked(chunk);

    // run reporters
    if (reporters != null)
      HttpExtExecutor.instance().report(reporters, response);

    return response;
  }

  @Override
  public Http executor(EoExecutor executor) {
    if (executor == null)
      throw new IllegalArgumentException("executor == null");
    this.data.setExecutor(executor);
    return this;
  }

  @Override
  public HttpPromise enqueue(Chunk chunk) {
    return this.data.getExecutor().enqueue(this.data.getUrl(), this, chunk);
  }

  @Override
  public Http tlsv(TLSv tlsv) {
    this.data.setTlsv(tlsv);
    return this;
  }

  @Override
  public HttpPromise enqueue() {
    return this.data.getExecutor().enqueue(this.data.getUrl(), this);
  }

  @Override
  public Http handler(IHttpHandler handler) {
    if (this.data.getHandlers() == null)
      this.data.setHandlers(new ArrayList<>());
    this.data.getHandlers().add(handler);
    return this;
  }

  @Override
  public Http reporter(IHttpReporter reporter) {
    if (this.data.getReporters() == null)
      this.data.setReporters(new ArrayList<>());
    this.data.getReporters().add(reporter);
    return this;
  }

  @Override
  public Http method(HttpMethod method) {
    if (method == null)
      throw new IllegalArgumentException("method == null");
    this.data.setMethod(method);
    return this;
  }

  @Override
  public Http config(EoHttpConfig config) {
    this.data.setConfig(
      config instanceof HttpHelperConfig ? (HttpHelperConfig) config : new HttpHelperConfig(config)
    );
    return this;
  }

  @Override
  public Http charset(Charset charset) {
    if (charset == null)
      throw new IllegalArgumentException("charset == null");
    this.data.setCharset(charset);
    return this;
  }

  @Override
  public Http url(EoUrl url) {
    if (url == null)
      throw new IllegalArgumentException("url == null");
    this.data.setUrl(url);
    HttpPara[] paras = url.paras();
    if (paras.length == 0)
      return this;
    if (this.data.getParas() == null)
      this.data.setParas(new HashSet<>());
    this.data.getParas().addAll(Stream.of(paras).collect(Collectors.toSet()));
    return this;
  }

  @Override
  public Http traditional(boolean traditional) {
    this.data.setTraditional(traditional);
    return this;
  }

  @Override
  public Http encode(boolean encode) {
    this.data.setEncode(encode);
    return this;
  }

  private Http para(String name, Object value, boolean array) {
    if (this.data.getParas() == null)
      this.data.setParas(new HashSet<>());
    if (value == null) {
      this.data.getParas().add(new HttpPara(name, "", array));
      return this;
    }
    if (value instanceof Path) {
      return this.para(name, (Path) value);
    }

    Set<HttpPara> itm = new HashSet<>();
    Iterator<HttpPara> iterator = this.data.getParas().iterator();
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
    itm.add(new HttpPara(name, String.valueOf(value), array));
    this.data.getParas().addAll(itm);
    itm.clear();
    return this;
  }

  @Override
  public Http para(String name, Object value) {
    if (name == null)
      return this;
    if (value == null) {
      return this.para(name, "", false);
    }
    if (value.getClass().isArray()) {
      return this.para(name, this.toArr(value));
    }
    return this.para(name, value.toString(), false);
  }

  @Override
  public Http para(String name, Object[] values) {
    if (name == null)
      return this;
    if (values == null) {
      return this.para(name, (Object) null);
    }
    for (Object value : values) {
      this.para(name, value, false);
    }
    return this;
  }

  @Override
  public Http para(String name, Collection values) {
    if (name == null)
      return this;
    if (values == null) {
      return this.para(name, (Object) null);
    }
    values.forEach(c -> this.para(name, c, false));
    return this;
  }

  @Override
  public Http para(String name, Path path) {
    if (path == null) {
      return this.para(name, (Object) null);
    }
    if (!Files.isRegularFile(path))
      throw new RuntimeException(new FileNotFoundException("Not found this file: " + path.toString()));
    if (this.data.getFormDatas() == null)
      this.data.setFormDatas(new ArrayList<>());
    this.data.getFormDatas().add(new HttpFormData(name, path));
    return this;
  }

  @Override
  public Http para(String name, String filename, byte[] bytes) {
    if (name == null)
      throw new IllegalArgumentException("name == null");
    if (this.data.getFormDatas() == null)
      this.data.setFormDatas(new ArrayList<>());
    this.data.getFormDatas().add(new HttpFormData(name, filename == null ? name : filename, bytes));
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
    this.data.setRaw(raw);
    if (contentType != null)
      this.data.setContentType(contentType);
    return this;
  }

  @Override
  public Http header(HttpHeader header) {
    if (header == null)
      throw new IllegalArgumentException("header == null");

    HttpHeader rmHeader = null;
    for (HttpHeader ahr : this.data.getHeaders()) {
      if (!ahr.name().equalsIgnoreCase(header.name())) {
        continue;
      }
      rmHeader = ahr;
      if (header.name().equalsIgnoreCase("cookie"))
        header = new HttpHeader(ahr.name(), ahr.value().concat("; ").concat(header.value()));
      break;
    }
    if (rmHeader != null)
      this.data.getHeaders().remove(rmHeader);
    this.data.getHeaders().add(header);
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
    if (this.data.getCookies() == null)
      this.data.setCookies(new HashSet<>());
    this.data.getCookies().add(new HttpCookie.Builder().name(name).value(value).build());
    return this;
  }

  @Override
  public Http contentType(String contentType) {
    this.data.setContentType(contentType);
    return this;
  }

  @Override
  public Http proxy(HttpProxy proxy) {
    if (proxy == null)
      throw new IllegalArgumentException("proxy == null");
    this.data.setProxy(proxy);
    return this;
  }

  @Override
  public Http auth(HttpAuth auth) {
    if (auth == null)
      throw new IllegalArgumentException("auth == null");
    this.data.setAuth(auth);
    return this;
  }

  @Override
  public Http binary(byte[] bytes) {
    if (bytes == null)
      throw new IllegalArgumentException("bytes == null");
    this.data.setBinary(bytes);
    return this;
  }

  @Override
  public HttpRequestData data() {
    return this.data;
  }

  @Override
  public String toString() {
    return super.toString();
  }
}
