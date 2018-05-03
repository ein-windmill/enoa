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
package io.enoa.repeater.provider.netty.server;

import io.enoa.log.kit.LogKit;
import io.enoa.repeater.EoxAccessor;
import io.enoa.repeater.EoxConfig;
import io.enoa.repeater.factory.provider.EoxProviderFactory;
import io.enoa.repeater.factory.server.RepeaterServer;
import io.enoa.toolkit.sys.EnvKit;
import io.enoa.toolkit.thr.EoException;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;


class _Netty implements RepeaterServer {

  private EoxAccessor accessor;

  _Netty(EoxAccessor accessor) {
    this.accessor = accessor;
  }

  @Override
  public void listen(String hostname, int port, boolean ssly, EoxConfig config, EoxProviderFactory factor) {
    EnvKit.set("io.netty.tmpdir", config.tmp().toString());
    _NettyAppInitializer app = new _NettyAppInitializer(this.accessor, factor.ts(), factor.rule(), factor.errorRender(), config);
    this.bind(app, port);
  }


  private void bind(_NettyAppInitializer app, int port) {
    EventLoopGroup boss = new NioEventLoopGroup();
    EventLoopGroup worker = new NioEventLoopGroup();
    try {
      ServerBootstrap bootstrap = new ServerBootstrap();
      bootstrap.group(boss, worker);
      bootstrap.channel(NioServerSocketChannel.class);
      bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
      bootstrap.option(ChannelOption.TCP_NODELAY, true);
      bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
      bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
          ChannelPipeline p = socketChannel.pipeline();
          p.addLast(app);
        }
      });
      ChannelFuture f = bootstrap.bind(port).sync();
      if (f.isSuccess()) {
        LogKit.debug("Boot netty server success.");
      }
      // 关闭连接
      f.channel().closeFuture().sync();

    } catch (Exception e) {
      LogKit.error("Boot neety server fail: ", e.getMessage(), e);
      throw new EoException(e.getMessage(), e);
    } finally {
      boss.shutdownGracefully();
      worker.shutdownGracefully();
    }
  }

}
