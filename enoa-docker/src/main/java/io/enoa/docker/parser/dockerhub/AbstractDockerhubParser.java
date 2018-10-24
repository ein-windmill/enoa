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
package io.enoa.docker.parser.dockerhub;

import io.enoa.docker.DockerhubConfig;
import io.enoa.docker.ret.dockerhub.HRet;
import io.enoa.docker.ret.registry.RResp;
import io.enoa.http.protocol.HttpResponse;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.map.Kv;
import io.enoa.toolkit.text.TextKit;

abstract class AbstractDockerhubParser<T> implements HIParser<T> {

  @Override
  public HRet<T> parse(DockerhubConfig config, RResp resp) {
    if (resp == null)
      return HRet.nullx();
    String string = resp.string();
    if (TextKit.isBlank(string))
      return HRet.nullx();
    HttpResponse response = resp.response();
    if (!response.header("content-type").contains("json"))
      return HRet.fail(resp, string);

    Kv kv = config.json().parse(string, Kv.class);
    if (kv.exists("detail")) {
      CollectionKit.clear(kv);
      return HRet.fail(resp, kv.string("detail"));
    }
    CollectionKit.clear(kv);
    T data = this.ok(config, resp);
    return HRet.ok(resp, data);
  }

  public abstract T ok(DockerhubConfig config, RResp resp);

}
