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
import redis.clients.jedis.BitOP;
import redis.clients.util.SafeEncoder;

import java.util.List;

interface StringCommand extends _Runner {


  default Long append(String key, String value) {
    return this.run((jedis, serializer) -> jedis.append(key, value));
  }

  default Long bitcount(String key) {
    return this.run((jedis, serializer) -> jedis.bitcount(SafeEncoder.encode(key)));
  }

  default Long bitcount(String key, long start, long end) {
    return this.run((jedis, serializer) -> jedis.bitcount(SafeEncoder.encode(key), start, end));
  }

  default Long bitop(BitOP op, String destKey, String... srcKeys) {
    return this.run((jedis, serializer) -> jedis.bitop(op, destKey, srcKeys));
  }

  default List<Long> bitfield(String key, String... arguments) {
    return this.run((jedis, serializer) -> jedis.bitfield(key, arguments));
  }

  default Long decr(String key) {
    return this.run((jedis, serializer) -> jedis.decr(key));
  }

  default Long decrby(String key, long integer) {
    return this.run((jedis, serializer) -> jedis.decrBy(key, integer));
  }

  default String get(String key) {
    return this.run((jedis, serializer) -> jedis.get(key));
  }

  default Boolean getbit(String key, long offset) {
    return this.run((jedis, serializer) -> jedis.getbit(key, offset));
  }

  default String getrange(String key, long startOffset, long endOffset) {
    return this.run((jedis, serializer) -> jedis.getrange(key, startOffset, endOffset));
  }

  default String getset(String key, String value) {
    return this.run((jedis, serializer) -> jedis.getSet(key, value));
  }

  default Long incr(String key) {
    return this.run((jedis, serializer) -> jedis.incr(key));
  }

  default Long incrby(String key, long integer) {
    return this.run((jedis, serializer) -> jedis.incrBy(key, integer));
  }

  default Double incrbyfloat(String key, double value) {
    return this.run((jedis, serializer) -> jedis.incrByFloat(key, value));
  }

  default List<String> mget(String... keys) {
    return this.run((jedis, serializer) ->
      EnoaRedisConvert.with(serializer).convertList(jedis.mget(EnoaRedisConvert.with(serializer).toBytesKeys(keys))));
  }

  default String mset(String... keysvalues) {
    if (keysvalues.length % 2 != 0)
      throw new IllegalArgumentException("wrong number of arguments for met, keysValues length can not be odd");
    return this.run((jedis, serializer) -> {
//      byte[][] kvs = new byte[keysvalues.length][];
//      for (int i = 0; i < keysvalues.length; i++) {
//        if (i % 2 == 0)
//          kvs[i] = SafeEncoder.encode(keysvalues[i]);
//        else
//          kvs[i] = SafeEncoder.encode(keysvalues[i]);
//      }
      return jedis.mset(keysvalues);
    });
  }

  default Long msetnx(String... keysvalues) {
    if (keysvalues.length % 2 != 0)
      throw new IllegalArgumentException("wrong number of arguments for met, keysValues length can not be odd");
    return this.run((jedis, serializer) -> {
//      byte[][] kvs = new byte[keysvalues.length][];
//      for (int i = 0; i < keysvalues.length; i++) {
//        if (i % 2 == 0)
//          kvs[i] = SafeEncoder.encode(keysvalues[i]);
//        else
//          kvs[i] = SafeEncoder.encode(keysvalues[i]);
//      }
      return jedis.msetnx(keysvalues);
    });
  }

  default String psetex(String key, long milliseconds, String value) {
    return this.run((jedis, serializer) -> jedis.psetex(key, milliseconds, value));
  }

  default String set(String key, String value) {
    return this.run((jedis, serializer) -> jedis.set(key, value));
  }

  default String set(String key, String value, String nxxx) {
    return this.run((jedis, serializer) -> jedis.set(key, value, nxxx));
  }

  default String set(String key, String value, String nxxx, String expx, int time) {
    return this.run((jedis, serializer) -> jedis.set(key, value, nxxx, expx, time));
  }

  default Boolean setbit(String key, long offset, String value) {
    return this.run((jedis, serializer) -> jedis.setbit(key, offset, value));
  }

  default String setex(String key, int seconds, String value) {
    return this.run((jedis, serializer) -> jedis.setex(key, seconds, value));
  }

  default Long setnx(String key, String value) {
    return this.run((jedis, serializer) -> jedis.setnx(key, value));
  }

  default Long setrange(String key, long offset, String value) {
    return this.run((jedis, serializer) -> jedis.setrange(key, offset, value));
  }

  default Long strlen(String key) {
    return this.run((jedis, serializer) -> jedis.strlen(key));
  }
}
