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

import io.enoa.toolkit.mark.IMark;

enum Symbol implements IMark {

  /**
   * 未被初始化的狀態
   */
  NONE,

  /**
   * json object 進入
   */
  JO_OPEN,

  /**
   * json object 結束
   */
  JO_CLOSE,

  /**
   * json array 進入
   */
  JA_OPEN,

  /**
   * json array 結束
   */
  JA_CLOSE,

  /**
   * 字符串开始
   */
  SBL_STRING_START,

  /**
   * 字符串结束
   */
  SBL_STRING_END,

  /**
   * json key value 对称符
   */
  JN_SYMMETRY,
  //
  ;

}
