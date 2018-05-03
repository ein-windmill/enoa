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
package io.enoa.http.protocol;

import io.enoa.promise.EoPromise;
import io.enoa.promise.PromiseResult;
import io.enoa.promise.PromiseThen;
import io.enoa.promise.PromiseValid;

public interface HttpPromise extends EoPromise<HttpPromise> {

  HttpPromise ok(PromiseResult<HttpResponse> result);

  HttpPromise error(PromiseResult<HttpResponse> result);

  <T> HttpPromise then(PromiseThen<Object, T> then);

  <T> HttpPromise valid(PromiseValid<T> valid);

  <T> HttpPromise execute(PromiseResult<T> execute);

  <T> HttpPromise fail(PromiseResult<T> fail);

}
