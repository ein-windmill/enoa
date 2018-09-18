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

import io.enoa.promise.EoPromise;
import io.enoa.promise.arg.PromiseCapture;
import io.enoa.promise.arg.PromiseVoid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EOePromiseBuilder {


  private List<PromiseCapture> captures;
  private PromiseVoid always;

  public List<PromiseCapture> captures() {
    return this.captures == null ? Collections.emptyList() : this.captures;
  }

  public PromiseVoid always() {
    return this.always;
  }

  public EoPromise build() {
    return new EoPromise() {
      @Override
      public EoPromise capture(PromiseCapture capture) {
        if (captures == null)
          captures = new ArrayList<>();
        captures.add(capture);
        return this;
      }

      @Override
      public void always(PromiseVoid always0) {
        always = always0;
      }
    };
  }

}
