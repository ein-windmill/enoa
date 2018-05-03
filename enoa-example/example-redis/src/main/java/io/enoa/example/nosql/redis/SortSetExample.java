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

import io.enoa.nosql.redis.EnoaRedisMgr;
import io.enoa.nosql.redis.Redis;
import io.enoa.serialization.provider.jdk.JdkSerializeProvider;

import java.util.Set;

public class SortSetExample {

  private String key = "sort_set";

  private void zadd() {
    Redis.zadd(this.key, 0.2D, 5);
    Redis.zadd(this.key, 0.2D, 4);
    Redis.zadd(this.key, 0.2D, 2);
    Redis.zadd(this.key, 0.2D, 1);
    Redis.zadd(this.key, 0.2D, -1);
    Redis.zadd(this.key, 0.2D, 100);
  }

  private void zrange() {
    Set<Object> zrange = Redis.zrange(this.key, 0, -1);
    System.out.println(zrange);
  }

  public static void main(String[] args) {
    SortSetExample example = new SortSetExample();
    EnoaRedisMgr.start("localhost", 6379, new JdkSerializeProvider());
    example.zadd();
    example.zrange();
    Redis.del(example.key);
  }

}
