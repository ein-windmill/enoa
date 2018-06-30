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
package io.enoa.repeater.provider.nanohttpd.server.plus;

import fi.iki.elonen.NanoHTTPD;
import io.enoa.log.Log;
import io.enoa.repeater.EoxConfig;
import io.enoa.toolkit.thr.EoException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class _RepeaterNanoTempFileManager implements NanoHTTPD.TempFileManager {

  private EoxConfig config;

  private final File tmpdir;

  private final List<NanoHTTPD.TempFile> tempFiles;


  public _RepeaterNanoTempFileManager(EoxConfig config) {
    this.config = config;
    if (!Files.exists(this.config.tmp())) {
      try {
        Files.createDirectories(this.config.tmp());
      } catch (IOException e) {
        throw new EoException("Can not create tmp folder.");
      }
    }
    this.tmpdir = new File(this.config.tmp().toString());
    this.tempFiles = new ArrayList<>();
  }


  @Override
  public void clear() {
    for (NanoHTTPD.TempFile file : this.tempFiles) {
      try {
        file.delete();
      } catch (Exception ignored) {
        Log.warn("could not delete file ", ignored);
      }
    }
    this.tempFiles.clear();
  }

  @Override
  public NanoHTTPD.TempFile createTempFile(String filename_hint) throws Exception {
    NanoHTTPD.DefaultTempFile tempFile = new NanoHTTPD.DefaultTempFile(this.tmpdir);
    this.tempFiles.add(tempFile);
    return tempFile;
  }
}
