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
package io.enoa.rpc;

import io.enoa.http.EoExecutor;
import io.enoa.http.EoHttpConfig;
import io.enoa.http.HttpAuth;
import io.enoa.http.protocol.HttpHeader;
import io.enoa.http.protocol.HttpMethod;
import io.enoa.http.protocol.HttpPara;
import io.enoa.http.proxy.HttpProxy;
import io.enoa.http.proxy.TcpProxy;
import io.enoa.rpc.handler.IHandler;
import io.enoa.rpc.http.HttpRpcPromise;
import io.enoa.rpc.http.HttpRpcResult;

import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.Collection;

public interface TcpRpc {

  TcpRpc executor(EoExecutor executor);

  default <T> HttpRpcPromise<T> enqueue(Class<T> clazz) {
    return this.enqueue((Type) clazz);
  }

  <T> HttpRpcPromise<T> enqueue(Type type);

  <T> HttpRpcPromise<T> enqueue(IHandler<T> handler);

  <T> HttpRpcResult<T> emit(IHandler<T> handler);

  default <T> HttpRpcResult<T> emit(Class<T> clazz) {
    return this.emit((Type) clazz);
  }

  <T> HttpRpcResult<T> emit(Type type);

  TcpRpc method(HttpMethod method);

  TcpRpc config(EoHttpConfig config);

  TcpRpc charset(Charset charset);

//  default TcpRpc url(String url) {
//    return null;
//  }
//
//  TcpRpc url(EoUrl url);

  default TcpRpc traditional() {
    return this.traditional(true);
  }

  TcpRpc traditional(boolean traditional);

  default TcpRpc encode() {
    return this.encode(true);
  }

  TcpRpc encode(boolean encode);

  TcpRpc para(String name, Object value);

  TcpRpc para(String name, Object... values);

  TcpRpc para(String name, Collection values);

  TcpRpc para(String name, Path path);

  TcpRpc para(String name, String filename, byte[] bytes);

  default TcpRpc para(HttpPara para) {
    return this.para(para.name(), para.value());
  }

  default TcpRpc para(HttpPara... paras) {
    if (paras == null)
      throw new IllegalArgumentException("paras == null");
    for (HttpPara para : paras) {
      this.para(para.name(), para.value());
    }
    return this;
  }

  default TcpRpc para(Collection<HttpPara> paras) {
    if (paras == null)
      throw new IllegalArgumentException("paras == null");
    paras.forEach(para -> this.para(para.name(), para.value()));
    return this;
  }

  TcpRpc para(Path path);

  default TcpRpc raw(String raw) {
    return this.raw(raw, null);
  }

  TcpRpc raw(String raw, String contentType);

  default TcpRpc header(String name, String value) {
    this.header(new HttpHeader(name, value));
    return this;
  }

  TcpRpc header(HttpHeader header);

  default TcpRpc header(HttpHeader[] headers) {
    if (headers == null)
      throw new IllegalArgumentException("headers == null");
    for (HttpHeader header : headers) {
      this.header(header);
    }
    return this;
  }

  default TcpRpc header(Collection<HttpHeader> headers) {
    if (headers == null)
      throw new IllegalArgumentException("headers == null");
    headers.forEach(this::header);
    return this;
  }

  TcpRpc cookie(String name, String value);

  TcpRpc contentType(String contentType);

  TcpRpc proxy(HttpProxy proxy);

  default TcpRpc proxy(String host, int port) {
    return this.proxy(host, port, null, null);
  }

  default TcpRpc proxy(String host, int port, String user) {
    return this.proxy(host, port, user, null);
  }

  default TcpRpc proxy(String host, int port, String user, String passwd) {
    return this.proxy(new TcpProxy(host, port, user, passwd));
  }

  TcpRpc auth(HttpAuth auth);

  TcpRpc binary(byte[] bytes);

  default TcpRpc binary(ByteBuffer binary) {
    if (binary == null)
      throw new IllegalArgumentException("binary == null");
    return this.binary(binary.array());
  }

}
