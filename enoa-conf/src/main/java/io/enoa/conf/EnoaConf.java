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

import io.enoa.conf.impl.EoDiskReader;
import io.enoa.conf.impl.EoEnvironmentReader;
import io.enoa.conf.impl.EoGitReader;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.toolkit.file.FileKit;
import io.enoa.toolkit.text.TextKit;
import io.enoa.toolkit.thr.EoException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EnoaConf {


  private static final String[] GIT_PROTOCOLS = {"http", "https"}; // "ssh", "git",
  private static final String[] ALLOW_SUFFIX = {"properties", "yml", "yaml"}; // "json",

  private static final Map<String, _EnoaConfReader> readerMap;
  private static final List<String> RESOURCE_PATH;

  private static List<EnoaConfDomain> confList = new ArrayList<>();

  static {
    readerMap = new HashMap<>();
    readerMap.put("env", new EoEnvironmentReader());
    readerMap.put("disk", new EoDiskReader());
    readerMap.put("git", new EoGitReader());

    RESOURCE_PATH = new ArrayList<>();

    Enumeration<URL> urls;
    try {
      urls = Thread.currentThread().getContextClassLoader().getResources("");
    } catch (IOException e) {
      throw new EoException(e.getMessage(), e);
    }
    while (urls.hasMoreElements())
      RESOURCE_PATH.add(urls.nextElement().getPath());

    /*
     主动初始化
     */
    load();
  }

  protected static List<EnoaConfDomain> confList() {
    return confList;
  }

  /**
   * 从资源路径装载配置
   * 支持本地文件以及 git 仓库
   *
   * @param paths 装载路径
   * @throws EoException EoE
   */
  public static void load(String... paths) throws EoException {
    // 如果初始化时发现已有信息, 清空后在继续
    if (CollectionKit.notEmpty(confList))
      CollectionKit.clear(confList);

    verifyPath(paths);

//    List<EnoaConfDomain> rets = new ArrayList<>();
    confList.addAll(byEnv());

    confList.addAll(byDisk());

    List<String> diskPath = Arrays.stream(paths)
      .filter(p -> Stream.of(GIT_PROTOCOLS).noneMatch(p.toLowerCase()::startsWith))
      .filter(p -> Stream.of(ALLOW_SUFFIX).anyMatch(p.toLowerCase()::endsWith))
      .collect(Collectors.toList());
    diskPath = fillDiskPath(diskPath);

    List<String> otherDiskPaths = new ArrayList<>();
    for (String dp : diskPath) {
      boolean has = false;
      for (EnoaConfDomain ret : confList) {
        if (ret.path() == null)
          continue;
        if (!ret.path().equals(dp))
          continue;
        has = true;
        break;
      }
      if (!has)
        otherDiskPaths.add(dp);
    }

    confList.addAll(byDisk(otherDiskPaths.toArray(new String[otherDiskPaths.size()])));
    CollectionKit.clear(diskPath, otherDiskPaths);

    String[] gitPaths = Arrays.stream(paths)
      .filter(p -> Stream.of(GIT_PROTOCOLS).anyMatch(p.toLowerCase()::startsWith))
      .toArray(String[]::new);
    confList.addAll(byGit(gitPaths));

  }

  private static List<String> fillDiskPath(List<String> diskPath) {
    List<String> rets = new ArrayList<>();
    FIR:
    for (int i = diskPath.size(); i-- > 0; ) {
      String path = diskPath.get(i);
      File file = new File(path);
      if (file.exists()) {
        rets.add(file.getAbsolutePath());
        continue;
      }
      SEC:
      for (String p : RESOURCE_PATH) {
//        file = new File(String.format("%s%s", p, path));
        file = new File(TextKit.union(p, path));
        if (!file.exists())
          continue SEC;
        rets.add(file.getAbsolutePath());
      }
    }
    CollectionKit.clear(diskPath);
    return rets;
  }

  private static List<EnoaConfDomain> byEnv() {
    return readerMap.get("env").read(null);
  }

  private static List<EnoaConfDomain> byDisk() throws EoException {
    List<EnoaConfDomain> rets = new ArrayList<>();
    RESOURCE_PATH.forEach(url -> {
      File resourcePath = new File(url);
      File[] files = resourcePath
        .listFiles(pathname ->
          Stream.of(ALLOW_SUFFIX).anyMatch(a ->
//            String.format(".%s", pathname.getName().toLowerCase()).endsWith(String.format(".%s", a))));
            TextKit.union(".", pathname.getName().toLowerCase()).endsWith(TextKit.union(".", a))));
      if (CollectionKit.isEmpty(files))
        return;
      rets.addAll(byDisk(Arrays.stream(files).map(File::getAbsolutePath).toArray(String[]::new)));
    });
    return rets;
  }

  private static List<EnoaConfDomain> byDisk(String... path) throws EoException {
    List<EnoaConfDomain> rets = new ArrayList<>();
    for (String s : path) {
      rets.addAll(readerMap.get("disk").read(s));
    }
    return rets;
  }

  private static List<EnoaConfDomain> byGit(String... path) {
    List<EnoaConfDomain> rets = new ArrayList<>();
    for (String s : path) {
      rets.addAll(readerMap.get("git").read(s));
    }
    return rets;
  }

  private static boolean existsFile(String name) {
    if (FileKit.exists(name))
      return true;
    for (String s : RESOURCE_PATH)
//      if (FileKit.exists(Paths.get(new File(String.format("%s%s", s, name)).toURI())))
      if (FileKit.exists(Paths.get(new File(TextKit.union(s, name)).toURI())))
        return true;
    return false;
  }

  private static void verifyPath(String... paths) {
    for (String path : paths) {
      if (TextKit.isBlank(path))
        continue; // skip
      // git repo
      if (Stream.of(GIT_PROTOCOLS).anyMatch(path.toLowerCase()::startsWith))
        continue;
      // local file
      if (existsFile(path))
        continue;
      throw new EoException(EnoaTipKit.message("eo.tip.conf.unrecognized_path", path));
    }
  }

}
