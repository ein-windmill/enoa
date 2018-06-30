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
package io.enoa.nosql.redis;

import io.enoa.log.Log;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;

public class JedisTest {

  private Jedis jedis;

  @Before
  public void init() {
    try {
      this.jedis = new Jedis("localhost", 6379);
    } catch (Exception e) {
      Log.error(e.getMessage(), e);
    }
  }

  @Test
  public void testJedis() {
    try {
//    jedis.auth("xxxx");
      jedis.set("key", "jedis");
      String value = jedis.get("key");
      Log.debug(value);
      jedis.close();
    } catch (Exception e) {
      Log.error(e.getMessage(), e);
    }
  }

  @Test
  public void testPool() {
    try {
      JedisPoolConfig config = new JedisPoolConfig();
      config.setMaxIdle(8);
      config.setMaxTotal(18);
      JedisPool pool = new JedisPool(config, "localhost", 6379, 2000);
      Jedis jedis = pool.getResource();
      jedis.set("key", "pool");
      String value = jedis.get("key");
      Log.debug(value);
      jedis.close();
      pool.close();
    } catch (Exception e) {
      Log.error(e.getMessage(), e);
    }
  }

  @Test
  public void testSentinel() {
    try {
      Set<String> sentinels = new HashSet<>();
      sentinels.add("localhost:6379");
//    sentinels.add("localhost:26379");
      JedisPoolConfig config = new JedisPoolConfig();
      config.setMaxIdle(5);
      config.setMaxTotal(20);
      JedisSentinelPool pool = new JedisSentinelPool("master", sentinels, config);
      Jedis jedis = pool.getResource();
      jedis.set("key", "sentinel");
      String value = jedis.get("key");
      Log.debug(value);
      jedis.close();
      pool.close();
    } catch (Exception e) {
      Log.error(e.getMessage(), e);
    }
  }

  @Test
  public void testSet() {

  }

}
