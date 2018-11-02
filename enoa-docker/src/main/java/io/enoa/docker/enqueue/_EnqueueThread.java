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
package io.enoa.docker.enqueue;

import io.enoa.promise.arg.PromiseArg;
import io.enoa.promise.arg.PromiseBool;
import io.enoa.promise.arg.PromiseCapture;
import io.enoa.promise.arg.PromiseThen;
import io.enoa.promise.builder.EPAssetPromiseBuilder;
import io.enoa.toolkit.collection.CollectionKit;

import java.util.List;

class _EnqueueThread implements Runnable {

  private EPAssetPromiseBuilder builder;
  private Object ret;

  _EnqueueThread(EPAssetPromiseBuilder builder, Object ret) {
    this.builder = builder;
    this.ret = ret;
  }

  @Override
  public void run() {
    try {
      String oldName = Thread.currentThread().getName();
      List<PromiseBool> assets = builder.assets();
      boolean pass = Boolean.TRUE;
      for (PromiseBool asset : assets) {
        boolean asret = asset.execute(ret);
        if (asret)
          continue;
        pass = Boolean.FALSE;
        break;
      }
      if (!pass) {
        List<PromiseArg> failthrows = builder.failthrows();
        for (PromiseArg failthrow : failthrows) {
          failthrow.execute(ret);
        }
        return;
      }

      Object tmp = ret;
      List<PromiseThen> thens = builder.thens();
      for (PromiseThen then : thens) {
        tmp = then.execute(tmp);
      }

      List<PromiseArg> executes = builder.executes();
      for (PromiseArg execute : executes) {
        execute.execute(tmp);
      }
    } catch (Exception e) {
      List<PromiseCapture> captures = builder.captures();
      if (CollectionKit.isEmpty(captures)) {
        e.printStackTrace();
        return;
      }
      for (PromiseCapture capture : captures) {
        capture.execute(e);
      }
    } finally {
      if (builder.always() != null)
        builder.always().execute();
    }
  }
}
