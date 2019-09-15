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
package io.enoa.yosart.kit.tip;

import io.enoa.log.Log;
import io.enoa.toolkit.is.Is;
import io.enoa.toolkit.text.TextKit;
import io.enoa.yosart.Oysart;

import java.util.stream.Stream;

public class OysartTip {

  private OysartTip() {
  }

  private static final String MARK = "#";

  private static String format(String text, Object... args) {
    if (Is.not().truthy(text))
      return text;

    Object[] fags = Stream.of(args).map(o -> o == null ? null : o.toString()).toArray(Object[]::new);
//    String ret = MessageFormat.format(text, fags);
    String ret = TextKit.format(text, fags);
    if (ret.charAt(0) == '\n') {
      ret = TextKit.union("\n", MARK, " ", ret.substring(1));
    } else {
      ret = TextKit.union(MARK, " ", ret);
    }
    return ret;
  }

  public static Object paras(Object... para) {
    return para;
  }

  private static void print(String text) {
    if (Oysart.config().infoUseLog()) {
      Log.debug(text);
      return;
    }
    System.out.println(text);
  }

  private static String msgStr(String text, int length, String placeholder, Object[] args) {
    text = format(text, args);
    if (length == 0)
      return text;
    if (text.length() >= length)
      return text;

    int tlen = text.startsWith("\n") ? text.length() - 1 : text.length();
    int gap = length - tlen;
    StringBuilder sb = new StringBuilder();
    for (int i = gap; i-- > 0; ) {
      sb.append(placeholder);
    }
    text = TextKit.union(text, " ", sb.toString());
    return text;
  }

  public static void message(String text, Object... args) {
    if (!Oysart.config().info())
      return;
    print(msgStr(text, 0, null, args));
  }

  public static void message(String text, int length, String placeholder, Object[] args) {
    if (!Oysart.config().info())
      return;

    text = msgStr(text, length, placeholder, args);
    print(text);
  }

  public static void message(String text, int length, Object[] args) {
    if (!Oysart.config().info())
      return;
    message(text, length, "=", args);
  }

}
