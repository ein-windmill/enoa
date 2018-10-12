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
package io.enoa.docker.dret.service;

import io.enoa.docker.dret.AbstractDRet;

public class ETmpfsOptions extends AbstractDRet {

  private Long sizebytes;
  private Integer mode;

  public ETmpfsOptions(Builder builder) {
    this.sizebytes = builder.sizebytes;
    this.mode = builder.mode;
  }

  public Long sizebytes() {
    return sizebytes;
  }

  public Integer mode() {
    return mode;
  }

  public static class Builder {

    private Long sizebytes;
    private Integer mode;

    public ETmpfsOptions build() {
      return new ETmpfsOptions(this);
    }

    public Builder sizebytes(Long sizebytes) {
      this.sizebytes = sizebytes;
      return this;
    }

    public Builder mode(Integer mode) {
      this.mode = mode;
      return this;
    }
  }

}
