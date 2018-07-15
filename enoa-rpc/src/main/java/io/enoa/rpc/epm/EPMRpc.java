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
package io.enoa.rpc.epm;

import io.enoa.json.EnoaJson;
import io.enoa.json.EoJsonFactory;
import io.enoa.json.Json;
import io.enoa.log.EnoaLog;
import io.enoa.log.EoLogFactory;
import io.enoa.log.Log;
import io.enoa.rpc.parser.IRpcParser;
import io.enoa.rpc.parser.ResponseType;
import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.toolkit.thr.EoException;

import java.util.HashMap;
import java.util.Map;

public class EPMRpc {

  private static class Holder {
    private static final EPMRpc INSTANCE = new EPMRpc();
  }

  public static EPMRpc instance() {
    return Holder.INSTANCE;
  }


  private EoJsonFactory json;
  private EoLogFactory log;
  private Map<ResponseType, IRpcParser> handler;

  private EPMRpc() {
    this.json = Json.epm().factory();
    this.log = Log.epm().factory();
    this.handler = new HashMap<>();
    this.handler(ResponseType.JSON, new _DefaultJsonRpcParser<>());
    this.handler(ResponseType.BINARY, new _DefaultBinaryRpcParser());
  }

  public EPMRegister register() {
    return EPMRegister.instance();
  }

  public EPMRpc json(EoJsonFactory json) {
    this.json = json;
    return this;
  }

  public EnoaJson json() {
    if (this.json == null)
      throw new EoException(EnoaTipKit.message("eo.tip.rpc.handler_json_provider_null"));
    return this.json.json();
  }

  public EPMRpc log(EoLogFactory log) {
    this.log = log;
    return this;
  }

  public EnoaLog log(String name) {
    return this.log.logger(name);
  }

  public EnoaLog log(Class<?> clazz) {
    return this.log.logger(clazz);
  }


  public <T> EPMRpc handler(ResponseType type, IRpcParser<T> handler) {
    this.handler.put(type, handler);
    return this;
  }

  public <T> IRpcParser<T> handler(ResponseType type) {
    return this.handler.get(type);
  }

}
