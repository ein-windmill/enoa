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
package io.enoa.yosart;

import io.enoa.toolkit.random.RandomKit;

public interface YoExt {

  enum Type {
    /**
     * boot hook
     */
    BOOT_HOOK(false),
    /**
     * after boot
     */
    BOOT_END(false),
    /**
     * router mapping
     */
    ROUTER(true),
    /**
     * static resource
     */
    ASSETS(true),
    /**
     * before start action
     */
    BEFORE_ACTION(false),
    /**
     * render
     */
    RENDER(false),
    /**
     * exception handler
     */
    EXCEPTION(true),
    /**
     * session extension
     */
    SESSION(true),
    //
    ;

    /**
     * only one allowed for this extension
     */
    private final boolean onlyOne;

    /**
     * @param onlyOne onlyOne
     */
    Type(boolean onlyOne) {
      this.onlyOne = onlyOne;
    }

    public boolean onlyOne() {
      return this.onlyOne;
    }
  }

  /**
   * extension type
   *
   * @return Type
   */
  Type type();

  /**
   * extension name
   *
   * @return String
   */
  String name();

  /**
   * extension version
   *
   * @return String
   */
  String version();

  /**
   * extension weight
   *
   * @return double
   */
  default double weight() {
    return RandomKit.nextDouble(1, 9);
  }

}
