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

import java.util.List;

interface ScriptCommand extends _Runner {

  default Object eval(String script, int keyCount, String... params) {
    return this.run((jedis, serializer) -> jedis.eval(script, keyCount, params));
  }

  default Object eval(String script, List<String> keys, List<String> args) {
    return this.run((jedis, serializer) -> jedis.eval(script, keys, args));
  }

  default Object eval(String script) {
    return this.run((jedis, serializer) -> jedis.eval(script));
  }

  default Object evalsha(String script) {
    return this.run((jedis, serializer) -> jedis.evalsha(script));
  }

  default Object evalsha(String sha1, List<String> keys, List<String> args) {
    return this.run((jedis, serializer) -> jedis.evalsha(sha1, keys, args));
  }

  default Object evalsha(String sha1, int keyCount, String... params) {
    return this.run((jedis, serializer) -> jedis.evalsha(sha1, keyCount, params));
  }

  default Boolean scriptexists(String sha1) {
    return this.run((jedis, serializer) -> jedis.scriptExists(sha1));
  }

  default List<Boolean> scriptexists(String... sha1) {
    return this.run((jedis, serializer) -> jedis.scriptExists(sha1));
  }

  default String scriptflush() {
    return this.run((jedis, serializer) -> jedis.scriptFlush());
  }

  default String scriptkill() {
    return this.run((jedis, serializer) -> jedis.scriptKill());
  }

  default String scriptload(String script) {
    return this.run((jedis, serializer) -> jedis.scriptLoad(script));
  }

}
