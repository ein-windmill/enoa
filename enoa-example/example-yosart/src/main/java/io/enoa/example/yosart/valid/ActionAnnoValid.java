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
package io.enoa.example.yosart.valid;

import io.enoa.ext.bea.beaction.Validator;
import io.enoa.json.kit.JsonKit;
import io.enoa.toolkit.map.Kv;
import io.enoa.yosart.kernel.http.YoRequest;
import io.enoa.yosart.resp.Resp;

public class ActionAnnoValid extends Validator {

  @Override
  protected void valid(YoRequest req) {
    String valid = req.para("valid", "pass");
    if (!"pass".equals(valid))
      super.error("msg.valid.pass", "Can not pass.");
  }

  @Override
  protected void handle(Kv attr, Resp resp) {
    resp.renderHtml(JsonKit.toJson(attr));
  }
}
