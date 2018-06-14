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
package io.enoa.http.provider.httphelper.http.resp;

import io.enoa.http.protocol.HttpCookie;
import io.enoa.http.protocol.HttpResponse;
import io.enoa.http.protocol.HttpResponseBody;
import io.enoa.http.protocol.HttpVersion;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;
import java.util.zip.GZIPInputStream;

public class _HttpHelperResponse implements HttpResponse {

  private int code;
  private URL url;
  private String protocol;
  private Charset charset;
  private HttpResponseBody body;
  private Map<String, List<String>> header;
  private Set<String> headerNames;
  private Map<String, HttpCookie> cookie;
  private String message;

  public _HttpHelperResponse(HttpURLConnection conn, InputStream inputStream, Charset charset) {
    this.url = conn.getURL();
    this.charset = charset;

    String url = this.url.toString();
    this.protocol = url.startsWith("http://") ? "http" : "https";
    this.header = conn.getHeaderFields();

    try {
      this.code = conn.getResponseCode();
      this.message = conn.getResponseMessage();
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage(), e);
    }

    ByteArrayOutputStream swapStream = null;
    try {
      swapStream = new ByteArrayOutputStream();
      boolean gzip = this.header("Content-Encoding") != null && this.header("Content-Encoding").equalsIgnoreCase("gzip");
      if (gzip)
        inputStream = new GZIPInputStream(inputStream);


      byte[] buff = new byte[1024];
      int rc = 0;
      while ((rc = inputStream.read(buff, 0, 100)) > 0) {
        swapStream.write(buff, 0, rc);
      }
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage(), e);
    } finally {
      if (swapStream != null)
        try {
          swapStream.close();
        } catch (IOException e) {
          // skip
        }
    }

    byte[] bytes = swapStream.toByteArray();
    this.body = HttpResponseBody.create(bytes, this.charset);
  }

//  private void swapHeader() {
////    this.header = new ArrayList<>();
////    this.originHeader.forEach((name, values) -> values.forEach(line -> {
////      this.header.add(new HttpHeader(name == null ? "" : name, line));
////    }));
//  }

  @Override
  public int code() {
    return this.code;
  }

  @Override
  public HttpVersion version() {
    try {
      String prot = this.header(null);
      if (prot == null || "".equals(prot))
        return null;
      prot = prot.substring(0, prot.indexOf(" "));
      return HttpVersion.of(prot);
    } catch (IOException e) {
      return null;
    }
  }

  @Override
  public boolean ok() {
    return "OK".equalsIgnoreCase(this.message);
  }

  @Override
  public String uri() {
    return this.url.getPath();
  }

  @Override
  public String url() {
    return this.url.toString();
  }

  @Override
  public String protocol() {
    return this.protocol;
  }

  @Override
  public String host() {
    return this.url.getHost();
  }

  @Override
  public Charset charset() {
    return this.charset;
  }

  @Override
  public String message() {
    return this.message;
  }

  @Override
  public boolean isRedirect() {
    return this.header("location") != null;
  }

  @Override
  public String[] cookieNames() {
    if (this.cookie == null) {
      this.cookie = new HashMap<>();
      List<java.net.HttpCookie> hcks = new ArrayList<>();
      this.header.keySet().stream()
        .filter(name -> "set-cookie".equalsIgnoreCase(name) || "set-cookie2".equalsIgnoreCase(name))
        .forEach(name -> this.header.get(name).forEach(value -> hcks.addAll(java.net.HttpCookie.parse(value))));
      hcks.forEach(ck -> {
        HttpCookie.Builder builder = new HttpCookie.Builder()
          .name(ck.getName())
          .value(ck.getValue())
          .expires(ck.getMaxAge());
        if (ck.getDomain() != null) {
          if (ck.isHttpOnly()) {
            builder.hostOnlyDomain(ck.getDomain());
          } else {
            builder.domain(ck.getDomain());
          }
        }
        builder.path(ck.getPath());
        if (ck.isHttpOnly())
          builder.httpOnly();
        if (ck.getSecure())
          builder.secure();
        this.cookie.put(ck.getName(), builder.build());
      });
      this.cookie = Collections.unmodifiableMap(this.cookie);
    }
    Set<String> cookieNames = this.cookie.keySet();
    return cookieNames.toArray(new String[cookieNames.size()]);
  }

  @Override
  public HttpCookie cookieObject(String name) {
    return this.cookie.get(name);
  }

  @Override
  public String cookie(String name) {
    HttpCookie cookie = this.cookie.get(name);
    return cookie == null ? null : cookie.value();
  }

  @Override
  public String[] headerNames() {
    if (this.headerNames != null)
      return this.headerNames.toArray(new String[this.headerNames.size()]);
    Set<String> names = new HashSet<>(this.header.keySet());
    this.headerNames = Collections.unmodifiableSet(names);
    return this.headerNames.toArray(new String[this.headerNames.size()]);
  }

  @Override
  public String header(String name, String def) {
    List<String> headers = this.headers(name);
    if (headers.isEmpty())
      return def;
    return headers.get(0);
  }

  @Override
  public String header(String name) {
    return this.header(name, null);
  }

  @Override
  public List<String> headers(String name) {
    if (name == null)
      return this.header.get(null);
    for (String headerName : this.headerNames()) {
      if (name.equalsIgnoreCase(headerName))
        return this.header.get(headerName);
    }
    return Collections.emptyList();
  }

  @Override
  public HttpResponseBody body() {
    return this.body;
  }

  @Override
  public void clear() {
    if (this.headerNames != null)
      this.headerNames.clear();
    if (this.header != null)
      this.header.clear();

  }

  @Override
  public String toString() {
    StringBuilder _ret = new StringBuilder();
    _ret.append(this.header(null)).append("\r\n");
    String[] hnames = this.headerNames();
    for (String hname : hnames) {
      if (hname == null)
        continue;
      List<String> headers = this.headers(hname);
      for (String header : headers) {
        _ret.append(hname).append(" ").append(header).append("\r\n");
      }
    }
    _ret.append("\r\n");

    String contentType = this.header("content-type");
    if (contentType == null) {
      _ret.append("+============================================+\r\n");
      _ret.append("+ Unknown body content type                  +\r\n");
      _ret.append("+============================================+\r\n");
      return _ret.toString();
    }
    if (contentType.contains("text/") ||
      contentType.contains("/json") ||
      contentType.contains("/xml")) {
      _ret.append(this.body().string());
      return _ret.toString();
    }
    _ret.append("+============================================+\r\n");
    _ret.append("+ Can not support octet-stream data show     +\r\n");
    _ret.append("+============================================+\r\n");
    return _ret.toString();
  }
}
