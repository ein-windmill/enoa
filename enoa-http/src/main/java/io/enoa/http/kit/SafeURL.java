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
package io.enoa.http.kit;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class SafeURL {

  private static Map<String, String> SYMBOLMAPPING = new HashMap<>();

  static {
    initSymbolMapping();
  }

  private static String encodeUrl(String url, Charset charset) {
    try {
      return URLEncoder.encode(url, charset.name());
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  private static void initSymbolMapping() {
    // not support utf-16 utf-32
    Charset utf8 = Charset.forName("UTF-8");
    String[] symbols = {
      "~", "!", "@", "#", "$", "^", "&", "*", "(", ")",
      "=", "[", "]", "{", "}", "|", "\\", ";", ":",
      "'", "\"", ",", "<", ".", ">", "?", "/"
    };
    for (String symbol : symbols) {
      String esym = encodeUrl(symbol, utf8);
      SYMBOLMAPPING.put(esym, symbol);
    }
  }

  private static String reductionSymbol(String encodeUrl) {
    for (String k : SYMBOLMAPPING.keySet()) {
      encodeUrl = encodeUrl.replace(k, SYMBOLMAPPING.get(k));
    }
    return encodeUrl;
  }

  public static String encode(String url, Charset charset) {
    if (url == null)
      throw new IllegalArgumentException("Encode url can not be null.");
    return reductionSymbol(encodeUrl(url, charset));
  }

  public static String decode(String url, Charset charset) {
    if (url == null)
      throw new IllegalArgumentException("Decode url can not be null.");
    try {
      return URLDecoder.decode(url, charset.name());
    } catch (UnsupportedEncodingException e) {
      return url;
    }
  }

}
