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
package io.enoa.toolkit.namecase;

import io.enoa.toolkit.namecase.icase.CamelCaseLower;
import io.enoa.toolkit.namecase.icase.CamelCaseUpper;
import io.enoa.toolkit.namecase.icase.NoneCase;
import io.enoa.toolkit.namecase.icase.UnderlineCase;

import java.util.HashMap;
import java.util.Map;

public class NamecaseKit {

  private NamecaseKit() {

  }

  private static class Case {

    private static final Map<NamecaseType, INameCase> CASE_CACHE;

    static {
      CASE_CACHE = new HashMap<NamecaseType, INameCase>() {{
        put(NamecaseType.CASE_CAMEL_LOWER, new CamelCaseLower());
        put(NamecaseType.CASE_NONE, new NoneCase());
        put(NamecaseType.CASE_CAMEL_UPPER, new CamelCaseUpper());
        put(NamecaseType.CASE_UNDERLINE, new UnderlineCase());
      }};
    }

    public static String convert(NamecaseType type, String text) {
      return namecase(type).convert(text);
    }

    static INameCase namecase(NamecaseType type) {
      return CASE_CACHE.get(type);
    }

  }

  public static INameCase namecase(NamecaseType type) {
    return Case.namecase(type);
  }

  /**
   * 下劃線轉為小駝峰風格
   *
   * @param text text
   * @return String
   */
  public static String camelcaselower(String text) {
    return Case.convert(NamecaseType.CASE_CAMEL_LOWER, text);
  }

  /**
   * 下劃線轉大駝峯風格
   *
   * @param text text
   * @return String
   */
  public static String camelcaseupper(String text) {
    return Case.convert(NamecaseType.CASE_CAMEL_UPPER, text);
  }

  /**
   * 不轉換
   *
   * @param text text
   * @return String
   */
  public static String nonecase(String text) {
    return Case.convert(NamecaseType.CASE_NONE, text);
  }

  /**
   * 駝峯轉下劃線
   *
   * @param text text
   * @return String
   */
  public static String underline(String text) {
    return Case.convert(NamecaseType.CASE_UNDERLINE, text);
  }

  /**
   * 轉換
   *
   * @param text     text
   * @param namecase name case
   * @return String
   */
  public static String convert(String text, INameCase namecase) {
    return namecase.convert(text);
  }

}
