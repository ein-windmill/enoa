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
import io.enoa.toolkit.number.NumberKit;
import io.enoa.toolkit.text.TextKit;
import io.enoa.yosart.ext.anost.valid.ivalid.IRowValid;
import io.enoa.yosart.kernel.http.YoRequest;

public class AoArrayValid extends AoObjectValid<AoArrayValid> {

  private YoRequest request;
  private boolean greenlight;
  private String name;

  AoArrayValid(YoRequest request, ValidImpl valid, String name) {
    super(request, valid, name);
    this.request = request;
    this.greenlight = valid.greenlight;
    this.name = name;
  }

  public AoArrayValid row(IRowValid valid, IMark mark, String message) throws ValidException {
    if (this.greenlight)
      return this;
    if (!super.verified)
      super.blank(mark, message);

    String[] vals = this.request.paraValues(this.name);
    for (String val : vals)
      if (!valid.pass(val))
        throw new ValidException(mark, message);

    return this;
  }

  public AoArrayValid row(IRowValid valid, int mark, String message) throws ValidException {
    return this.row(valid, MarkBuilder.build(mark), message);
  }

  public AoArrayValid row(IRowValid valid, IMark mark) throws ValidException {
    return this.row(valid, mark, null);
  }

  public AoArrayValid row(IRowValid valid, int mark) throws ValidException {
    return this.row(valid, MarkBuilder.build(mark));
  }

  public AoArrayValid row(IRowValid valid, String message) throws ValidException {
    return this.row(valid, ValidException.FAIL, message);
  }


  public AoArrayValid size(int len, IMark mark, String message) throws ValidException {
    if (this.greenlight)
      return this;
    if (!super.verified)
      super.blank(mark, message);
    String[] vals = this.request.paraValues(this.name);
    if (vals.length != len)
      throw new ValidException(mark, message);
    return this;
  }

  public AoArrayValid size(int len, int mark, String message) throws ValidException {
    return this.size(len, MarkBuilder.build(mark), message);
  }

  public AoArrayValid size(int len, IMark mark) throws ValidException {
    return this.size(len, mark, null);
  }

  public AoArrayValid size(int len, int mark) throws ValidException {
    return this.size(len, MarkBuilder.build(mark));
  }

  public AoArrayValid size(int len, String message) throws ValidException {
    return this.size(len, ValidException.FAIL, message);
  }

  public AoArrayValid sizeMin(int min, IMark mark, String message) throws ValidException {
    if (this.greenlight)
      return this;
    if (!super.verified)
      super.blank(mark, message);
    String[] vals = this.request.paraValues(this.name);
    if (vals.length < min)
      throw new ValidException(mark, message);
    return this;
  }

  public AoArrayValid sizeMin(int min, int mark, String message) throws ValidException {
    return this.sizeMin(min, MarkBuilder.build(mark), message);
  }

  public AoArrayValid sizeMin(int min, IMark mark) throws ValidException {
    return this.sizeMin(min, mark, null);
  }

  public AoArrayValid sizeMin(int min, int mark) throws ValidException {
    return this.sizeMin(min, MarkBuilder.build(mark));
  }

  public AoArrayValid sizeMin(int min, String message) throws ValidException {
    return this.sizeMin(min, ValidException.FAIL, message);
  }

  public AoArrayValid sizeMax(int max, IMark mark, String message) throws ValidException {
    if (this.greenlight)
      return this;
    if (!super.verified)
      super.blank(mark, message);
    String[] vals = this.request.paraValues(this.name);
    if (vals.length > max)
      throw new ValidException(mark, message);
    return this;
  }

  public AoArrayValid sizeMax(int max, int mark, String message) throws ValidException {
    return this.sizeMax(max, MarkBuilder.build(mark), message);
  }

  public AoArrayValid sizeMax(int max, IMark mark) throws ValidException {
    return this.sizeMax(max, mark, null);
  }

  public AoArrayValid sizeMax(int max, int mark) throws ValidException {
    return this.sizeMax(max, MarkBuilder.build(mark));
  }

  public AoArrayValid sizeMax(int max, String message) throws ValidException {
    return this.sizeMax(max, ValidException.FAIL, message);
  }

  public AoArrayValid numberArray(IMark mark, String message) throws ValidException {
    if (this.greenlight)
      return this;
    if (!super.verified)
      super.blank(mark, message);

    this.blankMember(mark, message);
    return this.row(NumberKit::isNumber, mark, message);
  }

  public AoArrayValid numberArray(int mark, String message) throws ValidException {
    return this.numberArray(MarkBuilder.build(mark), message);
  }

  public AoArrayValid numberArray(IMark mark) throws ValidException {
    return this.numberArray(mark, null);
  }

  public AoArrayValid numberArray(int mark) throws ValidException {
    return this.numberArray(MarkBuilder.build(mark));
  }

  public AoArrayValid numberArray(String message) throws ValidException {
    return this.numberArray(ValidException.FAIL, message);
  }

  public AoArrayValid boolArray(IMark mark, String message) throws ValidException {
    if (this.greenlight)
      return this;
    if (!super.verified)
      super.blank(mark, message);

    this.blankMember(mark, message);
    return this.row(val -> "1".equals(val) || "0".equals(val) || "true".equalsIgnoreCase(val) || "false".equalsIgnoreCase(val),
      mark, message);
  }

  public AoArrayValid boolArray(int mark, String message) throws ValidException {
    return this.boolArray(MarkBuilder.build(mark), message);
  }

  public AoArrayValid boolArray(IMark mark) throws ValidException {
    return this.boolArray(mark, null);
  }

  public AoArrayValid boolArray(int mark) throws ValidException {
    return this.boolArray(MarkBuilder.build(mark));
  }

  public AoArrayValid boolArray(String message) throws ValidException {
    return this.boolArray(ValidException.FAIL, message);
  }

  public AoArrayValid blankMember(IMark mark, String message) throws ValidException {
    if (this.greenlight)
      return this;
    if (!super.verified)
      super.blank(mark, message);

    return this.row(TextKit::notBlank, mark, message);
  }

  public AoArrayValid blankMember(int mark, String message) throws ValidException {
    return this.blankMember(MarkBuilder.build(mark), message);
  }

  public AoArrayValid blankMember(IMark mark) throws ValidException {
    return this.blankMember(mark, null);
  }

  public AoArrayValid blankMember(int mark) throws ValidException {
    return this.blankMember(MarkBuilder.build(mark));
  }

  public AoArrayValid blankMember(String message) throws ValidException {
    return this.blankMember(ValidException.FAIL, message);
  }

}
