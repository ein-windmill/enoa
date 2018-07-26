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

import io.enoa.toolkit.mark.IMark;
import io.enoa.yosart.kernel.http.YoRequest;

public class AoBooleanValid extends AoObjectValid<AoBooleanValid> {

  private YoRequest request;
  private boolean greenlight;
  private boolean valueMode;
  private String name;

  AoBooleanValid(YoRequest request, ValidImpl valid, String name) {
    super(request, valid, name);
    this.request = request;
    this.greenlight = valid.greenlight;
    this.valueMode = valid.valueMode;
    this.name = name;
  }

  public AoBooleanValid is(IMark mark, String message) throws ValidException {
    if (this.greenlight)
      return this;
    if (!super.verified)
      super.blank(mark, message);
    String val = super.value(this.name, this.valueMode);
    if ("1".equals(val) || "0".equals(val) || "true".equalsIgnoreCase(val) || "false".equalsIgnoreCase(val))
      return this;
    throw new ValidException(mark, message);
  }

  public AoBooleanValid is(int mark, String message) throws ValidException {
    return this.is(MarkBuilder.build(mark), message);
  }

  public AoBooleanValid is(IMark mark) throws ValidException {
    return this.is(mark, null);
  }

  public AoBooleanValid is(int mark) throws ValidException {
    return this.is(MarkBuilder.build(mark), null);
  }

  public AoBooleanValid is(String message) throws ValidException {
    return this.is(ValidException.FAIL, message);
  }

}
