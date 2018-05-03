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
package io.enoa.nosql.redis;

import io.enoa.serialization.EoSerializationFactory;
import io.enoa.serialization.provider.jdk.JdkSerializeProvider;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.Protocol;
import redis.clients.util.Pool;

import java.util.HashMap;
import java.util.Map;

public class EnoaRedisMgr {


  private static EoSerializationFactory DEF_SERIALIZATION = new JdkSerializeProvider();

  static Map<String, EnoaRedis> POOL = new HashMap<>();

  public static void start(String name, String host, int port, String passwd, EoSerializationFactory serialization) {
    RedisConfig config = new RedisConfig.Builder()
      .name(name)
      .host(host)
      .port(port)
      .passwd(passwd)
      .build();
    start(config, serialization);
  }

  public static void start(String name, String host, int port, EoSerializationFactory serialization) {
    start(name, host, port, null, serialization);
  }

  public static void start(String host, int port, String passwd, EoSerializationFactory serialization) {
    RedisConfig config = new RedisConfig.Builder()
      .host(host)
      .port(port)
      .passwd(passwd)
      .build();
    start(config, serialization);
  }

  public static void start(String host, int port, EoSerializationFactory serialization) {
    start(host, port, null, serialization);
  }

  public static void start(RedisConfig config, EoSerializationFactory serialization) {
    if (POOL.keySet().stream().anyMatch(config.name()::equals))
      throw new IllegalArgumentException("This name is already start. => " + config.name());

    Pool<Jedis> pool = new JedisPool(
      config.poolConfig() == null ? new GenericObjectPoolConfig() : config.poolConfig(),
      config.host(),
      config.port(),
      config.connectionTimeout(),
      config.soTimeout(),
      config.passwd(),
      config.database() == null ? Protocol.DEFAULT_DATABASE : config.database(),
      config.name(),
      config.ssl(),
      config.sslSocketFactory(),
      config.sslParameters(),
      config.hostnameVerifier()
    );
    POOL.put(config.name(), new EnoaRedis(pool, serialization == null ? DEF_SERIALIZATION : serialization));
  }

  public static void start(RedisSentinelConfig config, EoSerializationFactory serialization) {
    if (POOL.keySet().stream().anyMatch(config.name()::equals))
      throw new IllegalArgumentException("This name is already start. => " + config.name());

    Pool<Jedis> pool = new JedisSentinelPool(
      config.masterName(),
      config.sentinels(),
      config.poolConfig() == null ? new GenericObjectPoolConfig() : config.poolConfig(),
      config.connectionTimeout(),
      config.soTimeout(),
      config.passwd(),
      config.database(),
      config.name()
    );
    POOL.put(config.name(), new EnoaRedis(pool, serialization == null ? DEF_SERIALIZATION : serialization));
  }


  public static void start(String name, String host, int port, String passwd) {
    start(name, host, port, passwd, DEF_SERIALIZATION);
  }

  public static void start(String name, String host, int port) {
    start(name, host, port, null, DEF_SERIALIZATION);
  }

  public static void start(String host, int port, String passwd) {
    start(host, port, passwd, DEF_SERIALIZATION);
  }

  public static void start(String host, int port) {
    start(host, port, null, DEF_SERIALIZATION);
  }

  public static void start(RedisConfig config) {
    start(config, DEF_SERIALIZATION);
  }

  public static void start(RedisSentinelConfig config) {
    start(config, DEF_SERIALIZATION);
  }

  public static void stop(String name) {
    EnoaRedis ers = POOL.get(name);
    if (ers == null)
      return;

    ers.close();
  }

}
