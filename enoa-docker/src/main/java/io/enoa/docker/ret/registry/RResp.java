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
package io.enoa.docker.ret.registry;

import io.enoa.docker.ret.AbstractDRRet;
import io.enoa.http.protocol.HttpResponse;
import io.enoa.toolkit.binary.EnoaBinary;
import io.enoa.toolkit.text.TextKit;

import java.nio.ByteBuffer;

public class RResp extends AbstractDRRet {

  private int code;
  private String contenttype;
  private EnoaBinary binary;
  private HttpResponse response;

  public static RResp create(HttpResponse response) {
    RResp _r = new RResp();
    _r.code = response.code();
    _r.contenttype = response.header("Content-Type");
    _r.binary = EnoaBinary.create(response.body().bytes());
    _r.response = response;
    return _r;
  }


  public int code() {
    return code;
  }

  public String contenttype() {
    return contenttype;
  }

  public HttpResponse response() {
    return response;
  }

  public EnoaBinary binary() {
    return binary;
  }

  public String string() {
    return this.binary.string();
  }

  public ByteBuffer bytebuffer() {
    return this.binary.bytebuffer();
  }

  public byte[] bytes() {
    return this.binary.bytes();
  }

  @Override
  public String toString() {
    String text = this.string();
    return TextKit.ellipsis(text, 3000);
  }


}
