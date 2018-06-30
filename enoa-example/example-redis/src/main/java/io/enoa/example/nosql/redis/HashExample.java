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

import io.enoa.nosql.redis.Redis;
import io.enoa.serialization.provider.fst.FstProvider;

import java.util.Map;

public class HashExample {

  private String key = "hash";

  private void hset() {
    Redis.hset(this.key, "f0", "v0");
    Redis.hset(this.key, "f1", "v1");

  }

  private void hexists() {
    System.out.println(Redis.hexists(this.key, "f0"));
    System.out.println(Redis.hexists(this.key, "f1"));
    System.out.println(Redis.hexists(this.key, "f2"));
  }

  private void hget() {
    Map<String, String> hgetall = Redis.hgetall(this.key);
    System.out.println(hgetall);

    String f0 = Redis.hget(this.key, "f0");
    System.out.println(f0);
  }


  public static void main(String[] args) {
    HashExample example = new HashExample();
    Redis.epm().install("localhost", 6379, new FstProvider());
    example.hset();
    example.hexists();
    example.hget();
    Redis.del(example.key);
  }


}
