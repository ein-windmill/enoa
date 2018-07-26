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


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class TokenList implements Iterable<Token> {

  private String json;// todo need this field?
  private List<Token> tokens = new ArrayList<>();

  private int position;

  public TokenList add(Token token) {
    this.tokens.add(token);
    return this;
  }

  public Token peek() {
    return this.position < this.tokens.size() ? this.tokens.get(this.position) : null;
  }

  public Token previous() {
    return this.position - 1 < 0 ? null : this.tokens.get(this.position - 2);
  }

  public Token next() {
    Token token = this.position >= this.tokens.size() ? null : this.tokens.get(this.position);
    this.position += 1;
    return token;
  }

  public boolean hasNext() {
    return this.position < this.tokens.size();
  }

  public TokenList json(String json) {
    this.json = json;
    return this;
  }

  @Override
  public Iterator<Token> iterator() {
    return this.tokens.iterator();
  }

  @Override
  public String toString() {
    return this.tokens.toString();
  }
}
