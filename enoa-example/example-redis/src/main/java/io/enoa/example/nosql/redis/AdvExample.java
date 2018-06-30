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
package io.enoa.example.nosql.redis;

import io.enoa.nosql.redis.EnoaRedis;
import io.enoa.nosql.redis.EoRedisRunner;
import io.enoa.nosql.redis.Redis;
import io.enoa.nosql.redis.RedisSentinelConfig;
import io.enoa.serialization.EoSerializer;
import io.enoa.serialization.provider.jdk.JdkSerializeProvider;
import redis.clients.jedis.Jedis;
import redis.clients.util.SafeEncoder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class AdvExample {

  private String key = "adv";

  private void run() {
    // run interface does not need to clise jedis

    Redis.run(new EoRedisRunner() {
      @Override
      public Object run(Jedis jedis, EoSerializer serializer) {
        return jedis.set(key, "new interface");
      }
    });
    String ret = Redis.run(new EoRedisRunner() {
      @Override
      public Object run(Jedis jedis, EoSerializer serializer) {
        return jedis.get(key);
      }
    });
    System.out.println(ret);


    // lambda

    Redis.run((jedis, serializer) -> jedis.set(this.key, "lambda"));

    String lambda = Redis.run((jedis, serializer) -> jedis.get(this.key));
    System.out.println(lambda);
  }

  private void jedis() {
    Jedis jedis = Redis.jedis();
    jedis.set(this.key, "jedis");
    System.out.println(jedis.get(this.key));
    // if not use run interface, jedis need do close()
    jedis.close();
  }

  private void use() {
    // multi redis client
    EnoaRedis redis = Redis.use("other");
    redis.set(this.key, "other redis client");
    System.out.println(redis.get(this.key));
  }

  private void serialize() {
    Map<String, String> data = new HashMap<>();
    data.put("serialize", "yes");

    String ret0 = Redis.run((jedis, serializer) -> {
      byte[] key = SafeEncoder.encode(this.key);
      // this serializer is EMgrRedis.start set serializer provider
      byte[] value = serializer.serialize(data);
      // use jedisn binart set
      return jedis.set(key, value);
    });
    System.out.println(ret0);

    // resp type is your set data type
    Map<String, String> ret1 =
      Redis.run((jedis, serializer) -> {
        byte[] ret = jedis.get(SafeEncoder.encode(this.key));
        // use serializer reduction byte to object
        return serializer.reduction(ret);
      });
    System.out.println(ret1);
  }

  private void sentinel() {
    RedisSentinelConfig config = new RedisSentinelConfig.Builder()
      .name("sentinel")
      .database(1)
      .passwd("passwd")
      .soTimeout(1000)
      .masterName("master")
      .sentinels(new HashSet<String>() {{
        add("192.168.1.19:63790");
        add("192.168.1.20:63791");
        add("192.168.1.21:63792");
        add("192.168.1.22:63793");
      }})
      .build();
//    EMgrRedis.start(config);
    Redis.epm().install(config);
    EnoaRedis redis = Redis.use("sentinel");
    System.out.println(redis.set(this.key, "sentinel value"));
    String ret = redis.get(this.key);
    System.out.println(ret);
    redis.del(this.key);
  }

  public static void main(String[] args) {
    AdvExample example = new AdvExample();
//    EMgrRedis.start("localhost", 6379, new JdkSerializeProvider());
    Redis.epm().install("localhost", 6379, new JdkSerializeProvider());
//    EMgrRedis.start("main", "localhost", 6379, new JdkSerializeProvider()); // default name is main
    Redis.epm().install("other", "localhost", 6379);
    example.run();
    example.jedis();
    example.use();
    example.serialize();
    example.sentinel();
    Redis.del(example.key);

    // if different redis server
    // Redis.use("other").del(example.key);
  }

}
