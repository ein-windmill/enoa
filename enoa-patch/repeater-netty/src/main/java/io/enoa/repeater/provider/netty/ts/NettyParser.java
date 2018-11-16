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
package io.enoa.repeater.provider.netty.ts;

import io.enoa.log.Log;
import io.enoa.repeater.EoxConfig;
import io.enoa.repeater.factory.name.EoxNameRuleFactory;
import io.enoa.repeater.http.Cookie;
import io.enoa.repeater.http.UFile;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.text.TextKit;
import io.netty.handler.codec.http.cookie.ServerCookieDecoder;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.FileUpload;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

class NettyParser {

  private static ServerCookieDecoder cookieDecoder = ServerCookieDecoder.LAX;

  static Cookie[] parseCookies(String cookie) {
    if (TextKit.blanky(cookie))
      return CollectionKit.emptyArray(Cookie.class);
    Set<io.netty.handler.codec.http.cookie.Cookie> cookies = cookieDecoder.decode(cookie);
    if (cookies == null)
      return CollectionKit.emptyArray(Cookie.class);

    return cookies.stream().map(c -> {
      Cookie.Builder builder = new Cookie.Builder()
        .name(c.name())
        .value(c.value())
        .path(c.path())
        .expires(c.maxAge());
      if (c.domain() != null)
        builder.domain(c.domain());
      return builder.build();
    }).toArray(Cookie[]::new);
  }


  static ParseRet parsePostData(List<InterfaceHttpData> datas, EoxConfig config, EoxNameRuleFactory rule) {
    Map<String, List<String>> binparas = new HashMap<>();
    List<UFile> ufiles = new ArrayList<>();
    for (InterfaceHttpData data : datas) {
      try {
        String name;
        switch (data.getHttpDataType()) {
          case Attribute:
            Attribute attribute = (Attribute) data;
            name = attribute.getName();
            String value = attribute.getValue();
            List<String> vals = binparas.get(name);
            if (CollectionKit.notEmpty(vals)) {
              vals.add(value);
              continue;
            }
            binparas.put(name, new ArrayList<String>() {{
              add(value);
            }});
            break;
          case FileUpload:
            FileUpload fileUpload = (FileUpload) data;
            name = fileUpload.getName();
            String originName = fileUpload.getFilename();
            if (fileUpload.isCompleted()) {

              UFile.Builder ufile = new UFile.Builder()
                .name(name)
                .tmp(config.tmp())
                .originName(originName);

              if (config.holdFile()) {
                ufiles.add(ufile.filename(originName).binary(fileUpload.getByteBuf().array()).build());
              } else {
                //保存到磁盘
                String newname = rule.name(config.tmp(), originName);
                Path newfilepath = config.tmp().resolve(newname);
                fileUpload.renameTo(newfilepath.toFile());
                ufiles.add(ufile.filename(newname).path(newfilepath).build());
              }

            }
            break;
          case InternalAttribute:
            // todo InternalAttribute parse
            break;
          default:
            break;
        }
      } catch (IOException e) {
        Log.error(e.getMessage(), e);
      }
    }
    return new ParseRet.Builder()
      .paras(binparas)
      .ufiles(ufiles)
      .build();
  }


  static class ParseRet {
    private final Map<String, List<String>> paras;
    private final List<UFile> ufiles;

    private ParseRet(Builder builder) {
      this.paras = builder.paras;
      this.ufiles = builder.ufiles;
    }

    public Map<String, List<String>> paras() {
      return this.paras;
    }

    public List<UFile> ufiles() {
      return this.ufiles;
    }

    public void clear() {
      this.paras.clear();
      this.ufiles.clear();
    }

    private static class Builder {

      private Map<String, List<String>> paras;
      private List<UFile> ufiles;

      public ParseRet build() {
        return new ParseRet(this);
      }

      public Builder paras(Map<String, List<String>> paras) {
        this.paras = paras;
        return this;
      }

      public Builder ufiles(List<UFile> ufiles) {
        this.ufiles = ufiles;
        return this;
      }
    }

  }

}
