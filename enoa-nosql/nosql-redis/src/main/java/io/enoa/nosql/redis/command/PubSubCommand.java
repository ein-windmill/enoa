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

import redis.clients.jedis.JedisPubSub;
import redis.clients.util.SafeEncoder;

import java.util.List;
import java.util.Map;

interface PubSubCommand extends _Runner {


  default List<String> pubsubchannels(String pattern) {
    return this.run((jedis, serializer) -> jedis.pubsubChannels(pattern));
  }

  default Long pubsubnumpat() {
    return this.run((jedis, serializer) -> jedis.pubsubNumPat());
  }

  default Map<String, String> pubsubnumsub(String... channels) {
    return this.run((jedis, serializer) -> jedis.pubsubNumSub(channels));
  }

  default Long publish(String channel, Object message) {
    return this.run((jedis, serializer) -> jedis.publish(SafeEncoder.encode(channel), serializer.serialize(message)));
  }

  default void psubscribe(JedisPubSub jedisPubSub, String... patterns) {
    this.run((jedis, serializer) -> {
      jedis.psubscribe(jedisPubSub, patterns);
      return this;
    });
  }

  default void subscribe(JedisPubSub jedisPubSub, String... channels) {
    this.run((jedis, serializer) -> {
      jedis.subscribe(jedisPubSub, channels);
      return this;
    });
  }


}
