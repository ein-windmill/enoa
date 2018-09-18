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
package io.enoa.repeater.provider.netty.server;

import io.enoa.repeater.EoxAccessor;
import io.enoa.repeater.EoxConfig;
import io.enoa.repeater.factory.error.EoxErrorRenderFactory;
import io.enoa.repeater.factory.name.EoxNameRuleFactory;
import io.enoa.repeater.factory.ts.RepeaterTranslateFactory;
import io.enoa.repeater.provider.netty.server.plus._RepeaterNettyRequest;
import io.enoa.toolkit.alg.UnitConvKit;
import io.enoa.toolkit.number.NumberKit;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.*;

class _NettyAppInitializer extends ChannelInitializer<SocketChannel> {

  private EoxAccessor accessor;
  private RepeaterTranslateFactory<_RepeaterNettyRequest, FullHttpResponse> ts;
  private EoxNameRuleFactory rule;
  private EoxConfig config;
  private EoxErrorRenderFactory errorRender;

  _NettyAppInitializer(EoxAccessor accessor, RepeaterTranslateFactory<_RepeaterNettyRequest, FullHttpResponse> ts,
                       EoxNameRuleFactory rule, EoxErrorRenderFactory errorRender, EoxConfig config) {
    this.accessor = accessor;
    this.ts = ts;
    this.rule = rule;
    this.config = config;
    this.errorRender = errorRender;
  }

  @Override
  protected void initChannel(SocketChannel socketChannel) throws Exception {
    final ChannelPipeline p = socketChannel.pipeline();
    p.addLast("codec", new HttpServerCodec());
//    p.addLast("aggregator", new HttpObjectAggregator(this.config.other().integer("max_content_length", 1024 * 1024)));
    p.addLast("aggregator", new HttpObjectAggregator(
      this.config.other().integer("max_content_length",
        NumberKit.integer(UnitConvKit.convert(this.config.maxUploadSize(), UnitConvKit.Unit.MB, UnitConvKit.Unit.BYTE))))
    );
    p.addLast("decoder", new HttpRequestDecoder());
    p.addLast("encoder", new HttpRequestEncoder());
    p.addLast("handler", new _NettyApp(this.accessor, this.ts, this.rule, this.errorRender, this.config));
  }

}
