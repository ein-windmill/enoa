package io.enoa.example.gateay;

import io.enoa.example.gateay.auth.ExampleAuth;
import io.enoa.gateway.Gateway;
import io.enoa.repeater.provider.nanohttpd.server.NanoHTTPDProvider;

public class GatewayApp {


  public static void main(String[] args) {
    Gateway.createServer()
      .provider(new NanoHTTPDProvider())
      .auth(new ExampleAuth())
      .mapping("/", "http://localhost:2000")
      .mapping("/account", "http://localhost;2001")
      .mapping("/admin", "http://localhost:20002")
      .listen(9103);
  }

}
