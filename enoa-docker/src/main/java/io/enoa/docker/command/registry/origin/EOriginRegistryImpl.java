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
package io.enoa.docker.command.registry.origin;

import io.enoa.docker.RegistryConfig;
import io.enoa.docker.ret.registry.RResp;
import io.enoa.http.Http;
import io.enoa.http.protocol.HttpResponse;
import io.enoa.toolkit.text.TextKit;

public class EOriginRegistryImpl extends AbstractOriginRegistry {

  public EOriginRegistryImpl(RegistryConfig config) {
    super(config);
  }

  @Override
  public RegistryConfig _registryconfig() {
    return super.config();
  }

  @Override
  public RResp _catalog(Integer n, String last) {
    Http http = super.http("_catalog");
    if (n != null)
      http.para("n", n);
    if (TextKit.blankn(last))
      http.para("last", last);
    HttpResponse response = http.emit();
    return RResp.create(response);
  }

  @Override
  public RResp tags(String repository) {
    HttpResponse response = super.http(repository, "tags/list").emit();
    return RResp.create(response);
  }

  @Override
  public OriginManifests manifests() {
    return new EOriginRegistryManifests(this);
  }

  @Override
  public OriginBlob blob() {
    return new EOriginRegistryBlob(this);
  }

  @Override
  public OriginUpload upload() {
    return new EOriginRegistryUpload(this);
  }

}
