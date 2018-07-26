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
package io.enoa.repeater.kit.http;

import io.enoa.repeater.http.Cookie;
import io.enoa.repeater.http.Request;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.text.TextKit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnoaHttpKit {

  /**
   * 解析  para=1&para=2 到 map
   *
   * @param text url
   * @return Map
   */
  public static Map<String, List<String>> parsePara(String text) {
    if (TextKit.isBlank(text))
      return null;
    String urlPara = text;
    if (text.contains("?")) {
      urlPara = urlPara.substring(text.indexOf("?") + 1);
      if (TextKit.isBlank(urlPara))
        return null;
    }
    String[] paraItems = urlPara.split("&");

    Map<String, List<String>> urlp = new HashMap<>();
    for (String paraItem : paraItems) {
      int eqIx = paraItem.indexOf("=");
      if (eqIx == -1)
        continue;
      String name = paraItem.substring(0, eqIx),
        value = paraItem.substring(eqIx + 1);
      if (TextKit.isBlank(name) || TextKit.isBlank(value))
        continue;
      List<String> pval = urlp.get(name);
      if (CollectionKit.notEmpty(pval)) {
        pval.add(value);
        continue;
      }
      pval = new ArrayList<>();
      pval.add(value);
      urlp.put(name, pval);
    }
    return urlp;
  }

  public static Cookie[] parseCookie(String cookieStr) {
    if (TextKit.isBlank(cookieStr))
      return CollectionKit.emptyArray(Cookie.class);
    List<Cookie> cookies = new ArrayList<>();
    String[] cks = cookieStr.split(";");
    for (String ck : cks) {
      String[] cnvs = ck.split("=");
      if (CollectionKit.isEmpty(cnvs))
        continue;
      if (cnvs.length != 2)
        continue;
      String name = cnvs[0], value = cnvs[1];
      if (TextKit.isBlank(name))
        continue;
      if (TextKit.isBlank(value))
        continue;
      cookies.add(new Cookie.Builder().name(TextKit.nospace(name)).value(TextKit.nospace(value)).build());
    }
    return cookies.toArray(new Cookie[cookies.size()]);
  }

  public static String parseIp(Request request) {
    String ip = request.header("x-forwarded-for");
    if (TextKit.notBlank(ip) && !"unknown".equalsIgnoreCase(ip))
      return ip;
    ip = request.header("Proxy-Client-IP");
    if (TextKit.notBlank(ip) && !"unknown".equalsIgnoreCase(ip))
      return ip;
    ip = request.header("WL-Proxy-Client-IP");
    if (TextKit.notBlank(ip) && !"unknown".equalsIgnoreCase(ip))
      return ip;
    return null;
  }

}
