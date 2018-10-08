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

import java.util.Map;

public class ERuntimes extends AbstractDRet {

  private final Map<String, String> runc;

  private ERuntimes(Builder builder) {
    this.runc = builder.runc;
  }

  public Map<String, String> runc() {
    return this.runc;
  }

  public static class Builder {

    private Map<String, String> runc;


    public ERuntimes build() {
      return new ERuntimes(this);
    }

    public Builder runc(Map<String, String> runc) {
      this.runc = runc;
      return this;
    }
  }
}
