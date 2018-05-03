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
package io.enoa.ext.bea.beaction;

class BeaReg {

  enum Type {
    LOAD,
    UNLOAD
  }

  private final BeaExt.Mode mode;
  private final Type type;
  private final String uri;
  private final Interceptor interceptor;
  private final Class<? extends Interceptor> clears;

  BeaReg(BeaExt.Mode mode, String uri, Interceptor interceptor) {
    this.mode = mode;
    this.type = Type.LOAD;
    this.uri = uri;
    this.interceptor = interceptor;
    this.clears = null;
  }

  BeaReg(BeaExt.Mode mode, String uri, Class<? extends Interceptor> clears) {
    this.mode = mode;
    this.type = Type.UNLOAD;
    this.uri = uri;
    this.interceptor = null;
    this.clears = clears;
  }

  BeaExt.Mode mode() {
    return this.mode;
  }

  Type type() {
    return this.type;
  }

  String uri() {
    return this.uri;
  }

  Interceptor interceptor() {
    return this.interceptor;
  }

  Class<? extends Interceptor> clears() {
    return this.clears;
  }
}
