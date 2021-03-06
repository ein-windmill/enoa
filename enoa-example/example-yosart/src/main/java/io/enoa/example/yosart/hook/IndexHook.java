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
package io.enoa.example.yosart.hook;

import io.enoa.yosart.ext.anost.hook.HookException;
import io.enoa.yosart.ext.anost.hook.IHook;
import io.enoa.yosart.ext.anost.valid.Valid;
import io.enoa.yosart.kernel.http.YoRequest;
import io.enoa.yosart.resp.Resp;

public class IndexHook implements IHook {

  @Override
  public void hook(YoRequest request, Resp resp) throws HookException {
    Valid valid = Valid.with(request);

    valid.object("where")
      .blank("Please input an address");

    valid.number("age");
  }

}
