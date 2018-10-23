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
package io.enoa.docker.ret.docker.secret;

import io.enoa.docker.ret.AbstractDRRet;
import io.enoa.docker.ret.docker.common.EDriver;
import io.enoa.toolkit.map.Kv;

public class ESecretSpec extends AbstractDRRet {

  private final String name;
  private final Kv labels;
  private final EDriver driver;

  public ESecretSpec(Builder builder) {
    this.name = builder.name;
    this.labels = builder.labels;
    this.driver = builder.driver;
  }

  public String name() {
    return name;
  }

  public Kv labels() {
    return labels;
  }

  public EDriver driver() {
    return driver;
  }

  public static class Builder {

    private String name;
    private Kv labels;
    private EDriver driver;

    public ESecretSpec build() {
      return new ESecretSpec(this);
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder labels(Kv labels) {
      this.labels = labels;
      return this;
    }

    public Builder driver(EDriver driver) {
      this.driver = driver;
      return this;
    }
  }


}
