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
package io.enoa.docker.dket.docker.run;

import io.enoa.docker.dket.AbstractDRRet;
import io.enoa.docker.dket.docker.container.ECError;

import java.util.List;

public class EDRun extends AbstractDRRet {

  private final Integer statuscode;
  private final String log;
  private final List<String> cmd;
  private final ECError error;

  public EDRun(Builder builder) {
    this.statuscode = builder.statuscode;
    this.log = builder.log;
    this.error = builder.error;
    this.cmd = builder.cmd;
  }

  public List<String> cmd() {
    return this.cmd;
  }

  public Integer statuscode() {
    return this.statuscode;
  }

  public String log() {
    return this.log;
  }

  public ECError error() {
    return this.error;
  }

  public static class Builder {

    private Integer statuscode;
    private String log;
    private List<String> cmd;
    private ECError error;

    public EDRun build() {
      return new EDRun(this);
    }

    public Builder cmd(List<String> cmd) {
      this.cmd = cmd;
      return this;
    }

    public Builder statuscode(Integer statuscode) {
      this.statuscode = statuscode;
      return this;
    }

    public Builder log(String log) {
      this.log = log;
      return this;
    }

    public Builder error(ECError error) {
      this.error = error;
      return this;
    }
  }

}
