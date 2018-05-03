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
package io.enoa.yosart.ext.render.file;

import io.enoa.repeater.http.Header;
import io.enoa.repeater.http.HttpStatus;
import io.enoa.repeater.http.Request;
import io.enoa.repeater.http.ResponseBody;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.toolkit.file.FileKit;
import io.enoa.toolkit.map.Kv;
import io.enoa.toolkit.thr.EoException;
import io.enoa.yosart.kernel.render.YoRender;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Path;

class FileRender implements YoRender {

  private final Request req;
  private final Path path;
  private final String filename;
  private final String contentType;

  FileRender(Request req, Kv attr, Object[] args) throws FileNotFoundException {
    this.req = req;
    this.path = (Path) args[0];
    if (!FileKit.exists(this.path)) {
      throw new FileNotFoundException(EnoaTipKit.message("eo.tip.yosart.file_not_found", this.path.toString()));
    }

    if (args[1] == null) {
//      String fph = this.path.toUri().getPath();
      this.filename = this.path.getFileName().toString();
    } else {
      this.filename = (String) args[1];
    }
    this.contentType = (String) args[2];
  }

  @Override
  public HttpStatus stat() {
    return HttpStatus.OK;
  }

  @Override
  public String contentType() {
    if (this.contentType != null)
      return this.contentType;
    String contentType = FileKit.probeContentType(this.path);
    return contentType == null ? "application/octet-stream" : contentType;
  }

  @Override
  public Header[] headers() {
    if (!"application/octet-stream".equals(this.contentType))
      return CollectionKit.emptyArray(Header.class);
    return new Header[]{
      new Header("Accept-Ranges", "bytes"),
//      new Header("Accept-Length", String.valueOf(this.path.toFile().length())),
      new Header("Content-disposition", "attachment; " + this.encodeFileName(this.filename))
    };
  }

  @Override
  public ResponseBody render() {
    return ResponseBody.create(path);
  }


  /**
   * 依据浏览器判断编码规则
   */
  private String encodeFileName(String fileName) {
    String userAgent = this.req.header("user-agent");
    try {
      String encodedFileName = URLEncoder.encode(fileName, "UTF8");
      // 如果没有UA，则默认使用IE的方式进行编码
      if (userAgent == null) {
        return "filename=\"" + encodedFileName + "\"";
      }

      userAgent = userAgent.toLowerCase();
      // IE浏览器，只能采用URLEncoder编码
      if (userAgent.contains("msie")) {
        return "filename=\"" + encodedFileName + "\"";
      }

      // Opera浏览器只能采用filename*
      if (userAgent.contains("opera")) {
        return "filename*=UTF-8''" + encodedFileName;
      }

      // Safari浏览器，只能采用ISO编码的中文输出,Chrome浏览器，只能采用MimeUtility编码或ISO编码的中文输出
      if (userAgent.contains("safari") || userAgent.contains("applewebkit") || userAgent.contains("chrome")) {
        return "filename=\"" + new String(fileName.getBytes("UTF-8"), "ISO8859-1") + "\"";
      }

      // FireFox浏览器，可以使用MimeUtility或filename*或ISO编码的中文输出
      if (userAgent.contains("mozilla")) {
        return "filename*=UTF-8''" + encodedFileName;
      }

      return "filename=\"" + encodedFileName + "\"";
    } catch (UnsupportedEncodingException e) {
      throw new EoException(e.getMessage(), e);
    }
  }

}
