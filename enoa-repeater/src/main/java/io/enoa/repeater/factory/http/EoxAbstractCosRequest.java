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
package io.enoa.repeater.factory.http;

import io.enoa.repeater.EoxConfig;
import io.enoa.repeater.cos.*;
import io.enoa.repeater.factory.name.EoxNameRuleFactory;
import io.enoa.repeater.http.UFile;
import io.enoa.toolkit.alg.UnitConvKit;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.file.FileKit;
import io.enoa.toolkit.stream.StreamKit;
import io.enoa.toolkit.text.TextKit;
import io.enoa.toolkit.thr.EoException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.*;

public abstract class EoxAbstractCosRequest extends EoxAbstractRequest {


  private Map<String, List<String>> binparas = new HashMap<>();
  private List<UFile> ufiles = new ArrayList<>();


  /**
   * 解析 formdata 文件上传 repeater-cos
   *
   * @param stream stream
   * @param rule   file rename rule
   * @param config config
   */
  protected void handleUpload(InputStream stream, EoxConfig config, EoxNameRuleFactory rule) {
    String contentType = this.header("content-type");
    if (TextKit.isBlank(contentType) || !contentType.startsWith("multipart/form-data"))
      return;

    try {
      MultipartParser parser =
        new MultipartParser(new MultipartBody.Builder()
          .stream(new EoxInputStream(stream))
          .contentType(contentType)
          .contentLength(Long.parseLong(this.header("content-length")))
          .build(), (int) UnitConvKit.convert(config.maxUploadSize(), UnitConvKit.Unit.MB, UnitConvKit.Unit.BYTE),
          true, true, config.charset().name());

      FileKit.mkdirs(config.tmp());

      Part part;
      while ((part = parser.readNextPart()) != null) {
        String name = part.getName();
        if (name == null) {
          throw new IOException("Malformed input: parameter name missing (known Opera 7 bug)");
        }

        if (part.isParam()) {
          ParamPart paramPart = (ParamPart) part;
          String value = paramPart.getStringValue();
          List<String> paras = this.binparas.get(name);
          if (paras != null) {
            paras.add(value);
            continue;
          }
          paras = new ArrayList<>();
          paras.add(value);
          this.binparas.put(name, paras);
        }

        if (part.isFile()) {
          FilePart filePart = (FilePart) part;
          String fileName = filePart.getFileName();
          if (fileName == null) {
            this.ufiles.add(new UFile.Builder()
              .name(name)
              .tmp(config.tmp())
              .build()
            );
            continue;
          }

          String originName = filePart.getFileName();

          UFile.Builder ufile = new UFile.Builder()
            .name(name)
            .tmp(config.tmp())
            .originName(originName);

          if (config.holdFile()) {
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
              filePart.writeTo(baos);
              this.ufiles.add(ufile
                .filename(originName)
                .binary(baos.toByteArray()).build());
            }
          } else {
            String newname = rule.name(config.tmp(), originName);
            filePart.writeTo(config.tmp().toString(), newname);
            this.ufiles.add(ufile
              .filename(newname)
              .path(Paths.get(config.tmp().toString()).resolve(newname)).build());
          }
//          this.ufiles.add(
//            new UFile.Builder()
//              .name(name)
//              .filename(filePart.getFileName())
//              .originName(originName)
//              .path(Paths.get(config.tmp().toString(), newname))
//              .build()
//          );
        }
      }
      // fixme check
//      this.binparas = Collections.unmodifiableMap(this.binparas);
//      this.ufiles = Collections.unmodifiableList(this.ufiles);
    } catch (IOException e) {
      throw new EoException(e);
    } finally {
      StreamKit.close(stream);
    }
  }

  protected Map<String, String[]> paraMap(Map<String, List<String>> map1) {
    if (CollectionKit.notEmpty(this.binparas))
      return super.paraMap(map1, this.binparas);
    Map<String, String[]> ret = this.mapListToArray(map1);
    if (CollectionKit.notEmpty(ret))
      return Collections.unmodifiableMap(ret);
    return null;
  }

  @Override
  public UFile[] files() {
    String contentType = this.header("content-type");
    if (TextKit.isBlank(contentType) || !contentType.startsWith("multipart/form-data"))
      return CollectionKit.emptyArray(UFile.class);
    return this.ufiles.toArray(new UFile[this.ufiles.size()]);
  }

  @Override
  public UFile[] files(String name) {
    return this.ufiles.stream().filter(u -> u.name().equals(name)).toArray(UFile[]::new);
  }

  @Override
  public UFile file(String name) {
    UFile[] files = this.files(name);
    if (CollectionKit.isEmpty(files))
      return null;
    return files[0];
  }

  @Override
  public void clear() {
    CollectionKit.clear(this.binparas);
    CollectionKit.clear(this.ufiles);
  }
}
