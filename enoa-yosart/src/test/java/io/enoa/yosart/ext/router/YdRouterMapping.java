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
package io.enoa.yosart.ext.router;

import io.enoa.yosart.kernel.resources.OyResource;

public class YdRouterMapping {

  private final String uri;
  private final OyResource resource;

  private YdRouterMapping(Builder builder) {
    this.uri = builder.uri;
    this.resource = builder.resource;
  }

  public String uri() {
    return this.uri;
  }

  public OyResource resource() {
    return this.resource;
  }

  public static class Builder {
    private String uri;
    private OyResource resource;

    public YdRouterMapping build() {
      return new YdRouterMapping(this);
    }

    public Builder uri(String uri) {
      this.uri = uri;
      return this;
    }

    public Builder resource(OyResource resource) {
      this.resource = resource;
      return this;
    }
  }
}
