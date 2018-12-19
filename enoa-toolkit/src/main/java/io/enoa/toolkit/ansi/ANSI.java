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

import io.enoa.toolkit.mark.IMarkIx;
import io.enoa.toolkit.text.TextReader;
import io.enoa.toolkit.text.LineBreak;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ANSI {


  public static ANSI up() {
    return new ANSI();
  }

  public static ANSI wrap(String text) {
    return ANSIWrapper.instance().ansis(text);
  }

  public static String down(String text) {
    StringBuilder builder = new StringBuilder(text.length());
    TextReader reader = new TextReader(text);
    boolean entry = false;
    while (reader.hasNext()) {
      char now = reader.next();

      if (now == '\u001B') {
        char next = reader.next();
        if (next == '[') {
          entry = true;
          continue;
        }
        reader.back();
        continue;
      }

//      if (now == '[') {
//        if (reader.next() == '0' && reader.next() == 'm')
//          continue;
//        reader.back().back();
//      }

      if (entry) {
        if (now == 'm') {
          entry = Boolean.FALSE;
          continue;
        }
        continue;
      }

      builder.append(now);
    }
    return builder.toString();
  }



  /*
  Style                    Font color       Background color
  0 - Reset                30 - Black       40 - Black
  1 - FG Bright (Light)    31 - Red         41 - Red
  2 - Unknown              32 - Green       42 - Green
  3 - Unknown              33 - Yellow      43 - Yellow
  4 - Underline            34 - Blue        44 - Blue
  5 - BG Bright (Blink)    35 - Magenta     45 - Magenta
  6 - Unknown              36 - Cyan        46 - Cyan
  7 - Reverse              37 - White       47 - White
  8 - Conceal


  \033[Style;Font Color;Background colorm
   */

  public enum Style implements IMarkIx {
    NORMAL(0),

    BOLD(1),

    UNDERLINE(4),

    GRAY(5),

    ANTI(7),
    //
    ;
    private final int ix;

    Style(int ix) {
      this.ix = ix;
    }

    @Override
    public int ix() {
      return this.ix;
    }

    public static Style of(Integer ix) {
      if (ix == null)
        return null;
      for (Style style : Style.values()) {
        if (style.ix == ix)
          return style;
      }
      return null;
    }
  }

  public enum Background implements IMarkIx {

    BLACK(40),

    RED(41),

    GREEN(42),

    YELLOW(43),

    BLUE(44),

    MAGENTA(45),

    CYAN(46),

    WHITE(47),

    //
    ;

    private final int ix;

    Background(int ix) {
      this.ix = ix;
    }

    @Override
    public int ix() {
      return this.ix;
    }

    public static Background of(Integer ix) {
      if (ix == null)
        return null;
      for (Background background : Background.values()) {
        if (background.ix == ix)
          return background;
      }
      return null;
    }
  }

  public enum Color implements IMarkIx {

    BLACK(30),

    RED(31),

    GREEN(32),

    YELLOW(33),

    BLUE(34),

    MAGENTA(35),

    CYAN(36),

    WHITE(37),
    //
    ;

    private final int ix;

    Color(int ix) {
      this.ix = ix;
    }

    @Override
    public int ix() {
      return this.ix;
    }

    public static Color of(Integer ix) {
      if (ix == null)
        return null;
      for (Color color : Color.values()) {
        if (color.ix == ix)
          return color;
      }
      return null;
    }
  }

  private List<ANSIBody> bodies;
  private Map<Background, String> bgmap;
  private Map<Color, String> colormap;
  private boolean br;
  private String cssname;

  public ANSI() {
    this(new ArrayList<>());
  }

  ANSI(List<ANSIBody> bodies) {
    this.bodies = bodies;
  }

  public String string() {
    return ANSIBody.string(this.bodies);
  }

  public String html() {
    return ANSIBody.html(this.bodies, this.bgmap, this.colormap, this.cssname, this.br);
  }

  public ANSI br() {
    return this.br(Boolean.TRUE);
  }

  public ANSI br(boolean br) {
    this.br = br;
    return this;
  }

  public ANSI cssname(String cssname) {
    this.cssname = cssname;
    return this;
  }

  public ANSI color(Background background, String code) {
    if (this.bgmap == null)
      this.bgmap = new HashMap<>();
    this.bgmap.put(background, code);
    return this;
  }

  public ANSI color(Color color, String code) {
    if (this.colormap == null)
      this.colormap = new HashMap<>();
    this.colormap.put(color, code);
    return this;
  }

  public ANSI append(String text) {
    return this.append(text, null, null);
  }

  public ANSI append(String text, Style style) {
    return this.append(text, style, null, null);
  }

  public ANSI append(String text, Color color) {
    return this.append(text, color, null);
  }

  public ANSI append(String text, Background background) {
    return this.append(text, null, background);
  }

  public ANSI append(String text, Color color, Background background) {
    return this.append(text, null, color, background);
  }

  public ANSI append(String text, Style style, Color color, Background background) {
    this.bodies.add(ANSIBody.create(style, background, color, text));
    return this;
  }

  public ANSI text(String text, Style style, Color color, Background background) {
    return this.append(text, style, color, background);
  }

  public ANSI text(String text, Color color, Background background) {
    return this.append(text, color, background);
  }

  public ANSI text(String text, Color color) {
    return this.append(text, color);
  }

  public ANSI text(String text, Background background) {
    return this.append(text, background);
  }

  public ANSI text(String text) {
    return this.append(text);
  }

  public ANSI newline() {
    return this.newline("\n");
  }

  public ANSI newline(String newline) {
    return this.append(newline);
  }

  public ANSI newline(LineBreak newline) {
    return this.newline(newline.val());
  }

  public ANSI cr() {
    return this.newline(LineBreak.CR);
  }

  public ANSI lf() {
    return this.newline(LineBreak.LF);
  }

  public ANSI crlf() {
    return this.newline(LineBreak.CRLF);
  }

  public ANSI black(String text, Background background) {
    return this.append(text, Color.BLACK, background);
  }

  public ANSI red(String text, Background background) {
    return this.append(text, Color.RED, background);
  }

  public ANSI green(String text, Background background) {
    return this.append(text, Color.GREEN, background);
  }

  public ANSI yellow(String text, Background background) {
    return this.append(text, Color.YELLOW, background);
  }

  public ANSI blue(String text, Background background) {
    return this.append(text, Color.BLUE, background);
  }

  public ANSI magenta(String text, Background background) {
    return this.append(text, Color.MAGENTA, background);
  }

  public ANSI cyan(String text, Background background) {
    return this.append(text, Color.CYAN, background);
  }

  public ANSI white(String text, Background background) {
    return this.append(text, Color.WHITE, background);
  }

  public ANSI black(String text) {
    return this.append(text, Color.BLACK, null);
  }

  public ANSI red(String text) {
    return this.append(text, Color.RED, null);
  }

  public ANSI green(String text) {
    return this.append(text, Color.GREEN, null);
  }

  public ANSI yellow(String text) {
    return this.append(text, Color.YELLOW, null);
  }

  public ANSI blue(String text) {
    return this.append(text, Color.BLUE, null);
  }

  public ANSI magenta(String text) {
    return this.append(text, Color.MAGENTA, null);
  }

  public ANSI cyan(String text) {
    return this.append(text, Color.CYAN, null);
  }

  public ANSI white(String text) {
    return this.append(text, Color.WHITE, null);
  }

  public ANSI background(String text, Background background) {
    return this.append(text, null, background);
  }

  public ANSI bold(String text, Color color, Background background) {
    return this.append(text, Style.BOLD, color, background);
  }

  public ANSI bold(String text, Color color) {
    return this.append(text, Style.BOLD, color, null);
  }

  public ANSI bold(String text, Background background) {
    return this.append(text, Style.BOLD, null, background);
  }

  public ANSI bold(String text) {
    return this.append(text, Style.BOLD, null, null);
  }

  public ANSI underline(String text, Color color, Background background) {
    return this.append(text, Style.UNDERLINE, color, background);
  }

  public ANSI underline(String text, Color color) {
    return this.append(text, Style.UNDERLINE, color, null);
  }

  public ANSI underline(String text, Background background) {
    return this.append(text, Style.UNDERLINE, null, background);
  }

  public ANSI underline(String text) {
    return this.append(text, null, null, null);
  }

  public ANSI gray(String text, Color color, Background background) {
    return this.append(text, Style.GRAY, color, background);
  }

  public ANSI gray(String text, Color color) {
    return this.append(text, Style.GRAY, color, null);
  }

  public ANSI gray(String text, Background background) {
    return this.append(text, Style.GRAY, null, background);
  }

  public ANSI gray(String text) {
    return this.append(text, Style.GRAY, null, null);
  }

  public ANSI anti(String text, Color color, Background background) {
    return this.append(text, Style.ANTI, color, background);
  }

  public ANSI anti(String text, Color color) {
    return this.append(text, Style.ANTI, color, null);
  }

  public ANSI anti(String text, Background background) {
    return this.append(text, Style.ANTI, null, background);
  }

  public ANSI anti(String text) {
    return this.append(text, Style.ANTI, null, null);
  }


  @Override
  public String toString() {
    return this.string();
  }
}
