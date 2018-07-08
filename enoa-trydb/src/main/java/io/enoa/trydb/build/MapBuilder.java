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
package io.enoa.trydb.build;

import io.enoa.toolkit.convert.ConvertKit;
import io.enoa.toolkit.map.Kv;
import io.enoa.toolkit.namecase.INameCase;
import io.enoa.toolkit.reflect.ReflectKit;
import io.enoa.trydb.convert._BlobConverter;
import io.enoa.trydb.convert._ClobConverter;
import io.enoa.trydb.convert._NClobConverter;
import io.enoa.trydb.thr.TrydbException;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class MapBuilder implements IRsBuilder<Map> {


  MapBuilder() {

  }

  private void buildLabelNamesAndTypes(ResultSetMetaData meta, String[] names, int[] types) throws SQLException {
    for (int i = 1; i < names.length; i++) {
      names[i] = meta.getColumnLabel(i);
      types[i] = meta.getColumnType(i);
    }
  }

  private Map newMap(Class<Map> clazz) {
//    if (clazz.isInterface())
    if (Map.class.getName().equals(clazz.getName()))
      return Kv.create();
    return ReflectKit.newInstance(clazz).build();
  }

  @Override
  public List<Map> build(ResultSet rs, Class<Map> clazz, INameCase namecase) throws SQLException {
    List<Map> rets = new ArrayList<>();
    ResultSetMetaData meta = rs.getMetaData();
    int cct = meta.getColumnCount();
    String[] names = new String[cct + 1];
    int[] types = new int[cct + 1];
    this.buildLabelNamesAndTypes(meta, names, types);
    while (rs.next()) {
      Map map = this.newMap(clazz);
      for (int i = 1; i <= cct; i++) {
        Object value;
        if (types[i] < Types.BLOB) {
          value = rs.getObject(i);
          map.put(names[i], value);
          continue;
        }

        try {
          switch (types[i]) {
            case Types.CLOB:
              value = ConvertKit.to(rs.getClob(i), _ClobConverter.instance());
              break;
            case Types.NCLOB:
              value = ConvertKit.to(rs.getNClob(i), _NClobConverter.instance());
              break;
            case Types.BLOB:
              value = ConvertKit.to(rs.getBlob(i), _BlobConverter.instance());
              break;
            default:
              value = rs.getObject(i);
              break;
          }
        } catch (TrydbException e) {
          Throwable cause = e.getCause();
          if (cause instanceof SQLException)
            throw (SQLException) cause;
          throw e;
        }
        map.put(names[i], value);
      }
      rets.add(map);
    }

    return rets;
  }
}
