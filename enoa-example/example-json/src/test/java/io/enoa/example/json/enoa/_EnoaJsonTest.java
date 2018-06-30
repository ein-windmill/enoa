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
package io.enoa.example.json.enoa;

import org.junit.Ignore;
import org.junit.Test;

import java.util.Locale;

/**
 * Created by ein on 2017/8/20.
 */
@Ignore
public class _EnoaJsonTest {


  @Test
  public void testLang() {
    Locale locale = Locale.getDefault();
    System.out.println(locale.getLanguage());
    System.out.println(locale.getCountry());
  }

}
