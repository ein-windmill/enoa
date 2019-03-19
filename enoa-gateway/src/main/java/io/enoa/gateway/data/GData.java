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
package io.enoa.gateway.data;


import io.enoa.gateway.GErrorRenderFactory;
import io.enoa.gateway.IGatewayReporter;
import io.enoa.repeater.http.Header;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GData {

  private final boolean interceptoption;
  private final GMapping[] mappings;
  private final String[] noauths;
  private final GAuthData[] auths;
  private final boolean cros;
  private final GErrorRenderFactory errorRenderFactory;
  private final List<Header> crosHeaders;
  private final List<Header> defaultCrosHeaders;
  private final List<IGatewayReporter> reporters;

  public GData(boolean interceptoption,
               GMapping[] mappings,
               String[] noauths,
               GAuthData[] auths,
               List<IGatewayReporter> reporters,
               boolean cros,
               List<Header> crosHeaders,
               GErrorRenderFactory errorRenderFactory) {
    this.reporters = reporters;
    this.interceptoption = interceptoption;
    this.mappings = mappings;
    this.noauths = noauths;
    this.auths = auths;
    this.cros = cros;
    this.crosHeaders = crosHeaders;
    this.errorRenderFactory = errorRenderFactory;
    this.defaultCrosHeaders = new ArrayList<Header>() {{
      add(new Header("Access-Control-Allow-Origin", "*"));
      add(new Header("Access-Control-Allow-Credentials", "true"));
      add(new Header("Access-Control-Allow-Method", "GET,POST,PUT,PATCH,DELETE"));
      add(new Header("Access-Control-Allow-Headers", String.join(",", new String[]{
        "Content-Type",
        "X-HTTP-Method-Override",
        "Access-Control-Request-Headers",
        "Access-Control-Request-Method"
      })));
    }};
  }

  public List<IGatewayReporter> reporters() {
    return reporters;
  }

  public boolean interceptoption() {
    return interceptoption;
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

  public List<Header> crosHeaders() {
    return crosHeaders;
  }

  public boolean cros() {
    return cros;
  }

  public List<Header> defaultCrosHeaders() {
    return defaultCrosHeaders;
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
