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
package io.enoa.trydb.tsql.template;

import io.enoa.firetpl.FireBody;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.is.Is;
import io.enoa.trydb.dialect.IDialect;
import io.enoa.trydb.tsql.Trysql;

import java.util.Arrays;
import java.util.stream.Collectors;

public interface TSql extends Trysql<TSql> {

  Object[] paras();

  static TSql create(String sql) {
    return create(sql, CollectionKit.emptyArray(Object.class));
  }

  static TSql create(String sql, Object... paras) {
    return new TSql() {

      @Override
      public TSql dialect(IDialect dialect) {
        return this;
      }

      @Override
      public String sql() {
        return sql;
      }

      @Override
      public Object[] paras() {
        return paras;
      }

      @Override
      public String toString() {
        StringBuilder ret = new StringBuilder();
        ret.append(this.sql());
        if (Is.empty(this.paras()))
          return ret.toString();
        ret.append("       ----> [");
        ret.append(String.join(",", Arrays.stream(this.paras()).map(Object::toString).collect(Collectors.toList())));
        ret.append("]");
        return ret.toString();
      }
    };
  }

  static TSql create(FireBody body) {
    return create(body.tpl(), body.paras());
  }

}
