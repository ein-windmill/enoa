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
package io.enoa.docker.command.docker.eo;

import io.enoa.docker.Docker;
import io.enoa.docker.DockerConfig;
import io.enoa.docker.command.docker.generic.GenericDocker;
import io.enoa.docker.dket.docker.DRet;
import io.enoa.docker.dket.docker.common.ECreatedWithWarning;
import io.enoa.docker.dket.docker.container.ECWait;
import io.enoa.docker.dket.docker.dockerinfo.EDockerInfo;
import io.enoa.docker.dqp.DQP;
import io.enoa.docker.dqp.docker.container.DQPContainerCreate;
import io.enoa.docker.parser.docker.DIParser;
import io.enoa.json.Json;
import io.enoa.toolkit.value.Void;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class EnoaDockerImpl implements EoDocker {

  private GenericDocker docker;
  private EnoaDockerContainer container;
  private EnoaDockerImage image;
  private EnoaDockerNetwork network;
  private EnoaDockerVolume volume;
  private EnoaDockerExec exec;
  private EnoaDockerSwarm swarm;
  private EnoaDockerNode node;
  private EnoaDockerService service;
  private EnoaDockerTask task;
  private EnoaDockerSecret secret;
  private EnoaDockerConfig config;
  private EnoaDockerPlugin plugin;
  private EnoaDockerSystem system;
  private EnoaDockerDistribution distribution;


  public EnoaDockerImpl(GenericDocker docker) {
    this.docker = docker;
    this.container = new EnoaDockerContainer(this.docker);
    this.image = new EnoaDockerImage(this.docker);
    this.network = new EnoaDockerNetwork(this.docker);
    this.volume = new EnoaDockerVolume(this.docker);
    this.exec = new EnoaDockerExec(this.docker);
    this.swarm = new EnoaDockerSwarm(this.docker);
    this.node = new EnoaDockerNode(this.docker);
    this.service = new EnoaDockerService(this.docker);
    this.task = new EnoaDockerTask(this.docker);
    this.secret = new EnoaDockerSecret(this.docker);
    this.config = new EnoaDockerConfig(this.docker);
    this.plugin = new EnoaDockerPlugin(this.docker);
    this.system = new EnoaDockerSystem(this.docker);
    this.distribution = new EnoaDockerDistribution(this.docker);
  }

  @Override
  public DockerConfig _dockerconfig() {
    return this.docker._dockerconfig();
  }

  @Override
  public DRet<EDockerInfo> info() {
    return this.docker.info(DIParser.dockerinfo());
  }

  @Override
  public EnoaDockerContainer container() {
    return this.container;
  }

  @Override
  public EnoaDockerImage image() {
    return this.image;
  }

  @Override
  public EnoaDockerNetwork network() {
    return this.network;
  }

  @Override
  public EnoaDockerVolume volume() {
    return this.volume;
  }

  @Override
  public EnoaDockerExec exec() {
    return this.exec;
  }

  @Override
  public EnoaDockerSwarm swarm() {
    return this.swarm;
  }

  @Override
  public EnoaDockerNode node() {
    return this.node;
  }

  @Override
  public EnoaDockerService service() {
    return this.service;
  }

  @Override
  public EnoaDockerTask task() {
    return this.task;
  }

  @Override
  public EnoaDockerSecret secret() {
    return this.secret;
  }

  @Override
  public EnoaDockerConfig config() {
    return this.config;
  }

  @Override
  public EnoaDockerPlugin plugin() {
    return this.plugin;
  }

  @Override
  public EnoaDockerSystem system() {
    return this.system;
  }

  @Override
  public EnoaDockerDistribution distribution() {
    return this.distribution;
  }

  @Override
  public DRet<String> run(String name, DQPContainerCreate dqp) {
    ExecutorService executor = null;
    try {
      DRet<String> ret0 = this.system().ping();
      if (!ret0.ok()) {
        return DRet.fail(ret0.origin(), ret0.message());
      }
      if (!ret0.data().equals("OK")) {
        return DRet.fail(ret0.origin(), ret0.data());
      }

      executor = Executors.newFixedThreadPool(2);

      boolean autoremove = dqp.autoremove();
      boolean isinteractive = dqp.isinteractive();
      boolean showtty = dqp.showtty();
      boolean isdetach = dqp.isdetach();

      DRet<ECreatedWithWarning> ret1 = this.container().create(name, dqp);
      if (!ret1.ok()) {
        return DRet.fail(ret1.origin(), ret1.message());
      }
      String id = ret1.data().id();

      this.container().resize(id, DQP.docker().resize().width(269).height(7));


      Docker.async()
        .container()
        .start(id)
        .enqueue()
        .asset(DRet::ok)
        .failthrow(ret -> System.err.println(ret.message()))
        .capture(System.err::println);


      executor.execute(() -> {
        DRet<ECWait> ret2 = this.container().wait(id, autoremove ? "removed" : "next-exit");
        if (ret2.ok()) {
          System.out.println("REMOVED => " + id);
        }
//        System.out.println(ret2.ok());
//        System.out.println(Json.toJson(ret2.data()));
      });

      if (isinteractive && showtty) {
//        executor.submit()
        Future<DRet<String>> future = executor.submit(() -> this.container()
          .attach(id,
            DQP.docker().container().attch()
              .stderr()
              .stdin()
              .stdout()
              .stream()
          ));
        DRet<String> ret4;
        try {
          ret4 = future.get();
        } catch (Exception e) {
          if (e instanceof RuntimeException)
            throw (RuntimeException) e;
          throw new RuntimeException(e.getMessage(), e);
        }
        System.out.println("STDOUT: ");
        System.out.println(ret4.data());
      }


//      DRet<Void> ret3 = this.container().start(id);
//      if (!ret3.ok())
//        return DRet.fail(ret3.origin(), "");

      return DRet.ok(ret1.origin(), id);
    } finally {
      if (executor != null)
        executor.shutdown();
    }
  }


}
