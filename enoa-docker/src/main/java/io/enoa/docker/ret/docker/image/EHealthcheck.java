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
package io.enoa.docker.ret.docker.image;

import io.enoa.docker.ret.AbstractDRRet;

import java.util.List;

public class EHealthcheck extends AbstractDRRet {

  private List<String> test;
  private Integer interval;
  private Integer timeout;
  private Integer retries;
  private Integer startperiod;


  public EHealthcheck(Builder builder) {
    this.test = builder.test;
    this.interval = builder.interval;
    this.timeout = builder.timeout;
    this.retries = builder.retries;
    this.startperiod = builder.startperiod;
  }

  public List<String> test() {
    return this.test;
  }

  public Integer interval() {
    return this.interval;
  }

  public Integer timeout() {
    return this.timeout;
  }

  public Integer retries() {
    return this.retries;
  }

  public Integer startperiod() {
    return this.startperiod;
  }

  public static class Builder {
    private List<String> test;
    private Integer interval;
    private Integer timeout;
    private Integer retries;
    private Integer startperiod;


    public EHealthcheck build() {
      return new EHealthcheck(this);
    }

    public Builder test(List<String> test) {
      this.test = test;
      return this;
    }

    public Builder interval(Integer interval) {
      this.interval = interval;
      return this;
    }

    public Builder timeout(Integer timeout) {
      this.timeout = timeout;
      return this;
    }

    public Builder retries(Integer retries) {
      this.retries = retries;
      return this;
    }

    public Builder startperiod(Integer startperiod) {
      this.startperiod = startperiod;
      return this;
    }
  }


}
