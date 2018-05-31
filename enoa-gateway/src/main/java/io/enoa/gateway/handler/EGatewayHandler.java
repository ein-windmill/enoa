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
package io.enoa.gateway.handler;

import io.enoa.gateway.GatewayHandler;
import io.enoa.gateway.data.GAuthData;
import io.enoa.gateway.data.GData;
import io.enoa.gateway.data.GMapping;
import io.enoa.gateway.thr.GatewayAuthException;
import io.enoa.gateway.thr.GatewayException;
import io.enoa.gateway.thr.RouteNotFoundException;
import io.enoa.http.Http;
import io.enoa.http.protocol.HttpMethod;
import io.enoa.http.protocol.HttpResponse;
import io.enoa.log.Log;
import io.enoa.repeater.EoxConfig;
import io.enoa.repeater.http.*;
import io.enoa.toolkit.http.UriKit;
import io.enoa.toolkit.text.TextKit;

import java.nio.ByteBuffer;
import java.util.Map;

public class EGatewayHandler implements GatewayHandler {

  private EoxConfig eoxconfig;

  public EGatewayHandler(EoxConfig eoxconfig) {
    this.eoxconfig = eoxconfig;
  }

  @Override
  public Response handle(Request request, GData gateway) throws GatewayException {
    String uri = UriKit.correct(request.uri());
    if (!"/".equals(uri))
      uri = TextKit.union(uri, "/");

    this.auth(request, uri, gateway);

    GMapping mapping = this.chooseMapping(uri, gateway.mappings());
    if (mapping == null) {
      throw new RouteNotFoundException("Not found mapping -> {0}", uri);
    }

    if (mapping.auth() != null && request.method() != Method.OPTIONS) {
      mapping.auth().auth(request);
    }

    return this.call(request, uri, mapping);
  }

  private boolean noauths(String originUri, GData gateway) {
    String[] noauths = gateway.noauths();
    boolean noa = false;
    for (String noauth : noauths) {
      if (!originUri.startsWith(noauth))
        continue;
      noa = true;
      break;
    }
    return noa;
  }

  private void auth(Request request, String originUri, GData gateway) throws GatewayAuthException {
    // options 请求不验证
    if (request.method() == Method.OPTIONS)
      return;

    GAuthData[] authData = gateway.auth();
    for (GAuthData gad : authData) {

      /*
      鑑定當前請求 uri 是否無需進行權限認證
       */
      if (this.noauths(originUri, gateway)) {
        continue;
      }

      /*
      如果當前請求無需驗證, 判斷添加的驗證記錄中是否有 uri, 沒有表示每個請求都需要進行驗證
       */
      if (gad.uri() == null) {
        gad.auth().auth(request);
        continue;
      }
      /*
      否則判斷添加的驗證記錄 uri 是否與請求的 uri 配對 再進行驗證
       */
      if (!originUri.startsWith(gad.uri()))
        continue;

      gad.auth().auth(request);
    }
  }

  /**
   * 选择映射网关
   *
   * @param uri      请求 uri
   * @param mappings 网关列表
   * @return GMapping
   */
  private GMapping chooseMapping(String uri, GMapping[] mappings) {
    GMapping last = null;
    for (GMapping mapping : mappings) {
      // 判定请求 uri 与网关地址都是根路径 直接选取
      if ("/".equals(uri) && "/".equals(mapping.source()))
        return mapping;
      // 判定是否 uri 开头相同
      if (uri.startsWith(mapping.source())) {
        // 如果映射地址有根路径, 无论如何都会被匹配到, 因此当作最后选择方案, 如果配置的映射中找不到当前 uri 请求, 走到根路径地址
        if ("/".equals(mapping.source())) {
          last = mapping;
          continue;
        }
        return mapping;
      }
    }
    return last;
  }

  /**
   * 网关请求处理
   *
   * @param request   request
   * @param originUri 请求原始 uri
   * @param mapping   mappding 网关映射
   * @return Response
   */
  private Response call(Request request, String originUri, GMapping mapping) {
    String sourceUri = originUri.replace(mapping.source(), "/");
    sourceUri = UriKit.correct(sourceUri);
    String callUrl = TextKit.union(mapping.dest(), sourceUri);
    Log.info("{} -> [{}] => [{}]", request.method().name(), originUri, callUrl);

    Http http = Http.request(callUrl);

    String contentType = request.header("content-type");
    if (TextKit.notBlank(contentType)) {
      contentType = contentType.toLowerCase();

      // http body 請求
      if (!contentType.startsWith("application/x-www-form-urlencoded")) {
        RequestBody rb = request.body();
        if (rb != null) {
          try {
            ByteBuffer binary = rb.binary();
            if (binary != null)
              http.binary(binary);
          } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
          }
        }
      }

      // 文件上傳
      if (contentType.startsWith("multipart/form-data")) {
        UFile[] files = request.files();
        for (UFile file : files) {
          if (this.eoxconfig.holdFile()) {
            http.para(file.name(), file.filename(), file.bytes());
          } else {
            http.para(file.name(), file.path());
          }
        }
      }

    }
    Map<String, String[]> para = request.paraMap();
    if (para != null) {
      para.forEach(http::para);
    }
    for (String headerName : request.headerNames()) {
      http.header(headerName, request.header(headerName));
      if (headerName.toLowerCase().startsWith("access-")) {
        http.header(TextKit.union("x-", headerName), request.header(headerName));
      }
    }
    String origin = request.header("origin");
    if (TextKit.notBlank(origin))
      http.header("x-origin", origin);

    http.method(HttpMethod.of(request.method().name()));
    HttpResponse resp = http.emit();
    this.clearRequest(request);

    Response.Builder builder = new Response.Builder();
    builder.status(HttpStatus.of(resp.code()));
    for (String headerName : resp.headerNames()) {
      if (headerName == null)
        continue;
      if (headerName.equalsIgnoreCase("content-encoding"))
        continue;
      if (headerName.equalsIgnoreCase("transfer-encoding"))
        continue;
      builder.header(headerName, resp.header(headerName));
    }
    builder.charset(resp.charset());
    byte[] body = resp.body().bytes();
    builder.body(ResponseBody.create(body));

    return builder.build();
  }

  private void clearRequest(Request request) {
    request.clear();
  }

}
