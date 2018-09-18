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
package io.enoa.rpc.http;

import io.enoa.http.*;
import io.enoa.http.protocol.HttpHeader;
import io.enoa.http.protocol.HttpMethod;
import io.enoa.http.protocol.HttpPromise;
import io.enoa.http.protocol.HttpResponse;
import io.enoa.http.proxy.HttpProxy;
import io.enoa.rpc.TcpRpc;
import io.enoa.rpc.parser.IRpcParser;
import io.enoa.rpc.thr.RpcException;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.eo.tip.EnoaTipKit;

import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Set;

public class EnoaHttpRpc implements TcpRpc {

  private Http http;

  public EnoaHttpRpc(String name, String api) {
    this(name, api, EoHttp.def());
  }

  public EnoaHttpRpc(String name, String api, EoHttp http) {
    EoUrl url = _HttpRpcRegister.instance().url(name);
    if (url == null)
      throw new RpcException(RpcException.Type.HTTP, EnoaTipKit.message("eo.tip.rpc.http_rpc_name_404", name));
    this.http = Http.use(http)
      .url(EoUrl.with(url.end()).subpath(api));
    Set<HttpHeader> headers = _HttpRpcRegister.instance().headers(name);
    if (CollectionKit.isEmpty(headers))
      return;
    this.http.header(headers);
  }

  @Override
  public TcpRpc executor(EoExecutor executor) {
    this.http.executor(executor);
    return this;
  }

  @Override
  public <T> HttpRpcPromise<T> enqueue(Type type) {
    HttpPromise promise = this.http.enqueue();
    return new _HttpRpcPromiseImpl<>(promise, type);
  }

  @Override
  public <T> HttpRpcPromise<T> enqueue(IRpcParser<T> handler) {
    HttpPromise promise = this.http.enqueue();
    return new _HttpRpcPromiseImpl<>(promise, null, handler);
  }

  @Override
  public <T> HttpRpcResult<T> emit(IRpcParser<T> handler) {
    HttpResponse response = this.http.emit();
    return new _HttpRpcResultImpl<>(response, null, handler);
  }

  @Override
  public <T> HttpRpcResult<T> emit(Type type) {
    HttpResponse response = this.http.emit();
    return new _HttpRpcResultImpl<>(response, type);
  }

  @Override
  public TcpRpc method(HttpMethod method) {
    this.http.method(method);
    return this;
  }

  @Override
  public TcpRpc config(EoHttpConfig config) {
    this.http.config(config);
    return this;
  }

  @Override
  public TcpRpc charset(Charset charset) {
    this.http.charset(charset);
    return this;
  }

  @Override
  public TcpRpc traditional(boolean traditional) {
    this.http.traditional(traditional);
    return this;
  }

  @Override
  public TcpRpc encode(boolean encode) {
    this.http.encode(encode);
    return this;
  }

  @Override
  public TcpRpc para(String name, Object value) {
    this.http.para(name, value);
    return this;
  }

  @Override
  public TcpRpc para(String name, Object... values) {
    this.http.para(name, values);
    return this;
  }

  @Override
  public TcpRpc para(String name, Collection values) {
    this.http.para(name, values);
    return this;
  }

  @Override
  public TcpRpc para(String name, Path path) {
    this.http.para(name, path);
    return this;
  }

  @Override
  public TcpRpc para(String name, String filename, byte[] bytes) {
    this.http.para(name, filename, bytes);
    return this;
  }

  @Override
  public TcpRpc para(Path path) {
    this.http.para(path);
    return this;
  }

  @Override
  public TcpRpc raw(String raw, String contentType) {
    this.http.raw(raw, contentType);
    return this;
  }

  @Override
  public TcpRpc header(HttpHeader header) {
    this.http.header(header);
    return this;
  }

  @Override
  public TcpRpc cookie(String name, String value) {
    this.http.cookie(name, value);
    return this;
  }

  @Override
  public TcpRpc contentType(String contentType) {
    this.http.contentType(contentType);
    return this;
  }

  @Override
  public TcpRpc proxy(HttpProxy proxy) {
    this.http.proxy(proxy);
    return this;
  }

  @Override
  public TcpRpc auth(HttpAuth auth) {
    this.http.auth(auth);
    return this;
  }

  @Override
  public TcpRpc binary(byte[] bytes) {
    this.http.binary(bytes);
    return this;
  }
}
