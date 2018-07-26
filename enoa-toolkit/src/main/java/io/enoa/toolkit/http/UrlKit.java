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
package io.enoa.toolkit.http;

import io.enoa.toolkit.EoConst;
import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.toolkit.text.TextKit;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class UrlKit {

  private UrlKit() {

  }

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
      throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.toolkit.url_encode_null"));
    return reductionSymbol(encodeUrl(url, charset));
  }

  public static String decode(String url, Charset charset) {
    if (url == null)
      throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.toolkit.url_decode_null"));
    try {
      return URLDecoder.decode(url, charset.name());
    } catch (UnsupportedEncodingException e) {
      return url;
    }
  }

  public static String encode(String url) {
    return encode(url, EoConst.CHARSET);
  }

  public static String decode(String url) {
    return decode(url, EoConst.CHARSET);
  }

  public static String correct(String url) {
    String temp = TextKit.lower(url);
    if (!temp.startsWith("http://") && !temp.startsWith("https://"))
      throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.toolkit.url_protocol_fail"));

    int pix = temp.indexOf("://");
    String protocol = url.substring(0, pix);
    String content = url.substring(pix + 3);
    String uri = UriKit.correct(content).substring(1);
    return TextKit.union(protocol, "://", uri);
  }

  public static String[] analysis(String url) {
    int ix = url.indexOf("/", url.indexOf("//") + 2);
    String host = url.substring(0, ix),
      remain = url.substring(ix);
    return new String[]{host, remain};
  }
}
