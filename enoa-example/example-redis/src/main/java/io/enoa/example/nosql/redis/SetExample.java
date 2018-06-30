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
import io.enoa.serialization.provider.kryo.KryoProvider;

import java.util.Set;

public class SetExample {

  private String key = "set";

  private void sadd() {
    Redis.sadd(this.key, 0, 1, 2, 3, 3, 3, 4);
  }


  private void srem() {
    Long ret = Redis.srem(this.key, 0, 4);
    System.out.println(ret);
  }

  private void smembers() {
    Set<Object> smembers = Redis.smembers(this.key);
    System.out.println(smembers);
  }

  public static void main(String[] args) {
    SetExample example = new SetExample();
    Redis.epm().install("localhost", 6379, new KryoProvider());
    example.sadd();
    example.smembers();
    example.srem();
    example.smembers();
    Redis.del(example.key);
  }

}
