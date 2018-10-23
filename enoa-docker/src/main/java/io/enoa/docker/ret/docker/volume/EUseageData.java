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
package io.enoa.docker.ret.docker.volume;

import io.enoa.docker.ret.AbstractDockerRet;

public class EUseageData extends AbstractDockerRet {

  private Integer size;
  private Integer refcount;

  public EUseageData(Builder builder) {
    this.size = builder.size;
    this.refcount = builder.refcount;
  }

  public Integer size() {
    return size;
  }

  public Integer refcount() {
    return refcount;
  }

  public static class Builder {

    private Integer size;
    private Integer refcount;


    public EUseageData build() {
      return new EUseageData(this);
    }

    public Builder size(Integer size) {
      this.size = size;
      return this;
    }

    public Builder refcount(Integer refcount) {
      this.refcount = refcount;
      return this;
    }
  }

}
