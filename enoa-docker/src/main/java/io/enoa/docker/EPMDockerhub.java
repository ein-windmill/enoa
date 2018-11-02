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
package io.enoa.docker;

import io.enoa.docker.async.hub.eo.EAsyncEnoaDockerhub;
import io.enoa.docker.async.hub.generic.EAsyncGenericDockerhub;
import io.enoa.docker.async.hub.origin.EAsyncOriginDockerhub;
import io.enoa.docker.command.hub.eo.EnoaDockerhub;
import io.enoa.docker.command.hub.generic.GenericDockerhub;
import io.enoa.docker.command.hub.origin.EOriginDockerhubImpl;
import io.enoa.docker.command.hub.origin.OriginDockerhub;

public class EPMDockerhub {

  private static class Holder {
    private static final EPMDockerhub INSTANCE = new EPMDockerhub();
  }

  static EPMDockerhub instance() {
    return Holder.INSTANCE;
  }

  private OriginDockerhub origin;
  private GenericDockerhub generic;
  private EnoaDockerhub eo;
  private AsyncDockerhub async;

  private EAsyncOriginDockerhub asyncorigin;
  private EAsyncGenericDockerhub asyncgeneric;
  private EAsyncEnoaDockerhub asynceo;

  private EPMDockerhub() {

  }

  public void install(DockerhubConfig config) {
    this.origin = new EOriginDockerhubImpl(config);
  }

  OriginDockerhub origin() {
    return this.origin;
  }

  GenericDockerhub generic() {
    if (this.generic != null)
      return this.generic;

    OriginDockerhub origin = this.origin();
    if (this.origin == null)
      return null;

    this.generic = new GenericDockerhub(origin);
    return this.generic;
  }

  EnoaDockerhub dockerhub() {
    if (this.eo != null)
      return this.eo;

    GenericDockerhub generic = this.generic();
    if (this.generic == null)
      return null;

    this.eo = new EnoaDockerhub(generic);
    return this.eo;
  }

  AsyncDockerhub async() {
    if (this.async != null)
      return this.async;
    this.async = new AsyncDockerhub();
    return this.async;
  }

  EAsyncOriginDockerhub asyncorigin() {
    if (this.asyncorigin != null)
      return this.asyncorigin;
    this.asyncorigin = new EAsyncOriginDockerhub(this.origin());
    return this.asyncorigin;
  }

  EAsyncGenericDockerhub asyncgeneric() {
    if (this.asyncgeneric != null)
      return this.asyncgeneric;
    this.asyncgeneric = new EAsyncGenericDockerhub(this.generic());
    return asyncgeneric;
  }

  EAsyncEnoaDockerhub asynceo() {
    if (this.asynceo != null)
      return this.asynceo;
    this.asynceo = new EAsyncEnoaDockerhub(this.dockerhub());
    return asynceo;
  }
}
