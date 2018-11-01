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
package io.enoa.docker.promise;

import io.enoa.promise.AssetPromise;
import io.enoa.promise.arg.*;

public interface DockerAssetPromise<PARA> extends AssetPromise<PARA> {
  @Override
  DockerAssetPromise<PARA> failthrow(PromiseArg<PARA> arg);

  @Override
  DockerAssetPromise<PARA> asset(PromiseBool<PARA> bool);

  @Override
  DockerAssetPromise<PARA> capture(PromiseCapture capture);

  @Override
  <R, P> DockerAssetPromise<PARA> then(PromiseThen<R, P> then);

  @Override
  <T> DockerAssetPromise<PARA> execute(PromiseArg<T> arg);

  @Override
  void always(PromiseVoid always);
}
