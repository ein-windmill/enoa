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
package io.enoa.docker.dket.docker.node;

import io.enoa.docker.dket.AbstractDRRet;

public class ENodeResourceSpec extends AbstractDRRet {

  private String kind;
  private Integer value;

  public ENodeResourceSpec(Builder builder) {
    this.kind = builder.kind;
    this.value = builder.value;
  }

  public String kind() {
    return this.kind;
  }

  public Integer value() {
    return this.value;
  }

  public static class Builder {
    private String kind;
    private Integer value;

    public ENodeResourceSpec build() {
      return new ENodeResourceSpec(this);
    }

    public Builder kind(String kind) {
      this.kind = kind;
      return this;
    }

    public Builder value(Integer value) {
      this.value = value;
      return this;
    }
  }

}
