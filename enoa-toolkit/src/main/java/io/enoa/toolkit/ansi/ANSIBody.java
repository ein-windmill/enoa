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

import io.enoa.toolkit.is.Is;
import io.enoa.toolkit.text.TextKit;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ANSIBody implements Serializable {

  private final ANSI.Style style;
  private final ANSI.Background background;
  private final ANSI.Color color;
  private final String text;

  private static class ColorHolder {
    private static Map<ANSI.Background, String> bgmap = new HashMap<>();
    private static Map<ANSI.Color, String> colormap = new HashMap<>();

    static {

      bgmap.put(ANSI.Background.BLACK, "rgb(45,45,45)");
      bgmap.put(ANSI.Background.RED, "rgb(244,67,54)");
      bgmap.put(ANSI.Background.GREEN, "rgb(76,175,80)");
      bgmap.put(ANSI.Background.YELLOW, "rgb(187,187,0)");
      bgmap.put(ANSI.Background.BLUE, "rgb(33,150,243)");
      bgmap.put(ANSI.Background.MAGENTA, "rgb(194,24,91)");
      bgmap.put(ANSI.Background.CYAN, "rgb(139,195,74)");
      bgmap.put(ANSI.Background.WHITE, "rgb(255,255,255)");

      colormap.put(ANSI.Color.BLACK, "rgb(45,45,45)");
      colormap.put(ANSI.Color.RED, "rgb(244,67,54)");
      colormap.put(ANSI.Color.GREEN, "rgb(76,175,80)");
      colormap.put(ANSI.Color.YELLOW, "rgb(187,187,0)");
      colormap.put(ANSI.Color.BLUE, "rgb(33,150,243)");
      colormap.put(ANSI.Color.MAGENTA, "rgb(194,24,91)");
      colormap.put(ANSI.Color.CYAN, "rgb(139,195,74)");
      colormap.put(ANSI.Color.WHITE, "rgb(255,255,255)");
    }
  }


  static String string(List<ANSIBody> bodies) {
    if (Is.empty(bodies))
      return null;

    StringBuilder builder = new StringBuilder();
    bodies.forEach(body -> {
      if (body.style == null && body.background == null && body.color == null) {
        builder.append(body.text);
        return;
      }

      StringBuilder _sr = new StringBuilder(body.text.length() + 20); // 15    \u001B[0;30;41m{text}\u001B[0m
      _sr.append("\u001B[");
      if (body.style != null) {
        _sr.append(body.style.ix()).append(';');
      }
      if (body.color != null) {
        _sr.append(body.color.ix()).append(';');
      }
      if (body.background != null) {
        _sr.append(body.background.ix()).append(';');
      }
      _sr.delete(_sr.length() - 1, _sr.length());
      _sr.append('m')
        .append(body.text)
        .append("\u001B[0m");
      builder.append(_sr);
      _sr.delete(0, _sr.length());
    });
    return builder.toString();
  }

  static String html(List<ANSIBody> bodies, Map<ANSI.Background, String> bgmap, Map<ANSI.Color, String> colormap,
                     String cssname, boolean br) {
    if (Is.empty(bodies))
      return null;
    StringBuilder builder = new StringBuilder();
    bodies.forEach(body -> {
      ANSI.Style style = body.style;
      ANSI.Background background = body.background;
      ANSI.Color color = body.color;
      String text = body.text;

      if (style == null && background == null && color == null) {
        builder.append(text);
        return;
      }

      StringBuilder _sr = new StringBuilder();

      _sr.append("<span");
      if (Is.truthy(cssname)) {
        _sr.append(" class=\"");
        _sr.append(cssname).append(" ");
        if (background != null)
          _sr.append(cssname(cssname, background)).append(" ");
        if (color != null)
          _sr.append(cssname(cssname, color)).append(" ");
        _sr.append("\"");
      }

      _sr.append(" style=\"");
      if (background != null) {
        boolean defbg = true;
        if (style != null) {
          if (style == ANSI.Style.GRAY ||
            style == ANSI.Style.ANTI) {
            defbg = false;
          }
        }
        if (defbg) {
          _sr.append("background: ")
            .append(bgcolor(bgmap, background))
            .append(";");
        }
      }
      if (color != null) {
        boolean defcl = true;
        if (style != null) {
          if (style == ANSI.Style.ANTI) {
            defcl = false;
          }
        }
        if (defcl) {
          _sr.append("color: ")
            .append(ftcolor(colormap, color))
            .append(";");
        }
      }
      if (style != null) {
        switch (style) {
          case NORMAL:
            _sr.append("font-style: normal");
            break;
          case BOLD:
            _sr.append("font-weight: bold;");
            break;
          case GRAY:
            _sr.append("background: #ccc;");
            break;
          case UNDERLINE:
            _sr.append("text-decoration: underline;");
            break;
          case ANTI:
            ANSI.Color acolor = color == null ? ANSI.Color.WHITE : color;
            ANSI.Background acbg = background == null ? ANSI.Background.BLACK : background;
            _sr.append("background: ")
              .append(ftcolor(colormap, acolor))
              .append(";")
              .append("color: ")
              .append(bgcolor(bgmap, acbg))
              .append(";");
            break;
        }
      }

      _sr.append("\">")
        .append(body.text)
        .append("</span>");
      builder.append(_sr);
      _sr.delete(0, _sr.length());
    });
    if (!br)
      return builder.toString();

    String html = builder.toString();
    return html.replace("\n", "<br/>");
  }

  private static String cssname(String basename, ANSI.Background background) {
    return TextKit.union(basename == null ? "enoa-ansi-bg" : basename, "-", background.name().toLowerCase());
  }

  private static String cssname(String basename, ANSI.Color color) {
    return TextKit.union(basename == null ? "enoa-ansi-ft" : basename, "-", color.name().toLowerCase());
  }

  private static String bgcolor(Map<ANSI.Background, String> map, ANSI.Background background) {
    return map == null ?
      ColorHolder.bgmap.get(background) :
      (map.get(background) == null ?
        ColorHolder.bgmap.get(background) :
        map.get(background));
  }

  private static String ftcolor(Map<ANSI.Color, String> map, ANSI.Color color) {
    return map == null ?
      ColorHolder.colormap.get(color) :
      (map.get(color) == null ?
        ColorHolder.colormap.get(color) :
        map.get(color));
  }

  static ANSIBody create(String text) {
    return create(null, null, null, text);
  }

  static ANSIBody create(ANSI.Style style, ANSI.Background background, ANSI.Color color, String text) {
    return new ANSIBody(style, background, color, text);
  }

  private ANSIBody(ANSI.Style style, ANSI.Background background, ANSI.Color color, String text) {
    this.style = style;
    this.background = background;
    this.color = color;
    this.text = text;
  }

  @Override
  public String toString() {
    return "ANSIBody{" +
      "background=" + background +
      ", color=" + color +
      ", text='" + text + '\'' +
      '}';
  }

}
