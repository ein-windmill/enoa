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


import io.enoa.gateway.GErrorRenderFactory;

import java.util.Arrays;

public class GData {

  private GMapping[] mappings;
  private String[] noauths;
  private GAuthData[] auths;
  private GErrorRenderFactory errorRenderFactory;

  public GData(GMapping[] mappings,
               String[] noauths,
               GAuthData[] auths,
               GErrorRenderFactory errorRenderFactory) {
    this.mappings = mappings;
    this.noauths = noauths;
    this.auths = auths;
    this.errorRenderFactory = errorRenderFactory;
  }

  public GMapping[] mappings() {
    return mappings;
  }

  public String[] noauths() {
    return noauths;
  }

  public GAuthData[] auth() {
    return auths;
  }

  public GErrorRenderFactory errorRenderFactory() {
    return errorRenderFactory;
  }

  @Override
  public String toString() {
    return "GData{" +
      "mappings=" + Arrays.toString(mappings) +
      ", noauths=" + Arrays.toString(noauths) +
      ", auths=" + Arrays.toString(auths) +
      '}';
  }
}
