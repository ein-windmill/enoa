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
// Copyright (C) 1999-2001 by Jason Hunter <jhunter_AT_acm_DOT_org>.
// All rights reserved.  Use of this class is limited.
// Please see the LICENSE for more information.

package io.enoa.repeater.cos;

/**
 * A <code>Part</code> is an abstract upload part which represents an
 * <code>INPUT</code> form element in a <code>multipart/form-data</code> form
 * submission.
 *
 * @author Geoff Soutter
 * @version 1.0, 2000/10/27, initial revision
 * @see FilePart
 * @see ParamPart
 */
public abstract class Part {
  private String name;

  /**
   * Constructs an upload part with the given name.
   */
  Part(String name) {
    this.name = name;
  }

  /**
   * Returns the name of the form element that this Part corresponds to.
   *
   * @return the name of the form element that this Part corresponds to.
   */
  public String getName() {
    return name;
  }

  /**
   * Returns true if this Part is a FilePart.
   *
   * @return true if this is a FilePart.
   */
  public boolean isFile() {
    return false;
  }

  /**
   * Returns true if this Part is a ParamPart.
   *
   * @return true if this is a ParamPart.
   */
  public boolean isParam() {
    return false;
  }
}
