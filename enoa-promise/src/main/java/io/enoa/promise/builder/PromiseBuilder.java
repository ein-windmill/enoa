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
package io.enoa.promise.builder;

public class PromiseBuilder {

  private PromiseBuilder() {
  }

  public static EPXEnoaPromiseExecutorBuilder executor() {
    return EPXEnoaPromiseExecutorBuilder.instance();
  }

  /**
   * Default promise builder
   * @deprecated use def()
   * @return EPEoPromiseBuilder
   */
  @Deprecated
  public static EPEoPromiseBuilder eo() {
    return def();
  }

  public static EPEoPromiseBuilder def() {
    return new EPEoPromiseBuilder();
  }

  public static EPDonePromiseBuilder done() {
    return new EPDonePromiseBuilder();
  }

  /**
   * 带参数 dong promise
   *
   * @return EPDoneArgPromiseBuilder
   */
  public static <T> EPDoneArgPromiseBuilder<T> donearg() {
    return new EPDoneArgPromiseBuilder<>();
  }

  /**
   * then promise builder
   * @return EPThenPromiseBuilder
   */
  public static EPThenPromiseBuilder then() {
    return new EPThenPromiseBuilder();
  }

}
