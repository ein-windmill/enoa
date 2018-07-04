package io.enoa.tryjson.eson.parser.def;

import io.enoa.tryjson.json.Toa;

abstract class AbstractJsonParser<T extends Toa> implements IJsonParser<T> {

  protected boolean skip(char ch) {
    return ch == ' ' ||
      ch == '\r' ||
      ch == '\n' ||
      ch == '\t' ||
      ch == '\b' ||
      ch == '\f';
  }

}
