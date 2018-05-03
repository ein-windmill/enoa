///*
// * Copyright (c) 2018, enoa (ein.windmill@outlook.com)
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *     http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package io.enoa.gateway.repeater;
//
//import io.enoa.gateway.GatewayHandler;
//import io.enoa.gateway.data.EnoaGatewayData;
//import io.enoa.log.EoLogFactory;
//import io.enoa.repeater.EoxAccessor;
//import io.enoa.repeater.RepeaterFactory;
//import io.enoa.repeater.factory.error.EoxErrorRenderFactory;
//import io.enoa.repeater.http.Header;
//
//import java.util.List;
//
//@Deprecated
//public class EnoaGatewayRepeater implements RepeaterFactory {
//
//  private GatewayHandler handler;
//  private EnoaGatewayData gateway;
//  private EoxErrorRenderFactory errorRenderFactory;
//  private EoLogFactory logFactory;
//  private boolean cros;
//  private List<Header> crosHeaders;
//
//  public EnoaGatewayRepeater(GatewayHandler handler, EnoaGatewayData gateway,
//                             EoxErrorRenderFactory errorRenderFactory,
//                             EoLogFactory logFactory, boolean cros, List<Header> crosHeaders) {
//    this.handler = handler;
//    this.gateway = gateway;
//    this.errorRenderFactory = errorRenderFactory;
//    this.logFactory = logFactory;
//    this.cros = cros;
//    this.crosHeaders = crosHeaders;
//  }
//
//  @Override
//  public EoxAccessor accessor() {
//    return new EnoaGatewayAccessor(this.handler, this.gateway, this.cros, this.crosHeaders);
//  }
//
//  @Override
//  public EoLogFactory log() {
//    return this.logFactory;
//  }
//
//  @Override
//  public EoxErrorRenderFactory errorRender() {
//    return this.errorRenderFactory;
//  }
//}
