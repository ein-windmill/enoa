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
package io.enoa.yosart.plugin.redis;

import io.enoa.nosql.redis.EMgrRedis;
import io.enoa.nosql.redis.RedisConfig;
import io.enoa.nosql.redis.RedisSentinelConfig;
import io.enoa.serialization.EoSerializationFactory;
import io.enoa.yosart.YoPlugin;
import io.enoa.yosart.thr.OyPluginException;

public class RedisPlugin implements YoPlugin {

  private static final String DEF_NAME = "main";

  private RedisConfig config;
  private RedisSentinelConfig sentinel;
  private EoSerializationFactory serialization;

  public RedisPlugin(RedisConfig config) {
    this.config = config;
  }

  public RedisPlugin(RedisSentinelConfig sentinel) {
    this.sentinel = sentinel;
  }

  public RedisPlugin(String name, String host, int port, String passwd, EoSerializationFactory serialization) {
    this(new RedisConfig.Builder()
      .name(name)
      .host(host)
      .port(port)
      .passwd(passwd)
      .build());
    this.serialization = serialization;
  }

  public RedisPlugin(String name, String host, int port, EoSerializationFactory serialization) {
    this(name, host, port, null, serialization);
  }

  public RedisPlugin(String name, String host, EoSerializationFactory serialization) {
    this(name, host, 6379, null, serialization);
  }

  public RedisPlugin(String name, String host, int port, String passwd) {
    this(name, host, port, passwd, null);
  }

  public RedisPlugin(String name, String host, int port) {
    this(name, host, port, null, null);
  }

  public RedisPlugin(String name, String host) {
    this(name, host, 6379, null, null);
  }

  public RedisPlugin(String host, int port, String passwd, EoSerializationFactory serialization) {
    this(DEF_NAME, host, port, passwd, serialization);
  }

  public RedisPlugin(String host, int port, EoSerializationFactory serialization) {
    this(DEF_NAME, host, port, null, serialization);
  }

  public RedisPlugin(String host, EoSerializationFactory serialization) {
    this(DEF_NAME, host, 6379, null, serialization);
  }

  public RedisPlugin(String host, int port, String passwd) {
    this(DEF_NAME, host, port, passwd, null);
  }

  public RedisPlugin(String host, int port) {
    this(DEF_NAME, host, port, null, null);
  }

  public RedisPlugin(String host) {
    this(DEF_NAME, host, 6379, null, null);
  }


  @Override
  public String name() {
    return "RedisPlugin";
  }

  @Override
  public String version() {
    return "1";
  }

  @Override
  public String description() {
    return "Redis operate";
  }

  @Override
  public boolean start() throws OyPluginException {
    if (this.config != null) {
      EMgrRedis.start(this.config, this.serialization);
      return true;
    }
    if (this.sentinel != null) {
      EMgrRedis.start(this.sentinel, this.serialization);
      return true;
    }
    return false;
  }

  @Override
  public boolean stop() throws OyPluginException {
    String name = this.redisName();
    if (name == null)
      return false;

    EMgrRedis.stop(name);
    return true;
  }

  public String redisName() {
    String name = null;
    if (this.config != null)
      name = this.config.name();
    if (this.sentinel != null)
      name = this.sentinel.name();
    return name;
  }

//  public EnoaRedis redis() {
//    if (!this.started)
//      throw new OyExtException(EnoaTipKit.message("eo.tip.yosart.plugin.redis_no_start"));
//    return Redis.use(this.redisName());
//  }
}
