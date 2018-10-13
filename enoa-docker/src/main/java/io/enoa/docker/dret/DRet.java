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
package io.enoa.docker.dret;

public class DRet<T> extends AbstractDRet {

  //  private DockerConfig config;
  private DResp origin;
  private boolean ok;
  private T data;
  private String message;

  public static <J> DRet<J> fail(DResp origin, String message) {
    return new Builder<J>()
      .ok(false)
      .origin(origin)
      .message(message)
      .build();
  }

  public static <J> DRet<J> ok(DResp origin, J data) {
    return new Builder<J>()
      .ok(true)
      .origin(origin)
      .parser(data)
      .build();
  }

  private DRet(Builder<T> builder) {
//    this.config = builder.config;
    this.origin = builder.origin;
    this.ok = builder.ok;
    this.data = builder.data;
    this.message = builder.message;
  }

  public DResp origin() {
    return this.origin;
  }

  public boolean ok() {
    return this.ok;
  }

  public T data() {
    return this.data;
  }

  public String message() {
    return this.message;
  }

  @Override
  public String toString() {
    return this.origin.string();
  }


  private static class Builder<K> {

    private DResp origin;
    private boolean ok;
    private K data;
    private String message;


    public DRet<K> build() {
      return new DRet<>(this);
    }

    public Builder<K> origin(DResp origin) {
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
  }

}
