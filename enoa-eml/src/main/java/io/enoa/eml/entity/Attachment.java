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
package io.enoa.eml.entity;

import io.enoa.toolkit.binary.EnoaBinary;
import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.toolkit.file.FileKit;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.util.Arrays;

public class Attachment implements Serializable {

  private static final String DEF_MIME = "application/octet-stream";

  private final String name;
  private final byte[] binary;
  private final String mime;

//  public Attachment(byte[] binary) {
//    this(null, binary);
//  }

  public Attachment(String name, byte[] binary) {
    this(name, binary, DEF_MIME);
  }

  public Attachment(String name, byte[] binary, String mime) {
    if (binary == null)
      throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.email.attachment_body_null"));
    this.name = name;
    this.binary = binary;
    this.mime = mime;
  }

//  public static Attachment create(byte[] binary) {
//    return create(null, binary, DEF_MIME);
//  }
//
//  public static Attachment create(byte[] binary, String mime) {
//    return create(null, binary, mime);
//  }

  public static Attachment create(String name, byte[] binary) {
    return create(name, binary, DEF_MIME);
  }

  public static Attachment create(String name, byte[] binary, String mime) {
    return new Attachment(name, binary, mime);
  }

//  public static Attachment create(ByteBuffer binary) {
//    return create(null, binary, DEF_MIME);
//  }
//
//  public static Attachment create(ByteBuffer binary, String mime) {
//    return create(null, binary, mime);
//  }

  public static Attachment create(String name, ByteBuffer binary) {
    return create(name, binary, DEF_MIME);
  }

  public static Attachment create(String name, ByteBuffer binary, String mime) {
    if (binary == null)
      throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.email.attachment_body_null"));
    return new Attachment(name, binary.array(), mime);
  }

  public static Attachment create(Path path) {
    return create(path.getFileName().toString(), path);
  }

  public static Attachment create(String name, Path path) {
    if (path == null)
      throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.email.attachment_body_null"));
    if (!FileKit.exists(path))
      throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.email.attachment_path_404", path.toString()));
    String type = FileKit.probeContentType(path);
    EnoaBinary binary = FileKit.read(path);
    return new Attachment(name, binary.bytes(), type);
  }

//  public static Attachment create(EnoaBinary binary) {
//    return create(null, binary, DEF_MIME);
//  }
//
//  public static Attachment create(EnoaBinary binary, String mime) {
//    return create(null, binary, mime);
//  }

  public static Attachment create(String name, EnoaBinary binary) {
    return create(name, binary, DEF_MIME);
  }

  public static Attachment create(String name, EnoaBinary binary, String mime) {
    return new Attachment(name, binary.bytes(), mime);
  }

  public byte[] binary() {
    return this.binary;
  }

  public String mime() {
    return this.mime;
  }

  public String name() {
    return this.name;
  }

  @Override
  public String toString() {
    String bstr;
    if (this.binary.length <= 1000) {
      bstr = Arrays.toString(this.binary);
    } else {
      byte[] show = new byte[1000];
      System.arraycopy(this.binary, 0, show, 0, 1000);
      bstr = Arrays.toString(show);
    }
    return "Attachment{" +
      "binary=" + bstr +
      ", mime='" + mime + '\'' +
      '}';
  }
}
