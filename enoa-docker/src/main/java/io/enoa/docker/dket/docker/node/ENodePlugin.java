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

public class ENodePlugin extends AbstractDRRet {

  private String type;
  private String name;

  public ENodePlugin(Builder builder) {
    this.type = builder.type;
    this.name = builder.name;
  }

  public String type() {
    return this.type;
  }

  public String name() {
    return this.name;
  }

  public static class Builder {

    private String type;
    private String name;

    public ENodePlugin build() {
      return new ENodePlugin(this);
    }

    public Builder type(String type) {
      this.type = type;
      return this;
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }
  }
}
