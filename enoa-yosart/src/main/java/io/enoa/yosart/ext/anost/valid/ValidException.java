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

import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.mark.IMark;
import io.enoa.toolkit.mark.IMarkIx;
import io.enoa.yosart.ext.anost.hook.HookException;

public class ValidException extends HookException {

  private IMark mark;

  public ValidException(IMark mark) {
    super();
    this.mark = mark;
  }

  public ValidException(IMark mark, String message, Object... format) {
    super(message, format);
    this.mark = mark;
  }

  public ValidException(IMark mark, String message, Throwable cause, Object... format) {
    super(message, cause, format);
    this.mark = mark;
  }

  public ValidException(IMark mark, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object... format) {
    super(message, cause, enableSuppression, writableStackTrace, format);
    this.mark = mark;
  }

  public ValidException(String message) {
    this(message, CollectionKit.emptyArray(Object.class));
    this.mark = FAIL;
  }

  public ValidException(String message, Object... format) {
    super(message, format);
    this.mark = FAIL;
  }

  public ValidException(String message, Throwable cause, Object... format) {
    super(message, cause, format);
    this.mark = FAIL;
  }

  public ValidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object... format) {
    super(message, cause, enableSuppression, writableStackTrace, format);
    this.mark = FAIL;
  }

  public IMark mark() {
    return this.mark;
  }

  public static final IMarkIx FAIL = () -> -1;

}
