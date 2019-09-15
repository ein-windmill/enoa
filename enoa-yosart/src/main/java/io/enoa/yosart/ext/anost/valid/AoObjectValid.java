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

import io.enoa.toolkit.is.Is;
import io.enoa.toolkit.mark.IMark;
import io.enoa.yosart.kernel.http.YoRequest;

public class AoObjectValid<T extends AoObjectValid> {


  private YoRequest request;
  private boolean greenlight;
  private boolean valueMode;
  private String name;

  boolean verified;

  AoObjectValid(YoRequest request, ValidImpl valid, String name) {
    this.request = request;
    this.greenlight = valid.greenlight;
    this.valueMode = valid.valueMode;
    this.name = name;
    this.verified = false;
  }

  String value(String target, boolean valueMode) {
    if (valueMode)
      return target;

    String value = this.request.para(target);
    if (Is.truthy(value))
      return value;
    value = this.request.variable(target);
    return value;
  }


  public T blank(IMark mark, String message) throws ValidException {
    if (this.greenlight)
      return (T) this;
    if (this.name == null)
      throw new ValidException(mark, message);
    if (Is.not().truthy(this.value(this.name, this.valueMode)))
      throw new ValidException(mark, message);
    this.verified = true;
    return (T) this;
  }

  public T blank(int mark, String message) throws ValidException {
    return this.blank(MarkBuilder.build(mark), message);
  }

  public T blank(IMark mark) throws ValidException {
    return this.blank(mark, null);
  }

  public T blank(int mark) throws ValidException {
    return this.blank(mark, null);
  }

  public T blank(String message) throws ValidException {
    return this.blank(ValidException.FAIL, message);
  }

}
