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
package io.enoa.toolkit.bean;

import io.enoa.toolkit.convert.IConverter;

class $Bonfig$EnumOriginConverter implements IConverter<Object, Enum> {

  private static class Holder {
    private static final $Bonfig$EnumOriginConverter INSTANCE = new $Bonfig$EnumOriginConverter();
  }

  static $Bonfig$EnumOriginConverter instance() {
    return Holder.INSTANCE;
  }

  @Override
  public Object convert(Enum origin) {
    return origin;
  }
}
