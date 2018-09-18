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

import io.enoa.log.EoLogFactory;
import io.enoa.repeater.EoxAccessor;
import io.enoa.repeater.factory.error.EoxErrorRenderFactory;
import io.enoa.repeater.factory.name.EoxNameRuleFactory;
import io.enoa.repeater.factory.server.RepeaterServerFactory;

interface YoRepeater {

  Yosart provider(RepeaterServerFactory provider);

//  Yosart repeater(YasartRepeaterFactory repeater);

  Yosart log(EoLogFactory log);

  Yosart accessor(EoxAccessor accessor);

  Yosart rule(EoxNameRuleFactory rule);

  Yosart capture(EoxErrorRenderFactory capture);

}
