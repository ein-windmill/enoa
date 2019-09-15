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
package io.enoa.ext.sess.impl.file;

import io.enoa.log.Log;
import io.enoa.repeater.http.Cookie;
import io.enoa.serialization.EoSerializer;
import io.enoa.toolkit.binary.EnoaBinary;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.digest.UUIDKit;
import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.toolkit.file.FileKit;
import io.enoa.toolkit.is.Is;
import io.enoa.toolkit.map.Kv;
import io.enoa.toolkit.sys.ThrowableKit;
import io.enoa.toolkit.thr.EoException;
import io.enoa.yosart.kernel.http.Session;
import io.enoa.yosart.kernel.http.YoRequest;
import io.enoa.yosart.thr.OyExtException;

import java.io.NotSerializableException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class FileSessionImpl implements Session {

  private YoRequest request;
  private String sessKey;
  private Path savePath;


  private long expires;
  private String domain;
  private boolean hostOnly;
  private String path;
  private boolean secure;
  private boolean httpOnly;

  private String _value;

  private Cookie newCookie;
  private EoSerializer serializer;

  FileSessionImpl(YoRequest request, String sessKey, Path savePath, EoSerializer serializer) {
    this.request = request;
    this.sessKey = sessKey;
    this.savePath = savePath;

    this.serializer = serializer;
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
      .expires(this.expires)
      .path(this.path);
    if (this.secure)
      builder.secure();
    if (this.httpOnly)
      builder.httpOnly();
    if (Is.truthy(this.domain))
      builder.domain(this.domain);
    if (this.hostOnly)
      builder.hostOnlyDomain(this.domain);

    this.newCookie = builder.build();
    return this.newCookie;
  }

  @Override
  public String[] names() {
    String sessVal = this._value == null ? this.request.cookie(this.sessKey) : this._value;
    if (Is.not().truthy(sessVal)) {
      Log.warn(EnoaTipKit.message("eo.tip.ext.sess.session_404", this.sessKey));
      return CollectionKit.emptyArray(String.class);
    }
    Path savePath = Paths.get(this.savePath.toString(), sessVal);
    if (!Files.exists(savePath)) {
      Log.warn(EnoaTipKit.message("eo.tip.ext.sess.session_404", this.sessKey));
      return CollectionKit.emptyArray(String.class);
    }
    Kv data = this.deserialize(savePath);
    return data.keySet().toArray(new String[data.keySet().size()]);
  }

  @Override
  public <T> T get(String name) {
    String sessVal = this._value == null ? this.request.cookie(this.sessKey) : this._value;
    if (Is.not().truthy(sessVal)) {
      Log.warn(EnoaTipKit.message("eo.tip.ext.sess.session_404", this.sessKey));
      return null;
    }
    Path savePath = Paths.get(this.savePath.toString(), sessVal);
    if (!Files.exists(savePath)) {
      Log.warn(EnoaTipKit.message("eo.tip.ext.sess.session_404", this.sessKey));
      return null;
    }
    Kv data = this.deserialize(savePath);
    return data.as(name);
  }

  @Override
  public Session rm(String name) {
    String sessVal = this._value == null ? this.request.cookie(this.sessKey) : this._value;
    if (Is.not().truthy(sessVal))
      throw new EoException(EnoaTipKit.message("eo.tip.ext.sess.session_404", this.sessKey));
    Kv data = this.deserialize(Paths.get(this.savePath.toString(), sessVal));
    data.remove(name);
    this.serialize(data);
    return this;
  }

  @Override
  public Session set(String name, Object value) {
    if (this._value == null)
      this._value = this.request.cookie(this.sessKey);

    if (Is.not().truthy(this._value)) {
      this._value = UUIDKit.next(false);
    }
    Path saveFile = Paths.get(this.savePath.toString(), this._value);
    if (!Files.exists(saveFile)) {
      this._value = UUIDKit.next(false);
    }
    Kv data = Files.exists(saveFile) ? this.deserialize(saveFile) : Kv.create();
    data.set(name, value);
    this.serialize(data);
    return this;
  }

  @Override
  public Session expires(long expiresAt) {
    this.expires = expiresAt;
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

  private void serialize(Kv data) {
    try {
      ByteBuffer buffer = ByteBuffer.wrap(this.serializer.serialize(data));
      FileKit.write(Paths.get(this.savePath.toString(), this._value), buffer);
      buffer.clear();
    } catch (Exception e) {
      Throwable nse = ThrowableKit.position(e, NotSerializableException.class);
      if (nse == null)
        throw new RuntimeException(e.getMessage(), e);

      throw new OyExtException(EnoaTipKit.message("eo.tip.ext.sess.obj_not_serializable", nse.getMessage()), nse);
    }
  }

  private Kv deserialize(Path path) {
    EnoaBinary efile = FileKit.read(path);
    return this.serializer.reduction(efile.bytes());
  }
}
