package io.enoa.docker.command.origin;

public class ETCPDockerNetworks implements EOriginDockerNetworks {

  private EnoaTCPDocker docker;

  public ETCPDockerNetworks(EnoaTCPDocker docker) {
    this.docker = docker;
  }
}
