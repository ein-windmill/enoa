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
package io.enoa.yosart.ext.render.binary;

import io.enoa.repeater.http.Header;
import io.enoa.repeater.http.HttpStatus;
import io.enoa.repeater.http.Request;
import io.enoa.repeater.http.ResponseBody;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.map.Kv;
import io.enoa.yosart.kernel.render.YoRender;

class BinaryRender implements YoRender {

  private final Request req;
  private final Kv attr;
  private final byte[] binary;
  private final String contentType;


  BinaryRender(Request req, Kv attr, Object[] args) {
    this.req = req;
    this.attr = attr;
    this.binary = (byte[]) args[0];
    this.contentType = (String) args[1];
  }

  @Override
  public HttpStatus stat() {
    return HttpStatus.OK;
  }

  @Override
  public String contentType() {
    return this.contentType == null ? "application/octet-stream" : this.contentType;
  }

  @Override
  public Header[] headers() {
    return CollectionKit.emptyArray(Header.class);
  }

  @Override
  public ResponseBody render() {
    return ResponseBody.create(this.binary);
  }
}
