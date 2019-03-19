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
package io.enoa.docker.registry;

import io.enoa.docker.Registry;
import io.enoa.docker.RegistryConfig;
import io.enoa.http.Http;
import io.enoa.http.protocol.enoa.IHttpHandler;
import io.enoa.http.protocol.enoa.IHttpReporter;
import io.enoa.json.Json;
import io.enoa.json.provider.gson.GsonProvider;
import org.junit.Before;
import org.junit.Ignore;

@Ignore
public abstract class AbstractRegistryTest {

  @Before
  public void ibe() {
    RegistryConfig config = new RegistryConfig.Builder()
      .debug(Boolean.TRUE)
      .host("localhost")
      .port(5000)
      .http(() -> Http.use().reporter(IHttpReporter.def()).handler(IHttpHandler.def()))
      .json(new GsonProvider())
      .build();
    Registry.epm().install(config);
    Json.epm().install(new GsonProvider());
  }

}
