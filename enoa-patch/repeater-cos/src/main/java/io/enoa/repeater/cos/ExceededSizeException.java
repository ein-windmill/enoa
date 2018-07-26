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
// Copyright (C) 2007 by Jason Hunter <jhunter_AT_acm_DOT_org>.
// All rights reserved.  Use of this class is limited.
// Please see the LICENSE for more information.

package io.enoa.repeater.cos;

/**
 * Thrown to indicate an upload exceeded the maximum size.
 *
 * @author <b>Jason Hunter</b>, Copyright &#169; 2007
 * @version 1.0, 2007/04/11
 * @see com.oreilly.servlet.multipart.MultipartParser
 */
class ExceededSizeException extends RuntimeException {

  /**
   * Constructs a new ExceededSizeException with no detail message.
   */
  public ExceededSizeException() {
    super();
  }

  /**
   * Constructs a new ExceededSizeException with the specified
   * detail message.
   *
   * @param s the detail message
   */
  public ExceededSizeException(String s) {
    super(s);
  }
}
