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
package io.enoa.docker.dket.docker.plugin;

import io.enoa.docker.dket.AbstractDRRet;

public class EPluginNetwork extends AbstractDRRet {

  private String type;

  public EPluginNetwork(Builder builder) {
    this.type = builder.type;
  }

  public String type() {
    return type;
  }

  public static class Builder {
    private String type;

    public EPluginNetwork build() {
      return new EPluginNetwork(this);
    }

    public Builder type(String type) {
      this.type = type;
      return this;
    }
  }


}
