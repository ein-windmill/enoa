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
package io.enoa.docker.ret.docker.container;

import io.enoa.docker.ret.AbstractDockerRet;

public class ERestartPolicy extends AbstractDockerRet {

  private final Integer maximumretrycount;
  private final String name;

  public ERestartPolicy(Builder builder) {
    this.maximumretrycount = builder.maximumretrycount;
    this.name = builder.name;
  }

  public Integer maximumretrycount() {
    return maximumretrycount;
  }

  public String name() {
    return name;
  }

  public static class Builder {

    private Integer maximumretrycount;
    private String name;

    public ERestartPolicy build() {
      return new ERestartPolicy(this);
    }

    public Builder maximumretrycount(Integer maximumretrycount) {
      this.maximumretrycount = maximumretrycount;
      return this;
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }
  }
}
