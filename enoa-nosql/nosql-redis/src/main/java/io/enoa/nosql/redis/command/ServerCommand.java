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
package io.enoa.nosql.redis.command;

import redis.clients.jedis.DebugParams;
import redis.clients.jedis.JedisMonitor;
import redis.clients.util.Slowlog;

import java.util.List;

interface ServerCommand extends _Runner {


  default String bgrewriteaof() {
    return this.run((jedis, serializer) -> jedis.bgrewriteaof());
  }

  default String bgsave() {
    return this.run((jedis, serializer) -> jedis.bgsave());
  }

  default String clientgetname() {
    return this.run((jedis, serializer) -> jedis.clientGetname());
  }

  default String clientkill(final String client) {
    return this.run((jedis, serializer) -> jedis.clientKill(client));
  }

  default String clientlist() {
    return this.run((jedis, serializer) -> jedis.clientList());
  }

  default String clientsetname(final String name) {
    return this.run((jedis, serializer) -> jedis.clientSetname(name));
  }

  default List<String> configget(String pattern) {
    return this.run((jedis, serializer) -> jedis.configGet(pattern));
  }

  default String configresetstat() {
    return this.run((jedis, serializer) -> jedis.configResetStat());
  }

  default String configset(String parameter, String value) {
    return this.run((jedis, serializer) -> jedis.configSet(parameter, value));
  }

  default Long dbsize() {
    return this.run((jedis, serializer) -> jedis.dbSize());
  }

  default String debug(final DebugParams params) {
    return this.run((jedis, serializer) -> jedis.debug(params));
  }

  default String flushall() {
    return this.run((jedis, serializer) -> jedis.flushAll());
  }

  default String flushdb() {
    return this.run((jedis, serializer) -> jedis.flushDB());
  }

  default String info() {
    return this.run((jedis, serializer) -> jedis.info());
  }

  default Long lastsave() {
    return this.run((jedis, serializer) -> jedis.lastsave());
  }

  default void monitor(final JedisMonitor jedisMonitor) {
    this.run((jedis, serializer) -> {
      jedis.monitor(jedisMonitor);
      return this;
    });
  }

  default String save() {
    return this.run((jedis, serializer) -> jedis.save());
  }

  default String shutdown() {
    return this.run((jedis, serializer) -> jedis.shutdown());
  }

  default String slaveof(final String host, final int port) {
    return this.run((jedis, serializer) -> jedis.slaveof(host, port));
  }

  default List<Slowlog> slowlogget() {
    return this.run((jedis, serializer) -> jedis.slowlogGet());
  }

  default Long slowloglen() {
    return this.run((jedis, serializer) -> jedis.slowlogLen());
  }

  default String slowlogreset() {
    return this.run((jedis, serializer) -> jedis.slowlogReset());
  }

  default void sync() {
    this.run((jedis, serializer) -> {
      jedis.sync();
      return this;
    });
  }

  default List<String> time() {
    return this.run((jedis, serializer) -> jedis.time());
  }
}
