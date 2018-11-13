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
import io.enoa.toolkit.convert.ConvertKit;
import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.toolkit.number.NumberKit;
import io.enoa.toolkit.text.TextKit;
import io.enoa.toolkit.text.TextReader;

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
    String temp = TextKit.lower(TextKit.nospace(url));
    if (!temp.startsWith("http://") && !temp.startsWith("https://"))
      throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.toolkit.url_protocol_fail"));

    int pix = temp.indexOf("://");
    String protocol = url.substring(0, pix);
    String content = url.substring(pix + 3);
    String uri = UriKit.correct(content).substring(1);
    return TextKit.union(protocol, "://", uri);
  }

  public static String[] parts(String url) {
    int ix = url.indexOf("/", url.indexOf("//") + 2);
    String host = url.substring(0, ix),
      remain = url.substring(ix);
    return new String[]{host, remain};
  }

  public static ARL analysis(String url) {
    if (TextKit.blanky(url))
      return null;
    url = correct(url);
    String skipcaseurl = url.toLowerCase();
    ARL.Builder builder = new ARL.Builder();
    ARL.Protocol protocol = skipcaseurl.startsWith("http://") ? ARL.Protocol.HTTP : ARL.Protocol.HTTPS;
    int six = protocol == ARL.Protocol.HTTP ? 7 : 8;
    TextReader reader = new TextReader(url);
    StringBuilder host = new StringBuilder();
    StringBuilder port = new StringBuilder();
    int eix = 0;
    boolean entryport = false;
    while (reader.hasNext()) {
      eix += 1;
      while (six-- > 0) {
        eix += 1;
        reader.next();
      }
      char now = reader.next();
      if (now == '/' || now == '?') {
        if (now == '?')
          eix -= 1;
        break;
      }
      if (now == ':') {
        entryport = true;
        continue;
      }
      if (entryport) {
        port.append(now);
        continue;
      }
      host.append(now);
    }
    String remain = eix >= url.length() ? null : url.substring(eix);

    String paras = null;
    String path = remain;
    if (remain != null && remain.contains("?")) {
      int ix = remain.indexOf("?");
      paras = remain.substring(ix + 1);
      path = remain.substring(0, ix);
    }

    String _p = port.toString();
    builder.protocol(protocol)
      .host(host.toString())
      .port(NumberKit.isNumber(_p) ? ConvertKit.integer(_p) : protocol == ARL.Protocol.HTTP ? 80 : 443)
      .remain(TextKit.blanky(remain) ? null : remain)
      .path(TextKit.blanky(path) ? null : path)
      .paras(TextKit.blanky(paras) ? null : paras);
    host.delete(0, host.length());
    port.delete(0, port.length());
    return builder.build();
  }


}
