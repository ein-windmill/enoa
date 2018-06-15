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
package io.enoa.toolkit.digest.base;

import io.enoa.toolkit.binary.EnoaBinary;

import java.nio.charset.Charset;

public final class BaseKit {

  private BaseKit() {

  }

  public static String ebase64(byte[] bytes) {
    return Base64.encode(bytes);
  }

  public static String ebase64(String text) {
    return Base64.encode(text);
  }

  public static String ebase64(String text, Charset charset) {
    return Base64.encode(text, charset);
  }

  public static String ebase64url(byte[] bytes) {
    return Base64.encode(Base64.Mode.URLSAFE, bytes);
  }

  public static String ebase64url(String text) {
    return Base64.encode(Base64.Mode.URLSAFE, text);
  }

  public static String ebase64url(String text, Charset charset) {
    return Base64.encode(Base64.Mode.URLSAFE, text, charset);
  }

  public static EnoaBinary debase64(String text) {
    return Base64.decode(text);
  }

  public static EnoaBinary debase64(String text, Charset charset) {
    return Base64.decode(text, charset);
  }

  public static String debase64url(String text) {
    return Base64.decode(Base64.Mode.URLSAFE, text).string();
  }

  public static String debase64url(String text, Charset charset) {
    return Base64.decode(Base64.Mode.URLSAFE, text, charset).string();
  }

  public static String ebase62(byte[] bytes) {
    return EnoaBinary.create(Base62.createInstance().encode(bytes)).string();
  }

  public static String ebase62(String text, Charset charset) {
    return EnoaBinary.create(Base62.createInstance().encode(text.getBytes(charset))).string();
  }

  public static byte[] debase62(String text) {
    return Base62.createInstance().decode(text.getBytes());
  }

  public static String debase62(String text, Charset charset) {
    return EnoaBinary.create(Base62.createInstance().decode(text.getBytes())).string();
  }

}
