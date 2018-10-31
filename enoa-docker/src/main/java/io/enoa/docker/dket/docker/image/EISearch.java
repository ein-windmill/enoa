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
package io.enoa.docker.dket.docker.image;

import io.enoa.docker.dket.AbstractDRRet;

public class EISearch extends AbstractDRRet {

  private String description;
  private Boolean official;
  private Boolean automated;
  private String name;
  private Integer starcount;

  public EISearch(Builder builder) {
    this.description = builder.description;
    this.official = builder.official;
    this.automated = builder.automated;
    this.name = builder.name;
    this.starcount = builder.starcount;
  }

  public String description() {
    return this.description;
  }

  public Boolean official() {
    return this.official;
  }

  public Boolean automated() {
    return this.automated;
  }

  public String name() {
    return this.name;
  }

  public Integer starcount() {
    return this.starcount;
  }

  public static class Builder {

    private String description;
    private Boolean official;
    private Boolean automated;
    private String name;
    private Integer starcount;

    public EISearch build() {
      return new EISearch(this);
    }


    public Builder description(String description) {
      this.description = description;
      return this;
    }

    public Builder official(Boolean official) {
      this.official = official;
      return this;
    }

    public Builder automated(Boolean automated) {
      this.automated = automated;
      return this;
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder starcount(Integer starcount) {
      this.starcount = starcount;
      return this;
    }
  }


}
