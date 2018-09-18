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
package io.enoa.example.repeater;

import io.enoa.json.Json;
import io.enoa.log.Log;
import io.enoa.repeater.EoxAccessor;
import io.enoa.repeater.EoxConfig;
import io.enoa.repeater.Repeater;
import io.enoa.repeater.http.*;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.map.Kv;
import io.enoa.toolkit.text.TextKit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class ExampleRepeaterAccessorImpl implements EoxAccessor {

  private EoxConfig config = Repeater.config();

  @Override
  public Response access(Request request) {
    String error = request.para("error");
    if (TextKit.blankn(error)) {
      return this.respError(request.paraToInt("error"));
    }
    String body = "It works!";
    String contentType;
    try {
      switch (request.method()) {
        case GET:
          String type = request.para("type");
          boolean showJson = TextKit.blankn(type) && type.equalsIgnoreCase("json");
          contentType = showJson ?
            "application/json; charset=" + this.config.charset().displayName() :
            "text/html; charset=" + this.config.charset().displayName();
          body = showJson ? this.respJson(request) : this.respHtml(request);
          break;
        default:
          contentType = "application/json; charset=" + this.config.charset().displayName();
          body = this.respJson(request);
          break;
      }
    } catch (Exception e) {
      Log.error(e.getMessage(), e);
      return new Response.Builder()
        .header("Content-Type", "")
        .contentType("text/html; charset=" + this.config.charset().displayName())
        .cookie(new Cookie.Builder().name("test").value("val").build())
        .cookie(new Cookie.Builder().name("age").value("99").build())
        .body(ResponseBody.create(body))
        .build();
    }
    return new Response.Builder()
      .header("Content-Type", contentType)
      .cookie(new Cookie.Builder().name("test").value("val").build())
      .cookie(new Cookie.Builder().name("age").value("99").build())
      .body(ResponseBody.create(body))
      .build();

//    return new Response.Builder()
//      .header("Content-Transfer-Encoding", "binary")
//      .header("Content-Disposition", "attachment; filename=aaa")
//      .header("Content-Type", "application/force-downloa")
//      .cookie(new Cookie.Builder().name("test").value("val").build())
//      .cookie(new Cookie.Builder().name("age").value("99").build())
//      .body(ResponseBody.create(new File("/usr/bin/ls")))
//      .build();
  }

  private String respHtml(Request req) throws IOException {
    StringBuilder sb = new StringBuilder();
    String kvh = "<p><span class=\"name\">%s</span><span class=\"value\">%s</span>";
    sb.append("<!doctype html><html><head><meta charset=\"utf-8\"><title>Repeater</title>");
    sb.append("<style>* {font-size: 16px} p { display: block; width: 100% } .name { min-width: 220px; width: 100px auto !important; display: inline-block; font-weight: bold; color: #c33; } .value { display: inline-block; }</style>");
    sb.append("</head><body>");
    sb.append("<h1>Enoa Repeater</h1>");
    sb.append("<div><p><span class=\"name\">Server")
      .append("</span><span class=\"value\">")
      .append(this.config.other().string("_EOX_SERVER"))
      .append("<sup>").append(this.config.other().string("_EOX_SERVER_VERSION")).append("</sup>")
      .append("</span>");

    sb.append(String.format(kvh, "Method", req.method()));
    sb.append(String.format(kvh, "Context", req.context()));
    sb.append(String.format(kvh, "URI", req.uri()));
    sb.append(String.format(kvh, "URL", TextKit.blanky(req.url()) ? req.url() : req.url().replace("&", "&amp;")));
    sb.append("<p><span class=\"name\">Cookies<span><ul>");
    for (Cookie cookie : req.cookies()) {
      sb.append("<li><span class=\"name\">").append(cookie.name()).append("</span>")
        .append("<span class=\"value\">").append(cookie.value()).append("</span>");
    }
    sb.append("</ul>");
    sb.append("<p><span class=\"name\">Headers</span><ul>");
    for (String name : req.headerNames()) {
      sb.append("<li><span class=\"name\">").append(name).append("</span>")
        .append("<span class=\"value\">").append(req.header(name)).append("</span>");
    }
    sb.append("</ul>");
    if (CollectionKit.notEmpty(req.paraNames())) {
      sb.append("<p><span class=\"name\">Paras<span><ul>");
      Stream.of(req.paraNames()).forEach(name -> {
        sb.append("<li>");
        String[] vals = req.paraValues(name);
        sb.append("<span class=\"name\">").append(name).append("</span>").append("<span class=\"value\">");
        if (vals.length == 1) {
          sb.append(vals[0]);
          sb.append("</span>");
          return;
        }
        sb.append("[").append(String.join(", ", vals));
        sb.append("]</span>");
      });
      sb.append("</ul>");
    }
    RequestBody body = req.body();
    if (body != null) {
      sb.append(String.format(kvh, "Body", body.string().replaceAll("\n", "\\\\n")));
    }

    sb.append(String.format(kvh, "", ""));
    sb.append(String.format(kvh, "", ""));
    sb.append(String.format(kvh, "", ""));


    sb.append("</div></body></html>");
    return sb.toString();
  }

  private String filterJsonVal(String str) {
    if (TextKit.blanky(str))
      return str;
    return str.replaceAll("\\\\", "\\\\\\\\")
      .replaceAll("\"", "\\\\\"")
      .replaceAll("\n", "\\\\n");
  }

  private String respJson(Request req) throws IOException {
    Kv data = Kv.by("_", "Enoa Repeater")
      .set("server", Kv.by("name", this.config.other().string("_EOX_SERVER")).set("version", this.config.other().string("_EOX_SERVER_VERSION")))
      .set("method", req.method().name())
      .set("context", this.filterJsonVal(req.context()))
      .set("uri", this.filterJsonVal(req.uri()))
      .set("url", this.filterJsonVal(req.url()));

    if (CollectionKit.notEmpty(req.cookies())) {
      Kv ck = Kv.create();
      Arrays.stream(req.cookies()).forEach(cookie -> ck.set(cookie.name(), this.filterJsonVal(cookie.value())));
      data.set("cookies", ck);
    }
    if (CollectionKit.notEmpty(req.headerNames())) {
      Kv hd = Kv.create();
      Arrays.stream(req.headerNames()).forEach(name -> hd.set(name, this.filterJsonVal(req.header(name))));
      data.set("headers", hd);
    }
    if (CollectionKit.notEmpty(req.paraNames())) {
      Kv paras = Kv.create();
      Arrays.stream(req.paraNames()).forEach(name -> {
        String[] vals = req.paraValues(name);
        if (vals.length == 1) {
          paras.put(name, this.filterJsonVal(vals[0]));
          return;
        }
        List<String> filVal = Arrays.stream(vals).map(this::filterJsonVal).collect(Collectors.toList());
        paras.put(name, filVal);
      });
      data.set("paras", paras);
    }
    if (req.body() != null) {
      String content = req.body().string();
      data.set("body", content);
    }
    if (CollectionKit.notEmpty(req.files())) {
      List<Kv> ufs = new ArrayList<>();
      Arrays.stream(req.files()).forEach(ufile -> {
        Kv uf = Kv.by("name", this.filterJsonVal(ufile.name()))
          .set("origin_name", this.filterJsonVal(ufile.originName()))
          .set("filename", this.filterJsonVal(ufile.filename()))
          .set("path", this.filterJsonVal(ufile.path().toString()));
        ufs.add(uf);
      });
      data.set("files", ufs);
    }

    return Json.toJson(data);
  }

  private Response respError(int code) {
    return new Response.Builder()
      .status(HttpStatus.of(code))
      .body(ResponseBody.create(String.valueOf(code)))
      .build();
  }


}
