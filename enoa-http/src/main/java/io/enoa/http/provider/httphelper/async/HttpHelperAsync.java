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
package io.enoa.http.provider.httphelper.async;

import io.enoa.http.EoEmit;
import io.enoa.http.EoUrl;
import io.enoa.http.protocol.HttpResponse;
import io.enoa.chunk.Chunk;
import io.enoa.promise.arg.PromiseArg;
import io.enoa.promise.arg.PromiseBool;
import io.enoa.promise.arg.PromiseThen;

class HttpHelperAsync implements Runnable {

  private final EoEmit emit;
  private final String name;
  private final HttpHelperPromiseBuilder promise;
  private final Chunk chunk;

  HttpHelperAsync(EoUrl url, EoEmit emit, HttpHelperPromiseBuilder promise, Chunk chunk) {
    String utx = url.end();
    this.name = utx.substring(0, utx.length() < 70 ? utx.length() : 70).concat("...");
    this.emit = emit;
    this.promise = promise;
    this.chunk = chunk;
  }

  @Override
  public void run() {
    String oldName = Thread.currentThread().getName();
    Thread.currentThread().setName(this.name);
    try {
      HttpResponse resp = this.chunk == null ? this.emit.emit() : this.emit.chunk(this.chunk);

      int code = resp.code();
      if (this.promise.oks() != null && code < 400)
        this.promise.oks().forEach(ok -> ok.execute(resp));

      if (this.promise.errors() != null && code >= 400)
        this.promise.errors().forEach(error -> error.execute(resp));

      Object value = resp;

      if (this.promise.thens() != null)
        for (PromiseThen then : this.promise.thens())
          value = then.execute(value);

      boolean pass = true;
      if (this.promise.valids() != null) {
        for (PromiseBool valid : this.promise.valids()) {
          if (valid.execute(value))
            continue;
          pass = false;
          break;
        }
      }

      if (pass) {
        if (this.promise.execs() != null)
          for (PromiseArg execute : this.promise.execs())
            execute.execute(value);
        return;
      }

      if (this.promise.fails() != null)
        for (PromiseArg execute : this.promise.fails())
          execute.execute(value);

    } catch (Exception e) {
      if (this.promise.captures() == null) {
        e.printStackTrace();
        return;
      }
      this.promise.captures().forEach(capture -> capture.execute(e));
    } finally {
      if (this.promise.always() != null)
        this.promise.always().execute();
      Thread.currentThread().setName(oldName);
    }
  }
}
