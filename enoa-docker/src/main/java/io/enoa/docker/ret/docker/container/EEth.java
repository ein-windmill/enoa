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

public class EEth extends AbstractDockerRet {

  private final Integer rxbytes;
  private final Integer rxdropped;
  private final Integer rxerrors;
  private final Integer rxpackets;
  private final Integer txbytes;
  private final Integer txdropped;
  private final Integer txerrors;
  private final Integer txpackets;

  public EEth(Builder builder) {
    this.rxbytes = builder.rxbytes;
    this.rxdropped = builder.rxdropped;
    this.rxerrors = builder.rxerrors;
    this.rxpackets = builder.rxpackets;
    this.txbytes = builder.txbytes;
    this.txdropped = builder.txdropped;
    this.txerrors = builder.txerrors;
    this.txpackets = builder.txpackets;
  }

  public Integer rxbytes() {
    return rxbytes;
  }

  public Integer rxdropped() {
    return rxdropped;
  }

  public Integer rxerrors() {
    return rxerrors;
  }

  public Integer rxpackets() {
    return rxpackets;
  }

  public Integer txbytes() {
    return txbytes;
  }

  public Integer txdropped() {
    return txdropped;
  }

  public Integer txerrors() {
    return txerrors;
  }

  public Integer txpackets() {
    return txpackets;
  }

  public static class Builder {

    private Integer rxbytes;
    private Integer rxdropped;
    private Integer rxerrors;
    private Integer rxpackets;
    private Integer txbytes;
    private Integer txdropped;
    private Integer txerrors;
    private Integer txpackets;


    public EEth build() {
      return new EEth(this);
    }

    public Builder rxbytes(Integer rxbytes) {
      this.rxbytes = rxbytes;
      return this;
    }

    public Builder rxdropped(Integer rxdropped) {
      this.rxdropped = rxdropped;
      return this;
    }

    public Builder rxerrors(Integer rxerrors) {
      this.rxerrors = rxerrors;
      return this;
    }

    public Builder rxpackets(Integer rxpackets) {
      this.rxpackets = rxpackets;
      return this;
    }

    public Builder txbytes(Integer txbytes) {
      this.txbytes = txbytes;
      return this;
    }

    public Builder txdropped(Integer txdropped) {
      this.txdropped = txdropped;
      return this;
    }

    public Builder txerrors(Integer txerrors) {
      this.txerrors = txerrors;
      return this;
    }

    public Builder txpackets(Integer txpackets) {
      this.txpackets = txpackets;
      return this;
    }
  }


}
