package io.enoa.example.gateay.auth;

import io.enoa.gateway.auth.GatewayAuth;
import io.enoa.gateway.thr.GatewayAuthException;
import io.enoa.log.kit.LogKit;
import io.enoa.repeater.http.Request;

public class ExampleAuth implements GatewayAuth {
  @Override
  public void auth(Request request) throws GatewayAuthException {
    LogKit.error("auth ========== ");
  }
}
