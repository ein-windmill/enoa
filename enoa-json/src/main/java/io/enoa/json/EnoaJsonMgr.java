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
package io.enoa.json;

import io.enoa.json.provider.enoa.EoJsonProvider;

/**
 * enoa - io.enoa.json
 */
public class EnoaJsonMgr {

  private static class Holder {
    private static EnoaJsonMgr INSTANCE = new EnoaJsonMgr();
  }


  private static EoJsonFactory defJsonFactory = new EoJsonProvider();

  static String defDatePattern = null;


  public static EnoaJsonMgr instance() {
    return Holder.INSTANCE;
  }

  public void defJsonFactory(EoJsonFactory factory) {
    if (factory == null)
      throw new IllegalArgumentException("Json factory can not be null.");
    defJsonFactory = factory;
  }

  public void defDatePattern(String pattern) {
    if (pattern == null || "".equals(pattern))
      throw new IllegalArgumentException("defaultDatePattern can not be blank.");
    defDatePattern = pattern;
  }

  public EnoaJson json() {
    return defJsonFactory.json();
  }
}
