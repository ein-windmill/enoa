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
package io.enoa.http.protocol;

import java.nio.file.Path;

public class HttpFormData {

  private final byte[] EMPTY_BINARY = new byte[0];

  private final String name;
  private final String text;
  private final Path file;
  private final String filename;
  private final byte[] binary;
  private final Type type;

  public enum Type {
    TEXT,
    FILE,
    BINARY
  }

  public HttpFormData(String name, String text) {
    this.name = name;
    this.text = text;
    this.type = Type.TEXT;
    this.file = null;
    this.filename = null;
    this.binary = EMPTY_BINARY;
  }

  public HttpFormData(String name, Path file) {
    this.name = name;
    this.file = file;
    this.type = Type.FILE;
    this.text = null;
    this.filename = null;
    this.binary = EMPTY_BINARY;
  }

  public HttpFormData(String name, String filename, byte[] binary) {
    this.name = name;
    this.filename = filename;
    this.binary = binary;
    this.type = Type.BINARY;
    this.text = null;
    this.file = null;
  }

  //  public static HttpFormData from(HttpPara para) {
//    return new HttpFormData(para.name(), para.value());
//  }

  public String name() {
    return this.name;
  }

  public String text() {
    return this.text;
  }

  public Path file() {
    return this.file;
  }

  public Type type() {
    return this.type;
  }

  public String filename() {
    return this.filename;
  }

  public byte[] binary() {
    return this.binary;
  }

  @Override
  public String toString() {
    return "HttpFormData{" +
      "name='" + name + '\'' +
      ", type='" + this.type + '\'' +
      (this.text == null ? "" : ", text='" + text + '\'') +
      (this.file == null ? "" : ", file=" + file) +
      '}';
  }
}
