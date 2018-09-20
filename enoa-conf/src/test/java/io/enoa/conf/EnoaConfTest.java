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

import io.enoa.json.Json;
import io.enoa.log.Log;
import io.enoa.toolkit.path.PathKit;
import io.enoa.toolkit.thr.EoException;
import org.junit.Ignore;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

@Ignore
public class EnoaConfTest {

  @Test
  public void testLoad() {
    EnoaConf.load("conf.properties", "conf.json", "conf.yml",
      "https://github.com/mstine/config-repo.git");

    List<EnoaConfDomain> confs = ConfKit.confs();
    Log.debug(Json.toJson(confs));
  }

  @Test
  public void testPath() {

    Enumeration<URL> urls;
    try {
      urls = Thread.currentThread().getContextClassLoader().getResources("");
    } catch (IOException e) {
      throw new EoException(e.getMessage(), e);
    }
    while (urls.hasMoreElements()) {
      URL url = urls.nextElement();
      System.out.println(url.getPath());
    }
  }

  @Test
  public void testYml() throws FileNotFoundException {
    Yaml yaml = new Yaml();
    Map conf = yaml.loadAs(new FileInputStream(PathKit.debugPath().resolve("src/test/resources/conf.yml").toFile()),
      Map.class);
    System.out.println(conf);
  }

}
