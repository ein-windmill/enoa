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
package io.enoa.tryjson.eson.parser.def;

import io.enoa.toolkit.mark.IMarkIx;

public enum TokenType implements IMarkIx {

  BEGIN_OBJECT(1),
  END_OBJECT(2),
  BEGIN_ARRAY(4),
  END_ARRAY(8),
  NULL(16),
  NUMBER(32),
  STRING(64),
  BOOLEAN(128),
  SEP_COLON(256),
  SEP_COMMA(512),
  END_DOCUMENT(1024)

  //
  ;

  private final int ix;

  TokenType(int ix) {
    this.ix = ix;
  }

  @Override
  public int ix() {
    return ix;
  }

  public static TokenType of(Integer ix) {
    if (ix == null)
      return null;
    for (TokenType type : TokenType.values()) {
      if (type.ix == ix)
        return type;
    }
    return null;
  }
}
