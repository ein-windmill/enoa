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

public class DQPImageList implements DQP {


  /**
   * boolean
   * default false
   * <p>
   * Show all images. Only images from a final layer (no children) are shown by default.
   */
  private boolean all;
  /**
   * boolean
   * default false
   * <p>
   * Show digest information as a RepoDigests field on each image.
   */
  private boolean digests;
  /**
   * string
   * <p>
   * A JSON encoded value of the filters (a map[string][]string) to process on the images list. Available filters:
   * <p>
   * before=(<image-name>[:<tag>], <image id> or <image@digest>)
   * dangling=true
   * label=key or label="key=value" of an image label
   * reference=(<image-name>[:<tag>])
   * since=(<image-name>[:<tag>], <image id> or <image@digest>)
   */
  private FilterImageList filters;

  public static DQPImageList create() {
    return new DQPImageList();
  }

  public DQPImageList() {
  }

  public DQPImageList all() {
    return this.all(Boolean.TRUE);
  }

  public DQPImageList all(boolean all) {
    this.all = all;
    return this;
  }

  public DQPImageList digests() {
    return this.digests(Boolean.TRUE);
  }

  public DQPImageList digests(boolean digests) {
    this.digests = digests;
    return this;
  }

  public FilterImageList filters() {
    if (this.filters == null)
      this.filters = new FilterImageList(this);
    return this.filters;
  }

  @Override
  public DQR dqr() {
    DQR dqr = DQR.create();
    if (this.all)
      dqr.put("all", this.all);
    if (this.digests)
      dqr.put("digests", this.digests);
    if (this.filters != null)
      dqr.put(this.filters.dqr());
    return dqr;
  }
}
