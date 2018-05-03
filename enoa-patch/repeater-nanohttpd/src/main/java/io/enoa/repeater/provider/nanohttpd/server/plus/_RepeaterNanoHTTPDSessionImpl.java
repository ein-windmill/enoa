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
package io.enoa.repeater.provider.nanohttpd.server.plus;

import fi.iki.elonen.NanoHTTPD;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public abstract class _RepeaterNanoHTTPDSessionImpl implements _RepeaterNanoHTTPDSession {

  private NanoHTTPD.IHTTPSession session;
  private Map<String, Object> attribute = new ConcurrentHashMap<>();

  protected _RepeaterNanoHTTPDSessionImpl(NanoHTTPD.IHTTPSession session) {
    this.session = session;
  }

  @Override
  public Object originRequest() {
    return this.session;
  }

  @Override
  public void execute() throws IOException {
    this.session.execute();
  }

  @Override
  public NanoHTTPD.CookieHandler getCookies() {
    return this.session.getCookies();
  }

  @Override
  public Map<String, String> getHeaders() {
    return this.session.getHeaders();
  }

  @Override
  public InputStream getInputStream() {
    return this.session.getInputStream();
  }

  @Override
  public NanoHTTPD.Method getMethod() {
    return this.session.getMethod();
  }

  @Deprecated
  @Override
  public Map<String, String> getParms() {
    return this.session.getParms();
  }

  @Override
  public Map<String, List<String>> getParameters() {
    return this.session.getParameters();
  }

  @Override
  public String getQueryParameterString() {
    return this.session.getQueryParameterString();
  }

  @Override
  public String getUri() {
    return this.session.getUri();
  }

  @Override
  public void parseBody(Map<String, String> files) throws IOException, NanoHTTPD.ResponseException {
    this.session.parseBody(files);
  }

  @Override
  public String getRemoteIpAddress() {
    return this.session.getRemoteIpAddress();
  }

  @Override
  public String getRemoteHostName() {
    return this.session.getRemoteHostName();
  }


  @Override
  public <T> T attr(String name) {
    return (T) this.attribute.get(name);
  }

  @Override
  public <T> _RepeaterNanoHTTPDSession attr(String name, T data) {
    this.attribute.put(name, data);
    return this;
  }

  @Override
  public _RepeaterNanoHTTPDSession rmAttr(String name) {
    this.attribute.remove(name);
    return this;
  }

  @Override
  public String[] attrNames() {
    Set<String> attrs = this.attribute.keySet();
    return attrs.toArray(new String[attrs.size()]);
  }

  @Override
  public void clear() {
    this.attribute.clear();
  }
}
