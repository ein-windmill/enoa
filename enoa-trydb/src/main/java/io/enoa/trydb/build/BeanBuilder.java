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

import io.enoa.toolkit.bean.BeanKit;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.trydb.TrydbConfig;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

class BeanBuilder<T> implements IRsBuilder<T> {
  @Override
  public List<T> build(ResultSet rs, Class<T> clazz, TrydbConfig config) throws SQLException {
    List<Map> data = RsBuilder.build(rs, Map.class, config);
    List<T> rets = BeanKit.reductionMaps(data, clazz, config.lister(), config.namecase(), false, config.ignorecase());
    CollectionKit.clear(data);
    return rets;
  }
}
