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
package io.enoa.tryjson.converter;

import io.enoa.toolkit.EoConst;
import io.enoa.toolkit.text.TextKit;
import io.enoa.tryjson.Tryjson;
import io.enoa.tryjson.mark.DateFormatStrategy;

import java.text.DateFormat;
import java.util.Date;

class _DateConverter implements EsonConverter<Date> {

  private static class Holder {
    private static final _DateConverter INSTANCE = new _DateConverter();
  }

  static _DateConverter instance() {
    return Holder.INSTANCE;
  }

  @Override
  public String json(Date date, int depth, ConvConf conf) {
    if (date == null)
      return null;

    DateFormatStrategy dateFormatStrategy = Tryjson.epm().config().dateFormatStrategy();
    if (dateFormatStrategy == DateFormatStrategy.TIMESTAMP)
      return String.valueOf(date.getTime());

    String dateFormat = conf.dateFormat() == null ?
      Tryjson.epm().config().dateFormat() :
      conf.dateFormat();
    if (dateFormat == null)
      dateFormat = EoConst.DEF_FORMAT_DATE;

    DateFormat df = __ConverterCache.dateFormat(dateFormat);
    return TextKit.union("\"", df.format(date), "\"");
  }

}
