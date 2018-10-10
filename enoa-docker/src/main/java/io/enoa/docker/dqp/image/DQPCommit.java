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
package io.enoa.docker.dqp.image;

import io.enoa.docker.dqp.DQP;
import io.enoa.docker.dqp.DQR;

public class DQPCommit implements DQP {

  private String container;
  private String repo;
  private String tag;
  private String comment;
  private String author;
  private Boolean pause;
  private String changes;

  public static DQPCommit create() {
    return new DQPCommit();
  }

  public DQPCommit() {
    this.pause = Boolean.TRUE;
  }

  public DQPCommit container(String container) {
    this.container = container;
    return this;
  }

  public DQPCommit repo(String repo) {
    this.repo = repo;
    return this;
  }

  public DQPCommit tag(String tag) {
    this.tag = tag;
    return this;
  }

  public DQPCommit comment(String comment) {
    this.comment = comment;
    return this;
  }

  public DQPCommit author(String author) {
    this.author = author;
    return this;
  }

  public DQPCommit pause(Boolean pause) {
    this.pause = pause;
    return this;
  }

  public DQPCommit changes(String changes) {
    this.changes = changes;
    return this;
  }

  @Override
  public DQR dqr() {
    DQR dqr = DQR.create()
      .putIf("container", this.container)
      .putIf("repo", this.repo)
      .putIf("tag", this.tag)
      .putIf("comment", this.comment)
      .putIf("author", this.author)
      .put("pause", this.pause)
      .putIf("changes", this.changes);

    return dqr;
  }
}
