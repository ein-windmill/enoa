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
package io.enoa.docker.ret.registry;

import io.enoa.docker.ret.AbstractDRRet;
import io.enoa.json.Json;
import io.enoa.toolkit.map.Kv;
import io.enoa.toolkit.text.TextKit;

public class RError extends AbstractDRRet {


  private final String code;
  private final String message;
  private final Kv detail;

  public RError(Builder builder) {
    this.code = builder.code;
    this.message = builder.message;
    this.detail = builder.detail;
  }

  public String code() {
    return code;
  }

  public String message() {
    return message;
  }

  public Kv detail() {
    return detail;
  }

  @Override
  public String toString() {
    return TextKit.union("{\"code\": \"", this.code, "\", \"message\": \"", this.message, "\", \"detail\": ", Json.toJson(this.detail), "}");
  }

  public static class Builder {

    private String code;
    private String message;
    private Kv detail;


    public RError build() {
      return new RError(this);
    }

    public Builder code(String code) {
      this.code = code;
      return this;
    }

    public Builder message(String message) {
      this.message = message;
      return this;
    }

    public Builder detail(Kv detail) {
      this.detail = detail;
      return this;
    }
  }

}
