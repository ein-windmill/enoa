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
package io.enoa.template;

public class Template {

  public static EPMTemplate epm() {
    return EPMTemplate.instance();
  }

  public static EnoaEngine use(String name) {
    return epm().engine(name);
  }

  public static EnoaEngine use() {
    return epm().engine();
  }

  public static EnoaTemplate template(String viewName) {
    return use().template(viewName);
  }

  public static Object originEngine() {
    return use().originEngine();
  }

}
