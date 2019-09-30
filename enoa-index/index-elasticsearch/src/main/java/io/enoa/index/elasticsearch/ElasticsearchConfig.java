/*
 * Copyright 2016 ikidou
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
package io.enoa.index.elasticsearch;

import io.enoa.http.EoHttp;
import io.enoa.http.Http;
import io.enoa.json.EnoaJson;
import io.enoa.json.EoJsonFactory;

public class ElasticsearchConfig {

  private String name;
  private String host;
  private EoHttp http;
  private EnoaJson json;

  private ElasticsearchConfig(Builder builder) {
    this.name = builder.name;
    this.host = builder.host;
    this.http = builder.http;
    this.json = builder.json;
  }

  public String name() {
    return name;
  }

  public String host() {
    return host;
  }

  public Http http() {
    return this.http == null ?
      Http.use() :
      this.http.http();
  }

  public EnoaJson json() {
    return json;
  }

  public static class Builder {

    private String name;
    private String host;
    private EoHttp http;
    private EnoaJson json;

    public Builder() {
      this.name = "main";
    }

    public ElasticsearchConfig build() {
      return new ElasticsearchConfig(this);
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder host(String host) {
      this.host = host;
      return this;
    }

    public Builder http(EoHttp http) {
      this.http = http;
      return this;
    }

    public Builder json(EoJsonFactory factory) {
      this.json = factory.json();
      return this;
    }
  }


}
