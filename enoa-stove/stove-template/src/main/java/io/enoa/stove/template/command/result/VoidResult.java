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
package io.enoa.stove.template.command.result;

import io.enoa.stove.template.command.StoveResult;

public final class VoidResult implements StoveResult {

  private static class Holder {
    private static final VoidResult INSTANCE = new VoidResult();
  }

  public static VoidResult instance() {
    return Holder.INSTANCE;
  }

  @Override
  public Object value() {
    return null;
  }

//  @Override
//  public EnoaValue pipeval() {
//    return EnoaValue.NULL;
//  }
}
