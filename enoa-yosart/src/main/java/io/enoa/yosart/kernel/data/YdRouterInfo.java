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
package io.enoa.yosart.kernel.data;

import io.enoa.repeater.http.Method;
import io.enoa.yosart.kernel.resources.YaControl;
import io.enoa.yosart.kernel.resources.YoAction;
import io.enoa.yosart.kernel.resources.YoRouter;

public class YdRouterInfo extends YaData {

  private final Method method;
  private final String uri;
  private final YoAction action;
  private final Class<? extends YaControl> control;
  private final YoRouter router;


  private YdRouterInfo(Builder builder) {
    this.method = builder.method;
    this.uri = builder.uri;
    this.action = builder.action;
    this.control = builder.control;
    this.router = builder.router;
  }

  public Method method() {
    return this.method;
  }

  public String uri() {
    return this.uri;
  }

  public YoAction action() {
    return this.action;
  }

  public Class<? extends YaControl> control() {
    return this.control;
  }

  public YoRouter router() {
    return this.router;
  }

  public static class Builder {
    private Method method;
    private String uri;
    private YoAction action;
    private Class<? extends YaControl> control;
    private YoRouter router;

    public YdRouterInfo build() {
      return new YdRouterInfo(this);
    }

    public Builder method(Method method) {
      this.method = method;
      return this;
    }

    public Builder uri(String uri) {
      this.uri = uri;
      return this;
    }

    public Builder action(YoAction action) {
      this.action = action;
      return this;
    }

    public Builder control(Class<? extends YaControl> control) {
      this.control = control;
      return this;
    }

    public Builder router(YoRouter router) {
      this.router = router;
      return this;
    }

  }

}
