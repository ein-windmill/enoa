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

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Objects;

public class EnoaTipKitTest {


  private void findFile() {
    try {
      Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources("");
      while (urls.hasMoreElements()) {
        URL url = urls.nextElement();
//        if (!url.getPath().endsWith("/webapp/"))
//          continue;

        File f = new File(url.getPath());
        for (File file : Objects.requireNonNull(f.listFiles())) {
          System.out.println(file.getName());
        }
      }
    } catch (IOException e) {
      throw new RuntimeException("Can not find webapp.");
    }
  }


  @Test
  public void testFind() {
//    this.findFile();
    String message = EnoaTipKit.message("eo.tip.toolkit.noinit");
    System.out.println(message);
    message = EnoaTipKit.message("eo.tip.toolkit.tip_key_not_null");
    System.out.println(message);
    message = EnoaTipKit.message("notfound");
    System.out.println(message);
    // message key can not be null
//    message = EnoaTipKit.message("");
//    System.out.println(message);
  }

  @Test
  public void testFile() {
    String file ="/tmp/_eo_tip_yosart_en_US.eo.properties";
    File f = new File(file);
    System.out.println(f.getName());
  }

}
