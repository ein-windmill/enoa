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
package io.enoa.docker.dket.dockerhub.tag;

import io.enoa.docker.dket.AbstractDRRet;

public class EHImage extends AbstractDRRet {


  private Long size;
  private String architecture;
  private String variant;
  private String features;
  private String os;
  private String osversion;
  private String osfeatures;

  public EHImage(Builder builder) {
    this.size = builder.size;
    this.architecture = builder.architecture;
    this.variant = builder.variant;
    this.features = builder.features;
    this.os = builder.os;
    this.osversion = builder.osversion;
    this.osfeatures = builder.osfeatures;
  }

  public Long size() {
    return this.size;
  }

  public String architecture() {
    return this.architecture;
  }

  public String variant() {
    return this.variant;
  }

  public String features() {
    return this.features;
  }

  public String os() {
    return this.os;
  }

  public String osversion() {
    return this.osversion;
  }

  public String osfeatures() {
    return this.osfeatures;
  }

  public static class Builder {

    private Long size;
    private String architecture;
    private String variant;
    private String features;
    private String os;
    private String osversion;
    private String osfeatures;


    public EHImage build() {
      return new EHImage(this);
    }

    public Builder size(Long size) {
      this.size = size;
      return this;
    }

    public Builder architecture(String architecture) {
      this.architecture = architecture;
      return this;
    }

    public Builder variant(String variant) {
      this.variant = variant;
      return this;
    }

    public Builder features(String features) {
      this.features = features;
      return this;
    }

    public Builder os(String os) {
      this.os = os;
      return this;
    }

    public Builder osversion(String osversion) {
      this.osversion = osversion;
      return this;
    }

    public Builder osfeatures(String osfeatures) {
      this.osfeatures = osfeatures;
      return this;
    }
  }

}
