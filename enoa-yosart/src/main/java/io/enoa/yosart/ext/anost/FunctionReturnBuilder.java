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
package io.enoa.yosart.ext.anost;

import io.enoa.repeater.http.Response;
import io.enoa.yosart.kernel.http.YoRequest;
import io.enoa.yosart.resp.Respend;

class FunctionReturnBuilder {

  public Response build(AnostSerializer serializer, YoRequest request, Class<?> returnClazz, Object funcRet) {
    if (returnClazz == void.class)
      return null;
    if (funcRet instanceof Response)
      return (Response) funcRet;
    if (funcRet instanceof Respend)
      return ((Respend) funcRet).end();
    return serializer.serialize(request, funcRet);
  }

}
