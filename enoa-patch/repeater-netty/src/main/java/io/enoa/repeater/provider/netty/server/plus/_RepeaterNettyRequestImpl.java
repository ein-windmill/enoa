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
package io.enoa.repeater.provider.netty.server.plus;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public abstract class _RepeaterNettyRequestImpl implements _RepeaterNettyRequest {

  private FullHttpRequest request;
  private Map<String, Object> attribute = new ConcurrentHashMap<>();

  public _RepeaterNettyRequestImpl(FullHttpRequest request) {
    this.request = request;
  }

  @Override
  public Object originRequest() {
    return this.request;
  }

  @Override
  public <T> T attr(String name) {
    return (T) this.attribute.get(name);
  }

  @Override
  public <T> _RepeaterNettyRequest attr(String name, T data) {
    this.attribute.put(name, data);
    return this;
  }

  @Override
  public _RepeaterNettyRequest rmAttr(String name) {
    this.attribute.remove(name);
    return this;
  }

  @Override
  public String[] attrNames() {
    Set<String> attrs = this.attribute.keySet();
    return attrs.toArray(new String[attrs.size()]);
  }

  @Override
  public HttpHeaders trailingHeaders() {
    return this.request.trailingHeaders();
  }

  @Override
  public ByteBuf content() {
    return this.request.content();
  }

  @Override
  public FullHttpRequest copy() {
    return this.request.copy();
  }

  @Override
  public FullHttpRequest duplicate() {
    return this.request.duplicate();
  }

  @Override
  public FullHttpRequest retainedDuplicate() {
    return this.request.retainedDuplicate();
  }

  @Override
  public FullHttpRequest replace(ByteBuf content) {
    return this.request.replace(content);
  }

  @Override
  public FullHttpRequest retain(int increment) {
    return this.request.retain(increment);
  }

  @Override
  public int refCnt() {
    return this.request.refCnt();
  }

  @Override
  public FullHttpRequest retain() {
    return this.request.retain();
  }

  @Override
  public FullHttpRequest touch() {
    return this.request.touch();
  }

  @Override
  public FullHttpRequest touch(Object hint) {
    return this.request.touch(hint);
  }

  @Override
  public boolean release() {
    return this.request.release();
  }

  @Override
  public boolean release(int decrement) {
    return this.request.release(decrement);
  }

  @Deprecated
  @Override
  public HttpVersion getProtocolVersion() {
    return this.request.getProtocolVersion();
  }

  @Override
  public HttpVersion protocolVersion() {
    return this.request.protocolVersion();
  }

  @Override
  public FullHttpRequest setProtocolVersion(HttpVersion version) {
    return this.request.setProtocolVersion(version);
  }

  @Override
  public HttpHeaders headers() {
    return this.request.headers();
  }

  @Deprecated
  @Override
  public HttpMethod getMethod() {
    return this.request.getMethod();
  }

  @Override
  public HttpMethod method() {
    return this.request.method();
  }

  @Override
  public FullHttpRequest setMethod(HttpMethod method) {
    return this.request.setMethod(method);
  }

  @Deprecated
  @Override
  public String getUri() {
    return this.request.getUri();
  }

  @Override
  public String uri() {
    return this.request.uri();
  }

  @Override
  public FullHttpRequest setUri(String uri) {
    return this.request.setUri(uri);
  }

  @Deprecated
  @Override
  public DecoderResult getDecoderResult() {
    return this.request.getDecoderResult();
  }

  @Override
  public DecoderResult decoderResult() {
    return this.request.decoderResult();
  }

  @Override
  public void setDecoderResult(DecoderResult result) {
    this.request.setDecoderResult(result);
  }

  @Override
  public void clear() {
    this.attribute.clear();
  }
}
