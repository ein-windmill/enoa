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
package io.enoa.stove.template;

import io.enoa.stove.template.command.def._IfCmd;
import io.enoa.stove.template.eot._StoveTemplate;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class _StoveImpl implements Stove {

  private static Map<String, Stove> CACHE = new ConcurrentHashMap<>();

  private StoveConfig config;
  private SPM spm;

  _StoveImpl(String name, StoveConfig config) {
    if (CACHE.containsKey(name))
      throw new IllegalArgumentException("Stove exists.");
    this.config = config;
    this.init();
    CACHE.put(name, this);
  }

  private void init() {
    this.spm = new SPM();
    this.spm.install(new _IfCmd());
  }

  @Override
  public SPM spm() {
    return this.spm;
  }

  @Override
  public Tpl template(String tpl) {
    return _StoveTemplate.create(this.spm, this.config, tpl);
  }
}
