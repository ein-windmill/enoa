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

public class Header {

  private final String name;
  private final String value;

  private Header(Builder builder) {
    this.name = builder.name;
    this.value = builder.value;
  }

  public Header(String name, String value) {
    this.name = name;
    this.value = value;
  }

  public String name() {
    return this.name;
  }

  public String value() {
    return value;
  }

  public Builder newBuilder() {
    return new Builder(this);
  }

  private static class Builder {
    private String name;
    private String value;

    private Builder(Header header) {
      this.name = header.name;
      this.value = header.value;
    }

    public Header build() {
      return new Header(this);
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder value(String value) {
      this.value = value;
      return this;
    }
  }

}
