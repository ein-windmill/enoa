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
package io.enoa.toolkit.ansi;

import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.number.NumberKit;
import io.enoa.toolkit.text.TextKit;
import io.enoa.toolkit.text.TextReader;

import java.util.ArrayList;
import java.util.List;

public class ANSIWrapper {

  private String text;

  ANSIWrapper(String text) {
    this.text = text;
  }

  private List<ANSIBody> bodies() {
    TextReader reader = new TextReader(this.text);
    List<ANSIBody> bodies = new ArrayList<>();
    List<String> formats = new ArrayList<>();
    StringBuilder builder = new StringBuilder();
    //
    int level = 0, levelStartIx = 0;
    boolean entry = false;
    while (reader.hasNext()) {
      char now = reader.next();
      if (now == '\u001B' && reader.next() == '[') {
        reader.back();
        if (!entry) {
          entry = true;
          level += 1;
          if (level > 1)
            levelStartIx = builder.length() - 1;

          if (builder.length() > 0) {
            ANSIBody body = ANSIBody.create(builder.toString());
            bodies.add(body);
            builder.delete(0, builder.length());
          }


          StringBuilder format = new StringBuilder(10);
          boolean right = false;
          int fix = -1;
          for (int i = 0; i < 10; i++) {
            if (!reader.hasNext()) {
              break;
            }
            fix = i;
            char fn = reader.next();
            if (fn == 'm') {
              format.append(fn);
              right = true;
              break;
            }
            format.append(fn);
          }
          if (!right) {
            entry = false;
            level -= 1;
            if (fix == -1)
              continue;
            for (int i = 0; i < fix + 1; i++) {
              reader.back();
            }
          } else {
            formats.add(format.toString());
            format.delete(0, format.length());
          }


          continue;
        }
        char n0 = reader.next();
        char n1 = reader.next();
        char n2 = reader.next();
        if ((n0 == '[' && n1 == 'm') ||
          (n0 == '[' && n1 == '0' && n2 == 'm')) {
          if (n1 == 'm') {
            if (reader.hasNext())
              reader.back();
          }
          level -= 1;
          entry = level != 0;
          String format = formats.get(level);
          String text = builder.toString();
          if (entry) {
//            ANSIBody.create()
            String leveltext = text.substring(levelStartIx);
            ANSIBody body = ANSIBody.create(this.style(format), this.background(format), this.color(format), leveltext);
            bodies.add(body);
            formats.remove(level);
            levelStartIx = 0;
            continue;
          }
          ANSIBody body = ANSIBody.create(this.style(format), this.background(format), this.color(format), text);
          bodies.add(body);
          builder.delete(0, builder.length());
          formats.remove(level);
          continue;
        }
      }


      builder.append(now);
    }
    if (builder.length() > 0) {
      ANSIBody body = ANSIBody.create(builder.toString());
      bodies.add(body);
      builder.delete(0, builder.length());
    }

    return bodies;
  }

  private ANSI.Style style(String format) {
    String[] items = this.items(format);
    for (String item : items) {
      if (item.length() != 1)
        continue;
      if (!NumberKit.isDigit(item))
        continue;
      return ANSI.Style.of(NumberKit.integer(item));
    }
    return null;
  }

  private ANSI.Background background(String format) {
    String[] items = this.items(format);
    for (String item : items) {
      if (item.length() == 1)
        continue;
      if (item.charAt(0) == '3')
        continue;
      if (!NumberKit.isDigit(item))
        continue;
      return ANSI.Background.of(NumberKit.integer(item));
    }
    return null;
  }

  private ANSI.Color color(String format) {
    String[] items = this.items(format);
    for (String item : items) {
      if (item.length() == 1)
        continue;
      if (item.charAt(0) == '4')
        continue;
      if (!NumberKit.isDigit(item))
        continue;
      return ANSI.Color.of(NumberKit.integer(item));
    }
    return null;
  }

  private String[] items(String format) {
    if (TextKit.blanky(format))
      return CollectionKit.emptyArray(String.class);
    format = format.substring(1);
    format = format.substring(0, format.endsWith(";m") ? format.length() - 2 : format.length() - 1);
    return format.split(";");
  }

  public ANSI ansis() {
    return new ANSI(this.bodies());
  }

  public String html() {
    return this.ansis().html();
  }

  public String text() {
    return this.text;
  }

  @Override
  public String toString() {
    return this.text;
  }
}
