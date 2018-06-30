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

public class EPMRedis {

  private EPMRedis() {

  }

  private static class Holder {
    private static final EPMRedis INSTANCE = new EPMRedis();
  }

  static EPMRedis instance() {
    return Holder.INSTANCE;
  }

  private EoSerializationFactory defSerialization = new JdkSerializeProvider();

  private Map<String, EnoaRedis> pool = new HashMap<>();

  public void install(String name, String host, int port, String passwd, EoSerializationFactory serialization) {
    RedisConfig config = new RedisConfig.Builder()
      .name(name)
      .host(host)
      .port(port)
      .passwd(passwd)
      .build();
    this.install(config, serialization);
  }

  public void install(String name, String host, int port, EoSerializationFactory serialization) {
    this.install(name, host, port, null, serialization);
  }

  public void install(String host, int port, String passwd, EoSerializationFactory serialization) {
    RedisConfig config = new RedisConfig.Builder()
      .host(host)
      .port(port)
      .passwd(passwd)
      .build();
    this.install(config, serialization);
  }

  public void install(String host, int port, EoSerializationFactory serialization) {
    this.install(host, port, null, serialization);
  }

  public void install(RedisConfig config, EoSerializationFactory serialization) {
    if (this.pool.keySet().stream().anyMatch(config.name()::equals))
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
    this.pool.put(config.name(), new EnoaRedis(pool, serialization == null ? defSerialization : serialization));
  }

  public void install(RedisSentinelConfig config, EoSerializationFactory serialization) {
    if (this.pool.keySet().stream().anyMatch(config.name()::equals))
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
    this.pool.put(config.name(), new EnoaRedis(pool, serialization == null ? defSerialization : serialization));
  }

  public void install(String name, String host, int port, String passwd) {
    this.install(name, host, port, passwd, defSerialization);
  }

  public void install(String name, String host, int port) {
    this.install(name, host, port, null, defSerialization);
  }

  public void install(String host, int port, String passwd) {
    this.install(host, port, passwd, defSerialization);
  }

  public void install(String host, int port) {
    this.install(host, port, null, defSerialization);
  }

  public void install(RedisConfig config) {
    this.install(config, defSerialization);
  }

  public void install(RedisSentinelConfig config) {
    this.install(config, defSerialization);
  }

  public void uninstall(String name) {
    EnoaRedis ers = this.pool.get(name);
    if (ers == null)
      return;

    ers.close();
    this.pool.remove(name);
  }

  public EnoaRedis redis(String name) {
    return this.pool.get(name);
  }


}
