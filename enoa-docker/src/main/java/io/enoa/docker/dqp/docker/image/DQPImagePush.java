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

import io.enoa.docker.dqp.DQH;
import io.enoa.docker.dqp.DQP;
import io.enoa.docker.dqp.DQR;

public class DQPImagePush implements DQP {

  /**
   * string
   * <p>
   * The tag to associate with the image on the registry.
   */
  private String tag;
  /**
   * X-Registry-Auth
   * string Required
   * <p>
   * A base64-encoded auth configuration.
   * <a href="https://docs.docker.com/engine/api/v1.37/#section/Authentication">See the authentication section for details.</a>
   */
  private String registryauth;

  public static DQPImagePush create() {
    return new DQPImagePush();
  }

  public DQPImagePush() {
  }

  public DQPImagePush tag(String tag) {
    this.tag = tag;
    return this;
  }

  public DQPImagePush registryauth(String registryauth) {
    this.registryauth = registryauth;
    return this;
  }

  @Override
  public DQR dqr() {
    DQR dqr = DQR.create()
      .putIf("tag", this.tag);
    return dqr;
  }

  @Override
  public DQH dqh() {
    DQH dqh = DQH.create()
      .addIf("X-Registry-Auth", this.registryauth);
    return dqh;
  }
}
