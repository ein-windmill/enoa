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
package io.enoa.yosart.kit.yo;

import io.enoa.repeater.http.Cookie;
import io.enoa.repeater.http.HttpStatus;
import io.enoa.repeater.http.Response;

public class ResponseKit {

  public static Response merge(Response resp0, Response resp1) {
    return merge(resp0, resp1, false);
  }

  public static Response merge(Response resp0, Response resp1, boolean includeBody) {
    if (resp0 == null)
      return resp1;
    if (resp1 == null)
      return resp0;
    Response.Builder rb0 = resp0.newBuilder();

    rb0.charset(resp1.charset());

    resp1.headers().stream()
      .filter(h -> !h.name().equalsIgnoreCase("content-type") &&
        !h.name().equalsIgnoreCase("content-length"))
      .forEach(rb0::header);
//    rb0.contentLength(resp1.contentLength());
//    rb0.contentType(resp1.contentType());

    for (Cookie cookie : resp1.cookies())
      rb0.cookie(cookie);

    if (resp0.status() == HttpStatus.OK) {
      rb0.status(resp1.status());
    }

    if (includeBody)
      rb0.body(resp1.body());

    return rb0.build();
  }

}
