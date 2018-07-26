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
package io.enoa.conf.impl;

import io.enoa.conf.EnoaConfDomain;
import io.enoa.conf._EnoaConfReader;
import io.enoa.log.Log;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.file.FileKit;
import io.enoa.toolkit.text.TextKit;
import io.enoa.toolkit.thr.EoException;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EoGitReader extends _EnoaConfReader {


  private void clone(String url, String localPath) {
    try {
      Log.debug("Start clone {} to [{}]", url, localPath);
      CloneCommand cc = Git.cloneRepository().setURI(url);
      cc.setDirectory(new File(localPath)).call();
      Log.debug("Clone done");
    } catch (Exception e) {
      Log.error(e.getMessage(), e);
      throw new EoException(e.getMessage(), e);
    }
  }

  private String clonePath(String path) {
    int ix = 0;
//    String folder = String.format("%s%s%s", System.getenv("tmp"), File.separator, super.extraName(path));
    String folder = TextKit.union(System.getenv("tmp"), File.separator, super.extraName(path));
    while (ix < 99999) {
      String ret = ix == 0 ? folder : folder.concat("_") + ix;
      if (!FileKit.exists(ret))
        return ret;
      ix += 1;
    }
    throw new EoException("Can not clone to => {0}", path);
  }

  @Override
  public List<EnoaConfDomain> read(String path) {
    String clonePath = this.clonePath(path);
    this.clone(path, clonePath);
    File repo = new File(clonePath);
    File[] files = repo.listFiles(f ->
      f.getAbsolutePath().endsWith(".yaml") ||
        f.getAbsolutePath().endsWith(".yml") ||
        f.getAbsolutePath().endsWith(".json") ||
        f.getAbsolutePath().endsWith(".properties"));
    if (CollectionKit.isEmpty(files))
      return Collections.emptyList();
    List<EnoaConfDomain> rets = new ArrayList<>();
    for (File file : files) {
      String p = file.getAbsolutePath();
      if (p.endsWith(".yml") || p.endsWith(".yaml"))
        rets.addAll(EoYamlReader.me.read(EnoaConfDomain.Origin.GIT, p));
      if (p.endsWith(".properties"))
        rets.addAll(EoPropertiesReader.me.read(EnoaConfDomain.Origin.GIT, p));
      if (p.endsWith(".json"))
        rets.addAll(EoJsonReader.me.read(EnoaConfDomain.Origin.GIT, p));
    }
    return rets;
  }
}
