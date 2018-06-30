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
package io.enoa.json.provider.enoa;

import io.enoa.json.EnoaJson;

import java.lang.reflect.Type;
import java.util.List;


/**
 * enoa - io.enoa.json.provider.enoa
 */
class _EnoaJson extends EnoaJson {

  private static final _JFinalJson jFinalJson = new _JFinalJson();

  private static final String DEF_TIP_NOTSUPPORT = "The default json provider (EoJsonProvider) can not support convert json string to object, please choose another json provider.";

//  @Override
//  public Object origin() {
//    return null;
//  }

  @Override
  public String toJson(Object object) {
    return jFinalJson.toJson(object);
  }

  @Override
  public String toJson(Object object, String format) {
    return jFinalJson.toJson(object, format);
  }

  @Override
  public <T> T parse(String json, Class<T> type) {
//    throw new RuntimeException(EnoaTipKit.message("eo.tip.json.parse.notsupport"));
    throw new RuntimeException(DEF_TIP_NOTSUPPORT);
  }

  @Override
  public <T> T parse(String json, Type type) {
//    throw new RuntimeException(EnoaTipKit.message("eo.tip.json.parse.notsupport"));
    throw new RuntimeException(DEF_TIP_NOTSUPPORT);
  }

  @Override
  public <T> List<T> parseArray(String json, Class<T> type) {
//    throw new RuntimeException(EnoaTipKit.message("eo.tip.json.parse.notsupport"));
    throw new RuntimeException(DEF_TIP_NOTSUPPORT);
  }


}
