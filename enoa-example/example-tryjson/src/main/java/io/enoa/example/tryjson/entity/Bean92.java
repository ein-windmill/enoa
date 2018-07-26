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
package io.enoa.example.tryjson.entity;

import java.sql.Timestamp;

public class Bean92 {

  private String appId;
  private String websiteHost;
  private String token;
  private String expire_time;
  public Timestamp ctime;

  public String appId() {
    return this.appId;
  }

  public Bean92 appId(String appId) {
    this.appId = appId;
    return this;
  }

  public String websiteHost() {
    return this.websiteHost;
  }

  public Bean92 websiteHost(String websiteHost) {
    this.websiteHost = websiteHost;
    return this;
  }

  public String token() {
    return this.token;
  }

  public Bean92 token(String token) {
    this.token = token;
    return this;
  }

  public String expire_time() {
    return this.expire_time;
  }

  public Bean92 expire_time(String expire_time) {
    this.expire_time = expire_time;
    return this;
  }
}
