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
package io.enoa.repeater;

import io.enoa.repeater.http.Request;
import io.enoa.repeater.http.Response;

/**
 * Repeater 访问器, 所有第三方服务器请求最终进入到 Repeater 进行处理
 */
public interface EoxAccessor {

  /**
   * 访问请求
   *
   * @param request request
   * @return Response
   */
  Response access(Request request);

}
