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

public class EBlkioStats extends AbstractDockerRet {

  private final Object ioservicebytesrecursive;
  private final Object ioservicedrecursive;
  private final Object ioqueuerecursive;
  private final Object ioservicetimerecursive;
  private final Object iowaittimerecursive;
  private final Object iomergedrecursive;
  private final Object iotimerecursive;
  private final Object sectorsrecursive;

  public EBlkioStats(Builder builder) {
    this.ioservicebytesrecursive = builder.ioservicebytesrecursive;
    this.ioservicedrecursive = builder.ioservicedrecursive;
    this.ioqueuerecursive = builder.ioqueuerecursive;
    this.ioservicetimerecursive = builder.ioservicetimerecursive;
    this.iowaittimerecursive = builder.iowaittimerecursive;
    this.iomergedrecursive = builder.iomergedrecursive;
    this.iotimerecursive = builder.iotimerecursive;
    this.sectorsrecursive = builder.sectorsrecursive;
  }

  public Object ioservicebytesrecursive() {
    return ioservicebytesrecursive;
  }

  public Object ioservicedrecursive() {
    return ioservicedrecursive;
  }

  public Object ioqueuerecursive() {
    return ioqueuerecursive;
  }

  public Object ioservicetimerecursive() {
    return ioservicetimerecursive;
  }

  public Object iowaittimerecursive() {
    return iowaittimerecursive;
  }

  public Object iomergedrecursive() {
    return iomergedrecursive;
  }

  public Object iotimerecursive() {
    return iotimerecursive;
  }

  public Object sectorsrecursive() {
    return sectorsrecursive;
  }

  public static class Builder {

    private Object ioservicebytesrecursive;
    private Object ioservicedrecursive;
    private Object ioqueuerecursive;
    private Object ioservicetimerecursive;
    private Object iowaittimerecursive;
    private Object iomergedrecursive;
    private Object iotimerecursive;
    private Object sectorsrecursive;

    public EBlkioStats build() {
      return new EBlkioStats(this);
    }

    public Builder ioservicebytesrecursive(Object ioservicebytesrecursive) {
      this.ioservicebytesrecursive = ioservicebytesrecursive;
      return this;
    }

    public Builder ioservicedrecursive(Object ioservicedrecursive) {
      this.ioservicedrecursive = ioservicedrecursive;
      return this;
    }

    public Builder ioqueuerecursive(Object ioqueuerecursive) {
      this.ioqueuerecursive = ioqueuerecursive;
      return this;
    }

    public Builder ioservicetimerecursive(Object ioservicetimerecursive) {
      this.ioservicetimerecursive = ioservicetimerecursive;
      return this;
    }

    public Builder iowaittimerecursive(Object iowaittimerecursive) {
      this.iowaittimerecursive = iowaittimerecursive;
      return this;
    }

    public Builder iomergedrecursive(Object iomergedrecursive) {
      this.iomergedrecursive = iomergedrecursive;
      return this;
    }

    public Builder iotimerecursive(Object iotimerecursive) {
      this.iotimerecursive = iotimerecursive;
      return this;
    }

    public Builder sectorsrecursive(Object sectorsrecursive) {
      this.sectorsrecursive = sectorsrecursive;
      return this;
    }
  }

}
