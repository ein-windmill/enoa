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
package io.enoa.trydb.tsql.template;

import io.enoa.stove.api.StoveBody;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.text.TextKit;

import java.util.Arrays;
import java.util.stream.Collectors;

public abstract class TSql implements StoveBody {

  public abstract String sql();

  public abstract Object[] paras();

  public static TSql create(String sql) {
    return create(sql, CollectionKit.emptyArray(Object.class));
  }

  public static TSql create(String sql, Object... paras) {
    return new TSql() {
      @Override
      public String sql() {
        return sql;
      }

      @Override
      public Object[] paras() {
        return paras;
      }
    };
  }

  @Override
  public String toString() {
    return TextKit.union(this.sql(),
      " ----> ",
      "[", String.join(",", Arrays.stream(this.paras()).map(Object::toString).collect(Collectors.toList())), "]");
  }

}
