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
package io.enoa.repeater.http;

import java.util.Date;
import java.util.Map;

public interface Request {

  Object originRequest();

  Method method();

  String context();

  String uri();

  String url();

  RequestBody body();

  Cookie[] cookies();

  Cookie cookieObject(String name);

  default String cookie(String name) {
    return this.cookie(name, null);
  }

  String cookie(String name, String def);

  default Integer cookieToInt(String name) {
    return this.cookieToInt(name, null);
  }

  Integer cookieToInt(String name, Integer def);

  default Long cookieToLong(String name) {
    return this.cookieToLong(name, null);
  }

  Long cookieToLong(String name, Long def);

  String[] headerNames();

  String header(String name);

  // 避免歧义, 暂不放开
//  String header(String name, String def);

  default String para(String name) {
    return this.para(name, null);
  }

  String para(String name, String def);

  default Integer paraToInt(String name) {
    return this.paraToInt(name, null);
  }

  Integer paraToInt(String name, Integer def);

  default Long paraToLong(String name) {
    return this.paraToLong(name, null);
  }

  Long paraToLong(String name, Long def);

  default Boolean paraToBoolean(String name) {
    return this.paraToBoolean(name, null);
  }

  Boolean paraToBoolean(String name, Boolean def);

  default Double paraToDouble(String name) {
    return this.paraToDouble(name, null);
  }

  Double paraToDouble(String name, Double def);

  default Date paraToDate(String name) {
    return this.paraToDate(name, "yyyy-MM-dd", null);
  }

  default Date paraToDate(String name, String format) {
    return this.paraToDate(name, format, null);
  }

  Date paraToDate(String name, String format, Date def);

  Map<String, String[]> paraMap();

  String[] paraNames();

  String[] paraValues(String name);

  Integer[] paraValuesToInt(String name);

  Long[] paraValuesToLong(String name);

  <T> T attr(String name);

  <T> Request attr(String name, T data);

  String[] attrNames();

  Request rmAttr(String name);

  UFile[] files();

  UFile[] files(String name);

  UFile file(String name);

  void clear();
}
