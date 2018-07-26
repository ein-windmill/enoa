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

import io.enoa.toolkit.mark.IMarkMsg;

import java.util.Arrays;

public enum HttpStatus implements IMarkMsg {

  SWITCH_PROTOCOL(101, "Switching Protocols"),

  OK(200, "OK"),
  CREATED(201, "Created"),
  ACCEPTED(202, "Accepted"),
  NO_CONTENT(204, "No Content"),
  PARTIAL_CONTENT(206, "Partial Content"),
  MULTI_STATUS(207, "Multi-Status"),

  REDIRECT(301, "Moved Permanently"),
  /**
   * Many user agents mishandle 302 in ways that violate the RFC1945
   * spec (i.e., redirect a POST to a GET). 303 and 307 were added in
   * RFC2616 to address this. You should prefer 303 and 307 unless the
   * calling user agent does not support 303 and 307 functionality
   */
  @Deprecated
  FOUND(302, "Found"),
  REDIRECT_SEE_OTHER(303, "See Other"),
  NOT_MODIFIED(304, "Not Modified"),
  TEMPORARY_REDIRECT(307, "Temporary Redirect"),

  BAD_REQUEST(400, "Bad Request"),
  UNAUTHORIZED(401, "Unauthorized"),
  FORBIDDEN(403, "Forbidden"),
  NOT_FOUND(404, "Not Found"),
  METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
  NOT_ACCEPTABLE(406, "Not Acceptable"),
  REQUEST_TIMEOUT(408, "Request Timeout"),
  CONFLICT(409, "Conflict"),
  GONE(410, "Gone"),
  LENGTH_REQUIRED(411, "Length Required"),
  PRECONDITION_FAILED(412, "Precondition Failed"),
  PAYLOAD_TOO_LARGE(413, "Payload Too Large"),
  UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type"),
  RANGE_NOT_SATISFIABLE(416, "Requested Range Not Satisfiable"),
  EXPECTATION_FAILED(417, "Expectation Failed"),
  TOO_MANY_REQUESTS(429, "Too Many Requests"),

  INTERNAL_ERROR(500, "Internal Server Error"),
  NOT_IMPLEMENTED(501, "Not Implemented"),
  SERVICE_UNAVAILABLE(503, "Service Unavailable"),
  UNSUPPORTED_HTTP_VERSION(505, "HTTP Version Not Supported");

  private final int code;
  private final String description;

  HttpStatus(int code, String description) {
    this.code = code;
    this.description = description;
  }

  @Override
  public int code() {
    return code;
  }

  @Override
  public String description() {
    return description;
  }

  public static HttpStatus of(int stat) {
    for (HttpStatus status : HttpStatus.values()) {
      if (status.code() == stat) {
        return status;
      }
    }
    return null;
  }

  private static final HttpStatus[] OK_STATUS = {OK, CREATED, ACCEPTED, NO_CONTENT, PARTIAL_CONTENT, MULTI_STATUS, REDIRECT};

  public static boolean isOk(HttpStatus status) {
    return Arrays.stream(OK_STATUS).anyMatch(oks -> oks.code == status.code);
  }

  public static boolean isOk(int code) {
    return isOk(of(code));
  }
}
