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
package io.enoa.http.auth.oauth1;

import io.enoa.http.HttpAuth;

public class OAuth1 implements HttpAuth {

  private String ckey;
  private String csecret;
  private String token;
  private String tsecret;

  /**
   * todo  can not support now
   *
   * @param ckey    Consumer key
   * @param csecret Consumer secret
   * @param token   Access token
   * @param tsecret Token secret
   */
  @Deprecated
  public OAuth1(String ckey, String csecret, String token, String tsecret) {
    this.ckey = ckey;
    this.csecret = csecret;
    this.token = token;
    this.tsecret = tsecret;
  }

  @Override
  public Where where() {
    return Where.URL;
  }

  @Override
  public String identity() {
    return null;
  }

  @Override
  public String token() {
    return null;
  }

  @Override
  public String toString() {
    return "OAuth1{" +
      "ckey='" + ckey + '\'' +
      ", csecret='" + csecret + '\'' +
      ", token='" + token + '\'' +
      ", tsecret='" + tsecret + '\'' +
      '}';
  }
}
