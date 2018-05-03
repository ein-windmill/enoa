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

import io.enoa.toolkit.bean.BeanKit;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.namecase.NamecaseKit;
import io.enoa.toolkit.namecase.NamecaseType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

class BeanBuilder<T> implements IBuilder<T> {
  @Override
  public List<T> build(ResultSet rs, Class<T> clazz) throws SQLException {
    List<Map> data = TrydbBuilder.build(rs, Map.class);
    List<T> rets = BeanKit.reductionMaps(data, clazz, NamecaseKit.namecase(NamecaseType.CASE_UNDERLINE), false);
    CollectionKit.clear(data);
    return rets;
  }
}
