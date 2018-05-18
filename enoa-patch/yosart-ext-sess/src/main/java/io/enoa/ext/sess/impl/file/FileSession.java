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
package io.enoa.ext.sess.impl.file;

import io.enoa.ext.sess.SessFactory;
import io.enoa.log.kit.LogKit;
import io.enoa.serialization.EMgrSerialization;
import io.enoa.serialization.EoSerializationFactory;
import io.enoa.serialization.EoSerializer;
import io.enoa.toolkit.file.FileKit;
import io.enoa.yosart.kernel.http.Session;
import io.enoa.yosart.kernel.http.YoRequest;

import java.nio.file.Path;

public class FileSession implements SessFactory {

  private Path savePath;
  private EoSerializer serializer;
  private String sessKey;


  public FileSession(String sessKey, Path savePath, EoSerializationFactory serialization, boolean reset) {
    this.savePath = savePath;
    this.sessKey = sessKey;
    this.serializer = serialization == null ? EMgrSerialization.serialization().serializer() : serialization.serializer();
    if (reset)
      this.reset(savePath);
  }

  public FileSession(String sessKey, Path savePath, EoSerializationFactory serialization) {
    this(sessKey, savePath, serialization, true);
  }

  public FileSession(Path savePath, EoSerializationFactory serialization, boolean reset) {
    this("YOSESS", savePath, serialization, reset);
  }

  public FileSession(String sessKey, Path savePath, boolean reset) {
    this(sessKey, savePath, null, reset);
  }

  public FileSession(String sessKey, Path savePath) {
    this(sessKey, savePath, null, true);
  }

  public FileSession(Path savePath, boolean reset) {
    this("YOSESS", savePath, null, reset);
  }

  public FileSession(Path savePath) {
    this("YOSESS", savePath, null, true);
  }


  @Override
  public Session sess(YoRequest request) {
    return new FileSessionImpl(request, this.sessKey, this.savePath, this.serializer);
  }

  private void reset(Path path) {
    try {
      FileKit.delete(path.toString());
    } catch (Exception e) {
      LogKit.error(e.getMessage(), e);
    }
  }
}
