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
package io.enoa.toolkit.text;

import io.enoa.toolkit.eo.tip.EnoaTipKit;

public class PadKit {

  private PadKit() {

  }

  public enum Direction {
    LEFT,
    RIGHT
  }

  private static String fill(Direction direction, String text, String fill) {
    switch (direction) {
      case LEFT:
        return TextKit.union(fill, text);
      case RIGHT:
        return TextKit.union(text, fill);
      default:
        throw new IllegalArgumentException("Unknow direction.");
    }
  }

  /**
   * 追加字符
   *
   * @param direction 方向
   * @param text      原始字符
   * @param fill      填充字符
   * @param number    追加數量
   * @return String
   */
  private static String pad(Direction direction, String text, String fill, int number) {
    if (text == null)
      return null;
    if (fill == null)
      throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.toolkit.str_pad_fill_null"));
    number = number < 0 ? 0 : number;
    if (number == 0)
      return text;
    StringBuilder sb = new StringBuilder();
    for (int i = number; i-- > 0; ) {
      sb.append(fill);
    }
    return fill(direction, text, sb.toString());
  }

  public static String lpad(String text, String fill, int number) {
    return pad(Direction.LEFT, text, fill, number);
  }

  public static String rpad(String text, String fill, int number) {
    return pad(Direction.RIGHT, text, fill, number);
  }

  /**
   * 字符對齊
   *
   * @param direction 方向
   * @param text      原始字符
   * @param fill      填充字符
   * @param len       對齊長度
   * @return String
   */
  private static String align(Direction direction, String text, String fill, int len) {
    if (text == null)
      return null;
    if (fill == null)
      throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.toolkit.str_pad_fill_null"));
    if (text.length() > len)
      return text.substring(0, len);
    int fillNum = len - text.length();
    StringBuilder sb = new StringBuilder();
    for (int i = fillNum; i-- > 0; ) {
      sb.append(fill);
    }
    return fill(direction, text, sb.toString());
  }

  public static String lalign(String text, String fill, int len) {
    return align(Direction.LEFT, text, fill, len);
  }

  public static String ralign(String text, String fill, int len) {
    return align(Direction.RIGHT, text, fill, len);
  }


}
