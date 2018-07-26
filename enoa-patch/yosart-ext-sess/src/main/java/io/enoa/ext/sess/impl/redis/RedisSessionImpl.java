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
package io.enoa.ext.sess.impl.redis;

import io.enoa.log.Log;
import io.enoa.nosql.redis.EnoaRedis;
import io.enoa.repeater.http.Cookie;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.digest.UUIDKit;
import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.toolkit.map.Kv;
import io.enoa.toolkit.text.TextKit;
import io.enoa.toolkit.thr.EoException;
import io.enoa.yosart.kernel.http.Session;
import io.enoa.yosart.kernel.http.YoRequest;

class RedisSessionImpl implements Session {

  private YoRequest request;
  private String sessKey;
  private EnoaRedis redis;

  private long expires;
  private String domain;
  private boolean hostOnly;
  private String path;
  private boolean secure;
  private boolean httpOnly;

  private String _value;
  private Cookie newCookie;

  RedisSessionImpl(EnoaRedis redis, YoRequest request, String sessKey) {
    this.redis = redis;
    this.request = request;
    this.sessKey = sessKey;

    this.newCookie = null;
    this.path = "/";
    this.expires = 253402300799999L;
  }

  @Override
  public String _name() {
    return this.sessKey;
  }

  @Override
  public Cookie _value() {
    if (this._value == null)
      return null;
    String sessVal = this.request.cookie(this.sessKey);
    if (sessVal != null && sessVal.equals(this._value))
      return null;
    if (this.newCookie != null)
      return this.newCookie;

    Cookie.Builder builder = new Cookie.Builder()
      .name(this.sessKey)
      .value(this._value)
      .path(this.path)
      .expires(this.expires);
    if (this.secure)
      builder.secure();
    if (this.httpOnly)
      builder.httpOnly();
    if (TextKit.blankn(this.domain))
      builder.domain(this.domain);
    if (this.hostOnly)
      builder.hostOnlyDomain(this.domain);

    this.newCookie = builder.build();
    return this.newCookie;
  }

  @Override
  public String[] names() {
    String sessVal = this._value == null ? this.request.cookie(this.sessKey) : this._value;
    if (TextKit.blanky(sessVal)) {
      Log.warn(EnoaTipKit.message("eo.tip.ext.sess.session_404", this.sessKey));
      return CollectionKit.emptyArray(String.class);
    }
    Kv data = this.redis.hget(this.sessKey, sessVal);
    return data == null ? CollectionKit.emptyArray(String.class) : data.keySet().toArray(new String[data.keySet().size()]);
  }

  @Override
  public <T> T get(String name) {
    String sessVal = this._value == null ? this.request.cookie(this.sessKey) : this._value;
    if (TextKit.blanky(sessVal)) {
      Log.warn(EnoaTipKit.message("eo.tip.ext.sess.session_404", this.sessKey));
      return null;
    }
    Kv data = this.redis.hget(this.sessKey, sessVal);
    return data == null ? null : data.as(name);
  }

  @Override
  public Session rm(String name) {
    String sessVal = this._value == null ? this.request.cookie(this.sessKey) : this._value;
    if (TextKit.blanky(sessVal))
      throw new EoException(EnoaTipKit.message("eo.tip.ext.sess.session_404", this.sessKey));
    Kv data = this.redis.hget(this.sessKey, sessVal);
    data.remove(name);
    this.redis.hset(this.sessKey, sessVal, data);
    return this;
  }

  @Override
  public Session set(String name, Object value) {
    if (this._value == null)
      this._value = this.request.cookie(this.sessKey);

    if (TextKit.blanky(this._value))
      this._value = UUIDKit.next(false);
    Kv data = this.redis.hget(this.sessKey, this._value);
    if (data == null) {
      this._value = UUIDKit.next(false);
      data = Kv.create();
    }
    data.set(name, value);
    this.redis.hset(this.sessKey, this._value, data);
    return this;
  }

  @Override
  public Session expires(long expires) {
    this.expires = expires;
    return this;
  }

  @Override
  public Session domain(String domain) {
    this.domain = domain;
    return this;
  }

  @Override
  public Session hostOnly(boolean hostOnly) {
    this.hostOnly = hostOnly;
    return this;
  }

  @Override
  public Session path(String path) {
    this.path = path == null ? "/" : path;
    return this;
  }

  @Override
  public Session secure(boolean secure) {
    this.secure = secure;
    return this;
  }

  @Override
  public Session httpOnly(boolean httpOnly) {
    this.httpOnly = httpOnly;
    return this;
  }
}
