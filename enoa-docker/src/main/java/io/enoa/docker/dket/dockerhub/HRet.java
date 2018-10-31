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
package io.enoa.docker.dket.dockerhub;

import io.enoa.docker.dket.AbstractDRRet;
import io.enoa.docker.dket.registry.RError;
import io.enoa.docker.dket.registry.RResp;
import io.enoa.json.Json;

public class HRet<T> extends AbstractDRRet {

  private RResp origin;
  private boolean ok;
  private T data;
  private String message;

  public static <J> HRet<J> fail(RResp origin, RError error) {
    return new Builder<J>()
      .ok(false)
      .origin(origin)
      .message(error.message())
      .build();
  }

  public static <J> HRet<J> fail(RResp origin, String message) {
    return new Builder<J>()
      .ok(false)
      .origin(origin)
      .message(message)
      .build();
  }

  public static <J> HRet<J> ok(RResp origin, J data) {
    return new Builder<J>()
      .ok(true)
      .origin(origin)
      .parser(data)
      .build();
  }

  public static <J> HRet<J> nullx() {
    return fail(null, (String) null);
  }

  private HRet(Builder<T> builder) {
    this.origin = builder.origin;
    this.ok = builder.ok;
    this.data = builder.data;
    this.message = builder.message;
  }


  public RResp origin() {
    return origin;
  }

  public boolean ok() {
    return ok;
  }

  public T data() {
    return data;
  }

  public String message() {
    return message;
  }

  @Override
  public String toString() {
    return this.ok ? Json.toJson(this.data) : this.message;
  }

  private static class Builder<K> {

    private RResp origin;
    private boolean ok;
    private K data;
    private String message;


    public HRet<K> build() {
      return new HRet<>(this);
    }

    public Builder<K> origin(RResp origin) {
      this.origin = origin;
      return this;
    }

    public Builder<K> ok(boolean ok) {
      this.ok = ok;
      return this;
    }

    public Builder<K> parser(K data) {
      this.data = data;
      return this;
    }

    public Builder<K> message(String message) {
      this.message = message;
      return this;
    }

    public Builder<K> data(K data) {
      this.data = data;
      return this;
    }

  }
}
