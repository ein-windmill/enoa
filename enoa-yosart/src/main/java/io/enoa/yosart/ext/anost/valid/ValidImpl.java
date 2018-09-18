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
package io.enoa.yosart.ext.anost.valid;

import io.enoa.toolkit.text.TextKit;
import io.enoa.yosart.ext.anost.valid.ivalid.ISpecialValid;
import io.enoa.yosart.kernel.http.YoRequest;

class ValidImpl implements Valid {

  private YoRequest request;

  boolean greenlight; // 绿灯同行, 不进行验证
  boolean valueMode; // 是否 value 验证模式, 无需从 request 获取

  ValidImpl(YoRequest request) {
    this.request = request;
    this.greenlight = false;
    this.valueMode = false;
  }

  @Override
  public AoExistsValid exists(String name) {
    ValidImpl valid = new ValidImpl(this.request);
    String val = this.valueMode ? name : valid.object(name).value(name, false);
    valid.greenlight = TextKit.blanky(val);
    valid.valueMode = this.valueMode;
    return new AoExistsValid(valid, name);
  }

  @Override
  public AoObjectValid object(String name) {
    return new AoObjectValid(this.request, this, name);
  }

  @Override
  public AoStringValid string(String name) {
    return new AoStringValid(this.request, this, name);
  }

  @Override
  public AoNumberValid number(String name) {
    return new AoNumberValid(this.request, this, name);
  }

  @Override
  public AoBooleanValid bool(String name) {
    return new AoBooleanValid(this.request, this, name);
  }

  @Override
  public AoFieldValid field(String name) {
    return new AoFieldValid(this.request, this, name);
  }

  @Override
  public AoArrayValid array(String name) {
    return new AoArrayValid(this.request, this, name);
  }

  @Override
  public AoValueValid value(String value) {
    ValidImpl valid = new ValidImpl(this.request);
    valid.valueMode = true;
    return new AoValueValid(valid, value);
  }

  @Override
  public Valid special(ISpecialValid valid) throws ValidException {
    if (this.greenlight)
      return this;
    valid.valid(this.request);
    return this;
  }
}
