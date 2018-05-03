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
package io.enoa.repeater.http;

import io.enoa.toolkit.binary.EnoaBinary;
import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.toolkit.file.FileKit;
import io.enoa.toolkit.thr.EoException;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class UFile {

  /**
   * 表单字段名
   */
  private final String name;
  /**
   * 上传文件原始文件名
   */
  private final String originName;
  /**
   * 零时文件名
   */
  private final String filename;
  /**
   * 零时目录
   */
  private final Path tmp;
  /**
   * 文件
   */
  private final Path path;
  /**
   * 文件不持久化到硬盘直接字节存储在内存
   */
  private final byte[] binary;
  /**
   * UFile 类型
   */
  private final Type type;


  private enum Type {
    FILE,
    BINARY
  }

  private static ExecutorService ES = Executors.newSingleThreadExecutor();

  private UFile(Builder builder) {
    this.name = builder.name;
    this.originName = builder.originName;
    this.filename = builder.filename;
    this.path = builder.path;
    this.binary = builder.binary;
    this.type = builder.type;
    this.tmp = builder.tmp;
  }


  public String name() {
    return this.name;
  }

  public String originName() {
    return this.originName;
  }

  public String filename() {
    return this.filename;
  }

  public Path path() {
    if (this.type == Type.FILE)
      return this.path;
    Path path = this.tmp.resolve(this.filename);
    return this.move(path);
  }

  public byte[] bytes() {
    if (this.type == Type.BINARY)
      return this.binary;
    return FileKit.read(this.path).bytes();
  }

  public EnoaBinary binary() {
    if (this.type == Type.BINARY)
      return EnoaBinary.create(this.bytes());
    return FileKit.read(this.path);
  }

  public File file() {
    Path path = this.path();
    return path == null ? null : path.toFile();
  }

  public Path move(String to) throws EoException {
    return this.move(Paths.get(to));
  }

  public Path move(Path to) throws EoException {
    Path folder = to.getParent();
    if (!FileKit.exists(folder)) {
      FileKit.mkdirs(folder);
    }

    switch (this.type) {
      case FILE:
        Path newFile = FileKit.move(this.path, to);
        if (newFile == null)
          throw new EoException(EnoaTipKit.message("eo.tip.repeater.file_move_fail", this.path.toString(), to.toString()));
        return newFile;
      case BINARY:
        FileKit.write(to, ByteBuffer.wrap(this.bytes()));
        return to;
      default:
        throw new EoException(EnoaTipKit.message("eo.tip.repeater.file_move_fail", this.path.toString(), to.toString()));
    }
  }

  public Future<Path> asyncMove(ExecutorService es, String to) throws EoException {
    return this.asyncMove(es, Paths.get(to));
  }

  public Future<Path> asyncMove(ExecutorService es, Path to) throws EoException {
    if (this.path == null)
      throw new EoException(EnoaTipKit.message("eo.tip.repeater.file_cant_move"));
    if (to == null)
      throw new EoException(EnoaTipKit.message("eo.tip.repeater.file_move_to_null"));
    return es.submit(() -> this.move(to));
  }

  public Future<Path> asyncMove(String to) throws EoException {
    return this.asyncMove(ES, Paths.get(to));
  }

  public Future<Path> asyncMove(Path to) throws EoException {
    return this.asyncMove(ES, to);
  }

  public void delete() {
    if (this.type == Type.FILE) {
      FileKit.delete(this.path);
      return;
    }

    Path path = this.tmp.resolve(this.filename);
    if (!Files.exists(path))
      return;
    FileKit.delete(path);
  }

  public static class Builder {
    private String name;
    private String originName;
    private String filename;
    private Path tmp;
    private Path path;
    private byte[] binary;
    private Type type;


    public Builder() {

    }

    public UFile build() {
      return new UFile(this);
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder originName(String originName) {
      this.originName = originName;
      return this;
    }

    public Builder filename(String filename) {
      this.filename = filename;
      return this;
    }

    public Builder path(Path path) {
      this.path = path;
      this.type = Type.FILE;
      return this;
    }

    public Builder binary(byte[] binary) {
      this.binary = binary;
      this.type = Type.BINARY;
      return this;
    }

    public Builder tmp(Path tmp) {
      this.tmp = tmp;
      return this;
    }

  }

  @Override
  public String toString() {
    return "UFile{" +
      "name='" + name + '\'' +
      ", originName='" + originName + '\'' +
      ", filename='" + filename + '\'' +
      (
        this.type == null ? "ERROR UFILE." :
          (this.type == Type.FILE ? ", path=" + path : ", binary=" + Arrays.toString(this.binary))
      ) +
      '}';
  }
}
