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
import redis.clients.jedis.BinaryClient;
import redis.clients.util.SafeEncoder;

import java.util.List;

interface ListCommand extends _Runner {


  default <T> List<T> blpop(int timeout, String... keys) {
    return this.run((jedis, serializer) ->
      EnoaRedisConvert.with(serializer).convertList(jedis.blpop(timeout, EnoaRedisConvert.with(serializer).toBytesKeys(keys))));
  }

  default <T> List<T> blpop(int timeout, String key) {
    return this.run((jedis, serializer) -> EnoaRedisConvert.with(serializer).convertList(jedis.blpop(timeout, SafeEncoder.encode(key))));
  }

  default <T> List<T> blpop(String... args) {
    return this.run((jedis, serializer) ->
      EnoaRedisConvert.with(serializer).convertList(jedis.blpop(EnoaRedisConvert.with(serializer).toBytesKeys(args))));
  }

  default <T> List<T> brpop(int timeout, String... keys) {
    return this.run((jedis, serializer) ->
      EnoaRedisConvert.with(serializer).convertList(jedis.brpop(timeout, EnoaRedisConvert.with(serializer).toBytesKeys(keys))));
  }

  default <T> List<T> brpop(int timeout, String key) {
    return this.run((jedis, serializer) ->
      EnoaRedisConvert.with(serializer).convertList(jedis.brpop(timeout, EnoaRedisConvert.with(serializer).toBytesKeys(key))));
  }

  default <T> List<T> brpop(String... args) {
    return this.run((jedis, serializer) ->
      EnoaRedisConvert.with(serializer).convertList(jedis.brpop(EnoaRedisConvert.with(serializer).toBytesKeys(args))));
  }

  default <T> T brpoplpush(String source, String destination, int timeout) {
    return this.run((jedis, serializer) ->
      serializer.<T>reduction(jedis.brpoplpush(SafeEncoder.encode(source), SafeEncoder.encode(destination), timeout)));
  }

  default String lindex(String key, long index) {
    return this.run((jedis, serializer) -> jedis.lindex(key, index));
  }

  default Long linsert(String key, BinaryClient.LIST_POSITION where, Object pivot, Object value) {
    return this.run((jedis, serializer) -> jedis.linsert(SafeEncoder.encode(key), where,
      serializer.serialize(pivot),
      serializer.serialize(value)));
  }

  default Long llen(String key) {
    return this.run((jedis, serializer) -> jedis.llen(key));
  }

  default <T> T lpop(String key) {
    return this.run((jedis, serializer) -> serializer.reduction(jedis.lpop(SafeEncoder.encode(key))));
  }

  default Long lpush(String key, Object... values) {
    return this.run((jedis, serializer) ->
      jedis.lpush(SafeEncoder.encode(key), EnoaRedisConvert.with(serializer).toBytesValues(values)));
  }

  default Long lpushx(String key, Object... values) {
    return this.run((jedis, serializer) ->
      jedis.lpushx(SafeEncoder.encode(key), EnoaRedisConvert.with(serializer).toBytesValues(values)));
  }

  default <T> List<T> lrange(String key, long start, long end) {
    return this.run((jedis, serializer) ->
      EnoaRedisConvert.with(serializer).convertList(jedis.lrange(SafeEncoder.encode(key), start, end)));
  }

  default Long lrem(String key, long count, Object value) {
    return this.run((jedis, serializer) -> jedis.lrem(SafeEncoder.encode(key), count, serializer.serialize(value)));
  }

  default String lset(String key, long index, Object value) {
    return this.run((jedis, serializer) -> jedis.lset(SafeEncoder.encode(key), index, serializer.serialize(value)));
  }

  default String ltrim(String key, long start, long end) {
    return this.run((jedis, serializer) -> jedis.ltrim(key, start, end));
  }

  default <T> T rpop(String key) {
    return this.run((jedis, serializer) -> serializer.reduction(jedis.rpop(SafeEncoder.encode(key))));
  }

  default <T> T rpoplpush(String srckey, String dstkey) {
    return this.run((jedis, serializer) -> serializer.<T>reduction(jedis.rpoplpush(SafeEncoder.encode(srckey), SafeEncoder.encode(dstkey))));
  }

  default Long rpush(String key, Object... values) {
    return this.run((jedis, serializer) ->
      jedis.rpush(SafeEncoder.encode(key), EnoaRedisConvert.with(serializer).toBytesValues(values)));
  }

  default Long rpushx(String key, Object... values) {
    return this.run((jedis, serializer) ->
      jedis.rpushx(SafeEncoder.encode(key), EnoaRedisConvert.with(serializer).toBytesValues(values)));
  }
}
