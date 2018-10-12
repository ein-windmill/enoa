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

public class EServiceMount extends AbstractDRet {

  private String target;
  private String source;
  private String type;
  private Boolean readonly;
  private String consistency;
  private EBindOptions bindoptions;
  private EVolumeOptions volumeoptions;
  private ETmpfsOptions tmpfsoptions;

  public EServiceMount(Builder builder) {
    this.target = builder.target;
    this.source = builder.source;
    this.type = builder.type;
    this.readonly = builder.readonly;
    this.consistency = builder.consistency;
    this.bindoptions = builder.bindoptions;
    this.volumeoptions = builder.volumeoptions;
    this.tmpfsoptions = builder.tmpfsoptions;
  }

  public String target() {
    return target;
  }

  public String source() {
    return source;
  }

  public String type() {
    return type;
  }

  public Boolean readonly() {
    return readonly;
  }

  public String consistency() {
    return consistency;
  }

  public EBindOptions bindoptions() {
    return bindoptions;
  }

  public EVolumeOptions volumeoptions() {
    return volumeoptions;
  }

  public ETmpfsOptions tmpfsoptions() {
    return tmpfsoptions;
  }

  public static class Builder {

    private String target;
    private String source;
    private String type;
    private Boolean readonly;
    private String consistency;
    private EBindOptions bindoptions;
    private EVolumeOptions volumeoptions;
    private ETmpfsOptions tmpfsoptions;

    public EServiceMount build() {
      return new EServiceMount(this);
    }

    public Builder target(String target) {
      this.target = target;
      return this;
    }

    public Builder source(String source) {
      this.source = source;
      return this;
    }

    public Builder type(String type) {
      this.type = type;
      return this;
    }

    public Builder readonly(Boolean readonly) {
      this.readonly = readonly;
      return this;
    }

    public Builder consistency(String consistency) {
      this.consistency = consistency;
      return this;
    }

    public Builder bindoptions(EBindOptions bindoptions) {
      this.bindoptions = bindoptions;
      return this;
    }

    public Builder volumeoptions(EVolumeOptions volumeoptions) {
      this.volumeoptions = volumeoptions;
      return this;
    }

    public Builder tmpfsoptions(ETmpfsOptions tmpfsoptions) {
      this.tmpfsoptions = tmpfsoptions;
      return this;
    }
  }


}
