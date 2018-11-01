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

import io.enoa.docker.async.docker.eo.AsyncEnoaDocker;
import io.enoa.docker.async.docker.generic.AsyncEnoaGenericDocker;
import io.enoa.docker.async.docker.origin.AsyncOriginDocker;

public class AsyncDocker {

  private static class Holder {
    private static final AsyncDocker INSTANCE = new AsyncDocker();
  }

  static AsyncDocker instance() {
    return Holder.INSTANCE;
  }


  private AsyncDocker() {
  }

  public AsyncOriginDocker origin() {
    return new AsyncOriginDocker(Docker.origin());
  }

  public AsyncOriginDocker origin(String name) {
    return new AsyncOriginDocker(Docker.origin(name));
  }

  public AsyncEnoaGenericDocker generic() {
    return new AsyncEnoaGenericDocker(Docker.generic());
  }

  public AsyncEnoaGenericDocker generic(String name) {
    return new AsyncEnoaGenericDocker(Docker.generic(name));
  }

  public AsyncEnoaDocker use() {
    return new AsyncEnoaDocker(Docker.use());
  }

  public AsyncEnoaDocker use(String name) {
    return new AsyncEnoaDocker(Docker.use(name));
  }

}
