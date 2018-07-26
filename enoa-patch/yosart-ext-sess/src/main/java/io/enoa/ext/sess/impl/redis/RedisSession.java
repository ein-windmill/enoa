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
package io.enoa.ext.sess.impl.redis;

import io.enoa.ext.sess.SessFactory;
import io.enoa.log.Log;
import io.enoa.nosql.redis.EnoaRedis;
import io.enoa.nosql.redis.Redis;
import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.toolkit.reflect.ReflectKit;
import io.enoa.yosart.kernel.http.Session;
import io.enoa.yosart.kernel.http.YoRequest;
import io.enoa.yosart.plugin.redis.RedisPlugin;
import io.enoa.yosart.thr.OyExtException;

public class RedisSession implements SessFactory {

  private EnoaRedis redis;
  private String sessKey;

  public RedisSession(String sessKey, RedisPlugin plugin, boolean reset) {

    if (!ReflectKit.hasClazz("io.enoa.yosart.plugin.redis.RedisPlugin"))
      throw new OyExtException(EnoaTipKit.message("eo.tip.ext.sess.redis_session_dep"));
    if (!plugin.start())
      throw new OyExtException(EnoaTipKit.message("eo.tip.ext.sess.redis_cant_start"));

    this.redis = Redis.use(plugin.redisName());
    this.sessKey = sessKey;
    if (reset)
      this.reset();
  }

  public RedisSession(String sessKey, RedisPlugin plugin) {
    this(sessKey, plugin, true);
  }

  public RedisSession(RedisPlugin plugin, boolean restart) {
    this("YOSESS", plugin, restart);
  }

  public RedisSession(RedisPlugin plugin) {
    this("YOSESS", plugin, true);
  }

  @Override
  public Session sess(YoRequest request) {
    return new RedisSessionImpl(this.redis, request, this.sessKey);
  }

  private void reset() {
    try {
      this.redis.del(this.sessKey);
    } catch (Exception e) {
      Log.error(e.getMessage(), e);
    }
  }
}
