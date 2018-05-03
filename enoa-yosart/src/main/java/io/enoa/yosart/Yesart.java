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

import io.enoa.repeater.http.Method;
import io.enoa.yosart.kernel.resources.YaControl;
import io.enoa.yosart.kernel.resources.YoAction;
import io.enoa.yosart.kernel.resources.YoRouter;

public interface Yesart {

  Yosart handle(Method method, String uri, YoAction action);

  Yosart handle(String uri, YoAction action);

  Yosart handle(String uri, Class<? extends YaControl> control);

  Yosart handle(String uri, YoRouter router);

  Yosart handle(Class<? extends YaControl> control);

  Yosart handle(YoRouter router);

}
