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
package io.enoa.yosart.kernel.resources;

import io.enoa.repeater.http.Method;
import io.enoa.yosart.kernel.data.YdRouterInfo;

import java.util.ArrayList;
import java.util.List;

public abstract class YoRouter implements YaRouter {

  private Builder builder = new Builder();

  @Override
  public YdRouterInfo[] routes() {
    this.register();
    return this.builder.routes();
  }

  protected abstract void register();

  protected Builder add(Method method, String uri, YoAction action) {
    return this.builder.add(method, uri, action);
  }

  protected Builder add(String uri, YoAction action) {
    return this.builder.add(uri, action);
  }

  protected Builder add(String uri, Class<? extends YaControl> control) {
    return this.builder.add(uri, control);
  }

  protected Builder add(Class<? extends YaControl> control) {
    return this.builder.add(control);
  }

  protected Builder add(String uri, YoRouter router) {
    return this.builder.add(uri, router);
  }

  protected Builder add(YoRouter router) {
    return this.builder.add(router);
  }


  protected static class Builder {
    private List<YdRouterInfo> routes = new ArrayList<>();


    private YdRouterInfo[] routes() {
      return this.routes.toArray(new YdRouterInfo[this.routes.size()]);
    }

    public Builder add(Method method, String uri, YoAction action) {
      this.routes.add(new YdRouterInfo.Builder().method(method).uri(uri).action(action).build());
      return this;
    }

    public Builder add(String uri, YoAction action) {
      this.routes.add(new YdRouterInfo.Builder().uri(uri).action(action).build());
      return this;
    }

    public Builder add(String uri, Class<? extends YaControl> control) {
      this.routes.add(new YdRouterInfo.Builder().uri(uri).control(control).build());
      return this;
    }

    public Builder add(Class<? extends YaControl> control) {
      this.routes.add(new YdRouterInfo.Builder().control(control).build());
      return this;
    }

    public Builder add(String uri, YoRouter router) {
      this.routes.add(new YdRouterInfo.Builder().uri(uri).router(router).build());
      return this;
    }

    public Builder add(YoRouter router) {
      this.routes.add(new YdRouterInfo.Builder().router(router).build());
      return this;
    }
  }

}
