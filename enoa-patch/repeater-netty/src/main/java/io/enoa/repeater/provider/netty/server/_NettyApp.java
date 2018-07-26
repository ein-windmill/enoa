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
import io.enoa.repeater.http.HttpStatus;
import io.enoa.repeater.http.Request;
import io.enoa.repeater.http.Response;
import io.enoa.repeater.provider.netty.server.plus._RepeaterNettyRequest;
import io.enoa.repeater.provider.netty.server.plus._RepeaterNettyRequestImpl;
import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.toolkit.http.UriKit;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.ReferenceCountUtil;

class _NettyApp extends ChannelInboundHandlerAdapter {


  //
  private EoxAccessor accessor;
  private RepeaterTranslateFactory<_RepeaterNettyRequest, FullHttpResponse> ts;
  private EoxNameRuleFactory rule;
  private EoxConfig config;
  private EoxErrorRenderFactory errorRender;


  _NettyApp(EoxAccessor accessor, RepeaterTranslateFactory<_RepeaterNettyRequest, FullHttpResponse> ts,
            EoxNameRuleFactory rule, EoxErrorRenderFactory errorRender, EoxConfig config) {
    this.accessor = accessor;
    this.ts = ts;
    this.config = config;
    this.rule = rule;
    this.errorRender = errorRender;
  }


  private void end(final ChannelHandlerContext ctx, final HttpRequest msg, FullHttpResponse response) {
    if (!HttpUtil.isKeepAlive(msg)) {
      ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    } else {
      response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
      ctx.writeAndFlush(response);
    }
    ReferenceCountUtil.release(msg);
    ctx.close();
  }

  @Override
  public void channelReadComplete(ChannelHandlerContext ctx) {
    ctx.flush();
  }

  //
  @Override
  public void channelRead(final ChannelHandlerContext ctx, final Object msg) throws Exception {
    if (!(msg instanceof HttpRequest)) {
      ReferenceCountUtil.release(msg);
      return;
    }

    HttpRequest request = (HttpRequest) msg;

    /*
    context support
     */
    if (!"/".equals(this.config.context())) {
      String uri = request.uri();
      String[] uris = uri.split("\\?");
      uri = uris[0];
      uri = UriKit.correct(uri).concat("/");

      String context = this.config.context().concat("/");

      /*
      /test/a/
      /test/abc
      /testx/abc
      /test/a
       */

      if (!uri.startsWith(context)) {
        Response resp = this.errorRender.renderError(HttpStatus.NOT_FOUND, this.config.debug() ?
          EnoaTipKit.message("eo.tip.repeater.context_no_match", this.config.context()) : null);
        ByteBuf body = ctx.alloc().buffer();
        body.writeBytes(resp.body().bytes());
        FullHttpResponse response = new DefaultFullHttpResponse(
          HttpVersion.HTTP_1_1, HttpResponseStatus.valueOf(resp.status().code()), body);
        this.ts.response(this.config, response, resp);
        this.end(ctx, request, response);
        return;
      }
    }

    try {
      FullHttpRequest fullRequest = (FullHttpRequest) msg;

      _RepeaterNettyRequest nreq = new _RepeaterNettyRequestImpl(fullRequest) {
        @Override
        public String context() {
          return config.context();
        }
      };
      Request req = this.ts.request(this.config, this.rule, nreq);
      Response resp = this.accessor.access(req);

      ByteBuf body = ctx.alloc().buffer();
      body.writeBytes(resp.body().bytes());

      FullHttpResponse response = new DefaultFullHttpResponse(
        HttpVersion.HTTP_1_1, HttpResponseStatus.valueOf(resp.status().code()), body);

      this.ts.response(this.config, response, resp);

      this.end(ctx, nreq, response);
    } catch (Exception e) {
      Response resp = this.errorRender.renderError(HttpStatus.NOT_FOUND, e);
      ByteBuf body = ctx.alloc().buffer();
      body.writeBytes(resp.body().bytes());
      FullHttpResponse response = new DefaultFullHttpResponse(
        HttpVersion.HTTP_1_1, HttpResponseStatus.valueOf(resp.status().code()), body);
      this.ts.response(this.config, response, resp);
      this.end(ctx, request, response);
    }

  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    cause.printStackTrace();
    ctx.close();
  }

}
