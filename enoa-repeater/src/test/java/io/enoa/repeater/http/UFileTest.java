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
package io.enoa.repeater.http;

import io.enoa.toolkit.file.FileKit;
import org.junit.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UFileTest {


  //  @Test
  public void testUFile() {
//    UFile ufile = new UFile.Builder()
//      .path(Paths.get("/tmp", "ls"))
//      .build();
//
//    ufile.move("/tmp/lsmove");

    Path path = Paths.get("/tmp", "lsmove");
    System.out.println(FileKit.exists(path));
    path = Paths.get(new File("/tmp", "lsmove").toURI());
    System.out.println(FileKit.exists(path));
    Path lspath = Paths.get("/tmp/ls");
    System.out.println(FileKit.exists(lspath));
    FileKit.move(path, Paths.get("/tmp/ls"));
    System.out.println(FileKit.exists(lspath));


  }

  @Test
  public void testPath() {
    Path path = Paths.get("/tmp/ls");
    System.out.println(path.getRoot());
    System.out.println(path.getFileName());
    System.out.println(path.getParent());

  }


}
