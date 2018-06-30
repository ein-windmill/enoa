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
package io.enoa.toolkit.path;

import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.toolkit.http.UriKit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * enoa - io.enoa.toolkit.path
 */
public class PathKit {

  private static Path BOOT_PATH;
  private static Path USER_HOME;
  private static List<Path> RESOURCES_PATH;
  private static Map<String, Path> SUBPATHCACHE = new HashMap<>();

  static {
    USER_HOME = Paths.get(System.getProperty("user.home"));
    BOOT_PATH = Paths.get(System.getProperty("user.dir"));
    RESOURCES_PATH = new ArrayList<>();
    try {
      initResourcesPath();
    } catch (IOException ignored) {
    }
  }

  private static Path toPath(URL url, String... subpaths) {
    if (url == null)
      return null;
    try {
      return Paths.get(Paths.get(url.toURI().getPath()).toString(), subpaths);
    } catch (Exception e) {
      return Paths.get(new File(url.getFile()).getAbsolutePath(), subpaths);
    }
  }

  private static ClassLoader classLoader() {
    ClassLoader loader = Thread.currentThread().getContextClassLoader();
    return loader == null ? PathKit.class.getClassLoader() : loader;
  }

  private static void initResourcesPath() throws IOException {
    //
    Enumeration<URL> urls = classLoader().getResources("");
    while (urls.hasMoreElements()) {
      URL u0 = urls.nextElement();
      RESOURCES_PATH.add(toPath(u0));
    }
  }


  private static Path detectPath(String subpath) {
    subpath = Paths.get(UriKit.correct(subpath)).toString();
    if (subpath.length() == 1)
      subpath = ""; // remove correct uri first slash

    // 嘗試 resources path 是否有後綴路徑匹配
    for (Path resource : RESOURCES_PATH) {
      if (resource.endsWith(subpath))
        return resource;

      Path correct = correct(resource, subpath);
      if (correct == null)
        continue;

      SUBPATHCACHE.put(subpath, correct);
      return correct;
    }

    throw new RuntimeException(new FileNotFoundException(EnoaTipKit.message("eo.tip.toolkit.path_resource_404", subpath)));
  }

  /**
   * 糾正路徑, 通常在開發模式中會走的邏輯中
   * 開發時 resources 通常只會有編譯後運行的路徑, 因此進行目錄樹的提取, 從而找到指定的路徑
   *
   * @param path    path
   * @param subpath 子路徑
   * @return Path
   */
  private static Path correct(Path path, String subpath) {
    Path p0 = Paths.get(path.toString(), subpath);
    if (Files.exists(p0))
      return p0;

    List<String> pathTree = new ArrayList<>();
    try {
      pathTree(pathTree, path);
    } catch (IOException ignored) {

    }
    for (String tree : pathTree) {
      if (tree.endsWith(subpath))
        return Paths.get(tree);
    }
    return null;
  }

  private static void pathTree(List<String> trees, Path path) throws IOException {
    Files.list(path).forEach(sub -> {
      if (Files.isRegularFile(sub))
        return;
      trees.add(sub.toString());
      try {
        pathTree(trees, path.resolve(sub));
      } catch (IOException ignored) {

      }
    });
  }

  /**
   * 獲取應用啟動路徑
   * 適用與開發模式, 符合 maven 工程構造, 如果是 maven 工程, 獲取的是當前項目的根路徑
   * 例如 /project/src/main/... 獲取額是 /project
   * 如果非 maven 工程, 將會獲取工程啟動是所在的路徑
   * 例如在 /tmp 啟動工程, 那麼獲取到的也是 /tmp
   * 開發模式中會用到此路徑, 譬如是模板的路徑配置, 配置此路徑可避免應用的重啟即可進行模板的熱加載
   *
   * @return Path
   */
  public static Path debugPath() {
//    return BOOT_PATH;
//    return Paths.get("").normalize();
    try {
      Path ret = Paths.get(PathKit.class.getResource("/").toURI());
      if (ret.endsWith(Paths.get("target", "classes")) || ret.endsWith(Paths.get("target", "test-classes")))
        return ret.getParent().getParent();
      return BOOT_PATH;
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  /**
   * 系統賬戶路徑
   *
   * @return Path
   */
  public static Path userHome() {
    return USER_HOME;
  }

  /**
   * 所有配置在 classpath 中的資源路徑
   *
   * @return Path[]
   */
  public static Path[] resources() {
    return RESOURCES_PATH.toArray(new Path[RESOURCES_PATH.size()]);
  }

  public static Path path() {
    return path("");
  }

  /**
   * 根據子路徑查找絕對路徑
   * 從 classpath 中註冊的資源路徑去尋找
   * 通常在打包後的環境中是可以直接找到路徑並返回的, 為兼容開發環境的情況, 做了
   * 一次目錄樹的獲取, classpath 無法找到當前子路徑時從該樹中提取子目錄
   * <p>
   * 注意(非常重要):
   * 無論是 classpath 獲取還是目錄中提取都要注意, 子路徑的匹配模式是結尾相同, 因此若存在相同的目錄名, 提取的路徑已第一次匹配優先;
   * 避免提取錯誤的問題發生, 從三個點注意, 首先是目錄結構儘量避免重複, 另外是在提取時儘量將 subpath 寫全, 再有就是儘量保證開發環境與
   * 部署環境目錄結構一致.
   *
   * @param subpath 子路徑
   * @return Path
   */
  public static Path path(String subpath) {
    if (subpath == null)
      throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.toolkit.path_subpath_null"));
    Path pat = SUBPATHCACHE.get(subpath);
    if (pat != null)
      return pat;
    return detectPath(subpath);
  }


}
