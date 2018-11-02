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
package io.enoa.docker.command.docker.origin;

import io.enoa.docker.DockerConfig;
import io.enoa.docker.dket.docker.DResp;
import io.enoa.http.EoUrl;
import io.enoa.http.Http;
import io.enoa.http.protocol.HttpMethod;
import io.enoa.http.protocol.HttpResponse;
import io.enoa.http.protocol.enoa.IHttpHandler;
import io.enoa.http.protocol.enoa.IHttpReporter;
import io.enoa.toolkit.text.TextKit;

public class EnoaTCPDocker implements OriginDocker {

  private String _host;
  private DockerConfig dockerconfig;

  private EOriginDockerContainer container;
  private EOriginDockerImage image;
  private EOriginDockerNetwork network;
  private EOriginDockerVolume volume;
  private EOriginDockerExec exec;
  private EOriginDockerSwarm swarm;
  private EOriginDockerNode node;
  private EOriginDockerService service;
  private EOriginDockerTask task;
  private EOriginDockerSecret secret;
  private EOriginDockerConfig config;
  private EOriginDockerPlugin plugin;
  private EOriginDockerSystem system;
  private EOriginDockerDistribution distribution;

  public EnoaTCPDocker(DockerConfig dockerconfig) {
    this.dockerconfig = dockerconfig;
    this.container = new ETCPDockerContainer(this);
    this.image = new ETCPDockerImage(this);
    this.network = new ETCPDockerNetwork(this);
    this.volume = new ETCPDockerVolume(this);
    this.exec = new ETCPDockerExec(this);
    this.swarm = new ETCPDockerDockerSwarm(this);
    this.node = new ETCPDockerDockerNode(this);
    this.service = new ETCPDockerDockerService(this);
    this.task = new ETCPDockerDockerTask(this);
    this.secret = new ETCPDockerDockerSecret(this);
    this.config = new ETCPDockerConfig(this);
    this.plugin = new ETCPDockerDockerPlugin(this);
    this.system = new ETCPDockerDockerSystem(this);
    this.distribution = new ETCPDockerDistribution(this);
  }

  protected EoUrl url(String... subpaths) {
    if (this._host == null) {
      String host = this.dockerconfig.host();
      if (host.toLowerCase().startsWith("tcp://")) {
        host = TextKit.union(this.dockerconfig.tls() ? "https" : "http", host.substring(3));
      }
      this._host = host;
    }
    return EoUrl.with(this._host).subpath(subpaths);
  }

  protected Http http(String subpath) {
    return this.http(new String[]{subpath});
  }

  protected Http http(String... subpaths) {
    return this.http(this.url(subpaths));
  }

  protected Http http(EoUrl url) {
    Http http = Http.request(url);
    if (this.dockerconfig.debug())
      http.reporter(IHttpReporter.def())
        .handler(IHttpHandler.def());
    return http;
  }

  @Override
  public DockerConfig _dockerconfig() {
    return this.dockerconfig;
  }

  @Override
  public DResp info() {
    HttpResponse response = this.http(TextKit.union("/", this.dockerconfig.version(), "/info"))
      .method(HttpMethod.GET)
      .emit();
    return DResp.create(response);
  }

  @Override
  public EOriginDockerContainer container() {
    return this.container;
  }

  @Override
  public EOriginDockerImage image() {
    return this.image;
  }

  @Override
  public EOriginDockerNetwork network() {
    return this.network;
  }

  @Override
  public EOriginDockerVolume volume() {
    return this.volume;
  }

  @Override
  public EOriginDockerExec exec() {
    return this.exec;
  }

  @Override
  public EOriginDockerSwarm swarm() {
    return this.swarm;
  }

  @Override
  public EOriginDockerNode node() {
    return this.node;
  }

  @Override
  public EOriginDockerService service() {
    return this.service;
  }

  @Override
  public EOriginDockerTask task() {
    return this.task;
  }

  @Override
  public EOriginDockerSecret secret() {
    return this.secret;
  }

  @Override
  public EOriginDockerConfig config() {
    return this.config;
  }

  @Override
  public EOriginDockerPlugin plugin() {
    return this.plugin;
  }

  @Override
  public EOriginDockerSystem system() {
    return this.system;
  }

  @Override
  public EOriginDockerDistribution distribution() {
    return this.distribution;
  }

}
