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

import io.enoa.http.kit.SafeURL;
import io.enoa.http.protocol.HttpPara;

import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

public class EnoaUrl implements EoUrl {

  private Charset charset;
  private String url;
  private Set<HttpPara> paras;
  private boolean traditional;
  private boolean encode;
//  private boolean change;

  public EnoaUrl(String url) {
    this(true, url);
  }

  protected EnoaUrl(boolean valid, String url) {
    if (url == null)
      throw new IllegalArgumentException("Url can not be null.");
    if (valid && (!url.startsWith("http://") && !url.startsWith("https://")))
      throw new IllegalArgumentException("Url must http(s):// protocol -> " + url);
    int qIx = url.indexOf("?");
    this.charset = Charset.forName("UTF-8");
    if (qIx == -1) {
      this.url = url;
      this.paras = new HashSet<>();
    } else {
      this.url = url.substring(0, qIx);
      Set<HttpPara> pas = HttpPara.parse(url.substring(qIx + 1));
      this.paras = new HashSet<>(pas);
    }
//    this.change = Boolean.FALSE;
    this.encode = Boolean.FALSE;
    this.traditional = Boolean.TRUE;
  }

  private EoUrl para(String name, Object value, boolean array) {
    Set<HttpPara> itm = new HashSet<>();
    Iterator<HttpPara> iterator = this.paras.iterator();
    while (iterator.hasNext()) {
      HttpPara para = iterator.next();
      if (!para.name().equals(name))
        continue;
      // 当前添加的参数之前已添加, 将是否数组标志改为 true
      array = true;
      // 若之前添加的参数标记已是数组, 不进行操作
      if (para.array())
        continue;
      // 否则改变之前添加的参数标记为数组
      iterator.remove();
      itm.add(new HttpPara(para.name(), para.value(), true));
    }
    itm.add(new HttpPara(name, value.toString(), array));
    this.paras.addAll(itm);
    itm.clear();
//    this.change = true;
    return this;
  }

  private Object[] toArr(Object value) {
    int len = Array.getLength(value);
    Object[] ret = new Object[len];
    for (int i = 0; i < len; ++i) {
      ret[i] = Array.get(value, i);
    }
    return ret;
  }

  private String name(HttpPara para) {
    return para.name().concat(!this.traditional && para.array() ? "[]" : "");
  }

  private String endWithEncode(HttpPara para) {
    return SafeURL.encode(this.name(para).concat("=").concat(para.value()), this.charset);
  }

  private String endNoEncode(HttpPara para) {
    return this.name(para)
      .concat("=")
      .concat(para.value());
  }

  @Override
  public EoUrl charset(Charset charset) {
    this.charset = charset;
//    this.change = true;
    return this;
  }

  @Override
  public EoUrl subpath(String subpath) {
    if (subpath.startsWith("/"))
      subpath = subpath.substring(1);
    if (this.url.endsWith("/"))
      this.url = this.url.substring(0, this.url.length() - 1);
    this.url = this.url.concat("/").concat(subpath);
    return this;
  }

  @Override
  public EoUrl subpath(String... subpath) {
    for (String sp : subpath) {
      this.subpath(sp);
    }
    return this;
  }

  @Override
  public EoUrl para(String name, Object value) {
    if (name == null)
      throw new IllegalArgumentException("name == null");
    if (value == null)
      throw new IllegalArgumentException("value == null");

    if (value.getClass().isArray()) {
      return this.para(name, this.toArr(value));
    }
    this.para(name, value, false);
    return this;
  }

  @Override
  public EoUrl para(String name, Object[] values) {
    if (name == null)
      throw new IllegalArgumentException("name == null");
    if (values == null)
      throw new IllegalArgumentException("values == null");

    for (Object value : values) {
      this.para(name, value, true);
    }
    return this;
  }

  @Override
  public EoUrl para(String name, Collection values) {
    if (name == null)
      throw new IllegalArgumentException("name == null");
    if (values == null)
      throw new IllegalArgumentException("values == null");

    values.forEach(v -> this.para(name, v, true));
    return this;
  }

  @Override
  public EoUrl traditional(boolean traditional) {
    this.traditional = traditional;
//    this.change = true;
    return this;
  }

  @Override
  public EoUrl encode(boolean encode) {
    if (!encode)
      return this;
    this.encode = true;
//    this.change = true;
    return this;
  }

  @Override
  public HttpPara[] paras() {
    return Collections.unmodifiableSet(this.paras).toArray(new HttpPara[this.paras.size()]);
  }

  @Override
  public String end() {
//    if (!this.change)
//      return this.url;

    if (this.paras == null || this.paras.isEmpty())
      return this.url;

    String paras = String.join("&", this.paras.stream()
      .map(para -> this.encode ? this.endWithEncode(para) : this.endNoEncode(para))
      .collect(Collectors.toSet()));
    return this.url.concat("?").concat(paras);
  }

  @Override
  public String toString() {
    return this.end();
  }
}
