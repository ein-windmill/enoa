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
package io.enoa.conf;

import io.enoa.conf.impl.EoGitReader;
import org.junit.Test;

public class _EnoaConfReaderTest {

  @Test
  public void extraName() {
    String path = "/dev/github/jetcd/pom-dev.xml";
    _EnoaConfReader r = new EoGitReader();
    String s = r.extraName(path);
    System.out.println(s);
    System.out.println(r.extraEnv(path));
  }
}
