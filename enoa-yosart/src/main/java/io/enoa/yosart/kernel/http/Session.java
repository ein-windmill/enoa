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
package io.enoa.yosart.kernel.http;

public interface Session extends _Session {

  /**
   * session names
   *
   * @return String[]
   */
  String[] names();

  /**
   * session value
   *
   * @param name session key
   * @param <T>  value
   * @return T
   */
  <T> T get(String name);

  /**
   * remove session
   *
   * @param name name
   * @return Session
   */
  Session rm(String name);

  /**
   * set session
   *
   * @param name  name
   * @param value value
   * @return Session
   */
  Session set(String name, Object value);

  Session expires(long expiresAt);

  Session domain(String domain);

  Session hostOnly(boolean hostOnly);

  Session path(String path);

  Session secure(boolean secure);

  Session httpOnly(boolean httpOnly);

}
