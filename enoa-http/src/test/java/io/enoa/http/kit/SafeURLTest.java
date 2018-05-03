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
package io.enoa.http.kit;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;

public class SafeURLTest {


  @Test
  public void testEncode() {
    String url = "http://example/party/文#!page=lo%gi n&z?page=1&size=10&test=true&%other*={\"key\": \"文%\"}";
    url = SafeURL.encode(url, Charset.forName("UTF-8"));
    System.out.println(url);
    url = SafeURL.decode(url, Charset.forName("UTF-8"));
    System.out.println(url);


    try {
      String c1 = URLEncoder.encode("文", "utf-8");
      System.out.println(c1);
      c1 = URLDecoder.decode(c1, "utf-8");
      System.out.println(c1);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
  }

}
