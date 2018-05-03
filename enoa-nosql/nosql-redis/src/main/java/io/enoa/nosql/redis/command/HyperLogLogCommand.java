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
import redis.clients.util.SafeEncoder;

interface HyperLogLogCommand extends _Runner {

  default Long pfadd(String key, Object... elements) {
    return this.run((jedis, serializer) -> jedis.pfadd(SafeEncoder.encode(key), EnoaRedisConvert.with(serializer).toBytesValues(elements)));
  }

  default long pfcount(String... key) {
    return this.run((jedis, serializer) -> jedis.pfcount(key));
  }

  default long pfcount(String key) {
    return this.run((jedis, serializer) -> jedis.pfcount(key));
  }

  default String pfmerge(String destkey, String... sourcekeys) {
    return this.run((jedis, serializer) -> jedis.pfmerge(destkey, sourcekeys));
  }

}
