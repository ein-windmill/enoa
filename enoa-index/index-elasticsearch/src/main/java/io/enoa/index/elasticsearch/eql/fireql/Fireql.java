/*
 * Copyright 2016 ikidou
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
package io.enoa.index.elasticsearch.eql.fireql;

import io.enoa.index.elasticsearch.eql.Eql;
import io.enoa.toolkit.map.Kv;

import java.util.Map;

public class Fireql {


  public static EPMFeql epm() {
    return EPMFeql.instance();
  }

  public static EoFireql use(String name) {
    return epm().use(name);
  }

  public static EoFireql use() {
    return epm().use();
  }

  public static Eql eql(String name) {
    return use().eql(name);
  }

  public static Eql eql(String name, Kv kv) {
    return use().eql(name, kv);
  }

  public static Eql eql(String name, Map<String, ?> para) {
    return use().eql(name, para);
  }

}
