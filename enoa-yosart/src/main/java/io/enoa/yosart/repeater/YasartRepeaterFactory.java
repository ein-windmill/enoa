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
//package io.enoa.yosart.repeater;
//
//import io.enoa.log.EoLogFactory;
//import io.enoa.log.provider.jdk.JdkLogProvider;
//import io.enoa.repeater.EoxAccessor;
//import io.enoa.repeater.RepeaterFactory;
//import io.enoa.repeater.factory.error.EoxErrorRenderFactory;
//import io.enoa.repeater.factory.name.EoxNameRuleFactory;
//import io.enoa.repeater.name.OriginNameRule;
//
///**
// * @Deprecated 不再继续使用
// */
//@Deprecated
//public class YasartRepeaterFactory implements RepeaterFactory {
//
//  private EoxAccessor accessor;
//  private EoLogFactory logFactory;
//  private EoxNameRuleFactory ruleFactory;
//  private EoxErrorRenderFactory errorRenderFactory;
//
//  public YasartRepeaterFactory() {
//    this.accessor = new OysartAccessor();
//    this.logFactory = new JdkLogProvider();
//    this.ruleFactory = new OriginNameRule();
//    this.errorRenderFactory = new OysartRepeaterErrorRender();
//  }
//
//  public YasartRepeaterFactory(YasartRepeaterFactory repeater) {
//    this.accessor = repeater.accessor;
//    this.logFactory = repeater.logFactory;
//    this.ruleFactory = repeater.ruleFactory;
//    this.errorRenderFactory = repeater.errorRenderFactory;
//  }
//
//  public YasartRepeaterFactory(EoxAccessor accessor, EoLogFactory logFactory, EoxNameRuleFactory ruleFactory, EoxErrorRenderFactory errorRenderFactory) {
//    this.accessor = accessor;
//    this.logFactory = logFactory;
//    this.ruleFactory = ruleFactory;
//    this.errorRenderFactory = errorRenderFactory;
//  }
//
//  @Override
//  public EoxAccessor accessor() {
//    return this.accessor;
//  }
//
//  @Override
//  public EoLogFactory log() {
//    return this.logFactory;
//  }
//
//  @Override
//  public EoxNameRuleFactory rule() {
//    return this.ruleFactory;
//  }
//
//  @Override
//  public EoxErrorRenderFactory errorRender() {
//    return this.errorRenderFactory;
//  }
//}
