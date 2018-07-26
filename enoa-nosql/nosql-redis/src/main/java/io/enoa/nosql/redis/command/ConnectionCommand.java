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

interface ConnectionCommand extends _Runner {

  default String auth(String password) {
    return this.run((jedis, serializer) -> jedis.auth(password));
  }

  default String echo(Object object) {
    return this.run((jedis, serializer) -> jedis.echo(serializer.serialize(object)));
  }

  default String ping() {
    return this.run((jedis, serializer) -> jedis.ping());
  }

  default String quit() {
    return this.run((jedis, serializer) -> jedis.quit());
  }

  default String select(int index) {
    return this.run((jedis, serializer) -> jedis.select(index));
  }

}
