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
package io.enoa.stove.template;

import java.nio.file.Path;

public class StoveConfig {

  private final boolean debug;
  private final Path path;
  private final String suffix;
  private final String tokenVarStart;
  private final String tokenVarEnd;
  private final String tokenBlock;
  //  private final String tokenBlockStart;
//  private final String tokenBlockEnd;
  private final String tokenPipeline;
  private final String tokenCommend;


  private StoveConfig(Builder builder) {
    this.debug = builder.debug;
    this.path = builder.path;
    this.suffix = builder.suffix;
    this.tokenVarStart = builder.tokenVarStart;
    this.tokenVarEnd = builder.tokenVarEnd;
//    this.tokenBlockStart = builder.tokenBlockStart;
//    this.tokenBlockEnd = builder.tokenBlockEnd;
    this.tokenPipeline = builder.tokenPipeline;
    this.tokenBlock = builder.tokenBlock;
    this.tokenCommend = builder.tokenCommend;
  }


  public boolean debug() {
    return debug;
  }

  public Path path() {
    return path;
  }

  public String suffix() {
    return suffix;
  }

  public String tokenPipeline() {
    return this.tokenPipeline;
  }

  public String tokenVarStart() {
    return tokenVarStart;
  }

  public String tokenVarEnd() {
    return tokenVarEnd;
  }

  public String tokenBlock() {
    return this.tokenBlock;
  }

  public String tokenCommend() {
    return this.tokenCommend;
  }

//  public String tokenBlockStart() {
//    return tokenBlockStart;
//  }
//
//  public String tokenBlockEnd() {
//    return tokenBlockEnd;
//  }

  public static class Builder {
    private boolean debug;
    private Path path;
    private String suffix;

    private String tokenVarStart;
    private String tokenVarEnd;
    private String tokenBlock;
    //    private String tokenBlockStart;
//    private String tokenBlockEnd;
    private String tokenPipeline;
    private String tokenCommend;

    public Builder() {
      this.debug = Boolean.TRUE;
      this.tokenVarStart = "${";
      this.tokenVarEnd = "}";
      this.tokenBlock = "#";
//      this.tokenBlockStart = "#";
//      this.tokenBlockEnd = null;
      this.tokenPipeline = "|";
      this.tokenCommend = "##";
    }

    public StoveConfig build() {
      return new StoveConfig(this);
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

    public Builder suffix(String suffix) {
      this.suffix = suffix;
      return this;
    }

    public Builder tokenVarStart(String tokenVarStart) {
      this.tokenVarStart = tokenVarStart;
      return this;
    }

    public Builder tokenVarEnd(String tokenVarEnd) {
      this.tokenVarEnd = tokenVarEnd;
      return this;
    }

    public Builder tokenBlock(String tokenBlock) {
      this.tokenBlock = tokenBlock;
      return this;
    }

//    public Builder tokenBlockStart(String tokenBlockStart) {
//      this.tokenBlockStart = tokenBlockStart;
//      return this;
//    }
//
//    public Builder tokenBlockEnd(String tokenBlockEnd) {
//      this.tokenBlockEnd = tokenBlockEnd;
//      return this;
//    }
  }
}
