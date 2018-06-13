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
package io.enoa.toolkit.date;

import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.toolkit.thr.EoException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DateKit {

  private DateKit() {
  }

  private static Map<String, SimpleDateFormat> CACHE;

  private static DateFormat dateFormat(String format) {
    if (CACHE == null)
      CACHE = new ConcurrentHashMap<>();

    SimpleDateFormat sdf = CACHE.get(format);
    if (sdf == null) {
      sdf = new SimpleDateFormat(format);
      CACHE.put(format, sdf);
    }
    return sdf;
  }

  public static Date parse(String text, String format) {
    try {
      if (text == null)
//        throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.toolkit.date_parse_text_null"));
        return null;
      if (format == null)
        throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.toolkit.date_parse_format_null"));

      return dateFormat(format).parse(text);
    } catch (ParseException e) {
      throw new EoException(e.getMessage(), e);
    }
  }


//  /**
//   * 自動偵測字符串時間進行轉換
//   * 尚未完善
//   * 另因時間格式並不固定, 很多, 也不一定能實現
//   * @param text text
//   * @return Date
//   */
//  @Deprecated
//  public static Date parse(String text) {
//    throw new RuntimeException("Can not support now.");
//  }

  public static String format(Date date, String format) {
    try {
      DateFormat df = dateFormat(format);
      return df.format(date);
    } catch (Exception e) {
      throw new EoException(e.getMessage(), e);
    }
  }

  public static String format(long ts, String format) {
    return format(new Date(ts), format);
  }


}
