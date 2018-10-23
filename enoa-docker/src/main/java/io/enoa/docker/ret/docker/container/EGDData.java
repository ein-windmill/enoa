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
package io.enoa.docker.ret.docker.container;

import io.enoa.docker.ret.AbstractDockerRet;

public class EGDData extends AbstractDockerRet {

  private final String lowerdir;
  private final String mergeddir;
  private final String upperdir;
  private final String workdir;

  public EGDData(Builder builder) {
    this.lowerdir = builder.lowerdir;
    this.mergeddir = builder.mergeddir;
    this.upperdir = builder.upperdir;
    this.workdir = builder.workdir;
  }

  public String lowerdir() {
    return lowerdir;
  }

  public String mergeddir() {
    return mergeddir;
  }

  public String upperdir() {
    return upperdir;
  }

  public String workdir() {
    return workdir;
  }

  public static class Builder {

    private String lowerdir;
    private String mergeddir;
    private String upperdir;
    private String workdir;

    public EGDData build() {
      return new EGDData(this);
    }

    public Builder lowerdir(String lowerdir) {
      this.lowerdir = lowerdir;
      return this;
    }

    public Builder mergeddir(String mergeddir) {
      this.mergeddir = mergeddir;
      return this;
    }

    public Builder upperdir(String upperdir) {
      this.upperdir = upperdir;
      return this;
    }

    public Builder workdir(String workdir) {
      this.workdir = workdir;
      return this;
    }
  }


}
