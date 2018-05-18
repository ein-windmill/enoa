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

import io.enoa.nosql.redis.EMgrRedis;
import io.enoa.nosql.redis.Redis;
import io.enoa.serialization.provider.jdk.JdkSerializeProvider;
import redis.clients.util.SafeEncoder;

import java.util.HashMap;
import java.util.Map;

public class StringExample {

  private String key = "string";

  private void set() {
    String ret = Redis.set(this.key, "val0");
    System.out.println(ret);
  }

  private void append() {
    Long ret = Redis.append(this.key, "-val1");
    System.out.println(ret);
  }

  private void get() {
    String ret = Redis.get(this.key);
    System.out.println(ret);
  }

  private void setObj() {
    Map<String, String> data = new HashMap<>();
    data.put("key0", "val0");
    String ret = Redis.run((jedis, serializer) -> jedis.set(SafeEncoder.encode(this.key), serializer.serialize(data)));
    System.out.println(ret);
  }

  private void getObj() {
    Map<String, String> data = Redis.run((jedis, serializer) -> serializer.reduction(jedis.get(SafeEncoder.encode(this.key))));
    System.out.println(data);
  }

  public static void main(String[] args) {
    EMgrRedis.start("localhost", 6379, new JdkSerializeProvider());
    StringExample example = new StringExample();
    example.set();
    example.get();
    example.append();
    example.get();
    example.setObj();
    example.getObj();
//    Redis.del(example.key);
  }
}
