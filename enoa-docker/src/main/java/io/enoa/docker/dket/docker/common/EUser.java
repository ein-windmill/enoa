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
package io.enoa.docker.dket.docker.common;

import io.enoa.docker.dket.AbstractDRRet;

public class EUser extends AbstractDRRet {

  private Integer uid;
  private Integer gid;

  public EUser(Builder builder) {
    this.uid = builder.uid;
    this.gid = builder.gid;
  }

  public Integer uid() {
    return uid;
  }

  public Integer gid() {
    return gid;
  }

  public static class Builder {

    private Integer uid;
    private Integer gid;

    public EUser build() {
      return new EUser(this);
    }

    public Builder uid(Integer uid) {
      this.uid = uid;
      return this;
    }

    public Builder gid(Integer gid) {
      this.gid = gid;
      return this;
    }
  }

}
