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

import io.enoa.repeater.http.*;
import io.enoa.toolkit.collection.CollectionKit;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

class RespImpl extends RendererImpl implements Resp {

  private boolean changed = false;
  private HttpStatus stat;
  private List<Cookie> cookies = new ArrayList<>();
  private List<Header> headers = new ArrayList<>();
  private Response response;

  RespImpl(Request req) {
    super(req);
  }

  @Override
  public Resp stat(HttpStatus stat) {
    this.stat = stat;
    this.changed = true;
    return this;
  }

  @Override
  public Resp cookie(Cookie cookie) {
    this.cookies.add(cookie);
    this.changed = true;
    return this;
  }

  @Override
  public Resp header(Header header) {
    this.headers.add(header);
    this.changed = true;
    return this;
  }

  @Override
  public Response end() {
//    if (!this.changed && this.response != null)
//      return this.response;

    Response.Builder builder = super.builder();
    if (builder == null)
//      throw new EoException(EnoaTipKit.message("eo.tip.yosart.resp_build_cant_end"));
      builder = new Response.Builder();

    if (this.stat != null) {
      builder.status(this.stat);
    }
    if (CollectionKit.notEmpty(this.cookies)) {
      for (Cookie cookie : this.cookies) {
        builder.cookie(cookie);
      }
    }
    if (CollectionKit.notEmpty(this.headers)) {
      for (Header header : this.headers) {
        builder.header(header.name(), header.value());
      }
    }
    this.response = builder.build();
    this.changed = false;
    return this.response;
  }
}
