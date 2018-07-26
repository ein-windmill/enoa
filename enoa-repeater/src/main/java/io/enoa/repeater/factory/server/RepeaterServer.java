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
package io.enoa.repeater.factory.server;

import io.enoa.repeater.EoxConfig;
import io.enoa.repeater.factory.provider.EoxProviderFactory;

/**
 * web server
 */
public interface RepeaterServer {

  /**
   * 服务监听
   *
   * @param hostname 监听主机
   * @param port     端口
   * @param ssl      开启 ssl
   * @param config   配置
   * @param factory  服务提供者数据工厂
   */
  void listen(String hostname, int port, boolean ssl, EoxConfig config, EoxProviderFactory factory);

}
