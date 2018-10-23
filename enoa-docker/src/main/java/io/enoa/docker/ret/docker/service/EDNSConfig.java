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

import io.enoa.docker.ret.AbstractDockerRet;

import java.util.List;

public class EDNSConfig extends AbstractDockerRet {

  private List<String> nameservers;
  private List<String> search;
  private List<String> options;

  public EDNSConfig(Builder builder) {
    this.nameservers = builder.nameservers;
    this.search = builder.search;
    this.options = builder.options;
  }

  public List<String> nameservers() {
    return nameservers;
  }

  public List<String> search() {
    return search;
  }

  public List<String> options() {
    return options;
  }

  public static class Builder {

    private List<String> nameservers;
    private List<String> search;
    private List<String> options;

    public EDNSConfig build() {
      return new EDNSConfig(this);
    }

    public Builder nameservers(List<String> nameservers) {
      this.nameservers = nameservers;
      return this;
    }

    public Builder search(List<String> search) {
      this.search = search;
      return this;
    }

    public Builder options(List<String> options) {
      this.options = options;
      return this;
    }
  }

}
