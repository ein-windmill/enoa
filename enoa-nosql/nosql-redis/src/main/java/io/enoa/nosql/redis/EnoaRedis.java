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
package io.enoa.nosql.redis;

import io.enoa.nosql.redis.command._RedisCommand;
import io.enoa.serialization.EoSerializationFactory;
import io.enoa.serialization.EoSerializer;
import redis.clients.jedis.Jedis;
import redis.clients.util.Pool;

public class EnoaRedis implements _RedisCommand {

  private Pool<Jedis> pool;
  private EoSerializer serializer;

  public EnoaRedis(Pool<Jedis> pool, EoSerializationFactory serialization) {
    this.pool = pool;
    this.serializer = serialization.serializer();
  }

  @Override
  public Jedis jedis() {
    return this.pool.getResource();
  }

  @Override
  public <T> T run(EoRedisRunner runner) {
    Jedis jedis = this.pool.getResource();
    Object ret = runner.run(jedis, this.serializer);
    this.close(jedis);
    return (T) ret;
  }

  private void close(Jedis jedis) {
    if (jedis == null)
      return;
    jedis.close();
  }

  public void close() {
    pool.close();
  }

}
