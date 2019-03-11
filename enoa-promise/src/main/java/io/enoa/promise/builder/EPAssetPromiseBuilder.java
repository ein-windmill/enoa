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
package io.enoa.promise.builder;

import io.enoa.promise.AssetPromise;
import io.enoa.promise.ThenPromise;
import io.enoa.promise.arg.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EPAssetPromiseBuilder<PARA> extends EPThenPromiseBuilder {

  private EPThenPromiseBuilder oe;
  private List<PromiseBool> assets;
  private List<PromiseArg> failthrows;

  EPAssetPromiseBuilder() {
    this.oe = new EPThenPromiseBuilder();
  }


  public List<PromiseBool> assets() {
    return this.assets == null ? Collections.emptyList() : this.assets;
  }

  public List<PromiseArg> failthrows() {
    return this.failthrows == null ? Collections.emptyList() : this.failthrows;
  }

  @Override
  public List<PromiseThen> thens() {
    return this.oe.thens();
  }

  @Override
  public List<PromiseArg> executers() {
    return this.oe.executers();
  }

  @Override
  public List<PromiseCapture> captures() {
    return this.oe.captures();
  }

  @Override
  public PromiseVoid always() {
    return this.oe.always();
  }

  @Override
  public AssetPromise<PARA> build() {
    ThenPromise promise = this.oe.build();
    return new AssetPromise<PARA>() {
      @Override
      public AssetPromise<PARA> asset(PromiseBool<PARA> bool) {
        if (assets == null)
          assets = new ArrayList<>();
        assets.add(bool);
        return this;
      }

      @Override
      public AssetPromise<PARA> failthrow(PromiseArg<PARA> arg) {
        if (failthrows == null)
          failthrows = new ArrayList<>();
        failthrows.add(arg);
        return this;
      }

      @Override
      public AssetPromise<PARA> capture(PromiseCapture capture) {
        promise.capture(capture);
        return this;
      }

      @Override
      public <P> AssetPromise<PARA> then(PromiseThen<Object, P> then) {
        promise.then(then);
        return this;
      }

      @Override
      public <T> AssetPromise<PARA> execute(PromiseArg<T> arg) {
        promise.execute(arg);
        return this;
      }

      @Override
      public void always(PromiseVoid always) {
        promise.always(always);
      }
    };
  }
}
