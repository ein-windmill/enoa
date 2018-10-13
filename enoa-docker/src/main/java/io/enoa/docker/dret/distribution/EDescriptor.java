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
package io.enoa.docker.dret.distribution;

import io.enoa.docker.dret.AbstractDRet;

public class EDescriptor extends AbstractDRet {

  private final String mediatype;
  private final String digest;
  private final Long size;
  private final String[] urls;

  public EDescriptor(Builder builder) {
    this.mediatype = builder.mediatype;
    this.digest = builder.digest;
    this.size = builder.size;
    this.urls = builder.urls;
  }

  public String mediatype() {
    return this.mediatype;
  }

  public String digest() {
    return this.digest;
  }

  public Long size() {
    return this.size;
  }

  public String[] urls() {
    return this.urls;
  }

  public static class Builder {
    private String mediatype;
    private String digest;
    private Long size;
    private String[] urls;


    public EDescriptor build() {
      return new EDescriptor(this);
    }

    public Builder mediatype(String mediatype) {
      this.mediatype = mediatype;
      return this;
    }

    public Builder digest(String digest) {
      this.digest = digest;
      return this;
    }

    public Builder size(Long size) {
      this.size = size;
      return this;
    }

    public Builder urls(String[] urls) {
      this.urls = urls;
      return this;
    }
  }

}
