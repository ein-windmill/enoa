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

import io.enoa.toolkit.convert.ConvertKit;
import io.enoa.toolkit.mark.IMark;
import io.enoa.toolkit.number.NumberKit;
import io.enoa.yosart.kernel.http.YoRequest;

import java.util.Arrays;

public class AoNumberValid extends AoObjectValid<AoNumberValid> {

  private YoRequest request;
  private boolean greenlight;
  private boolean valueMode;
  private String name;
  private boolean verifyIsNumber;

  AoNumberValid(YoRequest request, ValidImpl valid, String name) {
    super(request, valid, name);
    this.request = request;
    this.greenlight = valid.greenlight;
    this.valueMode = valid.valueMode;
    this.name = name;
    this.verifyIsNumber = false;
  }

  public AoNumberValid is(IMark mark, String message) throws ValidException {
    if (this.greenlight)
      return this;
//    if (!super.verified)
    super.blank(mark, message);
    String val = super.value(this.name, this.valueMode);
    if (!NumberKit.isNumber(val))
      throw new ValidException(mark, message);

    this.verifyIsNumber = true;
    return this;
  }

  public AoNumberValid is(int mark, String message) throws ValidException {
    return this.is(MarkBuilder.build(mark), message);
  }

  public AoNumberValid is(IMark mark) throws ValidException {
    return this.is(mark, null);
  }

  public AoNumberValid is(int mark) throws ValidException {
    return this.is(MarkBuilder.build(mark), null);
  }

  public AoNumberValid is(String message) throws ValidException {
    return this.is(ValidException.FAIL, message);
  }

  /**
   * 是否純數字
   *
   * @param negative 是否支持負數
   * @param mark     mark
   * @param message  message
   * @return AoNumberValid
   * @throws ValidException ValidException
   */
  public AoNumberValid digit(boolean negative, IMark mark, String message) throws ValidException {
    if (this.greenlight)
      return this;
    if (!this.verifyIsNumber)
      this.is(mark, message);
    String val = super.value(this.name, this.valueMode);
    if (!NumberKit.isDigit(val, negative))
      throw new ValidException(mark, message);
    return this;
  }

  public AoNumberValid digit(boolean negative, int mark, String message) throws ValidException {
    return this.digit(negative, MarkBuilder.build(mark), message);
  }

  public AoNumberValid digit(boolean negative, IMark mark) throws ValidException {
    return this.digit(negative, mark, null);
  }

  public AoNumberValid digit(boolean negative, int mark) throws ValidException {
    return this.digit(negative, mark, null);
  }

  public AoNumberValid digit(boolean negative, String message) throws ValidException {
    return this.digit(negative, ValidException.FAIL, message);
  }

  public AoNumberValid digit(IMark mark, String message) throws ValidException {
    return this.digit(false, mark, message);
  }

  public AoNumberValid digit(int mark, String message) throws ValidException {
    return this.digit(false, MarkBuilder.build(mark), message);
  }

  public AoNumberValid digit(IMark mark) throws ValidException {
    return this.digit(false, mark, null);
  }

  public AoNumberValid digit(int mark) throws ValidException {
    return this.digit(false, mark, null);
  }

  public AoNumberValid digit(String message) throws ValidException {
    return this.digit(false, ValidException.FAIL, message);
  }

  public AoNumberValid min(int min, IMark mark, String message) throws ValidException {
    if (this.greenlight)
      return this;
    if (!this.verifyIsNumber)
      this.is(mark, message);
    int val = ConvertKit.integer(super.value(this.name, this.valueMode));
    if (val < min)
      throw new ValidException(mark, message);
    return this;
  }

  public AoNumberValid min(int min, int mark, String message) throws ValidException {
    return this.min(min, MarkBuilder.build(mark), message);
  }

  public AoNumberValid min(int min, IMark mark) throws ValidException {
    return this.min(min, mark, null);
  }

  public AoNumberValid min(int min, int mark) throws ValidException {
    return this.min(min, MarkBuilder.build(mark));
  }

  public AoNumberValid min(int min, String message) throws ValidException {
    return this.min(min, ValidException.FAIL, message);
  }


  public AoNumberValid max(int max, IMark mark, String message) throws ValidException {
    if (this.greenlight)
      return this;
    if (!this.verifyIsNumber)
      this.is(mark, message);
    int val = ConvertKit.integer(super.value(this.name, this.valueMode));
    if (val > max)
      throw new ValidException(mark, message);
    return this;
  }

  public AoNumberValid max(int max, int mark, String message) throws ValidException {
    return this.max(max, MarkBuilder.build(mark), message);
  }

  public AoNumberValid max(int max, IMark mark) throws ValidException {
    return this.max(max, mark, null);
  }

  public AoNumberValid max(int max, int mark) throws ValidException {
    return this.max(max, mark, null);
  }

  public AoNumberValid max(int max, String message) throws ValidException {
    return this.max(max, ValidException.FAIL, message);
  }

  public AoNumberValid range(int start, int end, IMark mark, String message) throws ValidException {
    if (this.greenlight)
      return this;
    if (!this.verifyIsNumber)
      this.is(mark, message);
    int val = ConvertKit.integer(super.value(this.name, this.valueMode));
    if (val < start)
      throw new ValidException(mark, message);
    if (val > end)
      throw new ValidException(mark, message);
    return this;
  }

  public AoNumberValid range(int start, int end, int mark, String message) throws ValidException {
    return this.range(start, end, MarkBuilder.build(mark), message);
  }

  public AoNumberValid range(int start, int end, IMark mark) throws ValidException {
    return this.range(start, end, mark, null);
  }

  public AoNumberValid range(int start, int end, int mark) throws ValidException {
    return this.range(start, end, mark, null);
  }

  public AoNumberValid range(int start, int end, String message) throws ValidException {
    return this.range(start, end, ValidException.FAIL, message);
  }

  public AoNumberValid allow(int[] allows, IMark mark, String message) throws ValidException {
    if (this.greenlight)
      return this;
    if (!this.verifyIsNumber)
      this.is(mark, message);
    int val = ConvertKit.integer(super.value(this.name, this.valueMode));
    if (Arrays.stream(allows).anyMatch(a -> a == val))
      return this;
    throw new ValidException(mark, message);
  }

  public AoNumberValid allow(int[] allows, int mark, String message) throws ValidException {
    return this.allow(allows, MarkBuilder.build(mark), message);
  }

  public AoNumberValid allow(int[] allows, IMark mark) throws ValidException {
    return this.allow(allows, mark, null);
  }

  public AoNumberValid allow(int[] allows, int mark) throws ValidException {
    return this.allow(allows, mark, null);
  }

  public AoNumberValid allow(int[] allows, String message) throws ValidException {
    return this.allow(allows, ValidException.FAIL, message);
  }

  public AoNumberValid lt(String otherName, IMark mark, String message) throws ValidException {
    if (this.greenlight)
      return this;
    this.is(mark, message);
    String originName = this.name;
    this.name = otherName;
    this.is(mark, message);
    this.name = originName;
    int self = ConvertKit.integer(super.value(this.name, this.valueMode));
    int target = ConvertKit.integer(super.value(otherName, false));
    if (self < target)
      return this;
    throw new ValidException(mark, message);
  }

  public AoNumberValid lt(String otherName, int mark, String message) throws ValidException {
    return this.lt(otherName, MarkBuilder.build(mark), message);
  }

  public AoNumberValid lt(String otherName, IMark mark) throws ValidException {
    return this.lt(otherName, mark, null);
  }

  public AoNumberValid lt(String otherName, int mark) throws ValidException {
    return this.lt(otherName, MarkBuilder.build(mark), null);
  }

  public AoNumberValid lt(String otherName, String message) throws ValidException {
    return this.lt(otherName, ValidException.FAIL, message);
  }

  public AoNumberValid lte(String otherName, IMark mark, String message) throws ValidException {
    if (this.greenlight)
      return this;
    this.is(mark, message);
    String originName = this.name;
    this.name = otherName;
    this.is(mark, message);
    this.name = originName;
    int self = ConvertKit.integer(super.value(this.name, this.valueMode));
    int target = ConvertKit.integer(super.value(otherName, false));
    if (self <= target)
      return this;
    throw new ValidException(mark, message);
  }

  public AoNumberValid lte(String otherName, int mark, String message) throws ValidException {
    return this.lte(otherName, MarkBuilder.build(mark), message);
  }

  public AoNumberValid lte(String otherName, IMark mark) throws ValidException {
    return this.lte(otherName, mark, null);
  }

  public AoNumberValid lte(String otherName, int mark) throws ValidException {
    return this.lte(otherName, MarkBuilder.build(mark), null);
  }

  public AoNumberValid lte(String otherName, String message) throws ValidException {
    return this.lte(otherName, ValidException.FAIL, message);
  }


  public AoNumberValid gt(String otherName, IMark mark, String message) throws ValidException {
    if (this.greenlight)
      return this;
    this.is(mark, message);
    String originName = this.name;
    this.name = otherName;
    this.is(mark, message);
    this.name = originName;
    int self = ConvertKit.integer(super.value(this.name, this.valueMode));
    int target = ConvertKit.integer(super.value(otherName, false));
    if (self > target)
      return this;
    throw new ValidException(mark, message);
  }


  public AoNumberValid gt(String otherName, int mark, String message) throws ValidException {
    return this.gt(otherName, MarkBuilder.build(mark), message);
  }

  public AoNumberValid gt(String otherName, IMark mark) throws ValidException {
    return this.gt(otherName, mark, null);
  }

  public AoNumberValid gt(String otherName, int mark) throws ValidException {
    return this.gt(otherName, MarkBuilder.build(mark), null);
  }

  public AoNumberValid gt(String otherName, String message) throws ValidException {
    return this.gt(otherName, ValidException.FAIL, message);
  }

  public AoNumberValid gte(String otherName, IMark mark, String message) throws ValidException {
    if (this.greenlight)
      return this;
    this.is(mark, message);
    String originName = this.name;
    this.name = otherName;
    this.is(mark, message);
    this.name = originName;
    int self = ConvertKit.integer(super.value(this.name, this.valueMode));
    int target = ConvertKit.integer(super.value(otherName, false));
    if (self >= target)
      return this;
    throw new ValidException(mark, message);
  }


  public AoNumberValid gte(String otherName, int mark, String message) throws ValidException {
    return this.gte(otherName, MarkBuilder.build(mark), message);
  }

  public AoNumberValid gte(String otherName, IMark mark) throws ValidException {
    return this.gte(otherName, mark, null);
  }

  public AoNumberValid gte(String otherName, int mark) throws ValidException {
    return this.gte(otherName, MarkBuilder.build(mark), null);
  }

  public AoNumberValid gte(String otherName, String message) throws ValidException {
    return this.gte(otherName, ValidException.FAIL, message);
  }

  public AoNumberValid eq(String otherName, IMark mark, String message) throws ValidException {
    if (this.greenlight)
      return this;
    this.is(mark, message);
    String originName = this.name;
    this.name = otherName;
    this.is(mark, message);
    this.name = originName;
    int self = ConvertKit.integer(super.value(this.name, this.valueMode));
    int target = ConvertKit.integer(super.value(otherName, false));
    if (self == target)
      return this;
    throw new ValidException(mark, message);
  }

  public AoNumberValid eq(String otherName, int mark, String message) throws ValidException {
    return this.eq(otherName, MarkBuilder.build(mark), message);
  }

  public AoNumberValid eq(String otherName, IMark mark) throws ValidException {
    return this.eq(otherName, mark, null);
  }

  public AoNumberValid eq(String otherName, int mark) throws ValidException {
    return this.eq(otherName, MarkBuilder.build(mark), null);
  }

  public AoNumberValid eq(String otherName, String message) throws ValidException {
    return this.eq(otherName, ValidException.FAIL, message);
  }

}
