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
package io.enoa.tryjson.eson.parser.tef;

import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.toolkit.mark.IMarkIx;
import io.enoa.tryjson.thr.TryjsonException;

enum TokenType implements IMarkIx {

  // {
  BEGIN_OBJECT(1),
  // }
  END_OBJECT(2),
  // [
  BEGIN_ARRAY(4),
  // ]
  END_ARRAY(8),
  // null
  NULL(16),
  // number
  NUMBER(32),
  // string
  STRING(64),
  // boolean
  BOOLEAN(128),
  // :
  SEP_COLON(256),
  // ;
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

  /**
   * 期望 token
   *
   * @param type   token
   * @param others other tokens
   * @return int
   */
  public static int expect(TokenType type, TokenType... others) {
    int expect = type.ix;
    for (TokenType other : others) {
      expect = expect | other.ix;
    }
    return expect;
  }

  /**
   * 檢查是否滿足期望類型
   *
   * @param type   type
   * @param expect expect 期望值
   */
  public static void check(TokenType type, int expect) throws TryjsonException {
    if ((type.ix & expect) == 0)
      throw new TryjsonException(EnoaTipKit.message("eo.tip.tryjson.expect_token")); // Parse error, invalid Token.
  }
}
