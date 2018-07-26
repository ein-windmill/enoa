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
package io.enoa.conf;

import java.io.File;

public abstract class _EnoaConfReader implements _ConfReader {

  protected String extraName(String path) {
    if (path.startsWith("http") || path.startsWith("https"))
      return this.extraNameByGit(path);
    return this.extraNameByDisk(path);
  }

  private String extraNameByDisk(String path) {
    // /path/conf-dev.yml -> conf-dev.yml
    path = path.substring(path.lastIndexOf(File.separator) + 1, path.length());
    // conf-dev.yml -> conf-dev
    path = path.substring(0, path.indexOf("."));
    return path;
  }

  private String extraNameByGit(String path) {
    // https://github.com/spring-cloud-samples/config-repo.git
    path = path.substring(path.lastIndexOf("/") + 1, path.length());
    path = path.substring(0, path.lastIndexOf("."));
    return path;
  }


  protected EnoaConfDomain.Env extraEnv(String path) {
    path = this.extraName(path);
    // conf-dev -> dev
    path = path.substring(path.lastIndexOf("-") + 1, path.length());
    return EnoaConfDomain.Env.of(path);
  }

}
