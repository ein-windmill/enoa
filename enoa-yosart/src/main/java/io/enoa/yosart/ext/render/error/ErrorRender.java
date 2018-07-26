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
package io.enoa.yosart.ext.render.error;

import io.enoa.repeater.http.Header;
import io.enoa.repeater.http.HttpStatus;
import io.enoa.repeater.http.Request;
import io.enoa.repeater.http.ResponseBody;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.map.Kv;
import io.enoa.toolkit.sys.ThrowableKit;
import io.enoa.toolkit.text.TextKit;
import io.enoa.yosart.Oysart;
import io.enoa.yosart.kernel.render.YoRender;

import java.nio.charset.Charset;

class ErrorRender implements YoRender {

  private final Request req;
  private final Kv attr;
  private final HttpStatus stat;
  private final String contentType;
  private final String body;
  private final Charset charset;
  private final Throwable e;

  private String defContentType;
  private String defBody;

  ErrorRender(Request req, Kv attr, Object... args) {
    this.req = req;
    this.attr = attr;
    this.stat = (HttpStatus) args[0];
    this.contentType = (String) args[1];
    this.body = (String) args[2];
    this.charset = (Charset) args[3];
    this.e = (Throwable) args[4];
  }


  ErrorRender defContentType(String contentType) {
    this.defContentType = contentType;
    return this;
  }

  ErrorRender defBody(String body) {
    this.defBody = body;
    return this;
  }


  @Override
  public HttpStatus stat() {
    return this.stat == null ? HttpStatus.INTERNAL_ERROR : this.stat;
  }

  @Override
  public String contentType() {
    return TextKit.blanky(this.contentType) ? this.defContentType : this.contentType;
  }

  @Override
  public Header[] headers() {
    return CollectionKit.emptyArray(Header.class);
  }

  @Override
  public ResponseBody render() {
    if (this.body != null)
      return ResponseBody.create(this.body, this.charset);
    if (this.defBody != null)
      return ResponseBody.create(this.defBody, this.charset);
    return ResponseBody.create(this.defBody(), this.charset);
  }

  private String fillHtml(String text) {
    return text.replace("<", "&lt;").replace(">", "&gt;").replace("\t", "  ");
  }

  private String defBody() {
    StringBuilder body = new StringBuilder();
    String title = null;
    Object msg = this.attr.get("msg");
    if (msg != null) {
      if (msg instanceof String) {
        body.append("<p>-> ").append(msg);
      }
//      // xtodo uncheck can not support now
//      if (msg instanceof Object[]) {
//        Stream.of(msg).forEach(m -> body.append("<p>-> ").append(m.toString()));
//      }
    }

    if (this.e != null) {
      title = this.e.getMessage();
      body.append(String.format("<pre>%s</pre>", this.fillHtml(ThrowableKit.string(this.e))));
    }
    return String.format("<!doctype html>" +
        "<html><head>" +
        "<meta charset=\"%s\">" +
        "<title>ERROR %s %s - Yosart</title>" +
        "<style>body, pre {font-family: sans-serif;} article {color: #676767} .body {color: #333;}</style>" +
        "</head>" +
        "<body>" +
        "<h1>ERROR %s</h1><b>Powered by Yosart %s</b> %s" +
        "%s" +
        "</body>" +
        "</html>",
      this.charset.name(),
      this.stat == null ? HttpStatus.INTERNAL_ERROR.code() : this.stat.code(),
      TextKit.blanky(title) ? "" : "-> ".concat(title),
      this.stat == null ? HttpStatus.INTERNAL_ERROR.code() : this.stat.code(),
      Oysart.version(),
      TextKit.blanky(title) ? "" : "<br><br><article>-> ".concat(title).concat("</article>"),
      TextKit.blanky(body.toString()) ? "" : "<hr/><div class=\"body\">".concat(body.toString()).concat("</div>")
    );
  }


}
