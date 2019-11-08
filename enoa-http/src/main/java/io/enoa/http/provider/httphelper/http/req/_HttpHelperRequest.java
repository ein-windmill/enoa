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
package io.enoa.http.provider.httphelper.http.req;

import io.enoa.http.protocol.HttpHeader;
import io.enoa.http.protocol.HttpMethod;
import io.enoa.http.protocol.HttpVersion;
import io.enoa.http.protocol.enoa.HttpRequest;
import io.enoa.http.provider.httphelper.HttpHelperConfig;
import io.enoa.http.provider.httphelper.conn.ssl.TLSv;
import io.enoa.http.proxy.HttpProxy;

import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Set;

public class _HttpHelperRequest implements HttpRequest {

  private final HttpMethod method;
  private final Charset charset;
  private final String url;
  private final HttpProxy proxy;
  private final Set<HttpHeader> headers;
  private final _HttpHelperRequestBody body;
  private final HttpHelperConfig config;
  private final HttpVersion version;

  private final TLSv tlsv;

  private _HttpHelperRequest(Builder builder) {
    this.method = builder.method;
    this.charset = builder.charset;
    this.url = builder.url;
    this.body = builder.body;
    this.proxy = builder.proxy;
    this.headers = builder.headers;
    this.config = builder.config;
    this.version = builder.version;
    this.tlsv = builder.tlsv;
  }

  @Override
  public HttpMethod method() {
    return method;
  }

  @Override
  public Charset charset() {
    return charset;
  }

  @Override
  public HttpVersion version() {
    return this.version;
  }

  @Override
  public String url() {
    return url;
  }

  @Override
  public HttpProxy proxy() {
    return proxy;
  }

  @Override
  public TLSv tlsv() {
    return this.tlsv;
  }

  @Override
  public _HttpHelperRequestBody body() {
    return this.body;
  }

  @Override
  public Set<HttpHeader> headers() {
    return this.headers;
  }

  @Override
  public HttpHelperConfig config() {
    return this.config;
  }

  @Override
  public String toString() {
    StringBuilder _ret = new StringBuilder();
    String url = this.url();
    String uri = url.substring(url.indexOf("//") + 2);
    uri = uri.substring(uri.indexOf("/"));
    _ret.append(this.method().name()).append(" ").append(uri).append(" ").append("HTTP/1.1").append("\r\n");


    Set<HttpHeader> headers = this.headers();
    headers.forEach(header -> _ret.append(header.name()).append(" ").append(header.value()).append("\r\n"));
    _ret.append("\r\n");

    if (this.body() == null)
      return _ret.toString();

    HttpHeader contentType = headers.stream()
      .filter(h -> h.name().equalsIgnoreCase("content-type"))
      .findFirst()
      .orElse(null);

    if (contentType == null) {
      _ret.append("+============================================+\r\n");
      _ret.append("+ Unknown body content type                  +\r\n");
      _ret.append("+============================================+\r\n");
      return _ret.toString();
    }

    String _ctype = contentType.value().toLowerCase();
    if (_ctype.contains("/x-www-form-urlencoded") ||
      _ctype.contains("/json") ||
      _ctype.contains("/xml") ||
      _ctype.contains("text/")) {
      _ret.append(this.body().string());
      return _ret.toString();
    }
    _ret.append("+============================================+\r\n");
    _ret.append("+ Can not support octet-stream data show     +\r\n");
    _ret.append("+============================================+\r\n");
    return _ret.toString();
  }

  public static class Builder {

    private HttpMethod method;
    private Charset charset;
    private String url;
    private HttpProxy proxy;
    private Set<HttpHeader> headers;
    private _HttpHelperRequestBody body;
    private HttpHelperConfig config;
    private HttpVersion version;

    private TLSv tlsv;

    public Builder() {
      this.version = HttpVersion.HTTP_1_1;
      this.method = HttpMethod.GET;
      this.tlsv = TLSv.V_1_1;
    }

    public _HttpHelperRequest build() {
      return new _HttpHelperRequest(this);
    }

    public Builder tlsv(TLSv tlsv) {
      this.tlsv = tlsv;
      return this;
    }

    public Builder method(HttpMethod method) {
      this.method = method;
      return this;
    }

    public Builder charset(Charset charset) {
      this.charset = charset;
      return this;
    }

    public Builder url(String url) {
      this.url = url;
      return this;
    }

    public Builder proxy(HttpProxy proxy) {
      this.proxy = proxy;
      return this;
    }

    public Builder header(HttpHeader header) {
      if (this.headers == null)
        this.headers = new HashSet<>();
      this.headers.add(header);
      return this;
    }

    public Builder headers(Set<HttpHeader> headers) {
      this.headers = headers;
      return this;
    }

    public Builder body(_HttpHelperRequestBody body) {
      this.body = body;
      return this;
    }

    public Builder config(HttpHelperConfig config) {
      this.config = config;
      return this;
    }

    public Builder version(HttpVersion version) {
      this.version = version;
      return this;
    }

  }
}
