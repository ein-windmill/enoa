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
import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.toolkit.thr.EoException;
import io.enoa.yosart.Oysart;
import io.enoa.yosart.YoExt;
import io.enoa.yosart.kernel.ext.YmRenderExt;
import io.enoa.yosart.kernel.ext.YmRouterExt;
import io.enoa.yosart.kernel.render.YoRender;

import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

class RendererImpl extends RespuseImpl implements Renderer {

  private static Map<String, YmRenderExt> RENDERER;

  private Request request;

  RendererImpl(Request req) {
    super(req);
    this.request = req;
  }

  private YmRouterExt routerExt() {
    return (YmRouterExt) Oysart.ext(YoExt.Type.ROUTER);
  }

  private YmRenderExt renderExt(String type) {
    if (RENDERER != null) {
      YmRenderExt ext = RENDERER.get(type);
      if (ext == null)
        throw new EoException(EnoaTipKit.message("eo.tip.yosart.ext_404_render"), type);
      return ext;
    }

    RENDERER = new HashMap<>();
    YoExt[] exts = Oysart.exts(YoExt.Type.RENDER);
    for (YoExt ext : exts) {
      YmRenderExt renderExt = (YmRenderExt) ext;
      RENDERER.put(renderExt.renderType(), renderExt);
    }
    return this.renderExt(type);
  }

  @Override
  public Respuse use(YoRender.Type type) {
    return this.use(type.name());
  }

  @Override
  public Respuse use(String type) {
    return super.ext(this.renderExt(type));
  }

  @Override
  public Renderer attr(String name, Object value) {
    super.attr(name, value);
    return this;
  }

  @Override
  public Respend render(HttpStatus status, String contentType, String body, Charset charset) {
    return this.use(YoRender.Type.COMMON).render(status, contentType, body, charset);
  }

  @Override
  public Respend renderTemplate(String viewPath, Charset charset) {
    return this.use(YoRender.Type.TEMPLATE).render(viewPath, charset);
  }

  @Override
  public Respend renderText(String text, Charset charset) {
    return this.use(YoRender.Type.TEXT).render(text, charset);
  }

  @Override
  public Respend renderHtml(String html, Charset charset) {
    return this.use(YoRender.Type.HTML).render(html, charset);
  }

  @Override
  public Respend renderJson(String content, Charset charset) {
    return this.use(YoRender.Type.JSON).render(content, charset);
  }

  @Override
  public Respend renderJson(Object object, Charset charset) {
    return this.use(YoRender.Type.JSON).render(object, charset);
  }

  @Override
  public Respend renderJsonp(String callback, String content, Charset charset) {
    return this.use(YoRender.Type.JSONP).render(callback, content, charset);
  }

  @Override
  public Respend renderJsonp(String callback, Object object, Charset charset) {
    return this.use(YoRender.Type.JSONP).render(callback, object, charset);
  }

  @Override
  public Respend renderFile(Path path, String filename, String contentType) {
    return this.use(YoRender.Type.FILE).render(path, filename, contentType);
  }

  @Override
  public Respend renderXml(String xml, Charset charset) {
    return this.use(YoRender.Type.XML).render(xml, charset);
  }

  @Override
  public Respend renderXml(Path path, Charset charset) {
    return this.use(YoRender.Type.XML).render(path, charset);
  }

  @Override
  public Respend renderXml(Object object, Charset charset) {
    return this.use(YoRender.Type.XML).render(object, charset);
  }

  @Override
  public Respend renderBinary(byte[] bytes, String contentType) {
    return this.use(YoRender.Type.BINARY).render(bytes, contentType);
  }

  @Override
  public Respend redirect(String url, Charset charset) {
    return this.use(YoRender.Type.REDIRECT).render(url, charset);
  }

  @Override
  public Response forward(String uri) {
    this.request.attr("_eo.request.clear", false);
    Response resp = this.routerExt().handle(uri, this.request);
    this.request.attr("_eo.request.clear", true);
    return resp;
  }

  @Override
  public Respend renderError(HttpStatus status, String contentType, String body, Charset charset, Throwable e) {
    return this.use(YoRender.Type.ERROR).render(status, contentType, body, charset, e);
  }

  @Override
  public Response end() {
    return super.end();
  }
}
