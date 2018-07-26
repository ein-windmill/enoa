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

public class AoStringValid extends AoObjectValid<AoStringValid> {


  private YoRequest request;
  private String name;
  private boolean greenlight;
  private boolean valueMode;

  AoStringValid(YoRequest request, ValidImpl valid, String name) {
    super(request, valid, name);
    this.request = request;
    this.greenlight = valid.greenlight;
    this.valueMode = valid.valueMode;
    this.name = name;
  }


  public AoStringValid len(int len, IMark mark, String message) throws ValidException {
    if (this.greenlight)
      return this;
    if (!super.verified)
      super.blank(mark, message);
    String val = super.value(this.name, this.valueMode);
    if (val.length() > len)
      throw new ValidException(mark, message);
    return this;
  }

  public AoStringValid len(int len, int mark, String message) throws ValidException {
    return this.len(len, MarkBuilder.build(mark), message);
  }

  public AoStringValid len(int len, IMark mark) throws ValidException {
    return this.len(len, mark, null);
  }

  public AoStringValid len(int len, int mark) throws ValidException {
    return this.len(len, mark, null);
  }

  public AoStringValid len(int len, String message) throws ValidException {
    return this.len(len, ValidException.FAIL, message);
  }

  public AoStringValid email(IMark mark, String message) throws ValidException {
    if (this.greenlight)
      return this;
    if (!super.verified)
      super.blank(mark, message);
    String val = super.value(this.name, this.valueMode);
    boolean matches = val.matches("^[a-z0-9]+([._\\\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$");
    if (matches)
      return this;
    throw new ValidException(mark, message);
  }

  public AoStringValid email(int mark, String message) throws ValidException {
    return this.email(MarkBuilder.build(mark), message);
  }

  public AoStringValid email(IMark mark) throws ValidException {
    return this.email(mark, null);
  }

  public AoStringValid email(int mark) throws ValidException {
    return this.email(mark, null);
  }

  public AoStringValid email(String message) throws ValidException {
    return this.email(ValidException.FAIL, message);
  }

  public AoStringValid alphanumber(IMark mark, String message) throws ValidException {
    if (this.greenlight)
      return this;
    if (!super.verified)
      super.blank(mark, message);
    String val = super.value(this.name, this.valueMode);
    for (int i = val.length(); i-- > 0; ) {
      char c = val.charAt(i);
      if (
        (c >= 'a' && c <= 'z') ||
          (c >= 'A' && c <= 'Z') ||
          (c >= 48 && c <= 57)
        )
        continue;
      throw new ValidException(mark, message);
    }
    return this;
  }

  public AoStringValid alphanumber(int mark, String message) throws ValidException {
    return this.alphanumber(MarkBuilder.build(mark), message);
  }

  public AoStringValid alphanumber(IMark mark) throws ValidException {
    return this.alphanumber(mark, null);
  }

  public AoStringValid alphanumber(int mark) throws ValidException {
    return this.alphanumber(mark, null);
  }

  public AoStringValid alphanumber(String message) throws ValidException {
    return this.alphanumber(ValidException.FAIL, message);
  }

}
