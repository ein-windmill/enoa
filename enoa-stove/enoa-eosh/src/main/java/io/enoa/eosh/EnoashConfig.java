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
package io.enoa.eosh;

import java.nio.charset.Charset;
import java.nio.file.Path;

public class EnoashConfig {

  private final String name;
  private final boolean debug;
  private final Path path;
  private final Charset charset;

  private EnoashConfig(Builder builder) {
    this.name = builder.name;
    this.debug = builder.debug;
    this.path = builder.path;
    this.charset = builder.charset;
  }

  public String name() {
    return this.name;
  }

  public boolean debug() {
    return this.debug;
  }

  public Path path() {
    return this.path;
  }

  public Charset charset() {
    return charset;
  }

  public static class Builder {
    private boolean debug;
    private Path path;
    private String name;
    private Charset charset;

    public Builder() {
      this.name = "main";
      this.charset = Charset.forName("UTF-8");
    }

    public EnoashConfig build() {
      return new EnoashConfig(this);
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder debug() {
      return this.debug(true);
    }

    public Builder debug(boolean debug) {
      this.debug = debug;
      return this;
    }

    public Builder path(Path path) {
      this.path = path;
      return this;
    }

    public Builder charset(Charset charset) {
      this.charset = charset;
      return this;
    }
  }
}
