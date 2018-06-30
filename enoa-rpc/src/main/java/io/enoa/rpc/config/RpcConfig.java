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
package io.enoa.rpc.config;

import io.enoa.log.provider.jdk.JdkLogProvider;
import io.enoa.rpc.parser.ResponseType;

public class RpcConfig {

  private static final RpcConfig INSTANCE = new RpcConfig();

  static {
    instance().handler().reg(ResponseType.JSON, new _DefaultJsonRpcParser());
    instance().handler().reg(ResponseType.BINARY, new _DefaultBinaryRpcParser());
    instance().factory().log(new JdkLogProvider());
  }

  private RpcConfig() {

  }

  public static RpcConfig instance() {
    return INSTANCE;
  }

  public ORpcFactory factory() {
    return ORpcFactory.instance();
  }

  public ORpcRegister register() {
    return ORpcRegister.instance();
  }

  public ORpcHandler handler() {
    return ORpcHandler.instance();
  }

}
