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
package io.enoa.stove.template.thr;

import io.enoa.toolkit.thr.EoException;

public class CommandNotFoundException extends EoException {
  public CommandNotFoundException() {
  }

  public CommandNotFoundException(String message, Object... format) {
    super(message, format);
  }

  public CommandNotFoundException(String message, Throwable cause, Object... format) {
    super(message, cause, format);
  }

  public CommandNotFoundException(Throwable cause) {
    super(cause);
  }

  public CommandNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object... format) {
    super(message, cause, enableSuppression, writableStackTrace, format);
  }
}
