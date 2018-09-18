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
package io.enoa.yosart.resp;

import io.enoa.repeater.http.HttpStatus;
import io.enoa.repeater.http.Request;
import io.enoa.repeater.http.Response;
import io.enoa.yosart.Oysart;
import io.enoa.yosart.kernel.render.YoRender;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Path;

public interface Renderer extends Respuse {

  static Renderer with(Request req) {
    return new RendererImpl(req);
  }

  Renderer attr(String name, Object value);

  Respuse use(String type);

  Respuse use(YoRender.Type type);

  default Respend render(String body) {
    return this.render(HttpStatus.OK, "text/html", body, Oysart.config().charset());
  }

  default Respend render(String contentType, String body) {
    return this.render(HttpStatus.OK, contentType, body, Oysart.config().charset());
  }

  default Respend render(String contentType, String body, Charset charset) {
    return this.render(HttpStatus.OK, contentType, body, charset);
  }

  default Respend render(HttpStatus status, String body) {
    return this.render(status, body, "text/html", Oysart.config().charset());
  }

  default Respend render(HttpStatus status, String body, Charset charset) {
    return this.render(status, body, "text/html", charset);
  }

  Respend render(HttpStatus status, String contentType, String body, Charset charset);

  default Respend renderTemplate(String viewPath) {
    return this.renderTemplate(viewPath, Oysart.config().charset());
  }

  Respend renderTemplate(String viewPath, Charset charset);

  default Respend renderText(String text) {
    return this.renderText(text, Oysart.config().charset());
  }

  Respend renderText(String text, Charset charset);

  default Respend renderHtml(String html) {
    return this.renderHtml(html, Oysart.config().charset());
  }

  Respend renderHtml(String html, Charset charset);

  default Respend renderJson(String content) {
    return this.renderJson(content, Oysart.config().charset());
  }

  Respend renderJson(String content, Charset charset);

  default Respend renderJson(Object object) {
    return this.renderJson(object, Oysart.config().charset());
  }

  Respend renderJson(Object object, Charset charset);

  default Respend renderJsonp(String content) {
    return this.renderJsonp("callback", content);
  }

  default Respend renderJsonp(String callback, String content) {
    return this.renderJsonp(callback, content, Oysart.config().charset());
  }

  Respend renderJsonp(String callback, String content, Charset charset);

  default Respend renderJsonp(Object content) {
    return this.renderJsonp("callback", content);
  }

  default Respend renderJsonp(String callback, Object object) {
    return this.renderJsonp(callback, object, Oysart.config().charset());
  }

  Respend renderJsonp(String callback, Object object, Charset charset);

  default Respend renderFile(Path path) {
    return this.renderFile(path, null, null);
  }

  default Respend renderFile(Path path, String filename) {
    return this.renderFile(path, filename, null);
  }

  Respend renderFile(Path path, String filename, String contentType);

  default Respend renderXml(String xml) {
    return this.renderXml(xml, Oysart.config().charset());
  }

  Respend renderXml(String xml, Charset charset);

  default Respend renderXml(Path path) {
    return this.renderXml(path, Oysart.config().charset());
  }

  Respend renderXml(Path path, Charset charset);

  default Respend renderXml(Object object) {
    return this.renderXml(object, Oysart.config().charset());
  }

  Respend renderXml(Object object, Charset charset);

  default Respend renderBinary(byte[] bytes) {
    return this.renderBinary(bytes, null);
  }

  Respend renderBinary(byte[] bytes, String contentType);

  default Respend renderBinary(ByteBuffer buffer) {
    return this.renderBinary(buffer.array(), null);
  }

  default Respend renderBinary(ByteBuffer buffer, String contentType) {
    return this.renderBinary(buffer.array(), contentType);
  }

  default Respend redirect(String url) {
    return this.redirect(url, Oysart.config().charset());
  }

  Respend redirect(String url, Charset charset);

  Response forward(String uri);

  default Respend renderError(HttpStatus status) {
    return this.renderError(status, null, null, Oysart.config().charset(), null);
  }

  default Respend renderError(HttpStatus status, String body) {
    return this.renderError(status, null, body, Oysart.config().charset(), null);
  }

  default Respend renderError(HttpStatus status, String contentType, String body) {
    return this.renderError(status, contentType, body, Oysart.config().charset(), null);
  }

  default Respend renderError(HttpStatus status, String contentType, String body, Charset charset) {
    return this.renderError(status, contentType, body, charset, null);
  }

  default Respend renderError(HttpStatus status, Throwable e) {
    return this.renderError(status, null, null, Oysart.config().charset(), e);
  }

  default Respend renderError(HttpStatus status, String body, Throwable e) {
    return this.renderError(status, null, body, Oysart.config().charset(), e);
  }

  default Respend renderError(HttpStatus status, String contentType, String body, Throwable e) {
    return this.renderError(status, contentType, body, Oysart.config().charset(), e);
  }

  Respend renderError(HttpStatus status, String contentType, String body, Charset charset, Throwable e);

}
