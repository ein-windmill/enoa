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
package io.enoa.docker.parser;

import io.enoa.docker.DockerConfig;
import io.enoa.docker.dret.DResp;
import io.enoa.docker.dret.DRet;
import io.enoa.toolkit.map.Kv;
import io.enoa.toolkit.text.TextKit;

abstract class AbstractParser<T> implements DIParser<T> {

  @Override
  public DRet<T> parse(DockerConfig config, DResp resp) {
    if (resp == null)
      return DRet.fail(null, null);
    String contenttype = resp.contenttype();
    if (contenttype.equalsIgnoreCase("application/json") ||
      contenttype.equalsIgnoreCase("text/plain")) {
      String origin = resp.string();
      if (TextKit.blanky(origin))
        return DRet.ok(resp, this.ok(config, resp));
      if (origin.charAt(0) != '{')
        return DRet.ok(resp, this.ok(config, resp));

      Kv kv = config.json().parse(origin, Kv.class);
      if (kv.notNullValue("message"))
        return DRet.fail(resp, kv.string("message"));
      return DRet.ok(resp, this.ok(config, resp));
    }
    return DRet.ok(resp, this.ok(config, resp));
  }

  public abstract T ok(DockerConfig config, DResp resp);

}
