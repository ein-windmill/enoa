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
package io.enoa.docker.parser.docker;

import io.enoa.docker.DockerConfig;
import io.enoa.docker.dket.docker.DResp;
import io.enoa.docker.dket.docker.DRet;
import io.enoa.docker.thr.DockerException;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.toolkit.is.Is;
import io.enoa.toolkit.map.Kv;

abstract class AbstractParser<T> implements DIParser<T> {

  @Override
  public DRet<T> parse(DockerConfig config, DResp resp) {
    if (resp == null)
      return DRet.fail(null, null);
    String contenttype = resp.contenttype();
    if (contenttype == null) {
      if (this instanceof EVoidParser)
        return DRet.ok(resp, this.ok(config, resp));

      throw new DockerException(EnoaTipKit.message("eo.tip.docker.serv_no_contenttype"));
    }

    OkCheck okcheck = this.isok(config, resp);
    if (!okcheck.ok)
      return DRet.fail(resp, okcheck.message);

    if ("application/json".equalsIgnoreCase(contenttype) ||
      "text/plain".equalsIgnoreCase(contenttype)) {
      String origin = resp.string();
      if (Is.not().truthy(origin))
        return DRet.ok(resp, this.ok(config, resp));

      if (origin.charAt(0) != '{') {
        if (resp.code() >= 400)
          return DRet.fail(resp, resp.string());
        return DRet.ok(resp, this.ok(config, resp));
      }

      Kv kv = config.json().parse(origin, Kv.class);
      try {
        if (Is.truthy(kv.string("message")))
          return DRet.fail(resp, kv.string("message"));
        return DRet.ok(resp, this.ok(config, resp));
      } finally {
        CollectionKit.clear(kv);
      }
    }

    return DRet.ok(resp, this.ok(config, resp));
  }

  protected OkCheck isok(DockerConfig config, DResp resp) {
    return OkCheck.OK;
  }

  public abstract T ok(DockerConfig config, DResp resp);

  protected static class OkCheck {
    private boolean ok;
    private String message;

    private OkCheck(boolean ok) {
      this.ok = ok;
    }

    protected static OkCheck ok() {
      return OK;
    }

    protected static OkCheck fail(String message) {
      OkCheck oc = new OkCheck(false);
      oc.message = message;
      return oc;
    }

    private static final OkCheck OK = new OkCheck(true);

  }
}
