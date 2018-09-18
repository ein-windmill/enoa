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
package io.enoa.repeater.http;

import io.enoa.log.Log;
import io.enoa.toolkit.EoConst;
import io.enoa.toolkit.convert.ConvertKit;
import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.toolkit.text.TextKit;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class Response {

  private final HttpStatus status;
  private final String contentType;
  private final long contentLength;
  private final List<Cookie> cookies;
  private final List<Header> headers;
  private final ResponseBody body;
  private final Charset charset;

  private Response(Builder builder) {
    this.status = builder.status;
    this.contentType = builder.contentType;
    this.contentLength = builder.contentLength;
    this.cookies = builder.cookies;
    this.headers = builder.headers;
    this.body = builder.body;
    this.charset = builder.charset;
  }

  public Builder newBuilder() {
    return new Builder(this);
  }

  public Cookie[] cookies() {
    return this.cookies.toArray(new Cookie[this.cookies.size()]);
  }

  public List<Header> headers() {
    return this.headers;
  }

  public HttpStatus status() {
    return this.status;
  }

  public String contentType() {
    return this.contentType;
  }

  public long contentLength() {
    return this.contentLength;
  }

  public ResponseBody body() {
    return this.body;
  }

  public Charset charset() {
    return this.charset;
  }

  public static class Builder {
    private HttpStatus status;
    private String contentType;
    private long contentLength;
    private List<Cookie> cookies;
    private List<Header> headers;
    private ResponseBody body;
    private Charset charset;

    public Builder() {
      this.status = HttpStatus.OK;
      this.contentType = "text/html";
      this.cookies = new ArrayList<>();
      this.headers = new ArrayList<>();
      this.charset = EoConst.CHARSET;
    }

    public Builder(Response resp) {
      this.status = resp.status;
      this.contentType = resp.contentType;
      this.contentLength = resp.contentLength;
      this.cookies = resp.cookies;
      this.headers = resp.headers;
      this.body = resp.body;
      this.charset = resp.charset;
    }

    public Response build() {
      return new Response(this);
    }

    public Builder cookie(Cookie cookie) {
      this.cookies.add(cookie);
      return this;
    }

    public Builder header(Header header) {
      this.headers.add(header);
      if ("content-type".equals(TextKit.lower(header.name())))
        this.contentType = header.value();
      if ("content-length".equals(TextKit.lower(header.name())))
        this.contentLength = ConvertKit.integer(header.value());
      if ("set-cookie".equals(TextKit.lower(header.name())))
        Log.warn(EnoaTipKit.message("eo.tip.repeater.usecookie"));
      return this;
    }

    public Builder header(String name, String value) {
      this.header(new Header(name, value));
      return this;
    }

    public Builder status(HttpStatus status) {
      this.status = status;
      return this;
    }

    public Builder contentType(String contentType) {
      this.contentType = contentType;
      return this;
    }

    public Builder contentLength(long length) {
      this.contentLength = length;
      return this;
    }

    public Builder body(ResponseBody body) {
      this.body = body;
      this.contentLength = body.length();
      return this;
    }

    public Builder charset(Charset charset) {
      this.charset = charset;
      return this;
    }

  }
}
