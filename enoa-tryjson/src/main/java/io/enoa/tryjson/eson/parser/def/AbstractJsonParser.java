package io.enoa.tryjson.eson.parser.def;

import io.enoa.tryjson.json.Toa;
import io.enoa.tryjson.thr.TryjsonException;

abstract class AbstractJsonParser<T extends Toa> implements IJsonParser<T> {

  protected boolean skip(char ch) {
    return ch == ' ' ||
      ch == '\r' ||
      ch == '\n' ||
      ch == '\t' ||
      ch == '\b' ||
      ch == '\f';
  }


  protected Block extraBlock(char symbol, int ix, String json) {
    Character end = null;
    if (symbol == '{')
      end = '}';
    if (symbol == '[')
      end = ']';
    if (end == null)
      throw new TryjsonException(""); // 提取 json 块错误

    int leftNum = 1;
    ix += 1;

    StringBuilder _block = new StringBuilder();
    _block.append(symbol);

    int len = json.length();
    boolean stringBlock = false;
    for (; ix < len; ix++) {
      char ch = json.charAt(ix);
      if (ch == '"') {
        if (ix > 0) {
          char prev = json.charAt(ix - 1);
          if (prev == '\\')
            continue;
        }
        stringBlock = !stringBlock;
        if (stringBlock)
          continue;
      }
      if (ch == symbol)
        leftNum += 1;
      if (ch == end)
        leftNum -= 1;
      _block.append(ch);
      if (leftNum == 0)
        return new Block(_block.toString(), ix);
    }
    throw new TryjsonException(""); // json 格式错误, 未能正确关闭 symbol
  }

  protected class Block {
    private String json;
    private Integer end;

    private Block(String json, Integer end) {
      this.json = json;
      this.end = end;
    }

    public String json() {
      return this.json;
    }

    public Integer end() {
      return this.end;
    }

  }
}
