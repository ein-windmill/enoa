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
import redis.clients.jedis.Tuple;
import redis.clients.jedis.ZParams;
import redis.clients.jedis.params.sortedset.ZAddParams;
import redis.clients.jedis.params.sortedset.ZIncrByParams;
import redis.clients.util.SafeEncoder;

import java.util.Map;
import java.util.Set;

interface SortSetCommand extends _Runner {

  default Long zadd(String key, double score, Object member) {
    return this.run((jedis, serializer) -> jedis.zadd(SafeEncoder.encode(key), score, serializer.serialize(member)));
  }

  default Long zadd(String key, double score, Object member, ZAddParams params) {
    return this.run((jedis, serializer) -> jedis.zadd(SafeEncoder.encode(key), score, serializer.serialize(member), params));
  }

  default Long zadd(String key, Map<String, Double> scoreMembers) {
    return this.run((jedis, serializer) -> jedis.zadd(SafeEncoder.encode(key), EnoaRedisConvert.with(serializer).toBytesZmap(scoreMembers)));
  }

  default Long zadd(String key, Map<String, Double> scoreMembers, ZAddParams params) {
    return this.run((jedis, serializer) -> jedis.zadd(SafeEncoder.encode(key), EnoaRedisConvert.with(serializer).toBytesZmap(scoreMembers), params));
  }

  default Long zcard(String key) {
    return this.run((jedis, serializer) -> jedis.zcard(key));
  }

  default Long zcount(String key, double min, double max) {
    return this.run((jedis, serializer) -> jedis.zcount(key, min, max));
  }

  default Long zcount(String key, Object min, Object max) {
    return this.run((jedis, serializer) -> jedis.zcount(SafeEncoder.encode(key), serializer.serialize(min), serializer.serialize(max)));
  }

  default Double zincrby(String key, double score, Object member) {
    return this.run((jedis, serializer) -> jedis.zincrby(SafeEncoder.encode(key), score, serializer.serialize(member)));
  }

  default Double zincrby(String key, double score, Object member, ZIncrByParams params) {
    return this.run((jedis, serializer) -> jedis.zincrby(SafeEncoder.encode(key), score, serializer.serialize(member), params));
  }

  default <T> Set<T> zrange(String key, long start, long end) {
    return this.run((jedis, serializer) -> EnoaRedisConvert.with(serializer).convertSet(jedis.zrange(SafeEncoder.encode(key), start, end)));
  }

  default <T> Set<T> zrangebyscore(String key, double min, double max) {
    return this.run((jedis, serializer) -> EnoaRedisConvert.with(serializer).convertSet(jedis.zrangeByScore(SafeEncoder.encode(key), min, max)));
  }

  default <T> Set<T> zrangebyscore(String key, T min, T max) {
    return this.run((jedis, serializer) -> EnoaRedisConvert.with(serializer).convertSet(
        jedis.zrangeByScore(SafeEncoder.encode(key), serializer.serialize(min), serializer.serialize(max))
    ));
  }

  default <T> Set<T> zrangebyscore(String key, double min, double max, int offset, int count) {
    return this.run((jedis, serializer) -> EnoaRedisConvert.with(serializer).convertSet(jedis.zrangeByScore(SafeEncoder.encode(key), min, max, offset, count)));
  }

  default <T> Set<T> zrangebyscore(String key, T min, T max, int offset, int count) {
    return this.run((jedis, serializer) -> EnoaRedisConvert.with(serializer).convertSet(
        jedis.zrangeByScore(SafeEncoder.encode(key), serializer.serialize(min), serializer.serialize(max), offset, count)
    ));
  }

  default Long zrank(String key, Object member) {
    return this.run((jedis, serializer) -> jedis.zrank(SafeEncoder.encode(key), serializer.serialize(member)));
  }

  default Long zrem(String key, Object... member) {
    return this.run((jedis, serializer) -> jedis.zrem(SafeEncoder.encode(key), EnoaRedisConvert.with(serializer).toBytesValues(member)));
  }

  default Long zremrangebyrank(String key, long start, long end) {
    return this.run((jedis, serializer) -> jedis.zremrangeByRank(key, start, end));
  }

  default Long zremrangebyscore(String key, double start, double end) {
    return this.run((jedis, serializer) -> jedis.zremrangeByScore(key, start, end));
  }

  default Long zremrangebyscore(String key, Object start, Object end) {
    return this.run((jedis, serializer) -> jedis.zremrangeByScore(SafeEncoder.encode(key), serializer.serialize(start), serializer.serialize(end)));
  }

  default <T> Set<T> zrevrange(String key, long start, long end) {
    return this.run((jedis, serializer) -> EnoaRedisConvert.with(serializer).convertSet(jedis.zrevrange(SafeEncoder.encode(key), start, end)));
  }

  default <T> Set<T> zrevrangebylex(String key, T max, T min) {
    return this.run((jedis, serializer) -> EnoaRedisConvert.with(serializer).convertSet(
      jedis.zrevrangeByLex(SafeEncoder.encode(key), serializer.serialize(max), serializer.serialize(min))
    ));
  }

  default <T> Set<T> zrevrangebylex(String key, T max, T min, int offset, int count) {
    return this.run((jedis, serializer) -> EnoaRedisConvert.with(serializer).convertSet(
      jedis.zrevrangeByLex(SafeEncoder.encode(key), serializer.serialize(max), serializer.serialize(min), offset, count)
    ));
  }

  default <T> Set<T> zrevrangebyscore(String key, double max, double min) {
    return this.run((jedis, serializer) -> EnoaRedisConvert.with(serializer).convertSet(
      jedis.zrevrangeByScore(SafeEncoder.encode(key), max, min)
    ));
  }

  default <T> Set<T> zrevrangebyscore(String key, T max, T min) {
    return this.run((jedis, serializer) -> EnoaRedisConvert.with(serializer).convertSet(
      jedis.zrevrangeByScore(SafeEncoder.encode(key), serializer.serialize(max), serializer.serialize(min))
    ));
  }

  default <T> Set<T> zrevrangebyscore(String key, double max, double min, int offset, int count) {
    return this.run((jedis, serializer) -> EnoaRedisConvert.with(serializer).convertSet(
      jedis.zrevrangeByScore(SafeEncoder.encode(key), max, min, offset, count)
    ));
  }

  default <T> Set<T> zrevrangebyscore(String key, T max, T min, int offset, int count) {
    return this.run((jedis, serializer) -> EnoaRedisConvert.with(serializer).convertSet(
      jedis.zrevrangeByScore(SafeEncoder.encode(key), serializer.serialize(max), serializer.serialize(min), offset, count)
    ));
  }

  default Long zrevrank(String key, Object member) {
    return this.run((jedis, serializer) -> jedis.zrevrank(SafeEncoder.encode(key), serializer.serialize(member)));
  }

  default Double zscore(String key, Object member) {
    return this.run((jedis, serializer) -> jedis.zscore(SafeEncoder.encode(key), serializer.serialize(member)));
  }

  default Long zunionstore(String dstkey, String... sets) {
    return this.run((jedis, serializer) -> jedis.zunionstore(dstkey, sets));
  }

  default Long zunionstore(String dstkey, ZParams params, String... sets) {
    return this.run((jedis, serializer) -> jedis.zunionstore(dstkey, params, sets));
  }

  default Long zinterstore(String dstkey, String... sets) {
    return this.run((jedis, serializer) -> jedis.zinterstore(dstkey, sets));
  }

  default Long zinterstore(String dstkey, ZParams params, String... sets) {
    return this.run((jedis, serializer) -> jedis.zinterstore(dstkey, params, sets));
  }

  default ScanResult<Tuple> zscan(String key, String cursor) {
    return this.run((jedis, serializer) -> jedis.zscan(key, cursor));
  }

  default ScanResult<Tuple> zscan(String key, String cursor, ScanParams params) {
    return this.run((jedis, serializer) -> jedis.zscan(key, cursor, params));
  }

  default <T> Set<T> zrangebylex(String key, T min, T max) {
    return this.run((jedis, serializer) -> EnoaRedisConvert.with(serializer).convertSet(
      jedis.zrangeByLex(SafeEncoder.encode(key), serializer.serialize(min), serializer.serialize(max))
    ));
  }

  default <T> Set<T> zrangebylex(String key, T min, T max, int offset, int count) {
    return this.run((jedis, serializer) -> EnoaRedisConvert.with(serializer).convertSet(
      jedis.zrangeByLex(SafeEncoder.encode(key), serializer.serialize(min), serializer.serialize(max), offset, count)
    ));
  }

  default Long zlexcount(String key, Object min, Object max) {
    return this.run((jedis, serializer) -> jedis.zlexcount(SafeEncoder.encode(key), serializer.serialize(min), serializer.serialize(max)));
  }

  default Long zremrangebylex(String key, Object min, Object max) {
    return this.run((jedis, serializer) -> jedis.zremrangeByLex(SafeEncoder.encode(key), serializer.serialize(min), serializer.serialize(max)));
  }
}
