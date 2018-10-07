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

import java.util.List;

public class EContainerCreated extends AbstractDRet {

  private String id;
  private List<String> warnings;

  public EContainerCreated(Builder builder) {
    this.id = builder.id;
    this.warnings = builder.warnings;
  }

  public String id() {
    return this.id;
  }

  public List<String> warnings() {
    return this.warnings;
  }

  public static class Builder {

    private String id;
    private List<String> warnings;

    public EContainerCreated build() {
      return new EContainerCreated(this);
    }

    public Builder id(String id) {
      this.id = id;
      return this;
    }

    public Builder warnings(List<String> warnings) {
      this.warnings = warnings;
      return this;
    }
  }
}
