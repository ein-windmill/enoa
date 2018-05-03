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
package io.enoa.toolkit.file;

import io.enoa.toolkit.binary.EnoaBinary;
import io.enoa.toolkit.path.PathKit;
import org.junit.Test;


public class FileKitTest {

  @Test
  public void getFileExtension() {
    System.out.println(FileKit.extension("a.txt"));
    System.out.println(FileKit.extension("a.png"));
    System.out.println(FileKit.extension("a.tgz"));
    System.out.println(FileKit.extension("a."));
    System.out.println(FileKit.extension("a"));
  }

  @Test
  public void testDelete() {
    FileKit.delete("/tmp/config-repo");
  }

  @Test
  public void readFile() {
    EnoaBinary efile = FileKit.read(PathKit.bootPath().resolve("src/test/java/io/enoa/toolkit/file/FileKitTest.java"));
    System.out.println(efile.string());
  }
}
