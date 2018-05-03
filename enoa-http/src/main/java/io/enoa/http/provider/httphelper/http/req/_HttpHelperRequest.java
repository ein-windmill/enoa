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
package io.enoa.http.provider.httphelper.http.req;

import io.enoa.http.protocol.HttpHeader;
import io.enoa.http.protocol.HttpMethod;
import io.enoa.http.provider.httphelper.HttpHelperConfig;
import io.enoa.http.proxy.HttpProxy;

import java.nio.charset.Charset;
import java.util.Set;

public class _HttpHelperRequest {

  private final HttpMethod method;
  private final Charset charset;
  private final String url;
  private final HttpProxy proxy;
  private final Set<HttpHeader> headers;
  private final _HttpHelperRequestBody body;
  private final HttpHelperConfig config;

  _HttpHelperRequest(Builder builder) {
    this.method = builder.method;
    this.charset = builder.charset;
    this.url = builder.url;
    this.body = builder.body;
    this.proxy = builder.proxy;
    this.headers = builder.headers;
    this.config = builder.config;
  }

  public HttpMethod method() {
    return method;
  }

  public Charset charset() {
    return charset;
  }

  public String url() {
    return url;
  }

  public HttpProxy proxy() {
    return proxy;
  }

  public _HttpHelperRequestBody body() {
    return this.body;
  }

  public Set<HttpHeader> headers() {
    return this.headers;
  }

  public HttpHelperConfig config() {
    return this.config;
  }

  @Override
  public String toString() {
    return "_HttpHelperRequest{" +
      "method=" + method +
      ", charset=" + charset +
      ", url='" + url + '\'' +
      ", proxy=" + proxy +
      ", headers=" + headers +
      ", body=" + body +
      '}';
  }

  public static class Builder {

    private HttpMethod method;
    private Charset charset;
    private String url;
    private HttpProxy proxy;
    private Set<HttpHeader> headers;
    private _HttpHelperRequestBody body;
    private HttpHelperConfig config;

    public _HttpHelperRequest build() {
      return new _HttpHelperRequest(this);
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

  }
}
