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
package io.enoa.example.http;

import io.enoa.http.EoUrl;
import io.enoa.http.Http;
import io.enoa.http.auth.basic.BasicAuth;
import io.enoa.http.protocol.HttpMethod;
import io.enoa.http.protocol.HttpResponse;
import io.enoa.http.protocol.HttpResponseBody;
import io.enoa.http.provider.httphelper.HttpHelperProvider;
import io.enoa.http.provider.httphelper.async.HttpHelperExecutor;
import io.enoa.http.proxy.HttpProxy;
import io.enoa.http.proxy.SocketProxy;
import io.enoa.toolkit.EoConst;
import io.enoa.toolkit.binary.EnoaBinary;
import io.enoa.toolkit.text.TextKit;

import java.nio.file.Paths;
import java.util.HashSet;

public class HttpExample {

  private String url = "https://httpbin.org";

  private void print(String type, HttpResponse response) {
    System.out.println("==================> " + type.toUpperCase());
    System.out.println(response.body().string());
    System.out.println("==================\n");
  }

  private void get() {
    HttpResponse resp = Http.request(this.url + "/get")
      .method(HttpMethod.GET)
      .para("arg0", "v0")
      .para("arg1", "v1")
      .header("user-agent", "Mozilla/5.0 firefox 54")
      .header("origin", "http://localhost:100")
      .emit();
    this.print(HttpMethod.GET.name(), resp);
  }

  private void post() {
    HttpResponse resp = Http.request(EoUrl.with(this.url).subpath("post").para("arg0", 1))
      .method(HttpMethod.POST)
      .para("arg0", "2")
      .para("arg1", 3)
      .cookie("key0", "value0")
      .emit();
    this.print(HttpMethod.POST.name(), resp);
  }

  private void put() {
    HttpResponse resp = Http.request(HttpMethod.PUT, EoUrl.with(this.url).subpath("put"))
      .para("arg0", 1)
      .para("arg0", 2)
      .para("arg3", 4)
      .para("method", "put")
      .emit();
    this.print(HttpMethod.PUT.name(), resp);
  }

  private void raw() {
    HttpResponse resp = Http.request(HttpMethod.POST, EoUrl.with(this.url + "/post?arg0=1"))
      .raw("{\"key\": \"value\"}", "application/json")
      .para("arg1", true)
      .emit();
    this.print("raw", resp);
  }

  private void array() {
    HttpResponse resp = Http.request(HttpMethod.POST, this.url + "/post?arg0=1&arg0=2&arg3=4")
      .para("array", new int[]{1, 2, 3, 4, 4, 5})
      .emit();
    this.print("array", resp);
  }

  private void upload() {
    HttpResponse resp = Http.request(this.url + "/post?k1=v1")
      .method(HttpMethod.POST)
      .para("k1", "v2")
      .para("f1", Paths.get(System.getProperty("os.name").toLowerCase().contains("win") ? "c:\\windows\\system32\\cmd.exe" : "/usr/bin/ls"))
      .emit();
    this.print("upload", resp);
  }

  private void binary() {
    HttpResponse resp = Http.request(EoUrl.with(this.url).subpath("post"))
      .method(HttpMethod.POST)
      .contentType("application/octet-stream")
      .para("arg0", 1)
      .para("arg1", 2)
      .binary(new byte[]{0, 1, 2, 3})
      .emit();
    this.print("binary", resp);
  }

  private void enqueue() {
    Http.request(EoUrl.with(this.url).subpath("get"))
      .method(HttpMethod.GET)
      .enqueue()
      .ok(resp -> this.print("enqueue ok", resp))
      .error(resp -> this.print("enqueue fail", resp))
      .then(HttpResponse::body)
      .then(HttpResponseBody::bytes)
      .<byte[]>then(body -> EnoaBinary.create(body, EoConst.CHARSET).string())
      .<String>valid(TextKit::blankn)
      .<String>valid(data -> data.length() > 30)
      .execute(System.out::println)
      .fail(System.err::println)
      .capture(System.err::println)
      .always(() -> System.out.println("finally.."));
  }

  private void other() {
    Http.request(EoUrl.with(this.url).subpath("get"))
      .method(HttpMethod.GET)
      .executor(HttpHelperExecutor.instance())
      .traditional(false)
      .encode()
      .para("k0", "學")
      .para("k0", "文")
      .para("k1", "A")
      .emit();
//      .enqueue()
//      .ok(resp -> this.print("enqueue ok", resp))
//      .error(resp -> this.print("enqueue fail", resp))
//      .then(HttpResponse::body)
//      .then(HttpResponseBody::bytes)
//      .<byte[]>then(body -> EnoaBinary.create(body, EoConst.CHARSET).string())
//      .<String>valid(TextKit::blankn)
//      .<String>valid(data -> data.length() > 30)
//      .<String>execute(System.out::println)
//      .<String>fail(System.err::println)
//      .capture(System.err::println)
//      .always(() -> System.out.println("finally.."));
  }

  private void auth() {
    HttpResponse resp = Http.request(this.url + "/get")
      .para("arg0", "v0")
      .auth(new BasicAuth("user", "passwd"))
//      .auth(new BearerAuth("token"))
//      .auth(new OAuth2("token"))
      .emit();
    this.print("auth", resp);
  }

  private void with() {
    HttpResponse resp = Http.use(HttpHelperProvider.instance())
      .url(this.url + "/get")
      .emit();
    this.print("with", resp);
  }

  private void proxy(HttpProxy proxy) {
    HttpResponse resp = Http.request("http://cip.cc")
      .proxy(proxy)
      .header("user-agent", "curl/7.57.0")
      .emit();
    this.print("proxy", resp);
  }

  private void url() {
    EoUrl url = EoUrl.with("http://example.com")
      .traditional(false)
      .encode()
      .subpath("manage")
      .subpath("user", "login")
      .para("arg0", "v0")
      .para("arg1", "v1")
      .para("array", 0)
      .para("array", "1")
      .para("array", new int[]{2, 3, 4, 5})
      .para("collection", new HashSet<Integer>() {{
        add(0);
        add(1);
        add(2);
      }});

    System.out.println(url.end());
  }

  public static void main(String[] args) {
    HttpExample example = new HttpExample();
    example.enqueue();
    example.get();
    example.post();
    example.put();
    example.raw();
    example.other();
    example.array();
    example.upload();
    example.binary();
    example.auth();
    example.with();
    example.proxy(new SocketProxy("127.0.0.1", 2310));
    example.url();
    System.out.println("FINAL");
  }

}
