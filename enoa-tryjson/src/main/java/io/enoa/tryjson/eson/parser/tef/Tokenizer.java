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

import io.enoa.tryjson.thr.TryjsonException;

public class Tokenizer {

  private static class Holder {
    private static final Tokenizer INSTANCE = new Tokenizer();
  }

  public static Tokenizer instance() {
    return Holder.INSTANCE;
  }


  private boolean blank(JsonReader reader, TokenList tl, char ch) {
    return ch == ' ' ||
      ch == '\r' ||
      ch == '\n' ||
      ch == '\t' ||
      ch == '\b';
  }


  public TokenList tokenize(JsonReader reader) throws TryjsonException {
    TokenList tl = new TokenList();
    Token token;
    do {
      token = this.parse(reader, tl);
      tl.add(token);
    } while (token.type() != TokenType.END_DOCUMENT);
    return tl;
  }

  private Token parse(JsonReader reader, TokenList tl) throws TryjsonException {
    char ch;
    while (true) {
      if (!reader.hasNext())
        return Token.create(TokenType.END_DOCUMENT, null);

      ch = reader.next();
      if (this.blank(reader, tl, ch))
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
        return this.parseNull(reader, tl);
      case 't':
      case 'f':
        return this.parseBool(reader, tl);
      case '"':
        return this.parseString(reader, tl);
      case '-':
        return this.parseNumber(reader, tl);
    }

    if (ch >= '0' && ch <= '9')
      return this.parseNumber(reader, tl);


    throw new TryjsonException(""); // Illegal character
  }

  private Token parseNull(JsonReader reader, TokenList tl) {
    if (reader.next() == 'u' &&
      reader.next() == 'l' &&
      reader.next() == 'l')
      return Token.create(TokenType.NULL, null);
    throw new TryjsonException(""); // Invalid json string
  }

  private Token parseBool(JsonReader reader, TokenList tl) {
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
    throw new TryjsonException(""); // Invalid json string
  }

  private Token parseString(JsonReader reader, TokenList tl) {
    StringBuilder text = new StringBuilder();
    while (true) {
      char ch = reader.next();
      if (ch == '"')
        return Token.create(TokenType.STRING, text.toString());

      if (ch == '\r' || ch == '\n')
        throw new TryjsonException(""); // Invalid character

      if (ch == '\\') {
        ch = reader.next();
        if (!this.escape(ch))
          throw new TryjsonException(""); // Invalid escape character

        text.append('\\');
        text.append(ch);
        if (ch == 'u') {
          for (int i = 0; i < 4; i++) {
            ch = reader.next();
            if (!this.hex(ch))
              throw new TryjsonException(""); // Invalid character
            text.append(ch);
          }
        }
        continue;
      }
      text.append(ch);
    }
  }

  private Token parseNumber(JsonReader reader, TokenList tl) {
    char ch = reader.peek();

    return null;
  }


  private boolean escape(char ch) {
    return ch == '"' ||
      ch == '\\' ||
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
