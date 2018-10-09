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
package io.enoa.docker.command.origin;

import io.enoa.docker.DockerConfig;
import io.enoa.http.EoUrl;
import io.enoa.http.Http;
import io.enoa.http.protocol.HttpMethod;
import io.enoa.http.protocol.HttpResponse;
import io.enoa.http.protocol.enoa.IHttpHandler;
import io.enoa.http.protocol.enoa.IHttpReporter;
import io.enoa.toolkit.text.TextKit;

public class EnoaTCPDocker implements OriginDocker {

  private String _host;
  private DockerConfig config;

  public EnoaTCPDocker(DockerConfig config) {
    this.config = config;
  }

  protected Http http(String subpath) {
    return this.http(new String[]{subpath});
  }

  protected Http http(String... subpaths) {
    if (this._host == null) {
      String host = this.config.host();
      if (host.toLowerCase().startsWith("tcp://")) {
        host = TextKit.union(this.config.tls() ? "https" : "http", host.substring(3));
      }
      this._host = host;
    }
    Http http = Http.request(EoUrl.with(this._host).subpath(subpaths));
    if (this.config.debug())
      http.reporter(IHttpReporter.def())
        .handler(IHttpHandler.def());
    return http;
  }

  @Override
  public DockerConfig _dockerconfig() {
    return this.config;
  }

  @Override
  public String info() {
    HttpResponse response = this.http(TextKit.union("/", this.config.version(), "/info"))
      .method(HttpMethod.GET)
      .emit();
    return response.body().string();
  }

  @Override
  public EOriginDockerContainer container() {
    return new ETCPDockerContainer(this);
  }

  @Override
  public EOriginDockerImage image() {
    return new ETCPDockerImage(this);
  }

  @Override
  public EOriginDockerNetwork network() {
    return new ETCPDockerNetwork(this);
  }

  @Override
  public EOriginDockerVolume volume() {
    return new ETCPDockerVolume(this);
  }

  @Override
  public EOriginDockerExec exec() {
    return new ETCPDockerExec(this);
  }

  @Override
  public EOriginSwarm swarm() {
    return new ETCPDockerSwarm(this);
  }

  @Override
  public EOriginNode node() {
    return new ETCPDockerNode(this);
  }

  @Override
  public EOriginService service() {
    return new ETCDockerService(this);
  }

  @Override
  public EOriginTask task() {
    return new ETCPDockerTask(this);
  }

  @Override
  public EOriginSecret secret() {
    return new ETCPDockerSecret(this);
  }

  @Override
  public EOriginConfig config() {
    return new ETCPDockerConfig(this);
  }

  @Override
  public EOriginPlugin plugin() {
    return new ETCPDockerPlugin(this);
  }

  @Override
  public EOriginSystem system() {
    return new ETCPDockerSystem(this);
  }

  @Override
  public EOriginDistribution distribution() {
    return new ETCPDockerDistribution(this);
  }
}
