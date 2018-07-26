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
package io.enoa.repeater.cos;

public class MultipartBody {

  private final EoxInputStream stream;
  private final String contentType;
  private final long contentLength;

  private MultipartBody(Builder builder) {
    this.stream = builder.stream;
    this.contentLength = builder.contentLength;
    this.contentType = builder.contentType;
  }

  public EoxInputStream stream() {
    return this.stream;
  }

  public String contentType() {
    return this.contentType;
  }

  public long contentLength() {
    return this.contentLength;
  }

  public static class Builder {
    private EoxInputStream stream;
    private String contentType;
    private long contentLength;

    public MultipartBody build() {
      return new MultipartBody(this);
    }

    public Builder stream(EoxInputStream stream) {
      this.stream = stream;
      return this;
    }

    public Builder contentType(String contentType) {
      this.contentType = contentType;
      return this;
    }

    public Builder contentLength(long contentLength) {
      this.contentLength = contentLength;
      return this;
    }

  }

}
