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
package io.enoa.trydb.dialect;

import io.enoa.toolkit.collection.CollectionKit;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;

public interface IDialect {

  default String defPrimaryKey() {
    return "id";
  }

  default void fillParas(PreparedStatement pst, Object... paras) throws SQLException {
    for (int i = 0; i < paras.length; i++) {
      pst.setObject(i + 1, paras[i]);
    }
  }

  default void fillParas(PreparedStatement pst, Collection paras) throws SQLException {
    Iterator iterator = paras.iterator();
    int i = 0;
    while (iterator.hasNext()) {
      pst.setObject(i + 1, iterator.next());
      i += 1;
    }
  }

  default String identifierQuoteStringLeft() {
    return "";
  }

  default String identifierQuoteStringRight() {
    return "";
  }

  default String[] keywords() {
    return CollectionKit.emptyArray(String.class);
  }

  String pageSql(long offset, int size, String psql);

}
