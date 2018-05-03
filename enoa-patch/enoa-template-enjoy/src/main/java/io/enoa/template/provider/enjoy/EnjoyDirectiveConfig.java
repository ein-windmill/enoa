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
package io.enoa.template.provider.enjoy;

import com.jfinal.template.Directive;

public class EnjoyDirectiveConfig {

  private final String name;
  private final Class<? extends Directive> clazz;

  public EnjoyDirectiveConfig(String name, Class<? extends Directive> clazz) {
    this.name = name;
    this.clazz = clazz;
  }

  public String name() {
    return name;
  }

  public Class<? extends Directive> clazz() {
    return clazz;
  }
}
