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
package io.enoa.docker.ret.docker.system;

import io.enoa.docker.ret.AbstractDRRet;
import io.enoa.docker.ret.docker.container.EContainer;
import io.enoa.docker.ret.docker.image.EImage;
import io.enoa.docker.ret.docker.volume.EVolume;

public class Edf extends AbstractDRRet {

  private final Long layerssize;
  private final EImage image;
  private final EContainer container;
  private final EVolume volume;

  public Edf(Builder builder) {
    this.layerssize = builder.layerssize;
    this.image = builder.image;
    this.container = builder.container;
    this.volume = builder.volume;
  }

  public Long layerssize() {
    return this.layerssize;
  }

  public EImage image() {
    return this.image;
  }

  public EContainer container() {
    return this.container;
  }

  public EVolume volume() {
    return this.volume;
  }

  public static class Builder {
    private Long layerssize;
    private EImage image;
    private EContainer container;
    private EVolume volume;

    public Edf build() {
      return new Edf(this);
    }

    public Builder layerssize(Long layerssize) {
      this.layerssize = layerssize;
      return this;
    }

    public Builder image(EImage image) {
      this.image = image;
      return this;
    }

    public Builder container(EContainer container) {
      this.container = container;
      return this;
    }

    public Builder volume(EVolume volume) {
      this.volume = volume;
      return this;
    }
  }


}
