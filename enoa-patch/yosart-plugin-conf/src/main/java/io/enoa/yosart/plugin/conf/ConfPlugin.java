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
package io.enoa.yosart.plugin.conf;

import io.enoa.conf.EnoaConf;
import io.enoa.yosart.YoPlugin;
import io.enoa.yosart.kernel.data.YdMount;
import io.enoa.yosart.kernel.mount.OyMountd;
import io.enoa.yosart.thr.OyPluginException;

public class ConfPlugin implements YoPlugin {

  private String[] paths;

  public ConfPlugin(String... paths) {
    this.paths = paths;
  }

  @Override
  public String name() {
    return "ConfPlugin";
  }

  @Override
  public String version() {
    return "1";
  }

  @Override
  public String description() {
    return "Config plugin, load config by environment, file and git repository.";
  }

  private static final String MOUNT_GROUP = "CONFGP";

  @Override
  public boolean start() throws OyPluginException {
    EnoaConf.load(this.paths);
    OyMountd.mounts().add(new YdMount(MOUNT_GROUP, "/conf", "Conf data", "Show load config", new ConfMount()));
    return true;
  }

  @Override
  public boolean stop() throws OyPluginException {
    return true;
  }
}
