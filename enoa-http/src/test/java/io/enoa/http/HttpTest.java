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
package io.enoa.http;

import io.enoa.http.auth.basic.BasicAuth;
import io.enoa.http.protocol.HttpHeader;
import io.enoa.http.protocol.HttpMethod;
import io.enoa.http.protocol.HttpResponse;
import io.enoa.http.protocol.HttpResponseBody;
import io.enoa.log.Log;
import io.enoa.log.provider.slf4j.Slf4JLogProvider;
import io.enoa.serialization.EoSerializer;
import io.enoa.serialization.provider.jdk.JdkSerializeProvider;
import io.enoa.toolkit.EoConst;
import io.enoa.toolkit.binary.EnoaBinary;
import io.enoa.toolkit.path.PathKit;
import io.enoa.toolkit.text.TextKit;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class HttpTest {

  private EoSerializer serializer = new JdkSerializeProvider().serializer();

  @Before
  public void before() {
    Log.epm().install(new Slf4JLogProvider());
  }

  @Test
  public void testUrl() {
    String url = "http://httpbin.org/get?ahh/=0=1&b=2&c?=3&d=44444?";
    String ub = EoUrl.with(url)
      .charset(Charset.forName("utf-8"))
      .traditional(true)
      .encode()
      .para("arg0", "v0")
      .para("arg1", "學")
      .para("arg2", "文")
      .para("あ", "v5")
      .para("arg4", "a")
      .para("d", new String[]{"bbbbb", "bbbbb", "c", "d"})
      .para("zzzzz", new int[]{1, 2, 3, 4})
      .end();
    System.out.println(ub);
  }

  @Test
  public void testHeader() {
    Set<HttpHeader> headers = new HashSet<>();
    headers.add(new HttpHeader("a", "1"));
    headers.add(new HttpHeader("a", "2"));
    System.out.println(headers);
  }

  private Http http() {
//    Path upload = Paths.get(PathKit.path().concat("/upload/file.txt"));
    Path upload = PathKit.debugPath().resolve("/upload/file.txt");
    String url;
    url = "http://localhost:9001/example";
    url = "http://localhost:9102/example/actionsess";
    url = "http://httpbin.org/get?a0=0";
    return Http.request(url)
      .method(HttpMethod.GET)
      .traditional(true)
      .encode()
      .charset(Charset.forName("UTF-8"))
      .contentType("application/x-www-form-urlencoded")
      .auth(new BasicAuth("admin", "passwd"))
//      .proxy("127.0.0.1", 2310)

      .header("h0", "hv0")
      .header("h0", "hv0")
      .header("h0", "hv1")
      .header("h1", "hv3")
      .header("cookie", "a")
      .header("Cookie", "b")
      .header("user-agent", "Mozilla/5.0 Firefox 54")

      .cookie("ck0", "cv0")
      .cookie("ck0", "cv1")
      .cookie("ck0", "cv0")
      .cookie("ck1", "cv2")

      // binary
//      .binary(this.serialize(Kv.by("ser0", "sev0")))


      /*
      application/x-www-form-urlencoded or multipart/form-data or text/plain or binary
       */
      .para("a0", "0")
      .para("a0", "1")
      .para("a0", "1")
      .para("a0", "2")
      .para("a1", "3")

      // multipart/form-data file upload
//      .para("file0", upload)
//      .para("file0", upload)
//      .para("file1", upload)
//      .para(upload)
//      .para(upload)

      // text/plain   request body
//      .raw("raw body!") // , "text/plain"
      //
      ;
  }

  private byte[] serialize(Object object) {
    return this.serializer.serialize(object);
  }

  private <T> T reduction(byte[] bytes) {
    return this.serializer.reduction(bytes);
  }


  @Test
  public void testHttp() {
    try {
      HttpResponse response = this.http().emit();
      System.out.println(response.body().string());
    } catch (Exception e) {
      Log.error(e.getMessage(), e);
    }
  }

  @Test
  public void testEnqueue() throws InterruptedException {
    this.http().enqueue()

//      .<HttpResponse>then(resp -> resp.body().string())
//      .<String>valid(TextKit::blankn)
//      .<String>execute(Log::debug)
//      .<String>fail(data -> Log.error("REQUEST FAIL"))
//      .capture(e -> Log.error(e.getMessage(), e))
//      .always(() -> Log.debug("finally."));


      .ok(System.out::println)
      .error(System.out::println)
      .then(HttpResponse::body)
      .then(HttpResponseBody::bytes)
      .<byte[]>then(body -> EnoaBinary.create(body, EoConst.CHARSET).string())
      .<String>valid(TextKit::blankn)
      .<String>valid(data -> data.length() > 30)
      .<String>execute(System.out::println)
      .<String>fail(System.err::println)
      .capture(System.err::println)
      .always(() -> System.out.println("finally.."));
    TimeUnit.SECONDS.sleep(3L);
  }

}
