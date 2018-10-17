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
package io.enoa.trydb.build;

import io.enoa.trydb.TrydbConfig;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class DefaultBuilder<T> implements IRsBuilder<T> {

  @Override
  public List<T> build(ResultSet rs, Class<T> clazz, TrydbConfig config) throws SQLException {
    List result = new ArrayList();
    int colAmount = rs.getMetaData().getColumnCount();

    if (colAmount == 1) {
      while (rs.next()) {
        result.add(rs.getObject(1));
      }
      return result;
    }

    if (colAmount > 1) {
      while (rs.next()) {
        Object[] temp = new Object[colAmount];
        for (int i = 0; i < colAmount; i++) {
          temp[i] = rs.getObject(i + 1);
        }
        result.add(temp);
      }
    }
    return result;
  }

}
