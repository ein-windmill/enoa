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
package io.enoa.toolkit.convert;

class _BooleanConverter implements IConverter<Boolean, String> {
  // mysql type: bit, tinyint(1)
  private boolean primitive;

  _BooleanConverter(boolean primitive) {
    this.primitive = primitive;
  }

  @Override
  public Boolean convert(String origin) {
    return ConvertKit.bool(origin, this.primitive ? false : null);
  }
}
