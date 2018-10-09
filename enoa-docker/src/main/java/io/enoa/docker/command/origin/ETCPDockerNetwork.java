package io.enoa.docker.command.origin;

public class ETCPDockerNetwork implements EOriginDockerNetwork {

  private EnoaTCPDocker docker;

  public ETCPDockerNetwork(EnoaTCPDocker docker) {
    this.docker = docker;
  }
}
