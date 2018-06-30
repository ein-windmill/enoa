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
package io.enoa.yosart;

import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.yosart.kernel.data.YdAssets;
import io.enoa.yosart.kernel.resources.OyResource;

import java.util.Arrays;
import java.util.Map;

public interface Oysart {

//  static Yosart yosart() {
//    return Oysartd.yo().yosart();
//  }

  static String version() {
    return Oysartd.yo().version();
  }

  static YoConfig config() {
    return Oysartd.yo().config();
  }

  static Map<String, OyResource[]> resources() {
    return Oysartd.yo().resources();
  }

  static YoExt[] exts() {
    return Arrays.stream(Oysartd.yo().exts())
      .sorted((o1, o2) -> (int) (o2.weight() * 100D - o1.weight() * 100D))
      .toArray(YoExt[]::new);
  }

  static YoExt[] exts(YoExt.Type type) {
    return Arrays.stream(Oysartd.yo().exts())
      .filter(e -> e.type() == type)
      .sorted((o1, o2) -> (int) (o2.weight() * 100D - o1.weight() * 100D))
      .toArray(YoExt[]::new);
  }

  static YoExt ext(YoExt.Type type) {
    YoExt[] exts = exts(type);
    return CollectionKit.isEmpty(exts) ? null : exts[0];
  }

  static YoPlugin[] plugins() {
    return Oysartd.yo().plugins();
  }

  static String name() {
    return Oysartd.yo().name();
  }

  static YdAssets assets() {
    return Oysartd.yo().assets();
  }

}
