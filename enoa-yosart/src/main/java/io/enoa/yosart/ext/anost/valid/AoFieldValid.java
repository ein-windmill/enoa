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
import io.enoa.toolkit.text.TextKit;
import io.enoa.yosart.kernel.http.YoRequest;

public class AoFieldValid extends AoObjectValid<AoFieldValid> {

  private YoRequest request;
  private boolean greenlight;
  private boolean valueMode;
  private String name;

  AoFieldValid(YoRequest request, ValidImpl valid, String name) {
    super(request, valid, name);
    this.request = request;
    this.greenlight = valid.greenlight;
    this.valueMode = valid.valueMode;
    this.name = name;
  }

  public AoFieldValid same(String otherName, IMark mark, String message) throws ValidException {
    if (this.greenlight)
      return this;
    super.blank(mark, message);
    if (super.value(this.name, this.valueMode).equals(super.value(otherName, false)))
      return this;
    throw new ValidException(mark, message);
  }

  public AoFieldValid same(String otherName, int mark, String message) throws ValidException {
    return this.same(otherName, MarkBuilder.build(mark), message);
  }

  public AoFieldValid same(String otherName, IMark mark) throws ValidException {
    return this.same(otherName, mark, null);
  }

  public AoFieldValid same(String otherName, int mark) throws ValidException {
    return this.same(otherName, MarkBuilder.build(mark));
  }

  public AoFieldValid same(String otherName, String message) throws ValidException {
    return this.same(otherName, ValidException.FAIL, message);
  }

  public AoFieldValid needOne(String[] otherNames, IMark mark, String message) throws ValidException {
    if (this.greenlight)
      return this;
    String val = super.value(this.name, this.valueMode);
    boolean selfNull = TextKit.blanky(val);
    if (otherNames == null && selfNull)
      throw new ValidException(mark, message);
    boolean needs[] = new boolean[otherNames == null ? 1 : otherNames.length + 1];
    needs[0] = selfNull;
    if (otherNames != null) {
      int i = 0;
      for (String otherName : otherNames) {
        String oval = super.value(otherName, false);
        needs[i + 1] = TextKit.blanky(oval);
      }
    }
    for (boolean need : needs)
      if (!need)
        return this;

    throw new ValidException(mark, message);
  }

  public AoFieldValid needOne(String[] otherNames, int mark, String message) throws ValidException {
    return this.needOne(otherNames, MarkBuilder.build(mark), message);
  }

  public AoFieldValid needOne(String[] otherNames, IMark mark) throws ValidException {
    return this.needOne(otherNames, mark, null);
  }

  public AoFieldValid needOne(String[] otherNames, int mark) throws ValidException {
    return this.needOne(otherNames, mark, null);
  }

  public AoFieldValid needOne(String[] otherNames, String message) throws ValidException {
    return this.needOne(otherNames, ValidException.FAIL, message);
  }

  public AoFieldValid needOne(String otherName, IMark mark, String message) throws ValidException {
    return this.needOne(new String[]{otherName}, mark, message);
  }

  public AoFieldValid needOne(String otherName, int mark, String message) throws ValidException {
    return this.needOne(new String[]{otherName}, MarkBuilder.build(mark), message);
  }

  public AoFieldValid needOne(String otherName, IMark mark) throws ValidException {
    return this.needOne(new String[]{otherName}, mark, null);
  }

  public AoFieldValid needOne(String otherName, int mark) throws ValidException {
    return this.needOne(new String[]{otherName}, mark, null);
  }

  public AoFieldValid needOne(String otherName, String message) throws ValidException {
    return this.needOne(new String[]{otherName}, ValidException.FAIL, message);
  }

  public AoFieldValid together(String[] otherNames, IMark mark, String message) throws ValidException {
    if (this.greenlight)
      return this;
    if (TextKit.blanky(super.value(this.name, this.valueMode)))
      throw new ValidException(mark, message);
    for (String otherName : otherNames)
      if (TextKit.blanky(super.value(otherName, false)))
        throw new ValidException(mark, message);
    return this;
  }

  public AoFieldValid together(String[] otherNames, int mark, String message) throws ValidException {
    return this.together(otherNames, MarkBuilder.build(mark), message);
  }

  public AoFieldValid together(String[] otherNames, IMark mark) throws ValidException {
    return this.together(otherNames, mark, null);
  }

  public AoFieldValid together(String[] otherNames, int mark) throws ValidException {
    return this.together(otherNames, MarkBuilder.build(mark));
  }

  public AoFieldValid together(String[] otherNames, String message) throws ValidException {
    return this.together(otherNames, ValidException.FAIL, message);
  }

  public AoFieldValid together(String otherName, IMark mark, String message) throws ValidException {
    return this.together(new String[]{otherName}, mark, message);
  }

  public AoFieldValid together(String otherName, int mark, String message) throws ValidException {
    return this.together(otherName, MarkBuilder.build(mark), message);
  }

  public AoFieldValid together(String otherName, IMark mark) throws ValidException {
    return this.together(otherName, mark, null);
  }

  public AoFieldValid together(String otherName, int mark) throws ValidException {
    return this.together(otherName, MarkBuilder.build(mark));
  }

  public AoFieldValid together(String otherName, String message) throws ValidException {
    return this.together(otherName, ValidException.FAIL, message);
  }

}
