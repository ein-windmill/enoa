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
package io.enoa.yosart.ext.anost.para;

import io.enoa.toolkit.EoConst;

import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Para {

  /**
   * 對應方法參數位置索引
   *
   * @return int
   */
  int index() default -1;

  /**
   * 參數名
   *
   * @return String
   */
  String value();

  /**
   * 默認值
   *
   * @return String
   */
  String def() default "";

  /**
   * 時間格式化用
   *
   * @return String
   */
  String format() default EoConst.DEF_FORMAT_DATE;

  /**
   * 描述
   *
   * @return String
   */
  String summary() default "";


}
