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

public class DQPImageCreate implements DQP {

  /**
   * string
   * <p>
   * Name of the image to pull. The name may include a tag or digest. This parameter may only be used when pulling an image. The pull is cancelled if the HTTP connection is closed.
   */
  private String fromimage;
  /**
   * string
   * <p>
   * Source to import. The value may be a URL from which the image can be retrieved or - to read the image from the request body. This parameter may only be used when importing an image.
   */
  private String fromsrc;
  /**
   * string
   * <p>
   * Repository name given to an image when it is imported. The repo may include a tag. This parameter may only be used when importing an image.
   */
  private String repo;
  /**
   * string
   * <p>
   * Tag or digest. If empty when pulling an image, this causes all tags for the given image to be pulled.
   */
  private String tag;
  /**
   * string
   * default ""
   * <p>
   * Platform in the format os[/arch[/variant]]
   */
  private String platform;
  /**
   * X-Registry-Auth
   * string
   * <p>
   * A base64-encoded auth configuration. See the authentication section for details.
   */
  private String registryauth;

  public static DQPImageCreate create() {
    return new DQPImageCreate();
  }

  public DQPImageCreate() {
  }

  public DQPImageCreate fromimage(String fromimage) {
    this.fromimage = fromimage;
    return this;
  }

  public DQPImageCreate fromsrc(String fromsrc) {
    this.fromsrc = fromsrc;
    return this;
  }

  public DQPImageCreate repo(String repo) {
    this.repo = repo;
    return this;
  }

  public DQPImageCreate tag(String tag) {
    this.tag = tag;
    return this;
  }

  public DQPImageCreate platform(String platform) {
    this.platform = platform;
    return this;
  }

  public DQPImageCreate registryauth(String registryauth) {
    this.registryauth = registryauth;
    return this;
  }

  @Override
  public DQR dqr() {
    DQR dqr = DQR.create()
      .putIf("fromImage", this.fromimage)
      .putIf("fromSrc", this.fromsrc)
      .putIf("repo", this.repo)
      .putIf("tag", this.tag)
      .putIf("platform", this.platform);
    return dqr;
  }

  @Override
  public DQH dqh() {
    DQH dqh = DQH.create()
      .addIf("X-Registry-Auth", this.registryauth);
    return dqh;
  }
}
