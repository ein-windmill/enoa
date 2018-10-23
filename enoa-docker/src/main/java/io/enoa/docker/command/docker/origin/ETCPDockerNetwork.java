package io.enoa.docker.command.docker.origin;

import io.enoa.docker.dqp.docker.network.DQPNetworkInspect;
import io.enoa.docker.dqp.docker.network.DQPNetworkList;
import io.enoa.docker.dqp.docker.common.DQPFilter;
import io.enoa.docker.ret.docker.DResp;
import io.enoa.http.Http;
import io.enoa.http.protocol.HttpMethod;
import io.enoa.http.protocol.HttpResponse;

public class ETCPDockerNetwork implements EOriginDockerNetwork {

  private EnoaTCPDocker docker;

  ETCPDockerNetwork(EnoaTCPDocker docker) {
    this.docker = docker;
  }

  @Override
  public DResp list(DQPNetworkList dqp) {
    Http http = this.docker.http("networks");
    if (dqp != null)
      http.para(dqp.dqr().http());
    HttpResponse response = http.emit();
    return DResp.create(response);
  }

  @Override
  public DResp inspect(String id, DQPNetworkInspect dqp) {
    Http http = this.docker.http("networks", id);
    if (dqp != null)
      http.para(dqp.dqr().http());
    HttpResponse response = http.emit();
    return DResp.create(response);
  }


  @Override
  public DResp remove(String id) {
    HttpResponse response = this.docker.http("networks", id)
      .method(HttpMethod.DELETE)
      .emit();
    return DResp.create(response);
  }

  @Override
  public DResp create(String body) {
    HttpResponse response = this.docker.http("networks/create")
      .method(HttpMethod.POST)
      .raw(body, "application/json")
      .emit();
    return DResp.create(response);
  }

  @Override
  public DResp connect(String id, String body) {
    HttpResponse response = this.docker.http("networks", id, "connect")
      .method(HttpMethod.POST)
      .raw(body, "application/json")
      .emit();
    return DResp.create(response);
  }

  @Override
  public DResp disconnect(String id, String body) {
    HttpResponse response = this.docker.http("networks", id, "disconnect")
      .method(HttpMethod.POST)
      .raw(body, "application/json")
      .emit();
    return DResp.create(response);
  }

  @Override
  public DResp prune(DQPFilter dqp) {
    Http http = this.docker.http("networks/prune")
      .method(HttpMethod.POST);
    if (dqp != null)
      http.para(dqp.dqr().http());
    HttpResponse response = http.emit();
    return DResp.create(response);
  }
}
