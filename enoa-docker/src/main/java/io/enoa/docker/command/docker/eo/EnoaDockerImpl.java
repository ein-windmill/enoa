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

import io.enoa.chunk.Chunk;
import io.enoa.docker.DockerConfig;
import io.enoa.docker.command.docker.generic.GenericDocker;
import io.enoa.docker.dket.docker.DRet;
import io.enoa.docker.dket.docker.common.ECreatedWithWarning;
import io.enoa.docker.dket.docker.container.ECWait;
import io.enoa.docker.dket.docker.dockerinfo.EDockerInfo;
import io.enoa.docker.dket.docker.run.EDRun;
import io.enoa.docker.dqp.DQP;
import io.enoa.docker.dqp.common.DQPResize;
import io.enoa.docker.dqp.docker.container.DQPContainerCreate;
import io.enoa.docker.parser.docker.DIParser;
import io.enoa.toolkit.convert.ConvertKit;
import io.enoa.toolkit.text.TextKit;
import io.enoa.toolkit.thread.TrdKit;
import io.enoa.toolkit.value.Void;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

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
  public DRet<EDRun> run(String name, DQPContainerCreate dqp, Chunk chunk, DQPResize resize) {
    return this.run(name, dqp, chunk, resize, Boolean.FALSE);
  }

  private DRet<EDRun> run(String name, DQPContainerCreate dqp, Chunk chunk, DQPResize resize, boolean isretry) {
    ExecutorService executor = null;
    try {
      DRet<String> retping = this.system().ping();
      if (!retping.ok()) {
        return DRet.fail(retping.origin(), retping.message());
      }
      if (!retping.data().equals("OK")) {
        return DRet.fail(retping.origin(), retping.data());
      }


      boolean autoremove = dqp.autoremove();
      boolean isinteractive = dqp.isinteractive();
      boolean showtty = dqp.showtty();
      boolean isdetach = dqp.isdetach();

      DRet<ECreatedWithWarning> retcreate = this.container().create(name, dqp);
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
          return this.run(name, dqp, chunk, resize, Boolean.TRUE);
        }
        return DRet.fail(retcreate.origin(), retcreate.message());
      }
      String id = retcreate.data().id();

      AtomicBoolean waitstart = new AtomicBoolean(Boolean.FALSE);
      AtomicReference<DRet<ECWait>> waitret = new AtomicReference<>();
      AtomicBoolean waitok = new AtomicBoolean(Boolean.TRUE);
      AtomicBoolean attachok = new AtomicBoolean(Boolean.TRUE);

      executor = Executors.newFixedThreadPool(3);
      CyclicBarrier barrier = new CyclicBarrier(isdetach ? 2 : 3);
      executor.execute(() -> {
        TrdKit.name(Thread.currentThread(), "docker-wait");
        waitstart.set(Boolean.TRUE);
        DRet<ECWait> retwait = this.container().wait(id, autoremove ? "removed" : "next-exit");
        if (!retwait.ok()) {
          System.err.println(TextKit.union("WAIT FAILD => ", id));
        }
        waitret.set(retwait);
        try {
          barrier.await();
        } catch (Exception e) {
          e.printStackTrace();
          waitok.set(Boolean.FALSE);
        }
      });

      if (isdetach) {
        DRet<Void> retstart = this.container().start(id);
        try {
          barrier.await();
        } catch (Exception e) {
          e.printStackTrace();
          return DRet.fail(retstart.origin(), e.getMessage());
        }
        ECWait ecwait = waitret.get().data();
        return retstart.ok() ?
          DRet.ok(retstart.origin(),
            new EDRun.Builder()
              .statuscode(ecwait.statuscode())
              .error(ecwait.error())
              .log(retstart.ok() ? retcreate.data().id() : null)
              .build()) :
          DRet.fail(retstart.origin(), retstart.message());
      }

      AtomicBoolean attachstart = new AtomicBoolean(Boolean.FALSE);
      AtomicReference<DRet<String>> attachret = new AtomicReference<>();
      executor.execute(() -> {
        TrdKit.name(Thread.currentThread(), "docker-attach");
        attachstart.set(Boolean.TRUE);
        DRet<String> attach = this.container()
          .attach(id, DQP.docker().container().attach()
              .stderr()
              .stdin()
              .stream()
              .stdout(),
            chunk);
        attachret.set(attach);
        try {
          barrier.await();
        } catch (Exception e) {
          e.printStackTrace();
          attachok.set(Boolean.FALSE);
        }
      });

      executor.execute(() -> {
        TrdKit.name(Thread.currentThread(), "docker-start");
        while (!waitstart.get() || !attachstart.get()) {
        }
        try {
          /*
          fixme connection check
          Enoa http 以及後續的 unixsocket 不能提供連接已建立的通知,
          因此, 不能確定在 start 之前, wait 以及 attach 連接是否已建立,
          如果是還沒有建立連接的狀態下就 start, 會導致 attach 無法抓取到
          tty 的記錄, 那麼這裏進行 100ms 的延遲, 尽量避免這種情況的發生
           */
          TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
          // skip
        }
        DRet<Void> retstart = this.container().start(id);
        if (!retstart.ok()) {
          attachret.set(DRet.fail(retstart.origin(), retstart.message()));
//          attachend.set(Boolean.TRUE);
          return;
        }
        if (resize != null) {
          this.container().resize(id, resize);
        }
      });

      long start = System.currentTimeMillis();

      try {
        barrier.await();
      } catch (Exception e) {
        e.printStackTrace();
        throw new RuntimeException(e.getMessage(), e);
      }
      if (!waitok.get() || !attachok.get()) {
        return DRet.fail(null, "CyclicBarrier ERROR");
      }

      long end = System.currentTimeMillis();
      if (this._dockerconfig().debug()) {
        System.out.println(TextKit.union("- Run pending time: ", end - start, "ms"));
      }
      DRet<ECWait> ecwait = waitret.get();
      DRet<String> ecattach = attachret.get();
      ECWait ecwd = ecwait.data();
      boolean ok = ecwait.ok() && ecattach.ok();
      if (!ok)
        return DRet.fail(ecwait.ok() ? ecwait.origin() : ecattach.origin(), ecwait.ok() ? ecwait.message() : ecattach.message());

//      String message = ecattach.ok() ? ecwait.message() : ecattach.message();
//      ECWait wcwd = ecwait.data();
//      if (wcwd.statuscode() != 0) {
//        message = wcwd.error() == null ? message : wcwd.error().message();
//      }
      Object cmdo = dqp.dqr().value("Cmd").get();
      List<String> cmds;
      if (cmdo == null) {
        cmds = Collections.emptyList();
      } else {
        if (cmdo instanceof String) {
          cmds = new ArrayList<>(1);
          cmds.add(ConvertKit.string(cmdo));
        }
        if (cmdo instanceof Collection) {
          cmds = (List<String>) ((Collection) cmdo).stream()
            .map(item -> ConvertKit.string(item))
            .collect(Collectors.toList());
        } else {
          cmds = Collections.emptyList();
        }
      }
      EDRun edrun = new EDRun.Builder()
        .log(ecattach.data())
        .error(ecwd.error())
        .statuscode(ecwd.statuscode())
        .cmd(cmds)
        .build();
      return DRet.ok(ecattach.origin(), edrun);
    } finally {
      if (executor != null)
        executor.shutdown();
    }
  }


}
