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
package io.enoa.docker.ret.docker.service;

import io.enoa.docker.ret.AbstractDRRet;

public class ESrvConfig extends AbstractDRRet {

  private ESrvFile file;
  private String configid;
  private String configname;

  public ESrvConfig(Builder builder) {
    this.file = builder.file;
    this.configid = builder.configid;
    this.configname = builder.configname;
  }

  public ESrvFile file() {
    return file;
  }

  public String configid() {
    return configid;
  }

  public String configname() {
    return configname;
  }

  public static class Builder {

    private ESrvFile file;
    private String configid;
    private String configname;

    public ESrvConfig build() {
      return new ESrvConfig(this);
    }

    public Builder file(ESrvFile file) {
      this.file = file;
      return this;
    }

    public Builder configid(String configid) {
      this.configid = configid;
      return this;
    }

    public Builder configname(String configname) {
      this.configname = configname;
      return this;
    }
  }

}
