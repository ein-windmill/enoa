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

import io.enoa.toolkit.path.PathKit;

import java.nio.file.Path;

public class YdAssets extends YaData {

  private final String uri;
  private final Path path;
  private final boolean showTree;

  private YdAssets(Builder builder) {
    this.uri = builder.uri;
    this.path = builder.path;
    this.showTree = builder.showTree;
  }

  public String uri() {
    return this.uri;
  }

  public Path path() {
    return this.path;
  }

  public boolean showTree() {
    return this.showTree;
  }

  public static class Builder {
    private String uri;
    private Path path;
    private boolean showTree;

    public Builder() {
      this.uri = "/assets";
      this.path = PathKit.path().resolve("assets");
      this.showTree = false;
    }

    public YdAssets build() {
      return new YdAssets(this);
    }

    public Builder uri(String uri) {
      this.uri = uri;
      return this;
    }

    public Builder path(Path path) {
      this.path = path;
      return this;
    }

    public Builder showTree(boolean showTree) {
      this.showTree = showTree;
      return this;
    }
  }

}
