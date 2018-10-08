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
package io.enoa.docker.dret.container;

import io.enoa.docker.dret.AbstractDRet;

import java.util.List;

public class EProcesses extends AbstractDRet {


  private final String[] titles;
  private final List<String[]> processes;

  public EProcesses(Builder builder) {
    this.titles = builder.titles;
    this.processes = builder.processes;
  }


  public String[] titles() {
    return titles;
  }

  public List<String[]> processes() {
    return processes;
  }

  public static class Builder {

    private String[] titles;
    private List<String[]> processes;


    public EProcesses build() {
      return new EProcesses(this);
    }

    public Builder titles(String[] titles) {
      this.titles = titles;
      return this;
    }

    public Builder processes(List<String[]> processes) {
      this.processes = processes;
      return this;
    }
  }


}
