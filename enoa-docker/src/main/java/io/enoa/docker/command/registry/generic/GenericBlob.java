package io.enoa.docker.command.registry.generic;

import io.enoa.docker.command.registry.origin.OriginRegistry;

public class GenericBlob {

  private OriginRegistry registry;

  public GenericBlob(OriginRegistry registry) {
    this.registry = registry;
  }
}
