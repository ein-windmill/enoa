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
package io.enoa.rpc.config;

import io.enoa.rpc.parser.IRpcParser;
import io.enoa.rpc.parser.ResponseType;

import java.util.HashMap;
import java.util.Map;

public class ORpcHandler {

  private static class Holder {
    private static final ORpcHandler INSTANCE = new ORpcHandler();
  }

  private ORpcHandler() {

  }

  static ORpcHandler instance() {
    return Holder.INSTANCE;
  }

  private Map<ResponseType, IRpcParser> handler = new HashMap<>();

  public <T> ORpcHandler reg(ResponseType type, IRpcParser<T> handler) {
    this.handler.put(type, handler);
    return this;
  }

  public <T> IRpcParser<T> handler(ResponseType type) {
    return this.handler.get(type);
  }

}
