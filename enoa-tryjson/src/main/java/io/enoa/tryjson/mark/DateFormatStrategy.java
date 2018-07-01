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
package io.enoa.tryjson.mark;

import io.enoa.toolkit.mark.IMarkIx;

/**
 * 时间格式化策略
 */
public enum DateFormatStrategy implements IMarkIx {

  /**
   * 格式化为字符串
   */
  STRING(1),

  /**
   * 格式化为 uinux 时间戳
   */
  TIMESTAMP(2),

  //
  ;

  private final int ix;

  DateFormatStrategy(int ix) {
    this.ix = ix;
  }

  @Override
  public int ix() {
    return this.ix;
  }


}
