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
package io.enoa.gateway.data;


import io.enoa.gateway.GatewayErrorRenderFactory;

import java.util.Arrays;

public class EnoaGatewayData {

  private GatewayMapping[] mappings;
  private String[] noauths;
  private EnoaGatewayAuthData[] auths;
  private GatewayErrorRenderFactory errorRenderFactory;

  public EnoaGatewayData(GatewayMapping[] mappings,
                         String[] noauths,
                         EnoaGatewayAuthData[] auths,
                         GatewayErrorRenderFactory errorRenderFactory) {
    this.mappings = mappings;
    this.noauths = noauths;
    this.auths = auths;
    this.errorRenderFactory = errorRenderFactory;
  }

  public GatewayMapping[] mappings() {
    return mappings;
  }

  public String[] noauths() {
    return noauths;
  }

  public EnoaGatewayAuthData[] auth() {
    return auths;
  }

  public GatewayErrorRenderFactory errorRenderFactory() {
    return errorRenderFactory;
  }

  @Override
  public String toString() {
    return "EnoaGatewayData{" +
      "mappings=" + Arrays.toString(mappings) +
      ", noauths=" + Arrays.toString(noauths) +
      ", auths=" + Arrays.toString(auths) +
      '}';
  }
}
