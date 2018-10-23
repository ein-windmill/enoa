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
package io.enoa.docker.ret.docker.network;

import io.enoa.docker.ret.AbstractDRRet;

import java.util.List;

public class ENetworPrune extends AbstractDRRet {

  private List<String> networksdeleted;

  public ENetworPrune(Builder builder) {
    this.networksdeleted = builder.networksdeleted;
  }

  public List<String> networksdeleted() {
    return networksdeleted;
  }

  public static class Builder {
    private List<String> networksdeleted;

    public ENetworPrune build() {
      return new ENetworPrune(this);
    }

    public Builder networksdeleted(List<String> networksdeleted) {
      this.networksdeleted = networksdeleted;
      return this;
    }
  }

}
