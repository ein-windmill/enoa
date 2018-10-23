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
package io.enoa.docker.dqp.docker.image;

import io.enoa.docker.dqp.DQP;
import io.enoa.docker.dqp.DQR;

public class DQPImageTag implements DQP {

  /**
   * string
   * <p>
   * The repository to tag in. For example, someuser/someimage.
   */
  private String repo;
  /**
   * string
   * <p>
   * The name of the new tag.
   */
  private String tag;

  public static DQPImageTag create() {
    return new DQPImageTag();
  }

  public DQPImageTag() {
  }


  public DQPImageTag repo(String repo) {
    this.repo = repo;
    return this;
  }

  public DQPImageTag tag(String tag) {
    this.tag = tag;
    return this;
  }


  @Override
  public DQR dqr() {
    DQR dqr = DQR.create()
      .putIf("repo", this.repo)
      .putIf("tag", this.tag);
    return dqr;
  }
}
