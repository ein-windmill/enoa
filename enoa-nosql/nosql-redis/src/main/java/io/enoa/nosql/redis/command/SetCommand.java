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
package io.enoa.nosql.redis.command;

import io.enoa.nosql.redis.EnoaRedisConvert;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;
import redis.clients.util.SafeEncoder;

import java.util.List;
import java.util.Set;

interface SetCommand extends _Runner {

  default Long sadd(String key, Object... member) {
    return this.run((jedis, serializer) -> jedis.sadd(SafeEncoder.encode(key), EnoaRedisConvert.with(serializer).toBytesValues(member)));
  }

  default Long scard(String key) {
    return this.run((jedis, serializer) -> jedis.scard(key));
  }

  default <T> Set<T> sdiff(String... keys) {
    return this.run((jedis, serializer) ->
      EnoaRedisConvert.with(serializer).convertSet(jedis.sdiff(EnoaRedisConvert.with(serializer).toBytesKeys(keys))));
  }

  default Long sdiffstore(String dstkey, String... keys) {
    return this.run((jedis, serializer) -> jedis.sdiffstore(dstkey, keys));
  }

  default Boolean sismember(String key, Object member) {
    return this.run((jedis, serializer) -> jedis.sismember(SafeEncoder.encode(key), serializer.serialize(member)));
  }

  default <T> Set<T> smembers(String key) {
    return this.run((jedis, serializer) -> EnoaRedisConvert.with(serializer).convertSet(jedis.smembers(SafeEncoder.encode(key))));
  }

  default Long smove(String srckey, String dstkey, Object member) {
    return this.run((jedis, serializer) -> jedis.smove(SafeEncoder.encode(srckey), SafeEncoder.encode(dstkey), serializer.serialize(member)));
  }

  default <T> T spop(String key) {
    return this.run((jedis, serializer) -> serializer.reduction(jedis.spop(SafeEncoder.encode(key))));
  }

  default <T> Set<T> spop(String key, long count) {
    return this.run((jedis, serializer) -> EnoaRedisConvert.with(serializer).convertSet(jedis.spop(SafeEncoder.encode(key), count)));
  }

  default <T> T srandmember(String key) {
    return this.run((jedis, serializer) -> serializer.reduction(jedis.srandmember(SafeEncoder.encode(key))));
  }

  default <T> List<T> srandmember(String key, int count) {
    return this.run((jedis, serializer) -> EnoaRedisConvert.with(serializer).convertList(jedis.srandmember(SafeEncoder.encode(key), count)));
  }

  default Long srem(String key, Object... member) {
    return this.run((jedis, serializer) -> jedis.srem(SafeEncoder.encode(key), EnoaRedisConvert.with(serializer).toBytesValues(member)));
  }

  default <T> Set<T> sunion(String... keys) {
    return this.run((jedis, serializer) ->
      EnoaRedisConvert.with(serializer).convertSet(jedis.sunion(EnoaRedisConvert.with(serializer).toBytesKeys(keys))));
  }

  default Long sunionstore(String dstkey, String... keys) {
    return this.run((jedis, serializer) -> jedis.sunionstore(dstkey, keys));
  }

  default <T> ScanResult<T> sscan(String key, String cursor) {
    return this.run((jedis, serializer) ->
      EnoaRedisConvert.with(serializer).convertScan(jedis.sscan(SafeEncoder.encode(key), SafeEncoder.encode(cursor))));
  }

  default <T> ScanResult<T> sscan(String key, String cursor, ScanParams params) {
    return this.run((jedis, serializer) ->
      EnoaRedisConvert.with(serializer).convertScan(jedis.sscan(SafeEncoder.encode(key), SafeEncoder.encode(cursor), params)));
  }

}
