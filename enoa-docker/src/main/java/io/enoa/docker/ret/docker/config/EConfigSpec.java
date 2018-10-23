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
package io.enoa.docker.ret.docker.config;

import io.enoa.docker.ret.AbstractDRRet;

public class EConfigSpec extends AbstractDRRet {

  private String name;

  public EConfigSpec(Builder builder) {
    this.name = builder.name;
  }

  public String name() {
    return name;
  }

  public static class Builder {
    private String name;

    public EConfigSpec build() {
      return new EConfigSpec(this);
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }
  }

}
