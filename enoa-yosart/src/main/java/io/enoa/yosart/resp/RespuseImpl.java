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

import io.enoa.log.Log;
import io.enoa.repeater.http.Header;
import io.enoa.repeater.http.HttpStatus;
import io.enoa.repeater.http.Request;
import io.enoa.repeater.http.Response;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.toolkit.map.Kv;
import io.enoa.toolkit.text.TextKit;
import io.enoa.toolkit.thr.EoException;
import io.enoa.yosart.Oysart;
import io.enoa.yosart.kernel.ext.YmRenderExt;
import io.enoa.yosart.kernel.render.YoRender;

import java.io.FileNotFoundException;

class RespuseImpl implements Respuse {

  private Request req;
  private YmRenderExt ext;
  private Kv attr = Kv.create();
  private Response.Builder builder;


  RespuseImpl(Request req) {
    this.req = req;
//    this.ext = ext;
  }

  Respuse ext(YmRenderExt ext) {
    this.ext = ext;
    return this;
  }

  @Override
  public Respuse attr(String name, Object value) {
    if (TextKit.isBlank(name))
      return this;
    this.attr.set(name, value);
    return this;
  }

  Response.Builder builder() {
    return this.builder;
  }

  private Respend render(YoRender renderer) {
    if (renderer == null) {
      return Renderer.with(this.req)
        .renderError(HttpStatus.INTERNAL_ERROR);
    }
    String contentType = renderer.contentType();
    if (!contentType.contains("charset")) {
      contentType = contentType.concat("; charset=").concat(Oysart.config().charset().name());
    }
    try {
      Response.Builder builder = new Response.Builder()
        .status(renderer.stat())
        .body(renderer.render())
        .contentType(contentType);

      Header[] headers = renderer.headers();
      if (CollectionKit.isEmpty(headers)) {
        this.builder = builder;
        return this;
      }

      for (Header header : headers)
        builder.header(header.name(), header.value());
      this.builder = builder;
      return this;
    } catch (Exception e) {
      Log.error(e.getMessage(), e);
      return Renderer.with(this.req)
        .renderError(HttpStatus.INTERNAL_ERROR, e);
    }
  }

  @Override
  public Respend render(Object... args) {
    try {
      if (this.ext == null)
        throw new EoException(EnoaTipKit.message("eo.tip.yosart.no_handle_ext"));

      YoRender renderer = this.ext.renderer(this.req, this.attr, args);
      return this.render(renderer);
    } catch (FileNotFoundException e) {
      Log.error(e.getMessage(), e);
      return Renderer.with(this.req)
        .renderError(HttpStatus.NOT_FOUND, e);
    } catch (Exception e) {
      return Renderer.with(this.req)
        .renderError(HttpStatus.INTERNAL_ERROR, e);
    }
  }

  @Override
  public Response end() {
    if (this.builder == null)
      throw new EoException(EnoaTipKit.message("eo.tip.yosart.resp_build_cant_end"));
    return this.builder.build();
  }
}
