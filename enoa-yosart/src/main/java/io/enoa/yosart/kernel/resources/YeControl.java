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
package io.enoa.yosart.kernel.resources;

import io.enoa.repeater.http.Cookie;
import io.enoa.repeater.http.Header;
import io.enoa.repeater.http.HttpStatus;
import io.enoa.repeater.http.Response;
import io.enoa.yosart.kernel.render.YoRender;
import io.enoa.yosart.resp.Respuse;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Path;

public abstract class YeControl<CTL> extends YaControl {


  private void transferAttr() {
    for (String name : super.request().attrNames())
      super.resp().attr(name, super.request().attr(name));
  }

  protected Respuse renderer(String type) {
    return super.resp().use(type);
  }

  protected Respuse renderer(YoRender.Type type) {
    return super.resp().use(type);
  }

  protected Response render(String body) {
    this.transferAttr();
    return super.resp().render(body).end();
  }

  protected Response render(String contentType, String body) {
    this.transferAttr();
    return super.resp().render(contentType, body).end();
  }

  protected Response render(String contentType, String body, Charset charset) {
    this.transferAttr();
    return super.resp().render(contentType, body, charset).end();
  }

  protected Response render(HttpStatus status, String body) {
    this.transferAttr();
    return super.resp().render(status, body).end();
  }

  protected Response render(HttpStatus status, String body, Charset charset) {
    this.transferAttr();
    return super.resp().render(status, body, charset).end();
  }

  protected Response render(HttpStatus status, String contentType, String body, Charset charset) {
    this.transferAttr();
    return super.resp().render(status, contentType, body, charset).end();
  }

  protected Response renderTemplate(String viewPath) {
    this.transferAttr();
    return super.resp().renderTemplate(viewPath).end();
  }

  protected Response renderTemplate(String viewPath, Charset charset) {
    this.transferAttr();
    return super.resp().renderTemplate(viewPath, charset).end();
  }

  protected Response renderText(String text) {
    this.transferAttr();
    return super.resp().renderText(text).end();
  }

  protected Response renderText(String text, Charset charset) {
    this.transferAttr();
    return super.resp().renderText(text, charset).end();
  }

  protected Response renderHtml(String html) {
    this.transferAttr();
    return super.resp().renderHtml(html).end();
  }

  protected Response renderHtml(String html, Charset charset) {
    this.transferAttr();
    return super.resp().renderHtml(html, charset).end();
  }

  protected Response renderJson(String content) {
    this.transferAttr();
    return super.resp().renderJson(content).end();
  }

  protected Response renderJson(String content, Charset charset) {
    this.transferAttr();
    return super.resp().renderJson(content, charset).end();
  }

  protected Response renderJson(Object object) {
    this.transferAttr();
    return super.resp().renderJson(object).end();
  }

  protected Response renderJson(Object object, Charset charset) {
    this.transferAttr();
    return super.resp().renderJson(object, charset).end();
  }

  protected Response renderJsonp(String content) {
    this.transferAttr();
    return super.resp().renderJsonp(content).end();
  }

  protected Response renderJsonp(String callback, String content) {
    this.transferAttr();
    return super.resp().renderJsonp(callback, content).end();
  }

  protected Response renderJsonp(String callback, String content, Charset charset) {
    this.transferAttr();
    return super.resp().renderJsonp(callback, content, charset).end();
  }

  protected Response renderJsonp(Object content) {
    this.transferAttr();
    return super.resp().renderJsonp(content).end();
  }

  protected Response renderJsonp(String callback, Object object) {
    this.transferAttr();
    return super.resp().renderJsonp(callback, object).end();
  }

  protected Response renderJsonp(String callback, Object object, Charset charset) {
    this.transferAttr();
    return super.resp().renderJsonp(callback, object, charset).end();
  }

  protected Response renderFile(Path path) {
    this.transferAttr();
    return super.resp().renderFile(path).end();
  }

  protected Response renderFile(Path path, String filename) {
    this.transferAttr();
    return super.resp().renderFile(path, filename).end();
  }

  protected Response renderFile(Path path, String filename, String contentType) {
    this.transferAttr();
    return super.resp().renderFile(path, filename, contentType).end();
  }

  protected Response renderXml(String xml) {
    this.transferAttr();
    return super.resp().renderXml(xml).end();
  }

  protected Response renderXml(String xml, Charset charset) {
    this.transferAttr();
    return super.resp().renderXml(xml, charset).end();
  }

  protected Response renderXml(Path path) {
    this.transferAttr();
    return super.resp().renderXml(path).end();
  }

  protected Response renderXml(Path path, Charset charset) {
    this.transferAttr();
    return super.resp().renderXml(path, charset).end();
  }

  protected Response renderXml(Object object) {
    this.transferAttr();
    return super.resp().renderXml(object).end();
  }

  protected Response renderXml(Object object, Charset charset) {
    this.transferAttr();
    return super.resp().renderXml(object, charset).end();
  }

  protected Response renderBinary(byte[] bytes) {
    this.transferAttr();
    return super.resp().renderBinary(bytes).end();
  }

  protected Response renderBinary(byte[] bytes, String contentType) {
    this.transferAttr();
    return super.resp().renderBinary(bytes, contentType).end();
  }

  protected Response renderBinary(ByteBuffer buffer) {
    this.transferAttr();
    return super.resp().renderBinary(buffer).end();
  }

  protected Response renderBinary(ByteBuffer buffer, String contentType) {
    this.transferAttr();
    return super.resp().renderBinary(buffer, contentType).end();
  }

  protected Response redirect(String url) {
    this.transferAttr();
    return super.resp().redirect(url).end();
  }

  protected Response redirect(String url, Charset charset) {
    this.transferAttr();
    return super.resp().redirect(url, charset).end();
  }

  protected Response forward(String uri) {
    this.transferAttr();
    return super.resp().forward(uri);
  }

  protected Response renderError(HttpStatus status) {
    this.transferAttr();
    return super.resp().renderError(status).end();
  }

  protected Response renderError(HttpStatus status, String body) {
    this.transferAttr();
    return super.resp().renderError(status, body).end();
  }

  protected Response renderError(HttpStatus status, String contentType, String body) {
    this.transferAttr();
    return super.resp().renderError(status, contentType, body).end();
  }

  protected Response renderError(HttpStatus status, String contentType, String body, Charset charset) {
    this.transferAttr();
    return super.resp().renderError(status, contentType, body, charset).end();
  }

  protected Response renderError(HttpStatus status, Throwable e) {
    this.transferAttr();
    return super.resp().renderError(status, e).end();
  }

  protected Response renderError(HttpStatus status, String body, Throwable e) {
    this.transferAttr();
    return super.resp().renderError(status, body, e).end();
  }

  protected Response renderError(HttpStatus status, String contentType, String body, Throwable e) {
    this.transferAttr();
    return super.resp().renderError(status, contentType, body, e).end();
  }

  protected Response renderError(HttpStatus status, String contentType, String body, Charset charset, Throwable e) {
    this.transferAttr();
    return super.resp().renderError(status, contentType, body, charset, e).end();
  }

  protected CTL header(String name, String value) {
    super.resp().header(new Header(name, value));
    return (CTL) this;
  }

  protected CTL header(Header header) {
    super.resp().header(header);
    return (CTL) this;
  }

  protected CTL cookie(Cookie cookie) {
    super.resp().cookie(cookie);
    return (CTL) this;
  }


}
