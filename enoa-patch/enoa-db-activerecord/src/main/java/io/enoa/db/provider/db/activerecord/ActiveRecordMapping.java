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
package io.enoa.db.provider.db.activerecord;

import com.jfinal.plugin.activerecord.Model;

public class ActiveRecordMapping {

  private final String table;
  private final String primary;
  private final Class<? extends Model<?>> modelClass;

  public ActiveRecordMapping(String table, Class<? extends Model<?>> modelClass) {
    this(table, null, modelClass);
  }

  public ActiveRecordMapping(String table, String primary, Class<? extends Model<?>> modelClass) {
    this.table = table;
    this.primary = primary;
    this.modelClass = modelClass;
  }

  public String table() {
    return this.table;
  }

  public String primary() {
    return this.primary;
  }

  public Class<? extends Model<?>> modelClass() {
    return this.modelClass;
  }

}
