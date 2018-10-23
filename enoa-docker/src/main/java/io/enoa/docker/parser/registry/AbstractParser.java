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
package io.enoa.docker.parser.registry;

import io.enoa.docker.RegistryConfig;
import io.enoa.docker.ret.registry.RError;
import io.enoa.docker.ret.registry.RResp;
import io.enoa.docker.ret.registry.RRet;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.map.Kv;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

abstract class AbstractParser<T> implements RIParser<T> {
  @Override
  public RRet<T> parse(RegistryConfig config, RResp resp) {
    if (resp == null)
      return RRet.nullx();
    String contenttype = resp.contenttype();
    if (!contenttype.contains("json"))
      return RRet.fail(resp, resp.string());
    Kv kv = config.json().parse(resp.string(), Kv.class);
    if (kv.exists("errors")) {
      Object ers = kv.get("errors");
      if (ers instanceof Collection) {
        Collection rs = (Collection) ers;
        List rl = (List) rs.stream().collect(Collectors.toList());
        Object o = rl.get(0);
        Kv ek = Kv.by((Map) o);
        RError.Builder erb = new RError.Builder()
          .code(ek.string("code"))
          .message(ek.string("message"));
        Object dl = ek.get("detail");
        if (dl instanceof Map)
          erb.detail(Kv.by((Map) dl));
        CollectionKit.clear(ek);
        CollectionKit.clear(rl, rs);
        return RRet.fail(resp, erb.build());
      }
      return RRet.fail(resp, resp.string());
    }
    T data = this.ok(config, resp);
    return RRet.ok(resp, data);
  }

  public abstract T ok(RegistryConfig config, RResp resp);

}
