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
package io.enoa.http.provider.httphelper.conn;

import io.enoa.http.protocol.HttpHeader;
import io.enoa.http.protocol.HttpResponse;
import io.enoa.http.protocol.chunk.Chunk;
import io.enoa.http.provider.httphelper.HttpHelperConfig;
import io.enoa.http.provider.httphelper.conn.ssl._HttpHelperSSL;
import io.enoa.http.provider.httphelper.http.req._HttpHelperRequest;
import io.enoa.http.provider.httphelper.http.resp._HttpHelperChunkedResponse;
import io.enoa.http.provider.httphelper.http.resp._HttpHelperResponse;
import io.enoa.http.proxy.HttpProxy;

import javax.net.ssl.HttpsURLConnection;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.util.Set;

public class _HttpHelperConn {


  private _HttpHelperRequest request;
  private HttpHelperConfig config;

  public _HttpHelperConn(HttpHelperConfig config, _HttpHelperRequest request) {
    this.config = config;
    this.request = request;
  }

  private HttpURLConnection connection() throws IOException {
    /*
    获取代理信息
     */
    HttpProxy httpProxy = this.request.proxy();
    Proxy proxy = null;
    if (httpProxy != null)
      proxy = this.request.proxy().proxy();

    /*
    构建连接
     */
    URL _url = new URL(this.request.url());
    /*
    根据是否有代理情况构建 HttpURLConnection
     */
    HttpURLConnection conn = proxy == null ? (HttpURLConnection) _url.openConnection() : (HttpURLConnection) _url.openConnection(proxy);

    /*
    是否 SSL
     */
    if (conn instanceof HttpsURLConnection) {
      ((HttpsURLConnection) conn).setSSLSocketFactory(_HttpHelperSSL.sslSocket());
      ((HttpsURLConnection) conn).setHostnameVerifier(_HttpHelperSSL.hostnameVerifier());
    }

    /*
    请求相关设定
     */
    conn.setDoOutput(true);
    conn.setDoInput(true);
    conn.setConnectTimeout(this.request.config().connectionTimeout());
    conn.setReadTimeout(this.request.config().soTimeout());
    try {
      conn.setRequestMethod(this.request.method().name());
    } catch (Exception e) {
      conn.setRequestMethod("POST");
      conn.setRequestProperty("X-HTTP-Method-Override", this.request.method().name());
    }

    Set<HttpHeader> headers = this.request.headers();
    /*
    没有请求头直接返回连接对象
     */
    if (headers == null || headers.isEmpty())
      return conn;

    /*
    将请求头写入到连接中
     */
    headers.forEach(header -> conn.setRequestProperty(header.name(), header.value()));

    /*
    此處不可進行 header.clear()  http 新增 handler 功能, 並且爲異步執行
    若此處進行 clear() 將會有機率導致同時執行, 觸發 ConcurrentModificationException 異常
    另外, header 目前宜不建議主動 clear, 應用程序可能在請求後還會對 header 進行更多操作.
     */
//    headers.clear();
    return conn;
  }

  private InputStream inputstream(HttpURLConnection conn) {
    OutputStream os = null;
    InputStream inputStream = null;
    try {
      conn.connect();

      if (this.request.body() != null) {
        os = conn.getOutputStream();
        os.write(this.request.body().bytes());
        os.flush();
        os.close();
      }

      int responseCode = conn.getResponseCode();
      inputStream = responseCode >= 400 ? conn.getErrorStream() : conn.getInputStream();


      return inputStream;
    } catch (Exception e) {
      if (e instanceof RuntimeException)
        throw (RuntimeException) e;
      /*
      ConnectException 拦截, 在错误消息中追加请求连接
      Enoa 中异常处理多数被优化取最终 cause 因此此处不可将 e 传递至 RuntimeException 中
       */
      if (e instanceof ConnectException)
        throw new RuntimeException(e.getMessage() + " => " + this.request.url());
      // httpurlconnection 在 url 無法訪問時(某種情況 .. 忘記)會拋出 FileNotFoundException 異常, 此處可以做進一步處理, 暫時直接拋出
      if (e.getCause() instanceof FileNotFoundException)
        throw new RuntimeException(e.getCause().getMessage(), e.getCause());
      throw new RuntimeException(e.getMessage(), e);
    } finally {
      if (os != null)
        try {
          os.close();
        } catch (IOException e) {
          // skip
        }

    }
  }

  public HttpResponse execute() {
    HttpURLConnection conn;
    try {
      conn = this.connection();
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
    try (InputStream inputstream = this.inputstream(conn)) {
      return new _HttpHelperResponse(this.config, conn, inputstream, this.request.charset());
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage(), e);
    } finally {
//      conn.disconnect();
    }
  }

  public HttpResponse chunked(Chunk chunk) {
    HttpURLConnection conn;
    try {
      conn = this.connection();
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
    try (InputStream inputstream = this.inputstream(conn)) {
      return new _HttpHelperChunkedResponse(this.config, conn, inputstream, this.request.charset(), chunk);
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage(), e);
    } finally {
//      conn.disconnect();
    }
  }


}
