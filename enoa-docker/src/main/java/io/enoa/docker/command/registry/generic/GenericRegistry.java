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
package io.enoa.docker.command.registry.generic;

import io.enoa.docker.command.registry._RegistryConfigSupport;
import io.enoa.docker.dket.registry.RRet;
import io.enoa.docker.parser.registry.RIParser;

public interface GenericRegistry extends _RegistryConfigSupport {

  default <T> RRet<T> _catalog(RIParser<T> parser) {
    return this._catalog(parser, 50, null);
  }

  <T> RRet<T> _catalog(RIParser<T> parser, Integer n, String last);

  <T> RRet<T> tags(RIParser<T> parser, String repository);

  GenericManifests manifests();

  GenericBlob blob();

  GenericUpload upload();

}
