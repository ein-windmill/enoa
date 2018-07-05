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
package io.enoa.tryjson.ext.detect;

import io.enoa.toolkit.convert.ConvertKit;
import io.enoa.toolkit.number.NumberKit;
import io.enoa.tryjson.Tsonfig;
import io.enoa.tryjson.thr.TryjsonException;

class DefaultTypeDetector implements ITypeDetector {


  private static class Holder {
    private static final DefaultTypeDetector INSTANCE = new DefaultTypeDetector();
  }

  static DefaultTypeDetector instance() {
    return Holder.INSTANCE;
  }

  @Override
  public Object detect(String val, Tsonfig config) throws TryjsonException {
    if (val == null)
      return null;

    // boolean 值类型转换
    if ("true".equalsIgnoreCase(val) || "false".equalsIgnoreCase(val)) {
      return ConvertKit.bool(val);
    }

    // 非数字直接加入
    if (!NumberKit.isNumber(val)) {
      return val;
    }

    // 浮点数转换 未考虑字符长度
    if (val.contains(".")) {
      return ConvertKit.doubler(val);
    }

    // 尝试转换字符为 int
    try {
      return ConvertKit.integer(val);
    } catch (Exception e) {
      // skip
      // 如果转换 int 失败, 数据可能是 long 类型
//      if (config.debug())
//        e.printStackTrace();
    }

    // 尝试转换字符为 long
    try {
      return ConvertKit.longer(val);
    } catch (Exception e) {
      // skip
      // long 类型转换失败
//      if (config.debug())
//        e.printStackTrace();
    }
    throw new TryjsonException(""); // 校验该字段未数字类型, 但是超出了数字类型所容许的长度, 一个错误的数字
  }

}
