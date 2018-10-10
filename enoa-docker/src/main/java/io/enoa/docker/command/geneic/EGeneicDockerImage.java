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
package io.enoa.docker.command.geneic;

import io.enoa.docker.DockerConfig;
import io.enoa.docker.command.origin.EOriginDockerImage;
import io.enoa.docker.command.origin.OriginDocker;
import io.enoa.docker.dqp.image.DQPBuild;
import io.enoa.docker.dqp.image.DQPListImage;
import io.enoa.docker.dret.DRet;
import io.enoa.docker.parser.DIParser;
import io.enoa.docker.stream.DStream;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.map.Kv;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EGeneicDockerImage {

  private OriginDocker docker;
  private DockerConfig config;
  private EOriginDockerImage image;

  EGeneicDockerImage(OriginDocker docker) {
    this.docker = docker;
    this.config = docker._dockerconfig();
    this.image = docker.image();
  }

  private DRet<List<Kv>> linestolist(String origin) {
    String[] lines = origin.split("\n");
    if (CollectionKit.isEmpty(lines))
      return DRet.ok(origin, Collections.emptyList());
    List<Kv> kvs = Stream.of(lines)
      .map(line -> this.config.json().parse(line, Kv.class))
      .collect(Collectors.toList());
    return DRet.ok(origin, kvs);
  }

  public <T> DRet<List<T>> list(DIParser<List<T>> parser) {
    return this.list(parser, null);
  }

  public <T> DRet<List<T>> list(DIParser<List<T>> parser, DQPListImage dqp) {
    String origin = this.image.list(dqp);
    return parser.parse(this.config, origin);
  }

  public DRet<List<Kv>> build(DQPBuild dqp, String dockerfile) {
    String origin = this.image.build(dqp, dockerfile);
    return this.linestolist(origin);
  }

  public DRet<List<Kv>> build(DQPBuild dqp, String dockerfile, DStream<Kv> dstream) {
    DStream<String> dst0 = DStream.<String>builder(text -> dstream.runner().run(this.config.json().parse(text, Kv.class)))
      .stopper(dstream.stopper()).build();
    String origin = this.image.build(dqp, dockerfile, dst0);
    return this.linestolist(origin);
  }

}
