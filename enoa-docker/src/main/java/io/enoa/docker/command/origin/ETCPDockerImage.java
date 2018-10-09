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
package io.enoa.docker.command.origin;

import io.enoa.docker.dqp.image.DQPBuild;
import io.enoa.docker.dqp.image.DQPListImage;
import io.enoa.docker.thr.DockerException;
import io.enoa.http.Http;
import io.enoa.http.protocol.HttpMethod;
import io.enoa.http.protocol.HttpResponse;
import io.enoa.toolkit.eo.tip.EnoaTipKit;

public class ETCPDockerImage implements EOriginDockerImage {

  private EnoaTCPDocker docker;

  ETCPDockerImage(EnoaTCPDocker docker) {
    this.docker = docker;
  }

  @Override
  public String list(DQPListImage dqp) {
    Http http = this.docker.http("images/json")
      .method(HttpMethod.GET);
    if (dqp != null)
      http.para(dqp.para().http());
    HttpResponse response = http.emit();
    return response.body().string();
  }

  @Override
  public String build(DQPBuild dqp, String dockerfile) {
    if (dqp == null)
      throw new DockerException(EnoaTipKit.message("eo.tip.docker.lost_dqp"));
    HttpResponse response = this.docker.http("build")
      .method(HttpMethod.POST)
      .para(dqp.para().http())
      .raw(dockerfile)
      .emit();
    return response.body().string();
  }

}
