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

public class ESELinuxContext extends AbstractDRet {

  private Boolean disable;
  private String  user;
  private String  role;
  private String  type;
  private String  level;

  public ESELinuxContext(Builder builder) {
    this.disable = builder.disable;
    this.user = builder.user;
    this.role = builder.role;
    this.type = builder.type;
    this.level = builder.level;
  }

  public Boolean disable() {
    return disable;
  }

  public String user() {
    return user;
  }

  public String role() {
    return role;
  }

  public String type() {
    return type;
  }

  public String level() {
    return level;
  }

  public static class Builder {

    private Boolean disable;
    private String user;
    private String  role;
    private String  type;
    private String  level;

    public ESELinuxContext build() {
      return new ESELinuxContext(this);
    }

    public Builder disable(Boolean disable) {
      this.disable = disable;
      return this;
    }

    public Builder user(String user) {
      this.user = user;
      return this;
    }

    public Builder role(String role) {
      this.role = role;
      return this;
    }

    public Builder type(String type) {
      this.type = type;
      return this;
    }

    public Builder level(String level) {
      this.level = level;
      return this;
    }
  }

}
