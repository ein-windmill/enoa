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
package io.enoa.docker.dret.container;

import io.enoa.docker.dret.AbstractDRet;

public class EChange extends AbstractDRet {

  private String path;
  private Integer kind;

  public EChange(Builder builder) {
    this.path = builder.path;
    this.kind = builder.kind;
  }

  public String path() {
    return path;
  }

  public Integer kind() {
    return kind;
  }

  public static class Builder {

    private String path;
    private Integer kind;

    public EChange build() {
      return new EChange(this);
    }

    public Builder path(String path) {
      this.path = path;
      return this;
    }

    public Builder kind(Integer kind) {
      this.kind = kind;
      return this;
    }
  }

}
