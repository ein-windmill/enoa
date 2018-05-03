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

import io.enoa.log.kit.LogKit;
import io.enoa.serialization.provider.jdk.JdkSerializeProvider;
import org.junit.Before;
import org.junit.Test;
import redis.clients.util.SafeEncoder;

import java.sql.Timestamp;

public class EnoaRedisMgrTest {

  private String name = "ENOA_REDIS_TEST";

  @Before
  public void init() {
    try {
      EnoaRedisMgr.start(this.name, "localhost", 6379, new JdkSerializeProvider());
    } catch (Exception e) {
      LogKit.error(e.getMessage(), e);
    }
  }

  private User user() {
    User user = new User();
    user.setId(234L);
    user.setName("Jkon");
    user.setEmail("test@example.com");
    user.setTs(new Timestamp(System.currentTimeMillis()));
    user.setGender(Gender.FMALE);
    return user;
  }

  @Test
  public void testJedis() {
    try {
      Redis.use(this.name).run((jedis, serializer) -> jedis.set("tk0", "tv0"));
      Redis.use(this.name).run((jedis, serializer) -> jedis.set(SafeEncoder.encode("tk1"), serializer.serialize("tv1")));
    } catch (Exception e) {
      LogKit.error(e.getMessage(), e);
    }
  }

  @Test
  public void testMap() {

  }

//  @Test
//  public void testRedis() {
//    String u0 = Redis.set("user", this.user());
//    LogKit.debug(u0);
//
//    EnoaRedis ers = Redis.use();
//    String u1 = ers.set("user", this.user());
//    LogKit.debug(u1);
//
//    User u2 = Redis.get("user");
//    LogKit.debug(JsonKit.toJson(u2));
//
//
//  }

}
