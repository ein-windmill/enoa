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
package io.enoa.docker.dret.node;

import io.enoa.docker.dret.AbstractDRet;
import io.enoa.toolkit.map.Kv;

import java.util.List;

public class ENodeEngine extends AbstractDRet {

  private String engineversion;
  private Kv labels;
  private List<ENodePlugin> plugins;

  public ENodeEngine(Builder builder) {
    this.engineversion = builder.engineversion;
    this.labels = builder.labels;
    this.plugins = builder.plugins;
  }

  public String engineversion() {
    return this.engineversion;
  }

  public Kv labels() {
    return this.labels;
  }

  public List<ENodePlugin> plugins() {
    return this.plugins;
  }

  public static class Builder {
    private String engineversion;
    private Kv labels;
    private List<ENodePlugin> plugins;

    public ENodeEngine build() {
      return new ENodeEngine(this);
    }

    public Builder engineversion(String engineversion) {
      this.engineversion = engineversion;
      return this;
    }

    public Builder labels(Kv labels) {
      this.labels = labels;
      return this;
    }

    public Builder plugins(List<ENodePlugin> plugins) {
      this.plugins = plugins;
      return this;
    }

  }

}
