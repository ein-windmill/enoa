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
package io.enoa.docker.ret.docker.swarm;

import io.enoa.docker.ret.AbstractDRRet;

public class EVersion extends AbstractDRRet {

  /**
   *  ObjectVersion
   *
   * The version number of the object such as node, service, etc.
   * This is needed to avoid conflicting writes.
   * The client must send the version number along with the modified specification when updating these objects.
   * This approach ensures safe concurrency and determinism in that the change on the object may not be applied
   * if the version number has changed from the last read. In other words,
   * if two update requests specify the same base version, only one of the requests can succeed.
   * As a result, two separate update requests that happen at the same time will not unintentionally overwrite each other.
   */
  private Integer index;

  public EVersion(Builder builder) {
    this.index = builder.index;
  }

  public Integer index() {
    return this.index;
  }

  public static class Builder {
    private Integer index;

    public EVersion build() {
      return new EVersion(this);
    }

    public Builder index(Integer index) {
      this.index = index;
      return this;
    }
  }

}
