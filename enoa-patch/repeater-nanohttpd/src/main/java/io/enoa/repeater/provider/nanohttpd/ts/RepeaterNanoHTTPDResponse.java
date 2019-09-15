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
package io.enoa.repeater.provider.nanohttpd.ts;

import fi.iki.elonen.NanoHTTPD;
import io.enoa.repeater.http.Cookie;
import io.enoa.repeater.http.Header;
import io.enoa.repeater.http.Response;
import io.enoa.toolkit.is.Is;

import java.io.ByteArrayInputStream;
import java.util.List;

class RepeaterNanoHTTPDResponse extends NanoHTTPD.Response {

  private Response resp;

  RepeaterNanoHTTPDResponse(Response response) {
    super(Status.lookup(response.status().code()),
      response.contentType(),
      new ByteArrayInputStream(response.body().bytes()),
      response.body().length()
    );
    this.resp = response;
    this.ts();
  }


  private void ts() {

    List<Header> headers = this.resp.headers();
    headers.stream()
      .filter(h -> !h.name().equalsIgnoreCase("content-type") && !h.name().equalsIgnoreCase("content-length"))
      .forEach(h -> super.addHeader(h.name(), h.value()));

    Cookie[] cookies = this.resp.cookies();
    if (Is.empty(cookies)) {
      return;
    }
    for (Cookie cookie : cookies) {
      super.addHeader("Set-Cookie", cookie.toString());
    }
  }

}
