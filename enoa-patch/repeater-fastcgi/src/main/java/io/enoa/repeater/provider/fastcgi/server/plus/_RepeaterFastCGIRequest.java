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
package io.enoa.repeater.provider.fastcgi.server.plus;

import io.enoa.repeater.provider._RepeaterPlusRequest;
import io.enoa.repeater.provider.fastcgi.kernel.FastCGIRequest;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public abstract class _RepeaterFastCGIRequest extends FastCGIRequest implements _RepeaterPlusRequest<FastCGIRequest> {

  private FastCGIRequest request;
  private Map<String, Object> attribute = new ConcurrentHashMap<>();

  protected _RepeaterFastCGIRequest(FastCGIRequest request) {
    this.request = request;
  }

  @Override
  public Map<String, String> prop() {
    return this.request.prop();
  }

  @Override
  public byte[] data() {
    return this.request.data();
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
  public <T> _RepeaterFastCGIRequest attr(String name, T data) {
    this.attribute.put(name, data);
    return this;
  }

  @Override
  public _RepeaterFastCGIRequest rmAttr(String name) {
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
    this.request.clear();
  }
}
