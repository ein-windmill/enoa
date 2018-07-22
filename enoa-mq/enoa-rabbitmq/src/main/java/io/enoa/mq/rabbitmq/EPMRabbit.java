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
package io.enoa.mq.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EPMRabbit {

  private static class Holder {
    private static final EPMRabbit INSTANCE = new EPMRabbit();
  }

  public static EPMRabbit instance() {
    return Holder.INSTANCE;
  }

  private Map<String, EnoaRabbit> rabbitMap = new ConcurrentHashMap<>();

  private boolean existsName(String name) {
    return this.rabbitMap.containsKey(name);
  }

  public void install(RabbitConfig config) {
    if (this.existsName(config.name()))
//      throw new EoException(EnoaTipKit.message("eo.tip.rabbitmq.name_exists", config.name()));
      throw new ERabbitMQException("This RabbitMQ name is exists. => " + config.name());
    ConnectionFactory factory = new ConnectionFactory();
    try {
      Connection connection = factory.newConnection(config.executor(), config.addresses(), config.name());
      Channel channel = config.channelNumber() != null ?
        connection.createChannel(config.channelNumber()) :
        connection.createChannel();
      this.rabbitMap.put(config.name(), new EnoaRabbit(channel));
    } catch (Exception e) {
//      throw new EoException(EnoaTipKit.message("eo.tip.rabbitmq.open_fail", e.getMessage()), e);
      throw new ERabbitMQException(e.getMessage(), e);
    }
  }

  public EnoaRabbit rabbit(String name) {
    return this.rabbitMap.get(name);
  }

  public EnoaRabbit rabbit() {
    return this.rabbit("main");
  }


  public void close(String name) {
    EnoaRabbit rabbit = this.rabbit(name);
    if (rabbit == null)
      return;
    rabbit.close();
    try {
      rabbit.getConnection().close();
    } catch (Exception e) {
//      throw new EoException(EnoaTipKit.message("eo.tip.rabbitmq.close_fail"));
      throw new ERabbitMQException(e.getMessage(), e);
    }
    this.rabbitMap.remove(name);
  }

  public void close() {
    this.close("main");
  }

}
