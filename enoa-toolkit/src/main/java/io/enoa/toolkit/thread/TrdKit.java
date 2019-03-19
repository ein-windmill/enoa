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
package io.enoa.toolkit.thread;

import io.enoa.toolkit.text.TextKit;

public class TrdKit {

  public static void name(Thread thread, String name) {
    String oldname = thread.getName();
    if (!oldname.startsWith("pool-")) {
      thread.setName(name);
      return;
    }
//    int ix = oldname.lastIndexOf("-");
//    String newname = TextKit.union(name, "-", oldname.substring(ix + 1));
//    thread.setName(newname);

    int fix = oldname.indexOf("-"),
      lix = oldname.lastIndexOf("-");

    String group = oldname.substring(fix + 1, lix),
      mem = oldname.substring(lix + 1);
    thread.setName(TextKit.union(name, "-", group, "-", mem));
  }

}
