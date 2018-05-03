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
package io.enoa.toolkit.http;

import io.enoa.toolkit.text.TextKit;

public class UriKit {

  private UriKit() {

  }

  /**
   * 纠正 uri 错误
   *
   * @param uri uri
   * @return String
   */
  public static String correct(String uri) {
    if (uri == null)
      return null;
    // 非 / 开头补齐
    if (!uri.startsWith("/"))
      uri = "/".concat(uri);
    // 将多个 // 替换成 /
    uri = uri.replaceAll("//+", "/");
    if ("/".equals(uri))
      return uri;

    int qix = uri.indexOf("?");
    String paras = null;
    if (qix != -1) {
       paras = uri.substring(qix + 1);
       uri = uri.substring(0, qix);
    }
    // 剔除最后一个 / 不考虑根路径的情况
    if (uri.length() > 1)
      uri = uri.endsWith("/") ? uri.substring(0, uri.lastIndexOf("/")) : uri;
    if (paras != null)
      return TextKit.union(uri, "?", paras);

    return uri;
  }

  /**
   * 剔除 uri 中 context 路径
   *
   * @param context context
   * @param uri     uri
   * @return String
   */
  public static String rmcontext(String context, String uri) {
    context = correct(context);
    // 纠正 uri
    uri = correct(uri);
    if (uri.length() < context.length())
      return uri;
    // 从 context 位置截取 uri
    uri = uri.substring(context.length(), uri.length());
    // 截取后为 "" 表明是根路径
    if ("".equals(uri))
      uri = "/";
    return correct(uri);
  }

}
