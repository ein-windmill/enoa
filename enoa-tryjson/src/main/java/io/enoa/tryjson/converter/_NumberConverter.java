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

class _NumberConverter implements EsonConverter<Number> {

  private static class Holder {
    private static final _NumberConverter INSTANCE = new _NumberConverter();
  }

  static _NumberConverter instance() {
    return Holder.INSTANCE;
  }

  @Override
  public String json(Number number, int depth, ConvConf conf) {
    if (number == null)
      return null;

    if (number instanceof Double) {
      if (((Double) number).isInfinite() || ((Double) number).isNaN())
        return null;
    }
    if (number instanceof Float) {
      if (((Float) number).isInfinite() || ((Float) number).isNaN())
        return null;
    }
    return number.toString();
  }
}
