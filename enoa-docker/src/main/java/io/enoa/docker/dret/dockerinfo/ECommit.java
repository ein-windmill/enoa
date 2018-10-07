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
package io.enoa.docker.dret.dockerinfo;

import io.enoa.docker.dret.AbstractDRet;

public class ECommit extends AbstractDRet {

  private String id;
  private String expected;

  private ECommit(Builder builder) {
    this.id = builder.id;
    this.expected = builder.expected;
  }

  public String id() {
    return this.id;
  }

  public String expected() {
    return this.expected;
  }

  public static class Builder {

    private String id;
    private String expected;

    public Builder() {
    }

    public ECommit build() {
      return new ECommit(this);
    }

    public Builder id(String id) {
      this.id = id;
      return this;
    }

    public Builder expected(String expected) {
      this.expected = expected;
      return this;
    }
  }
}
