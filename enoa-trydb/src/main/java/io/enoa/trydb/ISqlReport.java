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
package io.enoa.trydb;

import io.enoa.toolkit.random.RandomKit;

/**
 * sql 汇报接口, 用于每次将查询数据库的 sql 以及 参数调用接口,
 * 提供应用程序做其他操作
 */
public interface ISqlReport {

  default String mark() {
    return String.valueOf(RandomKit.nextInt(100, 999));
  }

  void report(String mark, String sql, Object[] paras);

}
