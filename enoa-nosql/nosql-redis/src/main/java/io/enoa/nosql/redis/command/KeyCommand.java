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

import io.enoa.nosql.redis.EnoaRedisConvert;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.SortingParams;
import redis.clients.util.SafeEncoder;

import java.util.List;
import java.util.Set;

interface KeyCommand extends _Runner {

  default Long del(String key) {
    return this.run((jedis, serializer) -> jedis.del(key));
  }

  default Long del(String... keys) {
    return this.run((jedis, serializer) -> jedis.del(keys));
  }

  default byte[] dump(String key) {
    return this.run((jedis, serializer) -> jedis.dump(key));
  }

  default Boolean exists(String key) {
    return this.run((jedis, serializer) -> jedis.exists(key));
  }

  default Long exists(String... keys) {
    return this.run((jedis, serializer) -> jedis.exists(keys));
  }

  default Long expire(String key, int seconds) {
    return this.run((jedis, serializer) -> jedis.expire(key, seconds));
  }

  default Long expireat(String key, long unixTime) {
    return this.run((jedis, serializer) -> jedis.expireAt(key, unixTime));
  }

  default Set<String> keys(String pattern) {
    return this.run((jedis, serializer) -> jedis.keys(pattern));
  }

  default String migrate(String host, int port, String key, int destinationDb, int timeout) {
    return this.run((jedis, serializer) -> jedis.migrate(host, port, key, destinationDb, timeout));
  }

  default Long move(String key, int dbIndex) {
    return this.run((jedis, serializer) -> jedis.move(key, dbIndex));
  }

  default String objectencoding(String string) {
    return this.run((jedis, serializer) -> jedis.objectEncoding(string));
  }

  default Long objectidletime(String string) {
    return this.run((jedis, serializer) -> jedis.objectIdletime(string));
  }

  default Long objectrefcount(String string) {
    return this.run((jedis, serializer) -> jedis.objectRefcount(string));
  }

  default Long persist(String key) {
    return this.run((jedis, serializer) -> jedis.persist(key));
  }

  default Long pexpire(String key, long milliseconds) {
    return this.run((jedis, serializer) -> jedis.pexpire(key, milliseconds));
  }

  default Long pexpireat(String key, long millisecondsTimestamp) {
    return this.run((jedis, serializer) -> jedis.pexpireAt(key, millisecondsTimestamp));
  }

  default Long pttl(String key) {
    return this.run((jedis, serializer) -> jedis.pttl(key));
  }

  default String randomkey() {
    return this.run((jedis, serializer) -> jedis.randomKey());
  }

  default String rename(String oldkey, String newkey) {
    return this.run((jedis, serializer) -> jedis.rename(oldkey, newkey));
  }

  default Long renamenx(String oldkey, String newkey) {
    return this.run((jedis, serializer) -> jedis.renamenx(oldkey, newkey));
  }

  default String restore(String key, int ttl, byte[] serializedValue) {
    return this.run((jedis, serializer) -> jedis.restore(key, ttl, serializedValue));
  }

  default <T> List<T> sort(String key) {
    return this.run((jedis, serializer) -> EnoaRedisConvert.with(serializer).convertList(jedis.sort(SafeEncoder.encode(key))));
  }

  default <T> List<T> sort(String key, SortingParams sortingParameters) {
    return this.run((jedis, serializer) ->
      EnoaRedisConvert.with(serializer).<T>convertList(jedis.sort(SafeEncoder.encode(key), sortingParameters)));
  }

  default Long sort(String key, SortingParams sortingParameters, String dstkey) {
    return this.run((jedis, serializer) -> jedis.sort(key, sortingParameters, dstkey));
  }

  default Long sort(String key, String dstkey) {
    return this.run((jedis, serializer) -> jedis.sort(key, dstkey));
  }

  default Long ttl(String key) {
    return this.run((jedis, serializer) -> jedis.ttl(key));
  }

  default String type(String key) {
    return this.run((jedis, serializer) -> jedis.type(key));
  }

  default <T> ScanResult<T> scan(String cursor) {
    return this.run((jedis, serializer) ->
      EnoaRedisConvert.with(serializer).convertScan(jedis.scan(SafeEncoder.encode(cursor))));
  }

  default <T> ScanResult<T> scan(String cursor, ScanParams params) {
    return this.run((jedis, serializer) ->
      EnoaRedisConvert.with(serializer).convertScan(jedis.scan(SafeEncoder.encode(cursor), params)));
  }
}
