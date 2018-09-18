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
package io.enoa.toolkit.digest;

import io.enoa.toolkit.EoConst;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class DigestKit {

  public static final long FNV_OFFSET_BASIS_64 = 0xcbf29ce484222325L;
  public static final long FNV_PRIME_64 = 0x100000001b3L;

  private static final java.security.SecureRandom random = new java.security.SecureRandom();
  private static final char[] HEX_DIGITS = "0123456789abcdef".toCharArray();
  private static final char[] CHAR_ARRAY = "_-0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

  public static long fnv1a64(String key) {
    long hash = FNV_OFFSET_BASIS_64;
    for (int i = 0, size = key.length(); i < size; i++) {
      hash ^= key.charAt(i);
      hash *= FNV_PRIME_64;
    }
    return hash;
  }

  public static String md5(String text) {
    return hash("MD5", text);
  }

  public static String sha1(String text) {
    return hash("SHA-1", text);
  }

  public static String sha256(String text) {
    return hash("SHA-256", text);
  }

  public static String sha384(String text) {
    return hash("SHA-384", text);
  }

  public static String sha512(String text) {
    return hash("SHA-512", text);
  }

  public static String hash(String algorithm, String text) {
    return hash(algorithm, text, EoConst.CHARSET);
  }

  public static String hash(String algorithm, String text, Charset charset) {
    return hash(algorithm, text.getBytes(charset));
  }

  public static String hash(String algorithm, byte[] binary) {
    byte[] bytes = digest(algorithm).digest(binary);
    return hex(bytes);
  }

  public static MessageDigest digest(String algorithm) {
    try {
      return MessageDigest.getInstance(algorithm.toUpperCase());
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  public static String hex(byte[] bytes) {
    StringBuilder ret = new StringBuilder(bytes.length * 2);
    for (byte b : bytes) {
      ret.append(HEX_DIGITS[(b >> 4) & 0x0f]);
      ret.append(HEX_DIGITS[b & 0x0f]);
    }
    return ret.toString();
  }

  /**
   * md5 128bit 16bytes
   * sha1 160bit 20bytes
   * sha256 256bit 32bytes
   * sha384 384bit 48bytes
   * sha512 512bit 64bytes
   */
  public static String salt(int saltLength) {
    StringBuilder salt = new StringBuilder(saltLength);
    for (int i = 0; i < saltLength; i++) {
      salt.append(CHAR_ARRAY[random.nextInt(CHAR_ARRAY.length)]);
    }
    return salt.toString();
  }

  public static String saltSha256() {
    return salt(32);
  }

  public static String saltSha512() {
    return salt(64);
  }

  public static boolean slowEquals(byte[] a, byte[] b) {
    if (a == null || b == null) {
      return false;
    }

    int diff = a.length ^ b.length;
    for (int i = 0; i < a.length && i < b.length; i++) {
      diff |= a[i] ^ b[i];
    }
    return diff == 0;
  }
}




