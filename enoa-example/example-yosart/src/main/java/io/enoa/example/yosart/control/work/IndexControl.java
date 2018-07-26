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
package io.enoa.example.yosart.control.work;

import io.enoa.repeater.http.Method;
import io.enoa.yosart.kernel.anno.Action;
import io.enoa.yosart.kernel.resources.YoControl;

public class IndexControl extends YoControl<IndexControl> {

  public void index() {
    super.render("index");
  }

  @Action(method = Method.GET, value = "/action_test")
  public void actionTest() {
    super.render("action test");
  }

}
