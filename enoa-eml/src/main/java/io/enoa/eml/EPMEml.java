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
package io.enoa.eml;

import io.enoa.toolkit.eo.tip.EnoaTipKit;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EPMEml {

  private static class Holder {
    private static final EPMEml INSTANCE = new EPMEml();
  }

  static EPMEml instance() {
    return Holder.INSTANCE;
  }

  private EPMEml() {

  }

  private Map<String, Eml> emlMap = new ConcurrentHashMap<>();

  public void install(EoEmlSession sess) {
    this.install("main", sess);
  }

  public void install(String name, EoEmlSession sess) {
    if (this.emlMap.containsKey(name))
      throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.email.name_exists", name));
    this.emlMap.put(name, Eml.with(sess));
  }

  public Eml eml() {
    return this.eml("main");
  }

  public Eml eml(String name) {
    return this.emlMap.get(name);
  }


}
