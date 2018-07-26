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
package io.enoa.toolkit.eo.tip;

import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.prop.Prop;
import io.enoa.toolkit.thr.EoException;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * enoa - io.enoa.toolkit.eo.tip
 */
public class EnoaTipKit {

  private static _EnoaTip tip;

  static {
    try {
      tip = new _EnoaTip();
      loadTip();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void loadTip() throws IOException {
    Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources("eocnf/");
    while (urls.hasMoreElements()) {
      URL url = urls.nextElement();

      if ("jar".equals(url.getProtocol())) {
        String jarPath = url.toString().substring(0, url.toString().indexOf("!/") + 2);

        URL jarURL = new URL(jarPath);
        JarURLConnection jarCon = (JarURLConnection) jarURL.openConnection();
        JarFile jarFile = jarCon.getJarFile();
        Enumeration<JarEntry> jarEntrys = jarFile.entries();

        while (jarEntrys.hasMoreElements()) {
          JarEntry entry = jarEntrys.nextElement();
          String name = entry.getName();
          if (name.endsWith(".eo.properties") && !entry.isDirectory()) {
            tip.add(name, new Prop(name));
          }
        }
      }

      if ("file".equals(url.getProtocol())) {
        File resourcePath = new File(url.getPath());
        File[] files = resourcePath.listFiles(pathname -> pathname.getName().endsWith(".eo.properties"));
        if (CollectionKit.isEmpty(files)) {
          throw new EoException("Not found tip config file.");
        }
        for (File file : files) {
          tip.add(file.getName(), new Prop(file));
        }
      }

    }
  }

  public static String message(String key, Object... args) {
    return tip.message(key, args);
  }
}
