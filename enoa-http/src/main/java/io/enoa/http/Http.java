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
package io.enoa.http;

import io.enoa.http.protocol.HttpHeader;
import io.enoa.http.protocol.HttpMethod;
import io.enoa.http.protocol.HttpPara;
import io.enoa.http.protocol.HttpPromise;
import io.enoa.http.protocol.enoa.HttpHandler;
import io.enoa.http.provider.httphelper.HttpHelperProvider;
import io.enoa.http.proxy.HttpProxy;
import io.enoa.http.proxy.TcpProxy;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.Collection;

public interface Http extends EoEmit {

//  static EoHttp def() {
//    return HttpHelperProvider.instance();
//  }

  static Http use(EoHttp http) {
    return http.http();
  }

  static Http use() {
    return use(HttpHelperProvider.instance());
  }

  static Http request() {
    return use();
  }

  static Http request(String url) {
    return request(EoUrl.with(url));
  }

  static Http request(HttpMethod method, String url) {
    return request(method, EoUrl.with(url));
  }

  static Http request(EoUrl url) {
    return request().url(url);
  }

  static Http request(HttpMethod method, EoUrl url) {
    return request().method(method).url(url);
  }

  Http executor(EoExecutor executor);

  HttpPromise enqueue();

  Http handler(HttpHandler handler);

  Http method(HttpMethod method);

  Http config(EoHttpConfig config);

  Http charset(Charset charset);

  default Http url(String url) {
    if (url == null)
      throw new IllegalArgumentException("url == null");
    return this.url(EoUrl.with(url));
  }

  Http url(EoUrl url);

  default Http traditional() {
    return this.traditional(true);
  }

  Http traditional(boolean traditional);

  default Http encode() {
    return this.encode(true);
  }

  Http encode(boolean encode);

  Http para(String name, Object value);

  Http para(String name, Object... values);

  Http para(String name, Collection values);

  Http para(String name, Path path);

  Http para(String name, String filename, byte[] bytes);

  default Http para(HttpPara para) {
    return this.para(para.name(), para.value());
  }

  default Http para(HttpPara... paras) {
    if (paras == null)
      throw new IllegalArgumentException("paras == null");
    for (HttpPara para : paras) {
      this.para(para.name(), para.value());
    }
    return this;
  }

  default Http para(Collection<HttpPara> paras) {
    if (paras == null)
      throw new IllegalArgumentException("paras == null");
    paras.forEach(para -> this.para(para.name(), para.value()));
    return this;
  }

  Http para(Path path);

  default Http raw(String raw) {
    return this.raw(raw, null);
  }

  Http raw(String raw, String contentType);

  default Http header(String name, String value) {
    this.header(new HttpHeader(name, value));
    return this;
  }

  Http header(HttpHeader header);

  default Http header(HttpHeader[] headers) {
    if (headers == null)
      throw new IllegalArgumentException("headers == null");
    for (HttpHeader header : headers) {
      this.header(header);
    }
    return this;
  }

  default Http header(Collection<HttpHeader> headers) {
    if (headers == null)
      throw new IllegalArgumentException("headers == null");
    headers.forEach(this::header);
    return this;
  }

  Http cookie(String name, String value);

  Http contentType(String contentType);

  Http proxy(HttpProxy proxy);

  default Http proxy(String host, int port) {
    return this.proxy(host, port, null, null);
  }

  default Http proxy(String host, int port, String user) {
    return this.proxy(host, port, user, null);
  }

  default Http proxy(String host, int port, String user, String passwd) {
    return this.proxy(new TcpProxy(host, port, user, passwd));
  }

  Http auth(HttpAuth auth);

  Http binary(byte[] bytes);

  default Http binary(ByteBuffer binary) {
    if (binary == null)
      throw new IllegalArgumentException("binary == null");
    return this.binary(binary.array());
  }

}
