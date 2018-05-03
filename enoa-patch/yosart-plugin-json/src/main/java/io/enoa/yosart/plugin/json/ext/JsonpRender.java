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
package io.enoa.yosart.plugin.json.ext;

import io.enoa.json.kit.JsonKit;
import io.enoa.repeater.http.Header;
import io.enoa.repeater.http.HttpStatus;
import io.enoa.repeater.http.Request;
import io.enoa.repeater.http.ResponseBody;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.map.Kv;
import io.enoa.toolkit.text.TextKit;
import io.enoa.yosart.kernel.render.YoRender;

import java.nio.charset.Charset;

class JsonpRender implements YoRender {

  private final Request req;
  private final Kv attr;
  private final String callback;
  private final Object object;
  private final Charset charset;

  JsonpRender(Request req, Kv attr, Object[] args) {
    this.req = req;
    this.attr = attr;
    this.callback = (String) args[0];
    this.object = args[1];
    this.charset = (Charset) args[2];
  }

  @Override
  public HttpStatus stat() {
    return HttpStatus.OK;
  }

  @Override
  public String contentType() {
    return "application/json";
  }

  @Override
  public Header[] headers() {
    return CollectionKit.emptyArray(Header.class);
  }

  @Override
  public ResponseBody render() {
    String json;
    if (this.object instanceof String) {
      json = (String) this.object;
    } else {
      json = JsonKit.toJson(this.object);
    }
    return ResponseBody.create(TextKit.union(this.callback(), "(", json, ")"), this.charset);
  }

  private String callback() {
    return this.callback.replace("<", "&lt;")
      .replace(">", "&gt;");
  }
}
