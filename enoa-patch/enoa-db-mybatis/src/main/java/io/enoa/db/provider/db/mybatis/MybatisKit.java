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
package io.enoa.db.provider.db.mybatis;

import org.apache.ibatis.session.SqlSession;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class MybatisKit {

  private static Map<String, MybatisSess> MANAGER_MAP = new ConcurrentHashMap<>();

  private MybatisKit() {
  }

  static void add(String name, SqlSession session) {
    Set<String> names = MANAGER_MAP.keySet();
    if (names.stream().anyMatch(k -> k.equals(name)))
      throw new RuntimeException(String.format("Already exists this name: [%s] -> %s", String.join(", ", name), name));
    MANAGER_MAP.put(name, new MybatisSess(session));
  }

  public static MybatisSess use(String name) {
    return MANAGER_MAP.get(name);
  }

  private static MybatisSess use() {
    return use("main");
  }

  public static SqlSession session() {
    return use().session();
  }

  public static <T> T with(Class<T> clazz) {
    return use().with(clazz);
  }

}
