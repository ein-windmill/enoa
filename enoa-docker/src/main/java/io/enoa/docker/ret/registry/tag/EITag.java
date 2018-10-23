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
package io.enoa.docker.ret.registry.tag;

import io.enoa.docker.ret.AbstractDRRet;

public class EITag extends AbstractDRRet {

  private final String name;
  private final String[] tags;

  public EITag(Builder builder) {
    this.name = builder.name;
    this.tags = builder.tags;
  }

  public String name() {
    return name;
  }

  public String[] tags() {
    return tags;
  }

  public static class Builder {

    private String name;
    private String[] tags;

    public EITag build() {
      return new EITag(this);
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder tags(String[] tags) {
      this.tags = tags;
      return this;
    }
  }

}
