/*
 * Copyright (c) 2018, enoa (ein.windmill@outlook.com)
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
package io.enoa.yosart.kernel.data;

import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.toolkit.thr.EoException;
import io.enoa.yosart.kernel.resources.YaResource;
import io.enoa.yosart.kernel.resources.YoAction;
import io.enoa.yosart.kernel.resources.YoRouter;

public class YdMount extends YaData {

  private final String uri;
  private final String name;
  private final String group;
  private final String description;
  private final YaResource resource;


  private YdMount(Builder builder) {
    this.uri = builder.uri;
    this.name = builder.name;
    this.group = builder.group;
    this.description = builder.description;
    this.resource = builder.resource;
  }

  public YdMount(String group, String uri, String name, String description, YoAction resource) {
    this(new Builder().group(group).uri(uri).name(name).description(description).resource(resource));
  }

  public YdMount(String group, String uri, String name, String description, YoRouter resource) {
    this(new Builder().group(group).uri(uri).name(name).description(description).resource(resource));
  }

  public YdMount(String group, String uri, String name, YoAction resource) {
    this(group, uri, name, null, resource);
  }

  public YdMount(String group, String uri, String name, YoRouter resource) {
    this(group, uri, name, null, resource);
  }

  public YdMount(String group, String uri, YoAction resource) {
    this(group, uri, null, null, resource);
  }

  public YdMount(String group, String uri, YoRouter resource) {
    this(group, uri, null, null, resource);
  }

  public String uri() {
    return this.uri;
  }

  public String name() {
    return this.name;
  }

  public String description() {
    return this.description;
  }

  public YaResource resource() {
    return this.resource;
  }

  public String group() {
    return this.group;
  }

  private static class Builder {
    private String uri;
    private String name;
    private String group;
    private String description;
    private YaResource resource;

    public YdMount build() {
      if (this.group == null)
        throw new EoException(EnoaTipKit.message("eo.tio.yosart.mount_group_null"));
      if (this.uri == null)
        throw new EoException(EnoaTipKit.message("eo.tip.yosart.mount_uri_null"));
      if (this.resource == null)
        throw new EoException(EnoaTipKit.message("eo.tip.yosart.mount_resource_null"));
      return new YdMount(this);
    }

    public Builder uri(String uri) {
      this.uri = uri;
      return this;
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder group(String group) {
      this.group = group;
      return this;
    }

    public Builder description(String description) {
      this.description = description;
      return this;
    }

    public Builder resource(YoAction resource) {
      this.resource = resource;
      return this;
    }

    public Builder resource(YoRouter resource) {
      this.resource = resource;
      return this;
    }
  }

}
