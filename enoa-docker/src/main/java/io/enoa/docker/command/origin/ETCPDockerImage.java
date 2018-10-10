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

import io.enoa.docker.dqp.DQR;
import io.enoa.docker.dqp.image.DQPBuild;
import io.enoa.docker.dqp.image.DQPListImage;
import io.enoa.docker.stream.DStream;
import io.enoa.docker.tar.DTar;
import io.enoa.docker.thr.DockerException;
import io.enoa.http.Http;
import io.enoa.http.protocol.HttpMethod;
import io.enoa.http.protocol.HttpResponse;
import io.enoa.http.protocol.chunk.Chunk;
import io.enoa.toolkit.binary.EnoaBinary;
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
      http.para(dqp.dqr().http());
    HttpResponse response = http.emit();
    return response.body().string();
  }

  @Override
  public String build(DQPBuild dqp, String dockerfile, DStream<String> dstream) {
    if (dqp == null)
      throw new DockerException(EnoaTipKit.message("eo.tip.docker.lost_dqp"));
    DQR dqr = dqp.dqr();
    Http http = this.docker.http("build")
      .method(HttpMethod.POST)
      .para(dqr.http())
      .header(dqp.dqh().headers())
      .binary(DTar.cvf(dqr.value("dockerfile").string(), dockerfile).bytebuffer());
    HttpResponse response = dstream == null ?
      http.emit() :
      http.chunk(Chunk.builder(bytes -> dstream.runner().run(EnoaBinary.create(bytes).string()))
        .stopper(() -> dstream.stopper() == null ? Boolean.FALSE : dstream.stopper().stop())
        .build());
    return response.body().string();
  }

}
