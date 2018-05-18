/*
 * Copyright (c) 2018, enoa (ein.windmill@outlook.com)
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
package io.enoa.toolkit.thr;

import io.enoa.toolkit.text.TextKit;

public class EoException extends RuntimeException {
  public EoException() {
  }

  public EoException(String message, Object... format) {
    super(message == null ? null : TextKit.format(message, format));
  }

  public EoException(String message, Throwable cause, Object... format) {
    super(message == null ? null : TextKit.format(message, format), cause);
  }

  public EoException(Throwable cause) {
    super(cause);
  }

  public EoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object... format) {
    super(message == null ? null : TextKit.format(message, format), cause, enableSuppression, writableStackTrace);
  }
}
