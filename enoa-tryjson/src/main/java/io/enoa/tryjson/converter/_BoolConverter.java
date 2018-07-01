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

import io.enoa.tryjson.Esonfig;

class _BoolConverter implements EsonConverter<Boolean> {
  private static class Holder {
    private static final _BoolConverter INSTANCE = new _BoolConverter();
  }

  static _BoolConverter instance() {
    return Holder.INSTANCE;
  }

  @Override
  public String json(Boolean bool, int depth, Esonfig conf) {
    if (bool == null)
      return null;
    return bool.toString();
  }
}
