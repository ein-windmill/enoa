package io.enoa.docker.command.registry.generic;

import io.enoa.docker.command.registry.origin.OriginRegistry;

public class GenericUpload {

  private OriginRegistry registry;

  public GenericUpload(OriginRegistry registry) {
    this.registry = registry;
  }
}
