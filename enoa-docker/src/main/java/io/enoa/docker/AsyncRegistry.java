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

import io.enoa.docker.async.registry.eo.EAsyncEnoaRegistry;
import io.enoa.docker.async.registry.generic.EAsyncGenericRegistry;
import io.enoa.docker.async.registry.origin.EAsyncOriginRegistry;

public class AsyncRegistry {

  private String name;

  AsyncRegistry(String name) {
    this.name = name;
  }


  public EAsyncOriginRegistry origin() {
    return Registry.epm().asyncorigin(this.name);
  }

  public EAsyncGenericRegistry generic() {
    return Registry.epm().asyncgeneric(this.name);
  }

  public EAsyncEnoaRegistry use() {
    return Registry.epm().asynceo(name);
  }


}
