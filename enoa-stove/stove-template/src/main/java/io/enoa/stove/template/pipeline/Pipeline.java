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
package io.enoa.stove.template.pipeline;

import io.enoa.toolkit.text.TextKit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Pipeline implements Serializable {

  private final String mainly;
  private final String[] pipelines;

  public Pipeline(String mainly, String... pipelines) {
    this.mainly = mainly;
    this.pipelines = pipelines;
  }

  public String mainly() {
    return this.mainly;
  }

  public String[] pipelines() {
    return this.pipelines;
  }


  public static Pipeline parse(String text) {
    return parse(text, "|");
  }

  public static Pipeline parse(String text, String symbol) {
    if (!text.contains(symbol))
      return new Pipeline(text);
    List<String> pipes = new ArrayList<>();

    int fix = 0, eix;
    // 默认管道符 | 属于正则表达式符号, 无法使用 split 分割
    while ((eix = text.indexOf(symbol, fix)) != -1) {
      pipes.add(TextKit.nospace(text.substring(fix, eix)));
      fix = eix + symbol.length();
    }
    pipes.add(text.substring(fix));
    String mainly = pipes.get(0);
    String[] pipelines = new String[pipes.size() - 1];
    for (int i = pipes.size(); i-- > 0; ) {
      if (i == 0)
        break;
      pipelines[i - 1] = pipes.get(i);
    }
    pipes.clear();
    return new Pipeline(mainly, pipelines);
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) return true;
    if (object == null || getClass() != object.getClass()) return false;
    Pipeline pipeline = (Pipeline) object;
    return Objects.equals(mainly, pipeline.mainly) &&
      Arrays.equals(pipelines, pipeline.pipelines);
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(mainly);
    result = 31 * result + Arrays.hashCode(pipelines);
    return result;
  }

  @Override
  public String toString() {
    return "Pipeline{" +
      "mainly='" + mainly + '\'' +
      ", pipelines=" + Arrays.toString(pipelines) +
      '}';
  }
}
