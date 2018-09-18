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
package io.enoa.yosart.ext.render.redirect;

import io.enoa.repeater.http.Header;
import io.enoa.repeater.http.HttpStatus;
import io.enoa.repeater.http.Request;
import io.enoa.repeater.http.ResponseBody;
import io.enoa.toolkit.map.Kv;
import io.enoa.toolkit.text.TextKit;
import io.enoa.yosart.kernel.render.YoRender;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;

class RedirectRender implements YoRender {

  private final Request req;
  private final String url;
  private final Charset charset;

  RedirectRender(Request req, Kv attr, Object[] args) {
    this.req = req;
    this.url = (String) args[0];
    this.charset = (Charset) args[1];
  }

  private String redirectUrl() {
    if (this.charset == null)
      return this.url;
    if (!this.url.contains("?"))
      return this.url;

    try {
      String[] urls = this.url.split("\\?");
      String right = urls[1];
      String[] paras = right.split("&");
      StringBuilder sb = new StringBuilder();
      int i = 0;
      for (String para : paras) {
        String[] item = para.split("=");
        String name = item[0];
        String value = item[1];
        value = URLEncoder.encode(value, this.charset.name());
        sb.append(name).append("=").append(value);
        if (i + 1 < para.length())
          sb.append("&");
        i += 1;
      }
//      return String.format("%s?%s", urls[0], sb.toString());
      return TextKit.union(urls[0], "?", sb.toString());
    } catch (UnsupportedEncodingException e) {
      return this.url;
    }
  }

  @Override
  public HttpStatus stat() {
    return HttpStatus.REDIRECT;
  }

  @Override
  public String contentType() {
    return "text/html";
  }

  @Override
  public Header[] headers() {
    return new Header[]{
      new Header("Location", this.redirectUrl())
    };
  }

  @Override
  public ResponseBody render() {
    return ResponseBody.create("REDIRECT: ".concat(this.redirectUrl()), this.charset);
  }
}
