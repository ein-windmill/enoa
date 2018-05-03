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
package io.enoa.toolkit.convert;

class _DateConverter implements IConverter<java.util.Date, String> {

  private static final String datePattern = "yyyy-MM-dd";
  private static final int timeStampWithoutSecPatternLen = "yyyy-MM-dd HH:mm".length();
  private static final int dateLen = datePattern.length();
  private static final String timeStampPattern = "yyyy-MM-dd HH:mm:ss";

  // java.util.Date 类型专为传统 java bean 带有该类型的 setter 方法转换做准备，万不可去掉
  // 经测试 JDBC 不会返回 java.util.Data 类型。java.sql.Date, java.sql.Time,java.sql.Timestamp 全部直接继承自 java.util.Data, 所以 getDate可以返回这三类数据
  @Override
  public java.util.Date convert(String s) {
    if (timeStampWithoutSecPatternLen == s.length()) {
      s = s + ":00";
    }
    if (s.length() > dateLen) {
      return ConvertKit.date(s, timeStampPattern);
    } else {
      return ConvertKit.date(s, datePattern);
    }
  }
}
