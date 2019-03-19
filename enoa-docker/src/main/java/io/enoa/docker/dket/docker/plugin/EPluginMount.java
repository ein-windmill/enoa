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
package io.enoa.docker.dket.docker.plugin;

import io.enoa.docker.dket.AbstractDRRet;

import java.util.List;

public class EPluginMount extends AbstractDRRet {

  private String name;
  private String description;
  private List<String> settable;
  private String source;
  private String destination;
  private String type;
  private List<String> options;

  public EPluginMount(Builder builder) {
    this.name = builder.name;
    this.description = builder.description;
    this.settable = builder.settable;
    this.source = builder.source;
    this.destination = builder.destination;
    this.type = builder.type;
    this.options = builder.options;
  }

  public String name() {
    return name;
  }

  public String description() {
    return description;
  }

  public List<String> settable() {
    return settable;
  }

  public String source() {
    return source;
  }

  public String destination() {
    return destination;
  }

  public String type() {
    return type;
  }

  public List<String> options() {
    return options;
  }

  public static class Builder {


    private String name;
    private String description;
    private List<String> settable;
    private String source;
    private String destination;
    private String type;
    private List<String> options;


    public EPluginMount build() {
      return new EPluginMount(this);
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder description(String description) {
      this.description = description;
      return this;
    }

    public Builder settable(List<String> settable) {
      this.settable = settable;
      return this;
    }

    public Builder source(String source) {
      this.source = source;
      return this;
    }

    public Builder destination(String destination) {
      this.destination = destination;
      return this;
    }

    public Builder type(String type) {
      this.type = type;
      return this;
    }

    public Builder options(List<String> options) {
      this.options = options;
      return this;
    }
  }

}
