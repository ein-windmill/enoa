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
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

interface HashCommand extends _Runner {

  default Long hdel(String key, String... field) {
    return this.run((jedis, serializer) -> jedis.hdel(key, field));
  }

  default Boolean hexists(String key, String field) {
    return this.run((jedis, serializer) -> jedis.hexists(key, field));
  }

  default <T> T hget(String key, String field) {
    return this.run((jedis, serializer) -> serializer.reduction(jedis.hget(SafeEncoder.encode(key), SafeEncoder.encode(field))));
  }

  default <T> Map<String, T> hgetall(String key) {
    return this.run((jedis, serializer) -> EnoaRedisConvert.with(serializer).<T>convertMap(jedis.hgetAll(SafeEncoder.encode(key))));
  }

  default Long hincrby(String key, String field, long value) {
    return this.run((jedis, serializer) -> jedis.hincrBy(key, field, value));
  }

  default Double hincrbyfloat(String key, String field, double value) {
    return this.run((jedis, serializer) -> jedis.hincrByFloat(key, field, value));
  }

  default Set<String> hkeys(String key) {
    return this.run((jedis, serializer) -> jedis.hkeys(key));
  }

  default Long hlen(String key) {
    return this.run((jedis, serializer) -> jedis.hlen(key));
  }

  default <T> List<T> hmget(String key, String... fields) {
    return this.run((jedis, serializer) ->
      EnoaRedisConvert.with(serializer).<T>convertList(jedis.hmget(SafeEncoder.encode(key),
        EnoaRedisConvert.with(serializer).toBytesKeys(fields))));
  }

  default String hmset(String key, Map<String, Object> hash) {
    return this.run((jedis, serializer) -> {
      Map<byte[], byte[]> trans = EnoaRedisConvert.with(serializer).serializeMap(hash);
      String ret = jedis.hmset(SafeEncoder.encode(key), trans);
      trans.clear();
      return ret;
    });
  }

  default Long hset(String key, String field, Object value) {
    return this.run((jedis, serializer) -> jedis.hset(SafeEncoder.encode(key), SafeEncoder.encode(field), serializer.serialize(value)));
  }

  default Long hsetnx(String key, String field, Object value) {
    return this.run((jedis, serializer) -> jedis.hsetnx(SafeEncoder.encode(key), SafeEncoder.encode(field), serializer.serialize(value)));
  }

  default <T> List<T> hvals(String key) {
    return this.run((jedis, serializer) -> EnoaRedisConvert.with(serializer).convertList(jedis.hvals(SafeEncoder.encode(key))));
  }

  default <T> ScanResult<Map.Entry<String, T>> hscan(String key, String cursor) {
    return this.run((jedis, serializer) -> {
      ScanResult<Map.Entry<byte[], byte[]>> data = jedis.hscan(SafeEncoder.encode(key), SafeEncoder.encode(cursor));
      List<Map.Entry<String, T>> res = data.getResult().stream()
        .map(EnoaRedisConvert.with(serializer)::<T>convertEntry)
        .collect(Collectors.toList());
      return new ScanResult<>(data.getCursorAsBytes(), res);
    });
  }

  default <T> ScanResult<Map.Entry<String, T>> hscan(String key, String cursor, ScanParams params) {
    return this.run((jedis, serializer) -> {
      ScanResult<Map.Entry<byte[], byte[]>> data = jedis.hscan(SafeEncoder.encode(key), SafeEncoder.encode(cursor), params);
      List<Map.Entry<String, T>> res = data.getResult().stream()
        .map(EnoaRedisConvert.with(serializer)::<T>convertEntry)
        .collect(Collectors.toList());
      return new ScanResult<>(data.getCursorAsBytes(), res);
    });
  }
  // fixme jedis client does not have this api
//  long hstrlen()
}
