/*
 * Copyright (c) 2018, enoa (ein.windmill@outlook.com)
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
package io.enoa.gateway;

import io.enoa.gateway.auth.GatewayAuth;
import io.enoa.gateway.data.EnoaGatewayAuthData;
import io.enoa.gateway.data.EnoaGatewayData;
import io.enoa.gateway.data.GatewayMapping;
import io.enoa.gateway.handler.EnoaGatewayHandler;
import io.enoa.gateway.repeater.EnoaGatewayAccessor;
import io.enoa.gateway.repeater.EnoaGatewayErrorRender;
import io.enoa.log.EoLogFactory;
import io.enoa.log.provider.jdk.JdkLogProvider;
import io.enoa.repeater.EoxConfig;
import io.enoa.repeater.Repeater;
import io.enoa.repeater.factory.server.RepeaterServerFactory;
import io.enoa.repeater.http.Header;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.toolkit.text.TextKit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class _EnoaGateway implements Gateway {

  private RepeaterServerFactory provider;
  private GatewayErrorRenderFactory capture;
  private boolean ssl;
  private EoxConfig eoxconfig;
  private List<GatewayMapping> mappings;
  private List<String> noauths;
  private List<EnoaGatewayAuthData> auths;
  private EoLogFactory log;
  private boolean cros;
  private List<Header> crosHeaders;

  _EnoaGateway() {
    this.ssl = Boolean.FALSE;
    this.cros = Boolean.FALSE;
    this.log = new JdkLogProvider();
  }

  private void printlog() {
    if (!this.eoxconfig.info())
      return;
    this.mappings.forEach(m -> {
      System.out.println(TextKit.union("LOAD -> SOURCE `", m.source(), "` TO DEST `", m.dest(), "`."));
    });
  }


  @Override
  public Gateway provider(RepeaterServerFactory provider) {
    this.provider = provider;
    return this;
  }

  @Override
  public Gateway log(EoLogFactory logFactory) {
    this.log = logFactory;
    return this;
  }

  @Override
  public Gateway capture(GatewayErrorRenderFactory capture) {
    this.capture = capture;
    return this;
  }

  @Override
  public Gateway ssl(boolean ssl) {
    this.ssl = ssl;
    return this;
  }

  @Override
  public Gateway cros() {
    this.cros = Boolean.TRUE;
    return this;
  }

  @Override
  public Gateway cros(Header header) {
    if (header == null)
      throw new IllegalArgumentException("CROS header can not be null.");
    this.cros();
    if (this.crosHeaders == null)
      this.crosHeaders = new ArrayList<>();
    this.crosHeaders.add(header);
    return this;
  }

  @Override
  public Gateway cros(Header... headers) {
    if (headers == null)
      throw new IllegalArgumentException("CROS header can not be null.");
    for (Header header : headers)
      this.cros(header);
    return this;
  }

  @Override
  public Gateway cros(Collection<Header> headers) {
    if (headers == null)
      throw new IllegalArgumentException("CROS header can not be null.");
    headers.forEach(this::cros);
    return this;
  }

  @Override
  public Gateway config(EoxConfig config) {
    this.eoxconfig = config;
    return this;
  }

  @Override
  public Gateway auth(GatewayAuth auth) {
    return this.auth(null, auth);
  }

  @Override
  public Gateway auth(String uri, GatewayAuth auth) {
    if (auth == null)
      throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.gateway.auth_null"));
    if (this.auths == null)
      this.auths = new ArrayList<>();
    this.auths.add(new EnoaGatewayAuthData(uri, auth));
    return this;
  }

  @Override
  public Gateway noauth(String uri) {
    if (uri == null)
      throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.gateway.noauth_uri_null"));
    if (this.noauths == null)
      this.noauths = new ArrayList<>();
    this.noauths.add(uri);
    return this;
  }

  @Override
  public Gateway noauth(String... uris) {
    if (uris == null)
      throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.gateway.noauth_uri_null"));
    for (String uri : uris)
      this.noauth(uri);
    return this;
  }

  @Override
  public Gateway noauth(Collection<String> uris) {
    if (uris == null)
      throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.gateway.noauth_uri_null"));
    uris.forEach(this::noauth);
    return this;
  }

  @Override
  public Gateway mapping(String name, String source, String dest, GatewayAuth auth) {
    return this.mapping(new GatewayMapping(name, source, dest, auth));
  }

  @Override
  public Gateway mapping(String source, String dest, GatewayAuth auth) {
    return this.mapping(new GatewayMapping(source, dest, auth));
  }

  @Override
  public Gateway mapping(String name, String source, String dest) {
    return this.mapping(new GatewayMapping(name, source, dest));
  }

  @Override
  public Gateway mapping(String source, String dest) {
    return this.mapping(new GatewayMapping(source, dest));
  }

  @Override
  public Gateway mapping(GatewayMapping mapping) {
    if (mapping == null)
      throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.gateway.mapping_null"));
    if (this.mappings == null)
      this.mappings = new ArrayList<>();

    this.mappings.add(mapping);
    return this;
  }

  @Override
  public Gateway mapping(GatewayMapping[] mappings) {
    if (CollectionKit.isEmpty(mappings))
      throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.gateway.mapping_null"));
    for (GatewayMapping mapping : mappings) {
      this.mapping(mapping);
    }
    return this;
  }

  @Override
  public Gateway mapping(Collection<GatewayMapping> mappings) {
    if (CollectionKit.isEmpty(mappings))
      throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.gateway.mapping_null"));
    mappings.forEach(this::mapping);
    return this;
  }

//  @Override
//  public Gateway skip(String uri) {
//    return this;
//  }


  @Override
  public void listen(String hostname, int port) {
    if (this.provider == null)
      throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.gateway.no_provider"));

    this.eoxconfig = this.eoxconfig == null ? EoxConfig.def() : this.eoxconfig;
    this.capture = this.capture == null ? new EnoaGatewayErrorRender() : this.capture;
    this.printlog();

    GatewayHandler handler = new EnoaGatewayHandler(this.eoxconfig);
    EnoaGatewayData data = new EnoaGatewayData(
      this.mappings == null ? CollectionKit.emptyArray(GatewayMapping.class) : this.mappings.toArray(new GatewayMapping[this.mappings.size()]),
      this.noauths == null ? CollectionKit.emptyArray(String.class) : this.noauths.toArray(new String[this.noauths.size()]),
      this.auths == null ? CollectionKit.emptyArray(EnoaGatewayAuthData.class) : this.auths.toArray(new EnoaGatewayAuthData[this.auths.size()]),
      this.capture
    );


    EnoaGatewayAccessor accessor = new EnoaGatewayAccessor(handler, data, this.cros, this.crosHeaders);
    Repeater.createServer(this.provider)
      .accessor(accessor)
      .log(this.log)
      .capture(this.capture)
      .ssl(this.ssl)
      .config(this.eoxconfig)
      .listen(hostname, port);
  }

  @Override
  public void listen(int port) {
    this.listen("localhost", port);
  }

  @Override
  public void listen() {
    this.listen(9103);
  }

}
