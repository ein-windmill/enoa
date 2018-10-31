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
package io.enoa.docker.parser.dockerhub;

import io.enoa.docker.DockerhubConfig;
import io.enoa.docker.dket.dockerhub.HRet;
import io.enoa.docker.dket.dockerhub.build.EHAutobuild;
import io.enoa.docker.dket.dockerhub.build.EHBuildHistory;
import io.enoa.docker.dket.dockerhub.explore.EHExplore;
import io.enoa.docker.dket.dockerhub.inspece.EHRepository;
import io.enoa.docker.dket.dockerhub.search.EHSearch;
import io.enoa.docker.dket.dockerhub.tag.EHTag;
import io.enoa.docker.dket.registry.RResp;

public interface HIParser<T> {

  static HIParser<EHExplore> explore() {
    return EHExploreParser.instance();
  }

  static HIParser<EHSearch> search() {
    return EHSearchParser.instance();
  }

  static HIParser<EHRepository> inspect() {
    return EHInspectParser.instance();
  }

  static HIParser<EHTag> tag() {
    return EHTagParser.instance();
  }

  static HIParser<EHAutobuild> autobuild() {
    return EHAutobuildParser.instance();
  }

  static HIParser<EHBuildHistory> buildhistory() {
    return EHBuildHistoryParser.instance();
  }

  static HIParser<String> dockerfile() {
    return EHDockerfileParser.instance();
  }

  HRet<T> parse(DockerhubConfig config, RResp resp);

}
