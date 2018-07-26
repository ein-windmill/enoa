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
package io.enoa.repeater.provider.jetty.server;

import io.enoa.repeater.EoxAccessor;
import io.enoa.repeater.EoxConfig;
import io.enoa.repeater.factory.provider.EoxProviderFactory;
import io.enoa.repeater.factory.server.RepeaterServer;
import io.enoa.toolkit.thr.EoException;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandler;

class _Jetty implements RepeaterServer {

  private EoxAccessor accessor;

  _Jetty(EoxAccessor accessor) {
    this.accessor = accessor;
  }

  @Override
  public void listen(String hostname, int port, boolean ssl, EoxConfig config, EoxProviderFactory factory) {
    // 创建Server对象，并绑定端口
    Server server = new Server();
    ServerConnector connector = new ServerConnector(server);
    connector.setPort(port);
    server.setConnectors(new Connector[]{connector});

    ContextHandler context0 = new ContextHandler();
    context0.setContextPath("/");
//    context0.setVirtualHosts(new String[]{hostname});
    context0.setHandler(new _JettyContext(this.accessor, factory.ts(), factory.rule(), factory.errorRender(), config));
    server.setHandler(context0);

    try {
      // 启动
      server.start();

//      // 打印dump时的信息
//      System.out.println(server.dump());

//      // join当前线程
//      server.join();
    } catch (Exception e) {
      throw new EoException(e);
    }

  }
}
