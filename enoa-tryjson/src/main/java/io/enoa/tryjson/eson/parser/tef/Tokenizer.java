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
package io.enoa.tryjson.eson.parser.tef;

import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.tryjson.thr.TryjsonException;

class Tokenizer {

  private static class Holder {
    private static final Tokenizer INSTANCE = new Tokenizer();
  }

  static Tokenizer instance() {
    return Holder.INSTANCE;
  }

  private Tokenizer() {

  }

  public TokenList tokenize(JsonReader reader) throws TryjsonException {
    TokenList tl = new TokenList();
    Token token;
    do {
      token = this.parse(reader);
      tl.add(token);
    } while (token.type() != TokenType.END_DOCUMENT);
    tl.json(reader.json());
    return tl;
  }

  private Token parse(JsonReader reader) throws TryjsonException {
    char ch;
    while (true) {
      if (!reader.hasNext())
        return Token.create(TokenType.END_DOCUMENT, null);

      ch = reader.next();
      if (this.blank(ch))
        continue;
      break;
    }

    switch (ch) {
      case '{':
        return Token.create(TokenType.BEGIN_OBJECT, ch);
      case '}':
        return Token.create(TokenType.END_OBJECT, ch);
      case '[':
        return Token.create(TokenType.BEGIN_ARRAY, ch);
      case ']':
        return Token.create(TokenType.END_ARRAY, ch);
      case ',':
        return Token.create(TokenType.SEP_COMMA, ch);
      case ':':
        return Token.create(TokenType.SEP_COLON, ch);
      case 'n':
        return this.parseNull(reader);
      case 't':
      case 'f':
        return this.parseBool(reader);
      case '"':
        return this.parseString(reader);
      case '-':
        return this.parseNumber(reader);
    }

    if (Character.isDigit(ch))
      return this.parseNumber(reader);


    throw new TryjsonException(EnoaTipKit.message("eo.tip.tryjson.illegal_char_parse", reader.line(), reader.cursor(), ch)); // Illegal character
  }

  private Token parseNull(JsonReader reader) {
    if (reader.next() == 'u' &&
      reader.next() == 'l' &&
      reader.next() == 'l')
      return Token.create(TokenType.NULL, null);
    throw new TryjsonException(EnoaTipKit.message("eo.tip.tryjson.parse_char_null", reader.line(), reader.cursor(), reader.peek())); // Invalid json string
  }

  private Token parseBool(JsonReader reader) {
    char peek = reader.peek();
    switch (peek) {
      case 't':
        if (reader.next() == 'r' &&
          reader.next() == 'u' &&
          reader.next() == 'e')
          return Token.create(TokenType.BOOLEAN, "true");
        break;
      case 'f':
        if (reader.next() == 'a' &&
          reader.next() == 'l' &&
          reader.next() == 's' &&
          reader.next() == 'e')
          return Token.create(TokenType.BOOLEAN, "false");
        break;
    }
    throw new TryjsonException(EnoaTipKit.message("eo.tip.tryjson.parse_char_bool", reader.line(), reader.cursor(), reader.peek())); // Invalid json string
  }

  private Token parseString(JsonReader reader) {
    StringBuilder text = new StringBuilder();
    while (true) {
      char ch = reader.next();
      if (ch == (char) -1)
        throw new TryjsonException(EnoaTipKit.message("eo.tip.tryjson.json_an_early_closure", reader.line(), reader.cursor(), ch)); // Invalid character
      if (ch == '"')
        return Token.create(TokenType.STRING, text.toString());

      if (ch == '\r' || ch == '\n')
        throw new TryjsonException(EnoaTipKit.message("eo.tip.tryjson.parse_char_string_newline", reader.line(), reader.cursor(), ch)); // Invalid character

      if (ch == '\\') {
        ch = reader.next();
        if (!this.escape(ch))
          throw new TryjsonException(EnoaTipKit.message("eo.tip.tryjson.parse_char_string_escape", reader.line(), reader.cursor(), ch)); // Invalid escape character

        text.append('\\');
        text.append(ch);
        if (ch == 'u') {
          for (int i = 0; i < 4; i++) {
            ch = reader.next();
            if (!this.hex(ch))
              throw new TryjsonException(EnoaTipKit.message("eo.tip.tryjson.parse_char_string_unicode", reader.line(), reader.cursor(), ch)); // Invalid character
            text.append(ch);
          }
        }
        continue;
      }
      text.append(ch);
    }
  }

  private Token parseNumber(JsonReader reader) {
    StringBuilder text = new StringBuilder();
    char ch = reader.peek();
    boolean negative = ch == '-';
    if (negative) {
      text.append(ch);
      ch = reader.next();
    }

    // 小數 0-1 區間小數
    if (ch == '0') {
      text.append(ch);
      text.append(this.parseNumc(reader));
      return Token.create(TokenType.NUMBER, text.toString());
    }

    // 大於 1 的小數或整數
    if (Character.isDigit(ch)) {
      do {
        text.append(ch);
        ch = reader.next();
      } while (Character.isDigit(ch));
      if (ch != (char) -1) {
        reader.back();
        text.append(this.parseNumc(reader));
      }
      return Token.create(TokenType.NUMBER, text.toString());
    }
    throw new TryjsonException(EnoaTipKit.message("eo.tip.tryjson.parse_char_number_fail", reader.line(), reader.cursor(), ch)); // invalid number
  }


  private String parseNumc(JsonReader reader) {
    StringBuilder text = new StringBuilder();
    char ch = reader.next();
    if (ch == '.') {
      text.append(ch);
      ch = reader.next();
      // 校驗是否數字
      if (!Character.isDigit(ch))
        throw new TryjsonException(EnoaTipKit.message("eo.tip.tryjson.parse_char_number_float", reader.line(), reader.cursor(), ch)); // invalid float
      do {
        text.append(ch);
        ch = reader.next();
      } while (Character.isDigit(ch));
    }

    // 校驗是否科學計數法
    if (this.scn(ch)) {
      ch = reader.peek();
      text.append(ch);
      text.append(this.parseScn(reader));
    } else {
      reader.back();
    }
    return text.toString();
  }

  /**
   * 科學計數法數字讀取
   *
   * @param reader JsonReader
   * @return String
   */
  private String parseScn(JsonReader reader) {
    StringBuilder text = new StringBuilder();
    char ch = reader.next();
    // 鑑定 e 之後首個字符是否 + 或 -
    if (ch == '+' || ch == '-') {
      text.append(ch);
      ch = reader.next();
    }
    // 無論是否有 + 或 - 判斷 後面字符是否數字
    if (!Character.isDigit(ch))
      throw new TryjsonException(EnoaTipKit.message("eo.tip.tryjson.parse_char_number_scientific_notation", reader.line(), reader.cursor(), ch)); // invalid scn number

    text.append(ch);
    ch = reader.next();
    // 如果是數字, 加入到字符中
    do {
      text.append(ch);
      ch = reader.next();
    } while (Character.isDigit(ch));
    // 如果 char 並非數字, 表明數字讀取完畢, 則回退一位, 若 json 字符讀取完畢則不回退
    if (ch != (char) -1)
      reader.back();

    return text.toString();
  }

  private boolean scn(char ch) {
    return ch == 'e' || ch == 'E';
  }

  private boolean blank(char ch) {
    return ch == ' ' ||
      ch == '\r' ||
      ch == '\n' ||
      ch == '\t' ||
      ch == '\b';
  }

  private boolean escape(char ch) {
    return ch == '"' ||
      ch == '\\' ||
      ch == '/' ||
      ch == 'u' ||
      ch == 'r' ||
      ch == 'n' ||
      ch == 'b' ||
      ch == 't' ||
      ch == 'f';
  }

  private boolean hex(char ch) {
    return (ch >= '0' && ch <= '9') ||
      ('a' <= ch && ch <= 'f') ||
      ('A' <= ch && ch <= 'F');
  }

}
