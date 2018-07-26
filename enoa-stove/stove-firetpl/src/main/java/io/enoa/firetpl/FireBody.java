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
package io.enoa.firetpl;

import io.enoa.stove.api.StoveBody;

import java.util.Arrays;
import java.util.stream.Collectors;

public interface FireBody extends StoveBody {

  /**
   * template value
   *
   * @return String
   */
  String tpl();

  /**
   * template paras
   * like. sql select * from table where id=<paras>
   *
   * @return Object[]
   */
  Object[] paras();


  static FireBody create(String tpl, Object... paras) {
    return new FireBody() {

      @Override
      public String tpl() {
        return tpl;
      }

      @Override
      public Object[] paras() {
        return paras;
      }

      @Override
      public String toString() {
        StringBuilder ret = new StringBuilder();
        ret.append(this.tpl());
        if (this.paras() == null || this.paras().length == 0)
          return ret.toString();

        ret.append("       ----> [");
        ret.append(String.join(",", Arrays.stream(this.paras()).map(Object::toString).collect(Collectors.toList())));
        ret.append("]");
        return ret.toString();
      }
    };
  }

}
