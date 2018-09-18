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
package io.enoa.yosart;

import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.yosart.thr.OyPluginException;

class YoPlugind {

  private static final YoPlugind yo = new YoPlugind();

  static YoPlugind yo() {
    return yo;
  }

  void start(YoPlugin plugin) throws OyPluginException {
    try {
      if (!plugin.start()) {
        throw new OyPluginException(EnoaTipKit.message("eo.tip.yosart.plugin_start_fail", plugin.getClass().getName()));
      }
    } catch (Exception e) {
      if (e instanceof OyPluginException)
        throw e;
      throw new OyPluginException(e.getMessage(), e);
    }
  }

  void stop(YoPlugin plugin) throws OyPluginException {
    try {
      if (!plugin.stop()) {
        throw new OyPluginException(EnoaTipKit.message("eo.tip.yosart.plugin_stop_fail", plugin.getClass().getName()));
      }
    } catch (Exception e) {
      if (e instanceof OyPluginException)
        throw e;
      throw new OyPluginException(e.getMessage(), e);
    }
  }

}
