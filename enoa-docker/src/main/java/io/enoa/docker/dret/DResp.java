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
package io.enoa.docker.dret;

import io.enoa.http.protocol.HttpResponse;
import io.enoa.toolkit.binary.EnoaBinary;
import io.enoa.toolkit.text.TextKit;

import java.nio.ByteBuffer;

public class DResp extends AbstractDRet {

  private int code;
  private String contenttype;
  private EnoaBinary binary;
  private Object origin;
  private Type type;

  public enum Type {
    UNKNOWN,
    TCP,
    UNIXSOCKET
  }

  public static DResp create(int code, String contenttype, byte[] bytes) {
    DResp dresp = new DResp();
    dresp.type = Type.UNKNOWN;
    dresp.contenttype = contenttype;
    dresp.code = code;
    dresp.binary = EnoaBinary.create(bytes);
    return null;
  }

  public static DResp create(HttpResponse response) {
    DResp dresp = new DResp();
    dresp.code = response.code();
    dresp.contenttype = response.header("Content-Type");
    dresp.binary = EnoaBinary.create(response.body().bytes());
    dresp.origin = response;
    dresp.type = Type.TCP;
    return dresp;
  }

  private DResp() {

  }

  public int code() {
    return code;
  }

  public String contenttype() {
    return contenttype;
  }

  public Type type() {
    return type;
  }

  public Object origin() {
    return origin;
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
