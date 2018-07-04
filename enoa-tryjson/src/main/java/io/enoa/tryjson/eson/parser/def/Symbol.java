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

import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.mark.IMark;

enum Symbol implements IMark {

  /**
   * 未被初始化的狀態
   */
  NONE(null, null, CollectionKit.emptyArray(Character.class)),

  /**
   * json object 進入
   */
  JO_OPEN('{', '}', new Character[]{'"'}),

  /**
   * json object 結束
   */
  JO_CLOSE('}', null, new Character[]{','}),

  /**
   * json array 進入
   */
  JA_OPEN('[', ']', new Character[]{'{', 'N', 'B'}),

  /**
   * json array 結束
   */
  JA_CLOSE(']', null, new Character[]{','}),

  /**
   * 字符串
   */
  SBL_STRING('"', '"', CollectionKit.emptyArray(Character.class)),

  //
  ;

  private final Character left;
  private final Character right;
  private final Character[] allows;

  Symbol(Character left, Character right, Character[] allows) {
    this.left = left;
    this.right = right;
    this.allows = allows;
  }

  public Character left() {
    return this.left;
  }

  public Character right() {
    return this.right;
  }

  public Character[] allows() {
    return this.allows;
  }
}
