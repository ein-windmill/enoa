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
package io.enoa.yosart.ext.render.template;

import io.enoa.repeater.http.Header;
import io.enoa.repeater.http.HttpStatus;
import io.enoa.repeater.http.Request;
import io.enoa.repeater.http.ResponseBody;
import io.enoa.template.EnoaEngine;
import io.enoa.template.EnoaTemplate;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.map.Kv;
import io.enoa.yosart.kernel.render.YoRender;

import java.nio.charset.Charset;

class TemplateRender implements YoRender {

  private final Request req;
  private final Kv attr;
  private final String viewPath;
  private final Charset charset;

  private EnoaEngine engine;

  TemplateRender(Request req, Kv attr, Object[] args) {
    this.req = req;
    this.attr = attr;
    this.viewPath = (String) args[0];
    this.charset = (Charset) args[1];
  }

  TemplateRender engine(EnoaEngine engine) {
    this.engine = engine;
    return this;
  }


  @Override
  public HttpStatus stat() {
    return HttpStatus.OK;
  }

  @Override
  public String contentType() {
    return "text/html";
  }

  @Override
  public Header[] headers() {
    return CollectionKit.emptyArray(Header.class);
  }

  @Override
  public ResponseBody render() {
    EnoaTemplate template = this.engine.template(this.viewPath);
    String view = CollectionKit.isEmpty(this.attr) ? template.render() : template.render(this.attr);
    return ResponseBody.create(view, this.charset);
  }
}
