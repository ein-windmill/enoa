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
package io.enoa.http.protocol;

import io.enoa.http.kit.SafeURL;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class HttpPara {


  private final String name;
  private final String value;
  private final boolean array;

  public HttpPara(String name, String value) {
    this(name, value, false);
  }

  public HttpPara(String name, String value, boolean array) {
    if (name == null)
      throw new IllegalArgumentException("Http para name can not be null.");
    if (value == null)
      throw new IllegalArgumentException("Http para value can not be null.");
    this.name = name;
    this.value = value;
//    this.value = value.replace("&", "&amp;");
    this.array = array;
  }

  public static Set<HttpPara> parse(String text) {
    if (text == null || "".equals(text))
      return Collections.emptySet();

    if (text.contains("?")) {
      text = text.startsWith("http://") || text.startsWith("https://") ? text.substring(text.indexOf("?") + 1) : text;
      if ("".equals(text))
        return Collections.emptySet();
    }
    String[] paraItems = text.split("&");

    Set<HttpPara> paras = new HashSet<>();
    for (String paraItem : paraItems) {
      int eqIx = paraItem.indexOf("=");
      if (eqIx == -1)
        continue;
      String name = paraItem.substring(0, eqIx),
        value = paraItem.substring(eqIx + 1);
      if ("".equals(name) || "".equals(value))
        continue;
      paras.add(new HttpPara(name, value));
    }
    return paras;
  }

  public String name() {
    return this.name;
  }

  public String value() {
    return this.value;
  }

  public boolean array() {
    return this.array;
  }

  public String output(boolean traditional, Charset charset) {
    StringBuilder para = new StringBuilder();
    para.append(SafeURL.encode(this.name, charset));
    if (!traditional && this.array)
      para.append("[]");
    para.append("=");
    para.append(SafeURL.encode(this.value, charset));
    return para.toString();
  }

  public String output(boolean traditional) {
    StringBuilder para = new StringBuilder();
    para.append(this.name);
    if (!traditional && this.array)
      para.append("[]");
    para.append("=");
    para.append(this.value);
    return para.toString();
  }

  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + this.name.hashCode();
    result = 31 * result + this.value.hashCode();
    result = result + (this.array ? 0 : 1);
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof HttpPara))
      return false;
    if (this == obj)
      return true;
    HttpPara that = (HttpPara) obj;
    return this.name.equals(that.name)
      && this.value.equals(that.value)
      && this.array == that.array;
  }

  @Override
  public String toString() {
    return String.format("%s=%s (%s)", this.name, this.value, this.array ? "array" : "single");
  }
}
