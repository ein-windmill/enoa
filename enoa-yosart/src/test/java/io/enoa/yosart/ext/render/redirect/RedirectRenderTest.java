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

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class RedirectRenderTest {


  @Test
  public void testUrlencode() throws UnsupportedEncodingException {
    String url = "http://example.com/?test=1&p=2&p=3&space=a b&chinese=中文";
    String[] urps = url.split("\\?");
    String right = urps[1], left = urps[0];
    String encode = URLEncoder.encode(right, "UTF-8");
    System.out.println(left + "?" + encode);

  }

}
