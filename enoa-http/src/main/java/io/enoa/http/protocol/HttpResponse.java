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

import io.enoa.http.EoHttpConfig;

import java.nio.charset.Charset;
import java.util.List;

public interface HttpResponse {

  EoHttpConfig config();

  int code();

  HttpVersion version();

  boolean ok();

  String uri();

  String url();

  String protocol();

  String host();

  Charset charset();

  String message();

  boolean isRedirect();

  String[] cookieNames();

  HttpCookie cookieObject(String name);

  String cookie(String name);

  String[] headerNames();

  String header(String name, String def);

  String header(String name);

  List<String> headers(String name);

  HttpResponseBody body();

  void clear();
}
