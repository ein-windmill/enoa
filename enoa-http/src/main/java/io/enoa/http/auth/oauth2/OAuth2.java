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
package io.enoa.http.auth.oauth2;

import io.enoa.http.HttpAuth;

public class OAuth2 implements HttpAuth {

  private String token;

  public OAuth2(String token) {
    this.token = token;
  }

  @Override
  public String identity() {
    return "Bearer";
  }

  @Override
  public String token() {
    return this.token;
  }

  @Override
  public String toString() {
    return "OAuth2{" +
      "token='" + token + '\'' +
      '}';
  }
}
