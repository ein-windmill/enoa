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

import io.enoa.http.EoUrl;
import io.enoa.http.HttpAuth;
import io.enoa.http.kit.SafeURL;
import io.enoa.http.protocol.*;
import io.enoa.http.provider.httphelper.http.req._HttpHelperRequest;
import io.enoa.http.provider.httphelper.http.req._HttpHelperRequestBody;
import io.enoa.http.proxy.HttpProxy;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

class RequestBuilder {

  private static final String HYPHENS = "---------------------------";
  private static final String DISPOSITION_PREFIX = "--";
  private static final String DISPOSITION_END = "\r\n";

//  private static final HttpHelperConfig DEF_CONFIG;

//  static {
//    DEF_CONFIG = new HttpHelperConfig.Builder().build();
//  }

  HttpMethod method;
  Charset charset;
  EoUrl url;
  boolean traditional;
  boolean encode;
  String contentType;
  HttpProxy proxy;
  HttpAuth auth;
  String raw;
  byte[] binary;
  Set<HttpCookie> cookies;
  Set<HttpHeader> headers;
  Set<HttpPara> paras;
  List<HttpFormData> formDatas;
  HttpHelperConfig config;

  private _HttpHelperRequest.Builder request;
  private _HttpHelperRequestBody body;
  private String boundary;

  _HttpHelperRequest build() {
    this.request = new _HttpHelperRequest.Builder();
    this.buildAuth();
    this.buildBody();
    this.buildHeader();
    this.buildContentType();
    this.buildConfig();

    this.request.url(this.url.end());
    this.request.charset(this.charset);
    this.request.headers(this.headers);
    this.request.method(this.method);
    this.request.proxy(this.proxy);
    this.request.body(this.body);
    this.clear();
    return this.request.build();
  }

  private void clear() {
    if (this.cookies != null)
      this.cookies.clear();
    if (this.paras != null)
      this.paras.clear();
    if (this.formDatas != null)
      this.formDatas.clear();
  }

  /**
   * builder header
   */
  private void buildHeader() {
    this.fillCookie();
    this.request.headers(this.headers);

    boolean hasHost = this.headers.stream().anyMatch(header -> header.name().equalsIgnoreCase("host"));
    if (hasHost)
      return;

    String _url = this.url.end();
    String host;
    int ix = _url.indexOf("/", _url.indexOf("//") + 2);
    if (ix == -1) {
      ix = _url.indexOf("?");
      if (ix > 0) {
        host = _url.substring(0, ix);
      } else {
        host = _url;
      }
    } else {
      host = _url.substring(0, ix);
    }
    this.request.header(new HttpHeader("Host", host));
  }

  private HttpHeader header(String name) {
    HttpHeader ret = null;
    for (HttpHeader header : this.headers) {
      if (!header.name().equalsIgnoreCase(name))
        continue;
      ret = header;
      break;
    }
    return ret;
  }

  private void fillCookie() {
    if (this.cookies == null || this.cookies.isEmpty())
      return;
    String ck = String.join("; ", this.cookies.stream().map(c -> c.name().concat("=").concat(c.value())).collect(Collectors.toSet()));
    HttpHeader ahr = this.header("cookie");
    if (ahr != null) {
      ck = ahr.value().concat("; ").concat(ck);
      this.headers.remove(ahr);
    } else {
      ahr = new HttpHeader("Cookie", ck);
    }
    this.cookies.clear();
    this.headers.add(new HttpHeader(ahr.name(), ck));
  }

  private void buildConfig() {
    this.request.config(this.config);
  }

  private void buildContentType() {
    HttpHeader header = this.header("content-type");
    if (header != null && !header.value().startsWith("multipart/form-data")) {
      String value = header.value();
      value = this.newContentType(value);
      this.headers.remove(header);
      this.headers.add(new HttpHeader(header.name(), value));
      return;
    }

    this.contentType = this.newContentType(this.contentType);
    this.headers.remove(header);
    this.headers.add(new HttpHeader("Content-Type", this.contentType));
  }

  private String newContentType(String contentType) {
    if (contentType == null)
      return "application/x-www-form-urlencoded";
    String checkType = contentType.toLowerCase().trim().replace(" ", "");
    if (checkType.startsWith("multipart/form-data")) {
      // 無需判定, 直接取用
//      if (checkType.contains(";"))
//        return contentType;
//      return checkType.concat(";charset=").concat(this.charset.name());
      return contentType;
    }
    if (checkType.startsWith("application/octet-stream"))
      return contentType;
    if (checkType.contains(";charset"))
      return contentType;
    if (!checkType.contains(";")) {
      return this.charset == null ? contentType : contentType.concat("; charset=").concat(this.charset.name());
    }
    return contentType;
  }

  private void buildAuth() {
    if (this.auth == null)
      return;
    HttpAuth.Where where = this.auth.where();
    String identity = this.auth.identity();
    String token = this.auth.token();
    if (token == null)
      return;
    switch (where) {
      case URL:
        Set<HttpPara> paras = HttpPara.parse(token);
        paras.forEach(p -> this.url.para(this.encode ? SafeURL.encode(p.name(), this.charset) : p.name(),
          this.encode ? SafeURL.encode(p.value(), this.charset) : p.value()));
        break;
      case HEADER:
        HttpHeader header = this.header("Authorization");
        if (header != null)
          this.headers.remove(header);
        this.headers.add(new HttpHeader("Authorization", identity == null ? token : identity.concat(" ").concat(token)));
        break;
      default:
        throw new IllegalArgumentException("Unrecognized auth where type. => " + where);
    }
  }

  private void buildBody() {
    if (this.paras != null && (this.raw == null && this.formDatas == null && this.binary == null)) {
      this.buildParasBody();
      return;
    }
    if (this.raw != null && (this.formDatas == null && this.binary == null)) {
      this.buildRawBody();
      return;
    }
    if (this.formDatas != null && (this.raw == null && this.binary == null)) {
      this.buildFormDataBody();
      return;
    }
    if (this.binary != null && (this.raw == null && this.formDatas == null)) {
      this.buildBinaryBody();
      return;
    }
    // get request
    if (this.paras == null && this.raw == null && this.formDatas == null && this.binary == null) {
      return;
    }
    throw new IllegalArgumentException("Multiple request types can not exist at the same time.");
  }

  private void buildParasBody() {
    if (this.paras == null)
      return;

    if (this.method == HttpMethod.GET) {
      this.paras.forEach(p -> {
        EoUrl eu = this.url.traditional(this.traditional).charset(this.charset);
        if (this.encode)
          eu.encode();
        eu.para(p.name(), p.value());
      });
      return;
    }

    StringBuilder body = new StringBuilder();
    int i = 0;
    for (HttpPara para : this.paras) {
      body.append(this.encode ? para.output(this.traditional, this.charset) : para.output(this.traditional));
      if (i + 1 < this.paras.size())
        body.append("&");
      i += 1;
    }

    this.body = _HttpHelperRequestBody.create(body.toString(), this.charset);
  }

  private void buildRawBody() {
    if (this.paras != null) {
      this.paras.forEach(p -> this.url.para(this.encode ? SafeURL.encode(p.name(), this.charset) : p.name(),
        this.encode ? SafeURL.encode(p.value(), this.charset) : p.value()));
    }
    if (this.contentType == null)
      this.contentType = "text/plain";

    this.body = _HttpHelperRequestBody.create(this.raw, this.charset);
  }

  private void buildBinaryBody() {
    if (this.paras != null) {
      this.paras.forEach(p -> this.url.para(this.encode ? SafeURL.encode(p.name(), this.charset) : p.name(),
        this.encode ? SafeURL.encode(p.value(), this.charset) : p.value()));
    }
    if (this.contentType == null)
      this.contentType = "application/octet-stream";
    this.body = _HttpHelperRequestBody.create(this.binary, this.charset);
  }

  private void buildFormDataBody() {
    if (this.paras != null) {
      Set<String> fdNames = this.formDatas.stream().map(HttpFormData::name).collect(Collectors.toSet());
      Set<String> paNames = this.paras.stream().map(HttpPara::name).collect(Collectors.toSet());
      int paraSize = paNames.size();
      paNames.addAll(fdNames);
      if (paNames.size() != fdNames.size() + paraSize) {
        paNames.clear();
        fdNames.clear();
        throw new RuntimeException("FORMDATA and PARAS can not have the same parameter name.");
      }
      paNames.clear();
      fdNames.clear();
      this.paras.forEach(para -> this.formDatas.add(new HttpFormData(para.name(), para.value())));
    }
    this.boundary = this.newBoundary();
    this.contentType = "multipart/form-data; boundary=".concat(HYPHENS).concat(this.boundary);

    ByteArrayOutputStream output = new ByteArrayOutputStream();
    for (HttpFormData data : this.formDatas) {
      switch (data.type()) {
        case TEXT:
          String dispText = new StringBuilder()
            .append(this.createDisposition(data.name()))
            .append(data.text()).toString();
          byte[] textBytes = dispText.getBytes(this.charset);
          output.write(textBytes, 0, textBytes.length);
          break;
        case FILE:
          String contentType = null;
          try {
            contentType = Files.probeContentType(data.file());
          } catch (IOException ignored) {
          }
          String dispFile = this.createDisposition(data.name(), data.file().getFileName().toString(), contentType);
          byte[] fileDispBytes = dispFile.getBytes(this.charset);
          output.write(fileDispBytes, 0, fileDispBytes.length);
          byte[] fileBytes = this.readFile(data.file());
          output.write(fileBytes, 0, fileBytes.length);
          break;
        case BINARY:
          String dispBinary = this.createDisposition(data.name(), data.filename());
          byte[] binaryDispBytes = dispBinary.getBytes(this.charset);
          output.write(binaryDispBytes, 0, binaryDispBytes.length);
          output.write(data.binary(), 0, data.binary().length);
          break;
        default:
          throw new IllegalArgumentException("Unrecognized formdata type: " + data.type());
      }
      byte[] endBytes = DISPOSITION_END.getBytes(this.charset);
      output.write(endBytes, 0, endBytes.length);
    }

    String dispEnd = this.endDisposition();
    byte[] endBytes = dispEnd.getBytes(this.charset);
    output.write(endBytes, 0, endBytes.length);

    byte[] bytes = output.toByteArray();
    this.body = _HttpHelperRequestBody.create(bytes, this.charset);
  }


  private String createDisposition(String name) {
    StringBuilder ret = new StringBuilder();
    ret.append(DISPOSITION_PREFIX)
      .append(HYPHENS)
      .append(this.boundary)
      .append(DISPOSITION_END);
    ret.append("Content-Disposition: form-data; ")
      .append("name=\"").append(name).append("\"")
      .append(DISPOSITION_END);
    ret.append(DISPOSITION_END);
    return ret.toString();
  }

  private String createDisposition(String name, String filename) {
    return createDisposition(name, filename, null);
  }

  private String createDisposition(String name, String filename, String contentType) {
    StringBuilder ret = new StringBuilder();
    ret.append(DISPOSITION_PREFIX)
      .append(HYPHENS)
      .append(this.boundary)
      .append(DISPOSITION_END);
//    String fileName = path.getFileName().toString();
    ret.append("Content-Disposition: form-data; ")
      .append("name=\"").append(name == null ? "" : name).append("\"; ")
      .append("filename=\"").append(filename).append("\"")
      .append(DISPOSITION_END);

    if (contentType == null) {
      ret.append(DISPOSITION_END);
      return ret.toString();
    }
    ret.append("Content-Type: ").append(contentType).append(DISPOSITION_END);
    ret.append(DISPOSITION_END);
    return ret.toString();
  }

  private String endDisposition() {
    return HYPHENS.concat("--").concat(this.boundary).concat("--").concat(DISPOSITION_END);
  }

  private String newBoundary() {
    String uuid = UUID.randomUUID().toString();
    uuid = uuid.replace("-", "");
    return "HttpHelperBoundary".concat(uuid);
  }

  private byte[] readFile(Path path) {
    FileChannel channel;
    try (FileInputStream fis = new FileInputStream(path.toFile())) {
      channel = fis.getChannel();
      ByteArrayOutputStream output = new ByteArrayOutputStream();
      ByteBuffer bbuf = ByteBuffer.allocate(1024);
      while (channel.read(bbuf) != -1) {
        output.write(bbuf.array(), 0, bbuf.position());
        bbuf.clear();
      }
      return output.toByteArray();
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

}
