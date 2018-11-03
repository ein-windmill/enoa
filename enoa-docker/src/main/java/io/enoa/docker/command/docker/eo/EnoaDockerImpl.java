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

import io.enoa.docker.DockerConfig;
import io.enoa.docker.command.docker.generic.GenericDocker;
import io.enoa.docker.dket.docker.DRet;
import io.enoa.docker.dket.docker.common.ECreatedWithWarning;
import io.enoa.docker.dket.docker.container.ECWait;
import io.enoa.docker.dket.docker.dockerinfo.EDockerInfo;
import io.enoa.docker.dqp.DQP;
import io.enoa.docker.dqp.common.DQPResize;
import io.enoa.docker.dqp.docker.container.DQPContainerCreate;
import io.enoa.docker.parser.docker.DIParser;
import io.enoa.toolkit.text.TextKit;
import io.enoa.toolkit.value.Void;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

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
  public DRet<String> run(String name, DQPContainerCreate create, DQPResize resize) {
    return this.run(name, create, resize, Boolean.FALSE);
  }

  private DRet<String> run(String name, DQPContainerCreate create, DQPResize resize, boolean isretry) {
    ExecutorService executor = null;
    try {
      DRet<String> retping = this.system().ping();
      if (!retping.ok()) {
        return DRet.fail(retping.origin(), retping.message());
      }
      if (!retping.data().equals("OK")) {
        return DRet.fail(retping.origin(), retping.data());
      }

      executor = Executors.newFixedThreadPool(3);

      boolean autoremove = create.autoremove();
      boolean isinteractive = create.isinteractive();
      boolean showtty = create.showtty();
      boolean isdetach = create.isdetach();

      DRet<ECreatedWithWarning> retcreate = this.container().create(name, create);
      if (!retcreate.ok()) {
        String message = retcreate.message();
        if (!isretry && message.startsWith("Conflict. The container name") && message.contains("is already in use by container")) {
          /*
          因爲使用線程異步執行, 如果兩次執行間隔時間較短,
          有可能發生 wait?condition=removed 沒有執行完畢的情況
          當檢測到這個異常, 重新進行一次嘗試, 如果重新嘗試後還是一樣的結果
          則直接拋出
           */
          try {
            TimeUnit.MILLISECONDS.sleep(100);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          return this.run(name, create, resize, Boolean.TRUE);
        }
        return DRet.fail(retcreate.origin(), retcreate.message());
      }
      String id = retcreate.data().id();

      AtomicBoolean waitattach = new AtomicBoolean();
      waitattach.set(Boolean.FALSE);
      AtomicBoolean waitnext = new AtomicBoolean();
      waitnext.set(Boolean.FALSE);

      executor.execute(() -> {
        waitnext.set(Boolean.TRUE);
        DRet<ECWait> retwait = this.container().wait(id, autoremove ? "removed" : "next-exit");
        if (!retwait.ok()) {
          System.err.println(TextKit.union("WAIT FAILD => ", id));
        }
      });

      if (isdetach) {
        DRet<Void> retstart = this.container().start(id);
        return retstart.ok() ?
          DRet.ok(retcreate.origin(), retcreate.data().id()) :
          DRet.fail(retstart.origin(), null);
      }

      AtomicBoolean complete = new AtomicBoolean();
      complete.set(Boolean.FALSE);
      AtomicReference<DRet<String>> result = new AtomicReference<>();
      executor.execute(() -> {
        waitattach.set(Boolean.TRUE);
        DRet<String> attach = this.container()
          .attach(id, DQP.docker().container().attach()
            .stderr()
            .stdin()
            .stdout()
            .stream()
          );
        result.set(attach);
        complete.set(Boolean.TRUE);
      });

      executor.execute(() -> {
        while (!waitattach.get() && !waitnext.get()) {
        }
        try {
          /*
          Enoa http 以及後續的 unixsocket 不能提供連接已建立的通知,
          因此, 不能確定在 start 之前, wait 以及 attach 連接是否已建立,
          如果是還沒有建立連接的狀態下就 start, 會導致 attach 無法抓取到
          tty 的記錄, 那麼這裏進行 100ms 的延遲, 精良避免這種情況的發生
           */
          TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
          // skip
        }
        DRet<Void> retstart = this.container().start(id);
        if (!retstart.ok()) {
          result.set(DRet.fail(retstart.origin(), null));
          complete.set(Boolean.TRUE);
          return;
        }
        if (resize != null) {
          this.container().resize(id, resize);
        }
      });

      long start = System.currentTimeMillis();
      while (!complete.get()) {
      }
      long end = System.currentTimeMillis();
      if (this._dockerconfig().debug()) {
        System.out.println(TextKit.union("- Run pending time: ", end - start, "ms"));
      }
      return result.get();
    } finally {
      if (executor != null)
        executor.shutdown();
    }
  }


}
