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
import io.enoa.toolkit.text.TextKit;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ANSIBody implements Serializable {

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
      bgmap.put(ANSI.Background.YELLOW, "rgb(255,235,59)");
      bgmap.put(ANSI.Background.BLUE, "rgb(33,150,243)");
      bgmap.put(ANSI.Background.MAGENTA, "rgb(194,24,91)");
      bgmap.put(ANSI.Background.CYAN, "rgb(139,195,74)");
      bgmap.put(ANSI.Background.WHITE, "rgb(255,255,255)");

      colormap.put(ANSI.Color.BLACK, "rgb(45,45,45)");
      colormap.put(ANSI.Color.RED, "rgb(244,67,54)");
      colormap.put(ANSI.Color.GREEN, "rgb(76,175,80)");
      colormap.put(ANSI.Color.YELLOW, "rgb(255,235,59)");
      colormap.put(ANSI.Color.BLUE, "rgb(33,150,243)");
      colormap.put(ANSI.Color.MAGENTA, "rgb(194,24,91)");
      colormap.put(ANSI.Color.CYAN, "rgb(139,195,74)");
      colormap.put(ANSI.Color.WHITE, "rgb(255,255,255)");
    }
  }


  static String string(List<ANSIBody> bodies) {
    if (CollectionKit.isEmpty(bodies))
      return null;

    StringBuilder builder = new StringBuilder();
    bodies.forEach(body -> {
      if (body.background == null && body.color == null) {
        builder.append(body.text);
        return;
      }

      StringBuilder _sr = new StringBuilder(body.text);
      if (body.background != null) {
        if (body.color == null) {
          _sr.insert(0, TextKit.union("\u001B[", body.background.ix(), "m"))
            .append("\u001B[0m");
          builder.append(_sr);
          _sr.delete(0, _sr.length() - 1);
          return;
        }
        _sr.insert(0, TextKit.union("\u001B[", body.background.ix(), ";", body.color.ix(), "m"))
          .append("\u001B[0m");
        builder.append(_sr);
        _sr.delete(0, _sr.length() - 1);
        return;
      }

      _sr.insert(0, TextKit.union("\u001B[", body.color.ix(), "m"))
        .append("\u001B[0m");
      builder.append(_sr);
      _sr.delete(0, _sr.length() - 1);
    });
    return builder.toString();
  }

  private static String cssname(String basename, ANSI.Background background) {
    return TextKit.union(basename == null ? "enoa-ansi-bg-" : basename, "-", background.name().toLowerCase());
  }

  public static String cssname(String basename, ANSI.Color color) {
    return TextKit.union(basename == null ? "enoa-ansi-ft-" : basename, "-", color.name().toLowerCase());
  }

  static String html(List<ANSIBody> bodies, Map<ANSI.Background, String> bgmap, Map<ANSI.Color, String> colormap,
                     String cssname, boolean br) {
    if (CollectionKit.isEmpty(bodies))
      return null;
    StringBuilder builder = new StringBuilder();
    bodies.forEach(body -> {
      ANSI.Background background = body.background;
      ANSI.Color color = body.color;
      String text = body.text;

      if (background == null && color == null) {
        builder.append(text);
        return;
      }

      StringBuilder _sr = new StringBuilder(text);
      if (background != null) {
        if (color == null) {
          _sr.insert(0, TextKit.union("<span style=\"background: ",
            bgcolor(bgmap, background),
            "\" class=\"",
            cssname(cssname, background),
            "\">"
          )).append("</span>");
          builder.append(_sr);
          _sr.delete(0, _sr.length() - 1);
          return;
        }
        _sr.insert(0, TextKit.union("<span style=\"background: ",
          bgcolor(bgmap, background),
          "; color: ",
          ftcolor(colormap, color),
          "\" class=\"",
          cssname(cssname, background), " ", cssname(cssname, color),
          "\">"
        )).append("</span>");
        builder.append(_sr);
        _sr.delete(0, _sr.length() - 1);
        return;
      }

      _sr.insert(0, TextKit.union("<span style=\"color: ",
        ftcolor(colormap, color),
        "\" class=\"",
        cssname(cssname, color),
        "\">"
      )).append("</span>");
      builder.append(_sr);
      _sr.delete(0, _sr.length() - 1);
    });
    if (!br)
      return builder.toString();

    String html = builder.toString();
    return html.replace("\n", "<br/>");
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


  static ANSIBody create(ANSI.Background background, ANSI.Color color, String text) {
    return new ANSIBody(background, color, text);
  }

  private ANSIBody(ANSI.Background background, ANSI.Color color, String text) {
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
