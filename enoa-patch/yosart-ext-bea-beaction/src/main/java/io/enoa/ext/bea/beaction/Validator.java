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
package io.enoa.ext.bea.beaction;

import io.enoa.toolkit.map.Kv;
import io.enoa.yosart.kernel.http.YoRequest;
import io.enoa.yosart.resp.Resp;
import io.enoa.yosart.thr.OyExtException;

public abstract class Validator implements Interceptor {

  private YoRequest req;
  private boolean error;

  @Override
  public void intercept(Invocation inv) {
    this.error = false;
    this.req = inv.req();
    try {
      this.valid(this.req);
    } catch (Exception e) {
      if (!(e instanceof ValidException))
        throw new OyExtException(e.getMessage(), e);
      this.error = true;
    }
    if (!this.error) {
      inv.next();
      return;
    }
    Resp resp = inv.resp();
    Kv attr = Kv.create();
    for (String name : this.req.attrNames()) {
      attr.set(name, this.req.attr(name));
    }
    this.handle(attr, resp);
    attr.clear();
  }

  protected void error(String name, String value) {
    this.req.attr(name, value);
    this.error = true;
    if (this.shortCircuit())
      throw new ValidException();
  }

  protected boolean shortCircuit() {
    return true;
  }

  protected abstract void valid(YoRequest req);

  protected abstract void handle(Kv attr, Resp resp);
}
