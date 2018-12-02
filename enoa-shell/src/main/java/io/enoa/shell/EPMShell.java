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
package io.enoa.shell;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EPMShell {

  private static class Holder {
    private static final EPMShell INSTANCE = new EPMShell();
  }

  static EPMShell instance() {
    return Holder.INSTANCE;
  }

  private Map<String, Shell> map;

  private EPMShell() {
    this.map = new ConcurrentHashMap<>();
  }

  public EPMShell install(String name, Shell shell) {
    this.map.put(name, shell);
    return this;
  }

  public EPMShell install(Shell shell) {
    return this.install("main", shell);
  }

  public void uninstall(String name) {
    this.map.remove(name);
  }

  public void uninstall() {
    this.uninstall("main");
  }


  Shell use(String name) {
    return this.map.get(name);
  }

  Shell use() {
    return this.map.get("main");
  }

}
