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

public class TextReader {

  private String text;
  private int len;
  private int position; // 總字符串遊標位置
  private int line; // 當前行
  private int cursor; // 行遊標位置

  public TextReader(String text) {
    this.text = text;
    this.len = text.length();
    this.line = 1;
  }

  public static TextReader with(String text) {
    return new TextReader(text);
  }

  public TextReader reset() {
    this.line = 1;
    this.position = 0;
    this.cursor = 0;
    return this;
  }

  /**
   * 當前字符
   *
   * @return char
   */
  public char peek() {
    if (this.position - 1 >= this.len)
      return (char) -1;
    if (this.position == 0)
      return (char) -1;
    return this.text.charAt(this.position - 1);
  }

  /**
   * 下一個字符
   *
   * @return char
   */
  public char next() {
    if (!this.hasNext())
      return (char) -1;
    char ch = this.text.charAt(this.position);
    this.position += 1;
    this.cursor += 1;
    if (ch == '\n') {
      this.line += 1;
      this.cursor = 0;
    }
    return ch;
  }

  public int position() {
    return this.position;
  }

  public int line() {
    return this.line;
  }

  public int cursor() {
    return this.cursor;
  }

  public String thisLine() {
    int _position = this.position;
    int _cursor = this.cursor;
    int _line = this.line;
    StringBuilder builder = new StringBuilder();
    while (true) {
      if (this.back().peek() == '\n' || this.position == 0)
        break;
    }
    while (this.hasNext()) {
      char ch = this.next();
      if (ch == '\n')
        break;
      builder.append(ch);
    }
    this.position = _position;
    this.cursor = _cursor;
    this.line = _line;
    String lineText = builder.toString();
    builder.delete(0, builder.length());
    return lineText;
  }

  public TextReader back() {
    if (this.position == 0)
      return this;

    char peek = this.peek();

    if (peek != '\n') {
      this.position -= 1;
      this.cursor -= 1;
      return this;
    }
    this.position -= 1;
    this.line -=1;

    int _position = this.position;
    int _line = this.line;

    int distance = 0;
    while (true) {
      char ch = this.back().peek();
      if (ch == '\n' || ch == (char) -1) {
        break;
      }
      distance += 1;
    }
    this.position = _position;
    this.line = _line;
    this.cursor = distance + 1;
    return this;
  }

  public boolean hasNext() {
    return this.position < this.len;
  }

  public String text() {
    return this.text;
  }

  @Override
  public String toString() {
    return TextKit.union("LENGTH: ", this.len, ',',
      " POSITION: ", this.position, ',',
      " LINE: ", this.line, ',',
      " CURSOR: ", this.cursor, ',',
      " TEXT => ", this.text);
  }
}
