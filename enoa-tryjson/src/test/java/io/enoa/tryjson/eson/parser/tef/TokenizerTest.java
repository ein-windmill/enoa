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

import io.enoa.toolkit.file.FileKit;
import io.enoa.toolkit.path.PathKit;
import io.enoa.toolkit.text.TextKit;
import org.junit.Test;

public class TokenizerTest {


  @Test
  public void testTokenize() {
    int ix = 3;
    String json = FileKit.read(PathKit.debugPath().resolve(TextKit.union("src/test/resources/", ix, ".json"))).string();
    JsonReader reader = new JsonReader(json);
    TokenList tl = Tokenizer.instance().tokenize(reader);
    System.out.println(tl);
  }

}
