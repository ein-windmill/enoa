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
package io.enoa.repeater.name;

import io.enoa.repeater.factory.name.EoxNameRuleFactory;
import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.toolkit.file.FileKit;
import io.enoa.toolkit.text.TextKit;
import io.enoa.toolkit.thr.EoException;

import java.io.File;
import java.nio.file.Path;

public class OriginNameRule implements EoxNameRuleFactory {
  @Override
  public String name(Path path, String originName) {
    String folder = path.toString();
    folder = folder.endsWith(File.separator) ? folder.substring(0, folder.length() - 1) : folder;
//    String fname = String.format("%s%s%s", folder, File.separator, originName);
    String fname = TextKit.union(folder, File.separator, originName);
    if (!FileKit.exists(fname))
      return originName;

    String leftName = originName.contains(".") ? originName.substring(0, originName.lastIndexOf(".")) : originName;
    String suffix = FileKit.extension(originName);
    int i = 0;
    while (i < 99999) {
//      String newName = String.format("%s-%d", leftName, i);
      String newName = TextKit.union(leftName, "-", i);
      if (TextKit.notBlank(suffix)) {
//        newName = newName.concat(".").concat(suffix);
        newName = TextKit.union(newName, ".", suffix);
      }
//      String nfe = String.format("%s%s%s", folder, File.separator, newName);
      String nfe = TextKit.union(folder, File.separator, newName);
      if (FileKit.exists(nfe)) {
        i += 1;
        continue;
      }
      return newName;
    }
    throw new EoException(EnoaTipKit.message("eo.tip.repeater.cant_upload_file_by_rename", folder, originName));
  }
}
