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
package io.enoa.docker.dret.plugin;

import io.enoa.docker.dret.AbstractDRet;

import java.util.List;

public class EPluginLinux extends AbstractDRet {

  private List<String> capabilities;
  private Boolean allowalldevices;
  private List<EPluginDevice> devices;

  public EPluginLinux(Builder builder) {
    this.capabilities = builder.capabilities;
    this.allowalldevices = builder.allowalldevices;
    this.devices = builder.devices;
  }

  public List<String> capabilities() {
    return capabilities;
  }

  public Boolean allowalldevices() {
    return allowalldevices;
  }

  public List<EPluginDevice> devices() {
    return devices;
  }

  public static class Builder {

    private List<String> capabilities;
    private Boolean allowalldevices;
    private List<EPluginDevice> devices;

    public EPluginLinux build() {
      return new EPluginLinux(this);
    }

    public Builder capabilities(List<String> capabilities) {
      this.capabilities = capabilities;
      return this;
    }

    public Builder allowalldevices(Boolean allowalldevices) {
      this.allowalldevices = allowalldevices;
      return this;
    }

    public Builder devices(List<EPluginDevice> devices) {
      this.devices = devices;
      return this;
    }
  }


}
