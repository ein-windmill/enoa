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
import io.enoa.serialization.provider.hessian.HessianProvider;

import java.util.List;

public class ListExample {

  private String key = "list";

  private void lpush() {
    Redis.lpush(this.key, 0, 1, 2, 3, 4);
  }

  private void llen() {
    System.out.println(Redis.llen(this.key));
  }

  public void lrange() {
    List<Object> lrange = Redis.lrange(this.key, 0, -1);
    System.out.println(lrange);
  }

  public static void main(String[] args) {
    ListExample example = new ListExample();
    Redis.epm().install("localhost", 6379, new HessianProvider());
    example.lpush();
    example.llen();
    example.lrange();
    Redis.del(example.key);
  }

}
