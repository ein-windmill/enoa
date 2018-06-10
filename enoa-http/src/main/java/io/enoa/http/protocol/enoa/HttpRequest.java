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
package io.enoa.http.protocol.enoa;

import io.enoa.http.EoHttpConfig;
import io.enoa.http.protocol.HttpHeader;
import io.enoa.http.protocol.HttpMethod;
import io.enoa.http.protocol.HttpVersion;
import io.enoa.http.proxy.HttpProxy;

import java.nio.charset.Charset;
import java.util.Set;

public interface HttpRequest {

  HttpVersion version();

  String url();

  HttpMethod method();

  Set<HttpHeader> headers();

  HttpRequestBody body();

  EoHttpConfig config();

  Charset charset();

  HttpProxy proxy();

}
