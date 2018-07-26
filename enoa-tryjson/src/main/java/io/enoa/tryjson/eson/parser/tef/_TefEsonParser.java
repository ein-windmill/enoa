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
package io.enoa.tryjson.eson.parser.tef;

import io.enoa.toolkit.convert.ConvertKit;
import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.toolkit.number.NumberKit;
import io.enoa.tryjson.Tsonfig;
import io.enoa.tryjson.eson.parser.EsonParser;
import io.enoa.tryjson.json.Ja;
import io.enoa.tryjson.json.Jo;
import io.enoa.tryjson.json.Toa;
import io.enoa.tryjson.thr.TryjsonException;

public class _TefEsonParser implements EsonParser {

  private static class Holder {
    private static final _TefEsonParser INSTANCE = new _TefEsonParser();
  }

  public static _TefEsonParser instance() {
    return Holder.INSTANCE;
  }

  @Override
  public Toa parse(String json, Tsonfig config) throws TryjsonException {
    TokenList tl = Tokenizer.instance().tokenize(new JsonReader(json));
    if (tl == null)
      return null;
    Token token = tl.next();
    switch (token.type()) {
      case BEGIN_ARRAY:
        return this.array(tl, config);
      case BEGIN_OBJECT:
        return this.object(tl, config);
      default:
        throw new TryjsonException(EnoaTipKit.message("eo.tip.tryjson.invalid_token"));
    }
  }

  private Jo object(TokenList tl, Tsonfig config) throws TryjsonException {
    Jo jo = Jo.create();
    int expect = TokenType.expect(TokenType.STRING, TokenType.END_OBJECT);
    String key = null;
//    Object val;

    while (tl.hasNext()) {
      Token token = tl.next();
      TokenType type = token.type();
      String tv = token.value();
      switch (type) {
        case BEGIN_OBJECT:
          TokenType.check(type, expect);
          jo.set(key, this.object(tl, config));
          expect = TokenType.expect(TokenType.SEP_COMMA, TokenType.END_OBJECT);
          break;
        case END_OBJECT:
          TokenType.check(type, expect);
          return jo;
        case BEGIN_ARRAY: // json array
          TokenType.check(type, expect);
          jo.set(key, this.array(tl, config));
          expect = TokenType.expect(TokenType.SEP_COMMA, TokenType.END_OBJECT);
          break;
        case NULL:
          TokenType.check(type, expect);
          jo.set(key, null);
          expect = TokenType.expect(TokenType.SEP_COMMA, TokenType.END_OBJECT);
          break;
        case NUMBER:
          TokenType.check(type, expect);
          if (tv.contains(".") || tv.contains("e") || tv.contains("E")) {
            jo.set(key, NumberKit.doubler(tv));
          } else {
            Long longer = NumberKit.longer(tv);
            if (longer > Integer.MAX_VALUE || longer < Integer.MIN_VALUE) {
              jo.set(key, longer);
            } else {
              jo.set(key, longer.intValue());
            }
          }
          expect = TokenType.expect(TokenType.SEP_COMMA, TokenType.END_OBJECT);
          break;
        case BOOLEAN:
          TokenType.check(type, expect);
          jo.set(key, ConvertKit.bool(tv));
          expect = TokenType.expect(TokenType.SEP_COMMA, TokenType.END_OBJECT);
          break;
        case STRING:
          TokenType.check(type, expect);
          Token previous = tl.previous();
          // 字符串作爲鍵時, 期待 SEP_COLON 作爲值, 期待 SEP_COMMA || END_OBJECT
          if (previous.type() == TokenType.SEP_COLON) {
//            val = token.value();
            if (!(config.skipNull() && tv == null)) {
              jo.set(key, config.detector().detect(tv, config));
            }
            expect = TokenType.expect(TokenType.SEP_COMMA, TokenType.END_OBJECT);
          } else {
            key = config.namecase().convert(token.value());
            expect = TokenType.expect(TokenType.SEP_COLON);
          }
          break;
        case SEP_COLON:
          TokenType.check(type, expect);
          expect = TokenType.expect(
            TokenType.NULL,
            TokenType.NUMBER,
            TokenType.BOOLEAN,
            TokenType.STRING,
            TokenType.BEGIN_OBJECT,
            TokenType.BEGIN_ARRAY
          );
          break;
        case SEP_COMMA:
          TokenType.check(type, expect);
          expect = TokenType.expect(TokenType.STRING);
          break;
        case END_DOCUMENT:
          TokenType.check(type, expect);
          return jo;
        default:
          throw new TryjsonException(EnoaTipKit.message("eo.tip.tryjson.unexpected_token")); // Unexpected Token.
      }
    }

    throw new TryjsonException(EnoaTipKit.message("eo.tip.tryjson.invalid_token"));
  }

  private Ja array(TokenList tl, Tsonfig config) throws TryjsonException {
    int expect = TokenType.expect(
      TokenType.BEGIN_ARRAY,
      TokenType.END_ARRAY,
      TokenType.BEGIN_OBJECT,
      TokenType.NULL,
      TokenType.NUMBER,
      TokenType.BOOLEAN,
      TokenType.STRING
    );
    Ja ja = Ja.create();
    while (tl.hasNext()) {
      Token token = tl.next();
      TokenType type = token.type();
      String tv = token.value();
      switch (type) {
        case BEGIN_OBJECT:
          TokenType.check(type, expect);
          ja.add(this.object(tl, config));
          expect = TokenType.expect(TokenType.SEP_COMMA, TokenType.END_ARRAY);
          break;
        case BEGIN_ARRAY:
          TokenType.check(type, expect);
          ja.add(this.array(tl, config));
          expect = TokenType.expect(TokenType.SEP_COMMA, TokenType.END_ARRAY);
          break;
        case END_ARRAY:
          TokenType.check(type, expect);
          return ja;
        case NULL:
          TokenType.check(type, expect);
          ja.add(null);
          expect = TokenType.expect(TokenType.SEP_COMMA, TokenType.END_ARRAY);
          break;
        case NUMBER:
          TokenType.check(type, expect);
          if (tv.contains(".") || tv.contains("e") || tv.contains("E")) {
            ja.add(NumberKit.doubler(tv));
          } else {
            Long longer = NumberKit.longer(tv);
            if (longer > Integer.MAX_VALUE || longer < Integer.MIN_VALUE) {
              ja.add(longer);
            } else {
              ja.add(longer.intValue());
            }
          }
          expect = TokenType.expect(TokenType.SEP_COMMA, TokenType.END_ARRAY);
          break;
        case BOOLEAN:
          TokenType.check(type, expect);
          ja.add(ConvertKit.bool(tv));
          expect = TokenType.expect(TokenType.SEP_COMMA, TokenType.END_ARRAY);
          break;
        case STRING:
          TokenType.check(type, expect);
          ja.add(config.detector().detect(tv, config));
          expect = TokenType.expect(TokenType.SEP_COMMA, TokenType.END_ARRAY);
          break;
        case SEP_COMMA:
          TokenType.check(type, expect);
          expect = TokenType.expect(
            TokenType.STRING,
            TokenType.NULL,
            TokenType.NUMBER,
            TokenType.BOOLEAN,
            TokenType.BEGIN_ARRAY,
            TokenType.BEGIN_OBJECT
          );
          break;
        case END_DOCUMENT:
          TokenType.check(type, expect);
          return ja;
        default:
          throw new TryjsonException(EnoaTipKit.message("eo.tip.tryjson.unexpected_token")); // Unexpected Token.
      }
    }
    throw new TryjsonException(EnoaTipKit.message("eo.tip.tryjson.invalid_token")); //
  }

}
