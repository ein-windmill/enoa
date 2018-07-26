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
package io.enoa.yosart;

import io.enoa.yosart.kernel.data.YdAssets;
import io.enoa.yosart.kernel.resources.OyResource;

import java.util.Map;

class Oysartd {

  private static Oysartd yo;

  private final String version;
  private final YoConfig config;
  private final Map<String, OyResource[]> resources;
  private final YoExt[] exts;
  private final YoPlugin[] plugins;
  //  private final Yosart yosart;
  private final String name;
  private final YdAssets assets;

  Oysartd(Builder builder) {
    this.version = builder.version;
    this.config = builder.config;
    this.resources = builder.resources;
    this.exts = builder.exts;
    this.plugins = builder.plugins;
    this.name = builder.name;
    this.assets = builder.assets;
//    this.yosart = builder.yosart;
  }

  static void yo(Builder builder) {
    yo = builder.build();
  }

  static Oysartd yo() {
    return yo;
  }

  String version() {
    return this.version;
  }

  YoConfig config() {
    return this.config;
  }

  Map<String, OyResource[]> resources() {
    return this.resources;
  }

  YoExt[] exts() {
    return this.exts;
  }

  YoPlugin[] plugins() {
    return this.plugins;
  }

//  Yosart yosart() {
//    return this.yosart;
//  }

  String name() {
    return this.name;
  }

  YdAssets assets() {
    return this.assets;
  }


  static class Builder {
    private String version;
    private YoConfig config;
    private Map<String, OyResource[]> resources;
    private YoExt[] exts;
    private YoPlugin[] plugins;
    //    private Yosart yosart;
    private String name;
    private YdAssets assets;

    Oysartd build() {
      return new Oysartd(this);
    }

    Builder version(String version) {
      this.version = version;
      return this;
    }

    Builder config(YoConfig config) {
      this.config = config;
      return this;
    }

    Builder resources(Map<String, OyResource[]> resources) {
      this.resources = resources;
      return this;
    }

    Builder exts(YoExt[] exts) {
      this.exts = exts;
      return this;
    }

    Builder plugins(YoPlugin[] plugins) {
      this.plugins = plugins;
      return this;
    }

    Builder name(String name) {
      this.name = name;
      return this;
    }

    Builder assets(YdAssets assets) {
      this.assets = assets;
      return this;
    }

//    Builder yosart(Yosart yosart) {
//      this.yosart = yosart;
//      return this;
//    }
  }
}
