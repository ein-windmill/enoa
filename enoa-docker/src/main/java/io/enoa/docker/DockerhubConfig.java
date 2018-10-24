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
package io.enoa.docker;

import io.enoa.docker.thr.DockerhubException;
import io.enoa.http.EoHttp;
import io.enoa.json.EnoaJson;
import io.enoa.json.EoJsonFactory;
import io.enoa.toolkit.eo.tip.EnoaTipKit;

public class DockerhubConfig {

  private final String context;
  private final EoHttp http;
  private final EnoaJson json;

  private DockerhubConfig(Builder builder) {
    this.json = builder.json.json();
    this.http = builder.http;
    this.context = builder.context;
  }

  public String context() {
    return this.context;
  }

  public EoHttp http() {
    return http;
  }

  public EnoaJson json() {
    return json;
  }

  public static class Builder {
    private String context;
    private EoJsonFactory json;
    private EoHttp http;

    public Builder() {
      this.context = "https://hub.docker.com/v2/";
      this.http = EoHttp.def();
    }

    public DockerhubConfig build() {
      if (this.json == null)
        throw new DockerhubException(EnoaTipKit.message("eo.tip.docker.no_json"));
      return new DockerhubConfig(this);
    }

    public Builder json(EoJsonFactory json) {
      this.json = json;
      return this;
    }

    public Builder http(EoHttp http) {
      this.http = http;
      return this;
    }
  }

}
